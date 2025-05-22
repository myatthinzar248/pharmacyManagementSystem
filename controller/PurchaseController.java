package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.util.*;
import java.sql.*;

import config.ClsDBConnection;
import model.PurchaseModel;

public class PurchaseController {
	public static Connection con = null;
	static {
		ClsDBConnection cls = new ClsDBConnection();
		try {
			con = cls.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Insert Fail,Inter error","Fail", JOptionPane.ERROR_MESSAGE);
		}

	}

	public int insert(PurchaseModel dain) {
		int result = 0;
		String sql = "insert into pharmacy.purchase (purchase_id,supplier_id,staff_id,purchase_date) values(?,?,?,?)";
		try {
			// Debug prints for troubleshooting
			System.out.println("purchase_id: " + dain.getPurchase_id());
			System.out.println("supplier_id: " + dain.getSupplier_id());
			System.out.println("staff_id: " + dain.getStaff_id());
			System.out.println("purchase_date: " + dain.getPurchase_date());

			// Ensure purchase_date is in yyyy-MM-dd format
			String purchaseDate = dain.getPurchase_date();
			if (purchaseDate != null && purchaseDate.length() > 10) {
				// Try to parse and reformat if needed
				try {
					java.util.Date parsed = new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(purchaseDate);
					purchaseDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(parsed);
				} catch (Exception ex) {
					// If parsing fails, keep original
				}
			}

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dain.getPurchase_id());
			ps.setString(2, dain.getSupplier_id());
			ps.setString(3, dain.getStaff_id());
			ps.setString(4, purchaseDate);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Insert Fail, SQL error: " + e.getMessage(), "Fail", JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}


}
