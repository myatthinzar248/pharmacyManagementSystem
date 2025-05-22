package config;

import java.util.Vector;

import javax.swing.JOptionPane;

public class Checking {
	@SuppressWarnings("deprecation")
	public static boolean IsValidName(String str) {
		if(Character.isSpace(str.charAt(0))) {
			return true;
		}else {
			return false;
		}
	}

	public static boolean IsEmailformat(String str) {
		boolean b = false;
		int dot = str.lastIndexOf(".");
		int at = str.indexOf("@");
		if((dot<0) || (at <0) ||(str.indexOf(" ")>0)) {
			return b;
		}
		String st = str.substring(0,at);
		String st1 = str.substring(dot+1);
		if(!st.trim().equals("")&&(st1.equals("com"))) {
			b = true;
			return b;
		}else {
			return b;
		}

	}

	public static boolean isPhoneNo(String str) {
		if(str !=null && str.length()<=11) {
			return str.startsWith("09");
		}
		return false;
	}

	public static boolean IsAllDigit(String str) {
	    for(int i=0; i<str.length(); i++) {
	    	if(Character.isLetter(str.charAt(i))) {
	    		return true;

	    }
	}
	   	return false;
	}

	public static boolean IsContain(String s,Vector str) {
		for(int i=0; i<str.size(); i++) {
			if(s.equals((String)str.elementAt(i))) {
				return true;
			}

		}
		return false;
	}
	public static boolean checktxtquantity(String strqp) {
		if(strqp.equals("")) {
			JOptionPane.showMessageDialog(null, "You must enter the Quantity");
			return false;
		}else if(IsAllDigit(strqp)) {
			JOptionPane.showMessageDialog(null, "You must enter NUMBER for Quantity");
			return false;
		}else if(Integer.parseInt(strqp)>10000) {
			JOptionPane.showMessageDialog(null, "The Quantity you entered is too many to purchase!");
			return false;
		}else {
			return true;
		}
	}

	public static boolean checktxtprice(String strqp) {
		System.out.println(strqp);
		if(strqp.equals("")) {
			JOptionPane.showMessageDialog(null, "You must enter the Price");
			return false;
		}else if(IsAllDigit(strqp)) {
			JOptionPane.showMessageDialog(null, "You must enter NUMBER the Price");
			return false;
		}else if(Long.parseLong(strqp)>1000000000) {
			JOptionPane.showMessageDialog(null, "The Price you entered is too much(more than 1,000,000,000)");
			return false;
		}else {
			return true;
		}
	}

	public static String Sumamount(Vector data, int t) {
		try {
			double sum = 0;
			for(int i=0; i<data.size(); i++) {
				String value = (String)data.elementAt(i);
				value = value.replaceAll(",", "").trim();
				sum += Double.parseDouble(value);
			}
			if(t==1) {
				return String.format("%,.2f", sum);
			} else {
				return String.format("%.2f", sum);
			}
		} catch (NumberFormatException e) {
			System.err.println("Error parsing number: " + e.getMessage());
			return "0.00";
		}
	}

}
