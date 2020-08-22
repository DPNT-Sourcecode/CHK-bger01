package befaster.solutions.CHK;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class PriceGrabberTest {

	@Test
	void testGetUnitPrice()
	{
		assertNull(PriceGrabber.getUnitPrice('e'));
		assertNull(PriceGrabber.getUnitPrice('E'));
		assertThat(PriceGrabber.getUnitPrice('A'), is(50));
	}

	@Test
	void testGetPrice()
	{
		assertNull(PriceGrabber.getPrice("e"));
		assertNull(PriceGrabber.getPrice("A"));
		assertNull(PriceGrabber.getPrice("AA"));
		assertThat(PriceGrabber.getPrice("AAA"), is(130));
		assertThat(PriceGrabber.getPrice("BB"), is(45));
		assertThat(PriceGrabber.getPrice("BBB"), is(75));
	}
}


