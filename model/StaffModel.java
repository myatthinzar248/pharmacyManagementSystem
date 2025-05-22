package model;

public class StaffModel {

	private String staff_name;
	private String password;
	private String staff_id;
	private String role;
	private String phone;
	private String address;
	private String nrc;
	private String email;
	private String dateOfBirdth;
	private String dateOfJob;
	private float salary;

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDateOfBirdth() {
		return dateOfBirdth;
	}

	public void setDateOfBirdth(String dateOfBirdth) {
		this.dateOfBirdth = dateOfBirdth;
	}

	public String getDateOfJob() {
		return dateOfJob;
	}

	public void setDateOfJob(String dateOfJob) {
		this.dateOfJob = dateOfJob;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
