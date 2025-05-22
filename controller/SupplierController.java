package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.sql.*;
import java.util.*;

import config.ClsDBConnection;
import model.SupplierModel;


public class SupplierController {
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
	
	public int insert(SupplierModel dain) {
		int result =0;
		String sql = "insert into pharmacy.supplier (supplier_id,supplier_name,address,phone,email) values (?,?,?,?,?)";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getSupplier_id());
			ps.setString(2,dain.getName());
			ps.setString(3, dain.getAddress());
			ps.setString(4,dain.getPhone());
			ps.setString(5,dain.getEmail());
		
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Insert Fail,Inter error","Fail", JOptionPane.ERROR_MESSAGE);
		}
		return result;
		
	}
	
	public int update(SupplierModel dain) {
		int result =0;
		String sql = "update pharmacy.supplier set supplier_name=?,address=?,phone=?,email=? where supplier_id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			
			ps.setString(1,dain.getName());
			ps.setString(2, dain.getAddress());
			ps.setString(3,dain.getPhone());
			ps.setString(4,dain.getEmail());
			ps.setString(5, dain.getSupplier_id());
			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	public int delete(SupplierModel dain) {
		int result =0;
		String sql = "delete from pharmacy.supplier where supplier_id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getSupplier_id());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Insert Fail,Inter error","Fail", JOptionPane.ERROR_MESSAGE);
		}
		return result;
		
	}
	
	public List<SupplierModel> selectall() throws SQLException {
		List<SupplierModel> list = new ArrayList<SupplierModel> ();
		String sql = "select * from pharmacy.supplier order by supplier_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			SupplierModel tm = new SupplierModel();
			tm.setSupplier_id(rs.getString("supplier_id"));
			tm.setName(rs.getString("supplier_name"));
			tm.setAddress(rs.getString("address"));
			tm.setPhone(rs.getString("phone"));
			tm.setEmail(rs.getString("email"));
			
			list.add(tm);
		}
		return list;
	}
	
	public List<SupplierModel> selectone(SupplierModel dain) throws SQLException {
		List<SupplierModel> list = new ArrayList<SupplierModel> ();
		String sql = "select * from pharmacy.supplier where supplier_name like ? order by supplier_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getName()+"%");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			SupplierModel tm = new SupplierModel();
			tm.setSupplier_id(rs.getString("supplier_id"));
			tm.setName(rs.getString("supplier_name"));
			tm.setAddress(rs.getString("address"));
			tm.setPhone(rs.getString("phone"));
			tm.setEmail(rs.getString("email"));
			
			list.add(tm);
		}
		return list;
	}
	
	public String searchTypeName(SupplierModel dain) {
		String result = null;
		String sql = "select name from pharmacy.supplier where supplier_id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,dain.getSupplier_id());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getString("supplier_name");
			}else{
				System.out.println("This supplier is not found");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List<SupplierModel> serachSupplierDetail(SupplierModel dain)throws SQLException{
		List<SupplierModel> list = new ArrayList<SupplierModel>();
		String suppliername = dain.getName();
		String sql = "select * from pharmacy.supplier where supplier_name = ? order by supplier_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1,suppliername);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			SupplierModel sm = new SupplierModel();
			sm.setSupplier_id(rs.getString("supplier_id"));
			sm.setName(rs.getString("supplier_name"));
			sm.setAddress(rs.getString("address"));
			sm.setPhone(rs.getString("phone"));
			sm.setEmail(rs.getString("email"));
			
			list.add(sm);
		}
		return list;
	}
	
	public String searchTypeId(SupplierModel dain) {
		String result = null;
		String sql = "select supplier_id from pharmacy.supplier where supplier_name=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1,dain.getName());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getString("supplier_id");
			}else{
				System.out.println("This brand is not found");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean isduplicate(SupplierModel dain) throws SQLException{
		boolean duplicate = false;
		String sql = "select * from pharmacy.supplier where supplier_name = ?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getName());
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			duplicate = true;
		}else {
			duplicate = false;
		}
		return duplicate;
	}
	
	

}
