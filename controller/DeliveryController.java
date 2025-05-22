package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.ClsDBConnection;
import model.DeliveryModel;
//import view.CustomTable;

public class DeliveryController {
	public static Connection con = null;

//	private CustomTable tblDeliveryShow;

	static {
		ClsDBConnection cls = new ClsDBConnection();
		try {
			con = cls.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Connection Error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public double getOrderTotal(String orderId) throws SQLException {
		double total = 0.0;
		String sql = "SELECT total FROM pharmacy.order WHERE order_id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, orderId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				total = rs.getDouble("total");
			}
		}
		return total;
	}

	public int insert(DeliveryModel delivery) throws SQLException {
		int result = 0;

		// First verify the order exists
		String checkOrderSql = "SELECT COUNT(*) FROM pharmacy.order WHERE order_id = ?";
		try (PreparedStatement checkPs = con.prepareStatement(checkOrderSql)) {
			checkPs.setString(1, delivery.getOrder_id());
			ResultSet rs = checkPs.executeQuery();
			if (rs.next() && rs.getInt(1) == 0) {
				throw new SQLException("Order ID " + delivery.getOrder_id() + " does not exist in the database");
			}
		}

		String sql = "INSERT INTO pharmacy.delivery (delivery_id, order_id, delivery_date, delivery_cost, staff_id, status,delivery_address) VALUES (?, ?, ?, ?, ?, ?,?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, delivery.getDelilvery_id());
			ps.setString(2, delivery.getOrder_id());
			ps.setString(3, delivery.getDelivery_date());
			ps.setDouble(4, delivery.getDelivery_cost());
			ps.setString(5, delivery.getStaff_id());
			ps.setString(6, delivery.getStatus());
			ps.setString(7, delivery.getDeliveryAddress());

			result = ps.executeUpdate();
		}
		return result;
	}

	public List<DeliveryModel> getAllDeliveries() throws SQLException {
		List<DeliveryModel> deliveries = new ArrayList<>();
		String sql = "SELECT d.delivery_id, d.order_id, d.delivery_date, d.delivery_cost, d.staff_id, s.staff_name "
				+ "FROM pharmacy.delivery d " + "LEFT JOIN pharmacy.staff s ON d.staff_id = s.staff_id "
				+ "ORDER BY d.delivery_id DESC";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				DeliveryModel delivery = new DeliveryModel();
				delivery.setDelilvery_id(rs.getString("delivery_id"));
				delivery.setOrder_id(rs.getString("order_id"));
				delivery.setDelivery_date(rs.getString("delivery_date"));
				ps.setDouble(4, delivery.getDelivery_cost());
				delivery.setStaff_id(rs.getString("staff_id"));
				deliveries.add(delivery);
			}
		}
		return deliveries;
	}

	public List<DeliveryModel> getDeliveriesByStaffId(String staffId) throws SQLException {
		List<DeliveryModel> deliveries = new ArrayList<>();
		String sql = "SELECT d.*, o.delivery_address " + "FROM pharmacy.delivery d "
				+ "LEFT JOIN pharmacy.order o ON d.order_id = o.order_id " + "WHERE d.staff_id = ? "
				+ "ORDER BY d.delivery_date DESC";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, staffId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				DeliveryModel delivery = new DeliveryModel();
				delivery.setDelilvery_id(rs.getString("delivery_id"));
				delivery.setOrder_id(rs.getString("order_id"));
				delivery.setDelivery_date(rs.getString("delivery_date"));
				delivery.setDelivery_cost(rs.getDouble("delivery_cost"));
				delivery.setStaff_id(rs.getString("staff_id"));
				delivery.setStatus(rs.getString("status"));
				delivery.setDeliveryAddress(rs.getString("delivery_address"));
				deliveries.add(delivery);
			}
		} catch (SQLException e) {
			System.out.println("Error getting deliveries for staff: " + e.getMessage());
			throw e;
		}
		return deliveries;
	}

	public int updateDeliveryStatus(String deliveryId, String newStatus) throws SQLException {
		String sql = "UPDATE pharmacy.delivery SET status = ? WHERE delivery_id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, newStatus);
			ps.setString(2, deliveryId);
			return ps.executeUpdate();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
