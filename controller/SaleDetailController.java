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
import model.SaleDetailModel;

public class SaleDetailController {
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

	public int insert(SaleDetailModel dain) {
		int result = 0;
		String sql = "insert into pharmacy.saledetail (sale_id,medicine_id,quantity,price) values(?,?,?,?)";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);

			ps.setString(1, dain.getSale_id());
			ps.setString(2, dain.getMedicine_id());
			ps.setInt(3, dain.getSale_qty());
			ps.setDouble(4, dain.getPrice());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public List<SaleDetailModel> showAll() throws SQLException {
		List<SaleDetailModel> list = new ArrayList<SaleDetailModel>();
		String sql = "select * from pharmacy.saledetail order by sale_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			SaleDetailController pdc = new SaleDetailController();
			SaleDetailModel pdm = new SaleDetailModel();
			pdm.setSale_id(rs.getString("sale_id"));
			pdm.setMedicine_id(rs.getString("medicine_id"));
			pdm.setSale_qty(rs.getInt("quantity"));
			pdm.setPrice(rs.getInt("price"));

			ItemModel im = new ItemModel();
			ItemController ic = new ItemController();
			im.setMedicine_name(pdm.getMedicine_name());

			list.add(pdm);
		}
		return list;
	}

	public List<SaleDetailModel> showOne(SaleDetailModel dain) throws SQLException {
		List<SaleDetailModel> list = new ArrayList<SaleDetailModel>();
		String sql = "select * from pharmacy.saledetail where medicine_id in(select medicine_id from medicine where medicine_name like ?) order by sale_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getMedicine_id() + "%");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			SaleDetailController pdc = new SaleDetailController();
			SaleDetailModel pdm = new SaleDetailModel();
			pdm.setSale_id(rs.getString("sale_id"));
			pdm.setMedicine_id(rs.getString("medicine_id"));
			pdm.setSale_qty(rs.getInt("quantity"));
			pdm.setPrice(rs.getInt("price"));

			ItemModel im = new ItemModel();
			ItemController ic = new ItemController();
			im.setMedicine_id(pdm.getMedicine_id());
			// pdm.setMedicine_name(ic.serachMedicineDetail(im));
			try {
				String[] data = MySqlQueries.getItemData1(im); // data[1] is name
				pdm.setMedicine_id(data[1]); // Set name correctly
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			list.add(pdm);
		}
		return list;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
