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
		assertThat(PriceGrabber.getPrice("AA"), is(100));
		assertThat(PriceGrabber.getPrice("AAA"), is(130));
		assertThat(PriceGrabber.getPrice("BB"), is(45));
		assertThat(PriceGrabber.getPrice("BBB"), is(75));
		assertThat(PriceGrabber.getPrice("AAAAAA"), is(260));
		assertThat(PriceGrabber.getPrice("ABAABA"), is(130 + 50 + 45));
	}

	@Test
	void testGetMultiBuyCount()
	{
		// As are groups of 3
		assertThat(new PriceGrabber().getMultiBuyCount('A', 7), is(2));
		assertThat(new PriceGrabber().getMultiBuyCount('A', 5), is(1));
		// Bs are 2
		assertThat(new PriceGrabber().getMultiBuyCount('B', 9), is(4));
		assertThat(new PriceGrabber().getMultiBuyCount('B', 10), is(5));
		assertThat(new PriceGrabber().getMultiBuyCount('B', 11), is(5));
	}
}

