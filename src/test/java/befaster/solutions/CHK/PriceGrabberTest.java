package befaster.solutions.CHK;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class PriceGrabberTest {

	@Test
	void testGetUnitPrice()
	{
		assertNull(PriceGrabber.getUnitPrice('e'));
		assertThat(PriceGrabber.getUnitPrice('E'), is(40));
		assertNotNull(PriceGrabber.getUnitPrice('F'));
		assertNull(PriceGrabber.getUnitPrice('G'));
		assertThat(PriceGrabber.getUnitPrice('A'), is(50));
	}

	@Test
	void testGetPrice()
	{
		assertThat(PriceGrabber.getPrice("FF"), is(20));
		assertThat(PriceGrabber.getPrice("FFF"), is(20));
		assertThat(PriceGrabber.getPrice("FFFF"), is(30));
		assertThat(PriceGrabber.getPrice("FFFFF"), is(40));
		assertThat(PriceGrabber.getPrice("FFFFFF"), is(40));
	}
}


