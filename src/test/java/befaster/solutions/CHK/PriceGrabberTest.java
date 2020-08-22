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
		// Single purchases are allowed
		assertThat(PriceGrabber.getPrice("A"), is(50));
		assertThat(PriceGrabber.getPrice("D"), is(15));
		assertThat(PriceGrabber.getPrice("AA"), is(100));
		assertThat(PriceGrabber.getPrice("AAA"), is(130));
		assertThat(PriceGrabber.getPrice("BB"), is(45));
		assertThat(PriceGrabber.getPrice("BBB"), is(75));
		assertThat(PriceGrabber.getPrice("AAAAAA"), is(260));
		assertThat(PriceGrabber.getPrice("ABAABA"), is(130 + 50 + 45));
		assertThat(PriceGrabber.getPrice("ABCD"), is(50 + 30 + 20 + 15));
		assertThat(PriceGrabber.getPrice("ABACDBA"), is(130 + 45 + 20 + 15));
	}
}


