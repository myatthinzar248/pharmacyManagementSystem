package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.ClsDBConnection;
import model.ItemModel;

public class ItemController {
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

	public int insert(ItemModel dain) {
		int result = 0;

		try {
			// Auto-generate unique ID
			String newId = generateNextMedicineId();
			dain.setMedicine_id(newId); // set to the model

			String sql = "INSERT INTO pharmacy.medicine (medicine_id, staff_id, medicine_name, category, package, current_price, stock_qty) VALUES (?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, dain.getMedicine_id());
				ps.setString(2, dain.getStaff_id());
				ps.setString(3, dain.getMedicine_name());
				ps.setString(4, dain.getCategory());
				ps.setString(5, dain.getMedicine_package());
				ps.setDouble(6, dain.getCurrent_price());
				ps.setInt(7, dain.getStock_qty());

				result = ps.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Insert Failed: Internal Error", "Fail", JOptionPane.ERROR_MESSAGE);
		}

		return result;
	}

	public int update(ItemModel dain) {
		int result = 0;
		String sql = "update pharmacy.medicine set current_price=?,stock_qty=?,expire_date=?,package=? where medicine_id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			// ps.setString(1, dain.getStaff_id());
			ps.setDouble(1, dain.getCurrent_price());
			ps.setInt(2, dain.getStock_qty());
			ps.setString(3, dain.getExpire_date());
			ps.setString(4, dain.getMedicine_package());
			ps.setString(5, dain.getMedicine_id());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public int update1(ItemModel dain) {
		int result = 0;
		String sql = "update pharmacy.medicine set current_price=?,stock_qty=?, package = ?where medicine_id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setDouble(1, dain.getCurrent_price());
			ps.setInt(2, dain.getStock_qty());

			ps.setString(3, dain.getMedicine_package());
			ps.setString(4, dain.getMedicine_id());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public int delete(ItemModel dain) {
		int result = 0;
		String deletePurchaseDetailSQL = "DELETE FROM pharmacy.purchase_detail WHERE medicine_id=?";
		String deleteMedicineSQL = "DELETE FROM pharmacy.medicine WHERE medicine_id=?";

		try (PreparedStatement ps1 = con.prepareStatement(deletePurchaseDetailSQL);
				PreparedStatement ps2 = con.prepareStatement(deleteMedicineSQL)) {
			con.setAutoCommit(false); // Start transaction

			ps1.setString(1, dain.getMedicine_id());
			ps1.executeUpdate();

			ps2.setString(1, dain.getMedicine_id());
			result = ps2.executeUpdate();

			con.commit(); // Commit transaction
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback(); // Rollback transaction on error
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Delete Failed: Related data exists or database error", "Fail",
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

	public List<ItemModel> selectall() throws SQLException {
		List<ItemModel> list = new ArrayList<ItemModel>();
		String sql = "select * from pharmacy.medicine order by medicine_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ItemModel tm = new ItemModel();
			tm.setMedicine_id(rs.getString("medicine_id"));
			tm.setStaff_id(rs.getString("staff_id"));
			tm.setMedicine_name(rs.getString("medicine_name"));
			tm.setCategory(rs.getString("category"));
			tm.setMedicine_package(rs.getString("package"));
			tm.setCurrent_price(rs.getDouble("current_price"));
			tm.setStock_qty(rs.getInt("stock_qty"));
			tm.setExpire_date(rs.getString("expire_date")); // Get as Date

			list.add(tm);
		}
		return list;
	}

	public List<ItemModel> selectone(ItemModel dain) throws SQLException {
		List<ItemModel> list = new ArrayList<ItemModel>();
		String sql = "select * from pharmacy.medicine where medicine_name like ? order by medicine_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getMedicine_name() + "%");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ItemModel tm = new ItemModel();
			tm.setMedicine_id(rs.getString("medicine_id"));
			tm.setStaff_id(rs.getString("staff_id"));
			tm.setMedicine_name(rs.getString("medicine_name"));
			tm.setCategory(rs.getString("category"));
			tm.setMedicine_package(rs.getString("package"));
			tm.setCurrent_price(rs.getDouble("current_price"));
			tm.setStock_qty(rs.getInt("stock_qty"));
			tm.setExpire_date(rs.getString("expire_date")); // Get as Date

			list.add(tm);
		}
		return list;
	}

	public List<ItemModel> searchMedicineName(ItemModel dain) throws SQLException {
		List<ItemModel> list = new ArrayList<>();
		String sql = "SELECT * FROM pharmacy.medicine WHERE medicine_name = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, dain.getMedicine_name());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					ItemModel im = new ItemModel();
					im.setMedicine_id(rs.getString("medicine_id"));
					im.setMedicine_name(rs.getString("medicine_name"));
					im.setCategory(rs.getString("category"));
					im.setMedicine_package(rs.getString("package"));
					im.setCurrent_price(rs.getDouble("current_price"));
					im.setStock_qty(rs.getInt("stock_qty"));
					im.setExpire_date(rs.getString("expire_date"));
					list.add(im);
				}
			}
		}
		return list;
	}

	public List<ItemModel> serachMedicineDetail(ItemModel dain) throws SQLException {
		List<ItemModel> list = new ArrayList<ItemModel>();
		String medicine_name = dain.getMedicine_name();
		String sql = "select * from pharmacy.medicine where medicine_name = ? order by medicine_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, medicine_name);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ItemModel tm = new ItemModel();
			tm.setMedicine_id(rs.getString("medicine_id"));
			tm.setStaff_id(rs.getString("staff_id"));
			tm.setMedicine_name(rs.getString("medicine_name"));
			tm.setCategory(rs.getString("category"));
			tm.setMedicine_package(rs.getString("package"));
			tm.setCurrent_price(rs.getDouble("current_price"));
			tm.setStock_qty(rs.getInt("stock_qty"));
			tm.setExpire_date(rs.getString("expire_date")); // Get as Date

			list.add(tm);
		}
		return list;
	}

	public String searchMedicineId(ItemModel dain) {
		String result = null;
		String sql = "select medicine_id from pharmacy.medicine where medicine_name=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getMedicine_name());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("medicine_id");
			} else {
				System.out.println("This medicine is not found");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean isduplicate(ItemModel dain) throws SQLException {
		boolean duplicate = false;
		String sql = "SELECT * FROM pharmacy.medicine WHERE medicine_name = ? AND category = ?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getMedicine_name());
		ps.setString(2, dain.getCategory());

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			duplicate = true;
		} else {
			duplicate = false;
		}
		return duplicate;
	}

	public String generateNextMedicineId() throws SQLException {
		String sql = "SELECT MAX(medicine_id) AS max_id FROM pharmacy.medicine";
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				String maxId = rs.getString("max_id");
				if (maxId != null && maxId.startsWith("MD-")) {
					int num = Integer.parseInt(maxId.substring(3));
					return String.format("MD-%08d", num + 1);
				}
			}
		}
		return "MD-00000001"; // Start from this if no entries exist
	}

	public List<String> getAllMedicineNames() throws SQLException {
		List<String> medicineNames = new ArrayList<>();
		String sql = "SELECT medicine_name FROM pharmacy.medicine ORDER BY medicine_name ASC";
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				medicineNames.add(rs.getString("medicine_name"));
			}
		}
		return medicineNames;
	}

	public List<String> searchMedicineNames(ItemModel dain) throws SQLException {
		List<String> medicineNames = new ArrayList<>();
		String sql = "SELECT medicine_name FROM pharmacy.medicine WHERE medicine_name LIKE ? ORDER BY medicine_name ASC";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, dain.getMedicine_name() + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					medicineNames.add(rs.getString("medicine_name"));
				}
			}
		}
		return medicineNames;
	}

	public String getMedicineNameById(String medicineId) {
		String medicineName = null;
		String sql = "SELECT medicine_name FROM pharmacy.medicine WHERE medicine_id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, medicineId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					medicineName = rs.getString("medicine_name");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error fetching medicine name by ID", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return medicineName;
	}

}
