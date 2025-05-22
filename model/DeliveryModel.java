package model;

public class DeliveryModel {
	private String delilvery_id;
	private String order_id;
	private String delivery_date;
	private String status;
	private String staff_id;
	private Double delivery_cost;
	private String deliveryAddress;
	
	public String getDelilvery_id() {
		return delilvery_id;
	}
	public void setDelilvery_id(String delilvery_id) {
		this.delilvery_id = delilvery_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}
	public Double getDelivery_cost() {
		return delivery_cost;
	}
	public void setDelivery_cost(Double delivery_cost) {
		this.delivery_cost = delivery_cost;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
}
