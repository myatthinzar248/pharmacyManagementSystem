package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.ClsDBConnection;
import config.MySqlQueries;
import model.ItemModel;
import model.OrderDetailModel;

public class OrderDetailController {
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

	public int insert(OrderDetailModel dain) {
		int result = 0;
		String sql = "insert into pharmacy.orderdetail (order_id,medicine_id,quantity,price) values(?,?,?,?)";
		try {
			con.setAutoCommit(false); // Start transaction

			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getOrder_id());
			ps.setString(2, dain.getMedicine_id());
			ps.setInt(3, dain.getQty());
			ps.setDouble(4, dain.getPrice());

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
			JOptionPane.showMessageDialog(null, "Error saving order detail: " + e.getMessage(), "Error",
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

	public List<OrderDetailModel> showAll() throws SQLException {
		List<OrderDetailModel> list = new ArrayList<OrderDetailModel>();
		String sql = "select * from pharmacy.orderdetail order by order_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			OrderDetailController pdc = new OrderDetailController();
			OrderDetailModel pdm = new OrderDetailModel();
			pdm.setOrder_id(rs.getString("order_id"));
			pdm.setMedicine_id(rs.getString("medicine_id"));
			pdm.setQty(rs.getInt("quantity"));
			pdm.setPrice(rs.getInt("price"));

			ItemModel im = new ItemModel();
			ItemController ic = new ItemController();
			im.setMedicine_id(pdm.getMedicine_id());
			// pdm.setMedicine_name(ic.serachMedicineDetail(im));

			list.add(pdm);
		}
		return list;
	}

	public List<OrderDetailModel> showOne(OrderDetailModel odm) throws SQLException {
		List<OrderDetailModel> list = new ArrayList<OrderDetailModel>();
		String sql = "select * from pharmacy.orderdetail where medicine_id in(select medicine_id from medicine where medicine_name like ?) order by order_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, odm.getMedicine_name() + "%");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			OrderDetailController pdc = new OrderDetailController();
			OrderDetailModel pdm = new OrderDetailModel();
			pdm.setOrder_id(rs.getString("order_id"));
			pdm.setMedicine_id(rs.getString("medicine_id"));
			pdm.setQty(rs.getInt("quantity"));
			pdm.setPrice(rs.getInt("price"));

			ItemModel im = new ItemModel();
			ItemController ic = new ItemController();
			im.setMedicine_id(pdm.getMedicine_id());
			// pdm.setMedicine_name(ic.serachMedicineDetail(im));
			try {
				String[] data = MySqlQueries.getItemData1(im); // data[1] is name
				pdm.setMedicine_name(data[1]); // Set name correctly
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			list.add(pdm);
		}
		return list;
	}

}