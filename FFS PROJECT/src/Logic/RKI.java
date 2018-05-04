package Logic;
import com.ferrari.finances.dk.rki.CreditRator;

public class RKI {
	
	private static RKI inst = null;
	
	private RKI() {}
	
	public static RKI instance() {
		if (inst == null) 
			inst = new RKI();
		
		return inst;
	}
	
	
	public void setCreditRating(Customer customer) {
		customer.setRating(CreditRator.i().rate(customer.getCPR()));
		
	}
	

}
