package logic;

// Used in calculating the repayment plan
public enum LoanPlanComponent {
	/*
	 * OUT_DEBT is the outstanding debt of the loan
	 * RATE is the amount of kr thats interest out of the monthly repayment 
	 * INSTALL the amount thats actually being paid off the loan
	 */
	
	OUT_DEBT, RATE, INSTALL;
}
