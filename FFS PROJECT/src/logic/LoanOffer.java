package logic;

public class LoanOffer {
	private int id;
	private String date;
	private int downPayment;
	private int repayments;
	private int costumerPhone;
	private int carId;
	private String salesmanName;
	private boolean approved;

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(int downPayment) {
		this.downPayment = downPayment;
	}

	public int getRepayments() {
		return repayments;
	}

	public void setRepayments(int repayments) {
		this.repayments = repayments;
	}

	public int getCostumerPhone() {
		return costumerPhone;
	}

	public void setCostumerPhone(int costumerPhone) {
		this.costumerPhone = costumerPhone;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanId) {
		this.salesmanName = salesmanId;
	}

}
