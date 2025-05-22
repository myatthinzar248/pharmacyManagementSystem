package model;

public class ItemModel {
	private String medicine_id;
	private String staff_id;
	private String medicine_name;
	private String category;
	private String medicine_package;
	private double current_price;
	private int stock_qty;
	private String expire_date;

	public String getMedicine_id() {
		return medicine_id;
	}

	public void setMedicine_id(String medicine_id) {
		this.medicine_id = medicine_id;
	}

	public String getMedicine_name() {
		return medicine_name;
	}

	public void setMedicine_name(String medicine_name) {
		this.medicine_name = medicine_name;
	}

	// setMedicine_name,setCategory setMedicine_package setCurrent_price
	// setStock_qty setExpire_date setStaff_id
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMedicine_package() {
		return medicine_package;
	}

	public void setMedicine_package(String medicine_package) {
		this.medicine_package = medicine_package;
	}

	public double getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(double current_price) {
		this.current_price = current_price;
	}

	public int getStock_qty() {
		return stock_qty;
	}

	public void setStock_qty(int stock_qty) {
		this.stock_qty = stock_qty;
	}

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getExpire_date() {
		return expire_date;
	}

	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}

	public String toString() {
		return medicine_name + " (" + category + ")"; // Display format
	}
}
