package logic;

public class LoanOffer {
	private int id;
	private double annualCost;
	private int downPayment;
	private double repayments;
	private int numberOfMonths;
	private Customer costumer;
	private Car car;
	private Salesman salesman;
	private boolean approved;

	public LoanOffer(int id, double annualCost, int downPayment, double repayments, int numberOfMonths,
			Customer costumer, Car car, Salesman salesman) {

		this.id = id;
		this.annualCost = annualCost;
		this.downPayment = downPayment;
		this.repayments = repayments;
		this.numberOfMonths = numberOfMonths;
		this.costumer = costumer;
		this.car = car;
		this.salesman = salesman;
	}

	public LoanOffer(double annualCost, int downPayment, double repayments, int numberOfMonths, Customer costumer,
			Car car, Salesman salesman) {

		this.annualCost = annualCost;
		this.downPayment = downPayment;
		this.repayments = repayments;
		this.numberOfMonths = numberOfMonths;
		this.costumer = costumer;
		this.car = car;
		this.salesman = salesman;
	}

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

	public double getRepayments() {
		return repayments;
	}

	public void setRepayments(double repayments) {
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
