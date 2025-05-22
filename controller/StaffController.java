package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.ClsDBConnection;
import model.StaffModel;

public class StaffController {

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

	public String searchStaffId(StaffModel dain) {
		String result = null;
		String sql = "select staff_id from pharmacy.staff where staff_name=? and password=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getStaff_name());
			ps.setString(2, dain.getPassword());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("staff_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean loginState(StaffModel dain) throws SQLException {
		boolean duplicate = false;
		String sql = "select * from pharmacy.staff where staff_name=? and password=?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getStaff_name());
		ps.setString(2, dain.getPassword());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			duplicate = true;
		} else {
			duplicate = false;
		}
		return duplicate;
	}

	public int update(StaffModel dain) {
		int result = 0;
		String sql = "update pharmacy.staff set staff_name=?, password=?,role=?,staff_phone=?,staff_address=?,staff_nrc=?,date_of_birdth=?,date_of_job=?,salary=?,staff_email=? where staff_id=?;";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			// ps.setString(1, dain.getStaff_id());
			ps.setString(1, dain.getStaff_name());
			ps.setString(2, dain.getPassword());
			ps.setString(3, dain.getRole());
			ps.setString(4, dain.getPhone());
			ps.setString(5, dain.getAddress());
			ps.setString(6, dain.getNrc());
			ps.setString(7, dain.getDateOfBirdth());
			ps.setString(8, dain.getDateOfJob());
			ps.setString(9, dain.getSalary().toString());
			ps.setString(10, dain.getEmail());
			ps.setString(11, dain.getStaff_id());
			result = ps.executeUpdate();
			System.out.println(dain.getStaff_id());

			// System.out.println("===" + result);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Fail update,Inter Error", "Fail", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public int delete(StaffModel dain) {
		int result = 0;
		String sql = "delete from pharmacy.staff where staff_id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getStaff_id());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Fail delete,Inter Error", "Fail", JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}

	public List<StaffModel> selectall() throws SQLException {
		List<StaffModel> list = new ArrayList<StaffModel>();
		String sql = "select * from pharmacy.staff order by staff_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			StaffModel bm = new StaffModel();
			bm.setStaff_id(rs.getString("staff_id"));
			bm.setStaff_name(rs.getString("staff_name"));
			bm.setPassword(rs.getString("password"));
			bm.setRole(rs.getString("role"));
			bm.setPhone(rs.getString("staff_phone"));
			bm.setAddress(rs.getString("staff_address"));
			bm.setNrc(rs.getString("staff_nrc"));
			bm.setDateOfBirdth(rs.getString("date_of_birdth"));
			bm.setDateOfJob(rs.getString("date_of_job"));
			bm.setSalary(Float.parseFloat(rs.getString("salary")));
			bm.setEmail(rs.getString("staff_email"));
			list.add(bm);
		}
		return list;
	}

	public List<StaffModel> selectone(StaffModel dain) throws SQLException {
		List<StaffModel> list = new ArrayList<StaffModel>();
		String sql = "select * from mdcr_pos.brand where name like ? order by brand_id desc";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getStaff_name() + "%");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			StaffModel bm = new StaffModel();
			bm.setStaff_id(rs.getString("brand_id"));
			bm.setStaff_name(rs.getString("name"));
			list.add(bm);
		}
		return list;
	}

	public String searchStaffName(StaffModel dain) {
		String result = null;
		String sql = "select staff_name from pharmacy.staff where staff_id =?";
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dain.getStaff_id());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("staff_name");
			} else {
				System.out.println("This Staff is not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

//	public String searchBrandId(StaffModel dain) {
//		String result = null;
//		String sql = "select brand_id from mdcr_pos.brand where name=?";
//		try {
//			PreparedStatement ps =(PreparedStatement) con.prepareStatement(sql);
//			ps.setString(1, dain.getName());
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()) {
//				result = rs.getString("brand_id") ;
//			}
//		}catch(SQLException e){
//			e.printStackTrace();
//		}
//	    return result;
//	}

	public boolean isduplicate(StaffModel dain) throws SQLException {
		boolean duplicate = false;
		String sql = "select * from pharmacy.staff where staff_name=?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, dain.getStaff_name());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			duplicate = true;
		} else {
			duplicate = false;
		}
		return duplicate;
	}

}
