package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ferrari.finances.dk.bank.developertools.InterestRateTestTool;

public class BankTest { //ansvar:Martin review Shahnaz

	@Test
	public void testGetCurrentRate() {
		double fixedRate = 5.0;
		assertEquals(5.0, InterestRateTestTool.newInterestRateMock(fixedRate).todaysRate(), 0);

	}

}
