package config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import model.CustomerModel;
import model.ItemModel;

public class MySqlQueries {
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

	public static void addCoboBox(String tableName, String columnName, JComboBox<String> comboBox) {
		String sql = "select " + columnName + " from " + tableName;
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			comboBox.removeAllItems();
			comboBox.addItem("-Select-");
			while (rs.next()) {
				String value = rs.getString(columnName);

				comboBox.addItem(value);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String[] getItemData(ItemModel dain) throws SQLException {
		String str[] = new String[7];
		String sql = "select * from pharmacy.medicine where medicine_name = ?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getMedicine_name());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			str[0] = rs.getString(1); // id
			str[1] = rs.getString(3); // name
			str[2] = rs.getString(5); // package
			str[3] = rs.getString(4);// category
			str[4] = rs.getString(6);// price
			str[5] = rs.getString(7);// qty
			str[6] = rs.getString(8);// date
		}

		return str;
	}

	public static String[] getCustomerData(CustomerModel dain) throws SQLException {
		String str[] = new String[4];
		String sql = "select * from pharmacy.customer where customer_id = ?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getCustomer_id());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			str[0] = rs.getString(1); // id
			str[1] = rs.getString(2); // name
			str[2] = rs.getString(3); // phone
			str[3] = rs.getString(4);// address
		}

		return str;
	}

	public static String[] getItemData1(ItemModel dain) throws SQLException {
		String str[] = new String[7];
		String sql = "select * from pharmacy.medicine where medicine_id = ?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getMedicine_id());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			str[0] = rs.getString(1); // id
			str[1] = rs.getString(3); // name
			str[2] = rs.getString(5); // package
			str[3] = rs.getString(4);// category
			str[4] = rs.getString(6);// price
			str[5] = rs.getString(7);// qty
			str[6] = rs.getString(8);// date
		}
		return str;
	}

	public static String[] getStaffByRole(String role) throws SQLException {
		String query = "SELECT staff_id, staff_name, role FROM staff WHERE role = ?";

		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(query);

		pstmt.setString(1, role);
		ResultSet rs = pstmt.executeQuery();

		List<String> staffList = new ArrayList<>();
		while (rs.next()) {
			staffList.add(rs.getString("staff_id"));
			staffList.add(rs.getString("staff_name"));
			staffList.add(rs.getString("role"));
		}

		return staffList.toArray(new String[0]);

	}

}
