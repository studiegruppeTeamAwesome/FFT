package Logic;

public class FFSController implements FacadeController {

	@Override

	public double getCurrentRate() {

		return Bank.instance().getCurrentRate();

	}

	@Override

	public void setCreditRating(Customer customer) {

		RKI.instance().setCreditRating(customer);

	}

}