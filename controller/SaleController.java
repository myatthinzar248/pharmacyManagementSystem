package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import config.ClsDBConnection;
import model.SaleModel;

public class SaleController {
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

	public String generateNextSaleId() throws SQLException {
		String sql = "SELECT MAX(sale_id) AS max_id FROM pharmacy.sale";
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				String maxId = rs.getString("max_id");
				if (maxId != null && maxId.startsWith("SL-")) {
					int num = Integer.parseInt(maxId.substring(3));
					return String.format("SL-%08d", num + 1);
				}
			}
		}
		return "SL-00000001"; // Start from this if no entries exist
	}

	public int insert(SaleModel dain) {
		int result = 0;
		String sql = "insert into pharmacy.sale (sale_id,customer_id,sale_date,total_amount,staff_id) values(?,?,?,?,?)";
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
			String newSaleId = generateNextSaleId();
			dain.setSale_id(newSaleId);

			con.setAutoCommit(false); // Start transaction

			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getSale_id());
			ps.setString(2, dain.getCustomer_id());
			ps.setString(3, dain.getSale_date());

			// Handle total amount safely
			double total = dain.getTotal_amount();
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
}
