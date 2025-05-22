package model;

public class PurchaseDetailModel {
	private String purchase_id;
	private String medicine_id;
	private double purchase_price;
	private int purchase_qty;
	private String medicine_name;

	public String getMedicine_name() {
		return medicine_name;
	}

	public void setMedicine_name(String medicine_name) {
		this.medicine_name = medicine_name;
	}

	public String getPurchase_id() {
		return purchase_id;
	}

	public void setPurchase_id(String purchase_id) {
		this.purchase_id = purchase_id;
	}

	public String getMedicine_id() {
		return medicine_id;
	}

	public void setMedicine_id(String medicine_id) {
		this.medicine_id = medicine_id;
	}

	public double getPurchase_price() {
		return purchase_price;
	}

	public void setPurchase_price(double d) {
		this.purchase_price = d;
	}

	public int getPurchase_qty() {
		return purchase_qty;
	}

	public void setPurchase_qty(int purchase_qty) {
		this.purchase_qty = purchase_qty;
	}

}
