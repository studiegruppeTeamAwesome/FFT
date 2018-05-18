package logic;

import java.time.LocalDateTime;

public class LoanOffer {
	private int id;
	private double annualCost;
	private int downPayment;
	private int repayments;
	private int numberOfMonths;
	private Customer costumer;
	private Car car;
	private Salesman salesman;
	
	public LoanOffer(double annualCost, int downPayment, int repayments, int numberOfMonths,
			Customer costumer, Car car, Salesman salesman) {

		this.annualCost = annualCost;
		this.downPayment = downPayment;
		this.repayments = repayments;
		this.numberOfMonths = numberOfMonths;
		this.costumer = costumer;
		this.car = car;
		this.salesman = salesman;
	}

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

	public Customer getCostumer() {
		return costumer;
	}

	public void setCostumerPhone(Customer costumer) {
		this.costumer = costumer;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
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
