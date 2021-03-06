package befaster.solutions.CHK;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class CheckoutSolutionTest {
	
	private CheckoutSolution checkoutSolution;

	@BeforeAll
	public void setUp()
	{
		checkoutSolution = new CheckoutSolution();
	}

	@Test
	void testCheckout()
	{
		// Assert valid return values
		assertEquals(-1, checkoutSolution.checkout(null));
		// Empty basket is valid
		assertEquals(0, checkoutSolution.checkout(""));
		assertEquals(-1, checkoutSolution.checkout("1"));
		assertEquals(-1, checkoutSolution.checkout("e"));
		assertThat(checkoutSolution.checkout("ABBC"), greaterThan(-1));
	}

}
