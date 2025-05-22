package model;

public class SaleDetailModel {
	private String sale_id;
	private String medicine_id;
	private int sale_qty;
	private double price;
	private String medicine_name;

	public String getMedicine_name() {
		return medicine_name;
	}

	public void setMedicine_name(String medicine_name) {
		this.medicine_name = medicine_name;
	}

	public String getSale_id() {
		return sale_id;
	}

	public void setSale_id(String sale_id) {
		this.sale_id = sale_id;
	}

	public String getMedicine_id() {
		return medicine_id;
	}

	public void setMedicine_id(String medicine_id) {
		this.medicine_id = medicine_id;
	}

	public int getSale_qty() {
		return sale_qty;
	}

	public void setSale_qty(int sale_qty) {
		this.sale_qty = sale_qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
