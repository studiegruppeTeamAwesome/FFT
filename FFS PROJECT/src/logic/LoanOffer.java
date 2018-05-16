package logic;

import java.time.LocalDateTime;

public class LoanOffer {
	private int id;
	private LocalDateTime date;
	private double annualCost;
	private int downPayment;
	private int repayments;
	private int numberOfMonths;
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
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

	public int getNumberOfMonths() {
		return numberOfMonths;
	}

	public void setNumberOfMonths(int numberOfMonths) {
		this.numberOfMonths = numberOfMonths;
	}

	public double getAnnualCost() {
		return annualCost;
	}

	public void setAnnualCost(double annualCost) {
		this.annualCost = annualCost;
	}

}
