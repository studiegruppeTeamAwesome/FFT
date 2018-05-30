package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class) // ansvar:Martin, review Shahaz
@SuiteClasses({ BankTest.class, RKITest.class, CalculatorTest.class, DataLayerImpTest.class, FFSControllerTest.class })
public class AllTests {

}