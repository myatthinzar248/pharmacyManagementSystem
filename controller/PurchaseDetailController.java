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
import model.PurchaseDetailModel;

public class PurchaseDetailController {
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

	public int insert(PurchaseDetailModel dain) {
		int result = 0;
		String sql = "insert into pharmacy.purchase_detail (purchase_id,medicine_id,purchase_qty,purchase_price) values(?,?,?,?)";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getPurchase_id());
			System.out.println(dain.getPurchase_id());
			ps.setString(2, dain.getMedicine_id());
			ps.setInt(3, dain.getPurchase_qty());
			ps.setDouble(4, dain.getPurchase_price());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public List<PurchaseDetailModel> showAll() throws SQLException {
		List<PurchaseDetailModel> list = new ArrayList<PurchaseDetailModel>();
		String sql = "select * from pharmacy.purchase_detail order by purchase_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			PurchaseDetailController pdc = new PurchaseDetailController();
			PurchaseDetailModel pdm = new PurchaseDetailModel();
			pdm.setPurchase_id(rs.getString("purchase_id"));
			pdm.setMedicine_id(rs.getString("medicine_id"));
			pdm.setPurchase_qty(rs.getInt("purchase_qty"));
			pdm.setPurchase_price(rs.getInt("purchase_price"));

			ItemModel im = new ItemModel();
			ItemController ic = new ItemController();
			im.setMedicine_id(pdm.getMedicine_id());

			list.add(pdm);
		}
		return list;
	}

	public List<PurchaseDetailModel> showOne(PurchaseDetailModel dain) throws SQLException {
		List<PurchaseDetailModel> list = new ArrayList<PurchaseDetailModel>();
		String sql = "select * from pharmacy.purchase_detail where medicine_id in(select medicine_id from medicine where medicine_name like ?) order by purchase_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getMedicine_name() + "%");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			PurchaseDetailController pdc = new PurchaseDetailController();
			PurchaseDetailModel pdm = new PurchaseDetailModel();
			pdm.setPurchase_id(rs.getString("purchase_id"));
			pdm.setMedicine_id(rs.getString("medicine_id"));
			pdm.setPurchase_qty(rs.getInt("purchase_qty"));
			pdm.setPurchase_price(rs.getInt("purchase_price"));

			ItemController ic = new ItemController();
			String medName = ic.getMedicineNameById(pdm.getMedicine_id());
			pdm.setMedicine_name(medName);
			list.add(pdm);
		}
		return list;
	}

}
