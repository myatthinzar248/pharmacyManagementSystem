package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import config.ClsDBConnection;
import model.OrderModel;

public class OrderController {
	public static Connection con = null;
	static {
		ClsDBConnection cls = new ClsDBConnection();
		try {
			con = cls.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public String generateNextOrderId() throws SQLException {
		String sql = "SELECT MAX(order_id) AS max_id FROM pharmacy.order";
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				String maxId = rs.getString("max_id");
				if (maxId != null && maxId.startsWith("OD-")) {
					int num = Integer.parseInt(maxId.substring(3));
					return String.format("OD-%08d", num + 1);
				}
			}
		}
		return "OD-00000001"; // Start from this if no entries exist
	}

	public int insert(OrderModel dain) {
		int result = 0;
		String sql = "insert into pharmacy.order (order_id,customer_id,order_date,total,staff_id) values(?,?,?,?,?)";
		try {
			// First verify customer exists
			String checkCustomerSql = "SELECT COUNT(*) FROM pharmacy.customer WHERE customer_id = ?";
			try (PreparedStatement checkPs = con.prepareStatement(checkCustomerSql)) {
				checkPs.setString(1, dain.getCustomer_id());
				try (ResultSet rs = checkPs.executeQuery()) {
					if (rs.next() && rs.getInt(1) == 0) {
						JOptionPane.showMessageDialog(null, "Customer does not exist in the database", "Error",
								JOptionPane.ERROR_MESSAGE);
						return 0;
					}
				}
			}

			// Generate new order ID
			String newOrderId = generateNextOrderId();
			dain.setOrder_id(newOrderId);

			con.setAutoCommit(false); // Start transaction

			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getOrder_id());
			ps.setString(2, dain.getCustomer_id());
			ps.setString(3, dain.getOrder_date());

			// Handle total amount safely
			String totalStr = dain.getTotal();
			double total = 0.0;
			if (totalStr != null && !totalStr.trim().isEmpty()) {
				try {
					total = Double.parseDouble(totalStr.trim());
				} catch (NumberFormatException e) {
					System.out.println("Warning: Invalid total amount format, using 0.0");
				}
			}
			ps.setDouble(4, total);

			ps.setString(5, dain.getStaff_id());

			result = ps.executeUpdate();

			if (result > 0) {
				con.commit(); // Commit transaction
			} else {
				con.rollback(); // Rollback if no rows affected
			}
		} catch (SQLException e) {
			try {
				con.rollback(); // Rollback on error
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error saving order: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				con.setAutoCommit(true); // Restore default
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public String getLastOrderId() throws SQLException {
		String sql = "SELECT order_id FROM pharmacy.order ORDER BY order_id DESC LIMIT 1";
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getString("order_id");
			}
		}
		return null;
	}

	public boolean isOrderExists(String orderId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM pharmacy.order WHERE order_id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, orderId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		}
		return false;
	}

}
