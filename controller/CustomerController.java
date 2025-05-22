package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.ClsDBConnection;
import model.CustomerModel;

public class CustomerController {
	public static Connection con = null;
	static {
		ClsDBConnection cls = new ClsDBConnection();
		try {
			con = cls.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Insert Fail,Inter error", "Fail", JOptionPane.ERROR_MESSAGE);
		}

	}

	public String generateNextCustomerId() throws SQLException {
		String sql = "SELECT MAX(customer_id) AS max_id FROM pharmacy.customer";
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				String maxId = rs.getString("max_id");
				if (maxId != null && maxId.startsWith("CU-")) {
					int num = Integer.parseInt(maxId.substring(3));
					return String.format("CU-%07d", num + 1);
				}
			}
		}
		return "CU-0000001"; // Start from this if no entries exist
	}

	public int insert(CustomerModel dain) {
		int result = 0;
		String sql = "insert into pharmacy.customer (customer_id,customer_name,phone,address) values(?,?,?,?)";

		try {
			// Generate new customer ID
			String newId = generateNextCustomerId();
			dain.setCustomer_id(newId);

			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getCustomer_id());
			ps.setString(2, dain.getCustomer_name());
			ps.setString(3, dain.getPhone());
			ps.setString(4, dain.getAddress());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error saving customer: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}

	public List<CustomerModel> selectall() throws SQLException {
		List<CustomerModel> list = new ArrayList<CustomerModel>();
		String sql = "select * from pharmacy.customer order by customer_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			CustomerModel cs = new CustomerModel();
			cs.setCustomer_id(rs.getString("customer_id"));
			cs.setCustomer_name(rs.getString("customer_name"));
			cs.setPhone(rs.getString("phone"));
			cs.setAddress(rs.getString("address"));

			list.add(cs);
		}
		return list;
	}

	public List<CustomerModel> selectone(CustomerModel dain) throws SQLException {
		List<CustomerModel> list = new ArrayList<CustomerModel>();
		String sql = "select * from pharmacy.customer where customer_name like ? order by customer_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getCustomer_name() + "%");
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			CustomerModel cs = new CustomerModel();
			cs.setCustomer_id(rs.getString("customer_id"));
			cs.setCustomer_name(rs.getString("customer_name"));
			cs.setPhone(rs.getString("phone"));
			cs.setAddress(rs.getString("address"));
			list.add(cs);
		}
		return list;
	}

//	public String searchCustomerName(CustomerModel dain) {
//		String name = null;
//		String sql = "select customer_name from pharmacy.customer where customer_id=?";
//		try {
//			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
//			ps.setString(1, dain.getCustomer_id());
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				name = rs.getString("customer_name");
//			} else {
//				System.out.println("Customer Name is not found");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return name;
//	}
	public List<CustomerModel> searchCustomerName(CustomerModel dain) throws SQLException {
		List<CustomerModel> list = new ArrayList<>();
		String sql = "SELECT * FROM pharmacy.customer WHERE customer_name = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, dain.getCustomer_name());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					CustomerModel im = new CustomerModel();
					im.setCustomer_id(rs.getString("customer_id"));
					im.setCustomer_name(rs.getString("customer_name"));
					im.setPhone(rs.getString("phone"));
					im.setAddress(rs.getString("address"));
					list.add(im);
				}
			}
		}
		return list;
	}

	public int update(CustomerModel dain) {
		int result = 0;
		String sql = "update pharmacy.customer set customer_name=?,phone=?,address=? where customer_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getCustomer_name());
			ps.setString(2, dain.getPhone());
			ps.setString(3, dain.getAddress());
			ps.setString(4, dain.getCustomer_id());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public int delete(CustomerModel dain) {
		int result = 0;
		String sql = "delete from pharmacy.customer where customer_id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getCustomer_id());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Insert Fail,Inter error", "Fail", JOptionPane.ERROR_MESSAGE);
		}
		return result;

	}

	public List<CustomerModel> selectById(String customerId) throws SQLException {
		List<CustomerModel> list = new ArrayList<CustomerModel>();
		String sql = "SELECT * FROM pharmacy.customer WHERE customer_id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, customerId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					CustomerModel cs = new CustomerModel();
					cs.setCustomer_id(rs.getString("customer_id"));
					cs.setCustomer_name(rs.getString("customer_name"));
					cs.setPhone(rs.getString("phone"));
					cs.setAddress(rs.getString("address"));
					list.add(cs);
				}
			}
		}
		return list;
	}

	public boolean isduplicate(CustomerModel dain) throws SQLException {
		boolean duplicate = false;
		String sql = "select * from pharmacy.customer where customer_name = ?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getCustomer_name());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			duplicate = true;
		} else {
			duplicate = false;
		}
		return duplicate;
	}

}
