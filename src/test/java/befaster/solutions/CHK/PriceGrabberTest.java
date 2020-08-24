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
		assertThat(PriceGrabber.getUnitPrice('E'), is(40));
		assertNull(PriceGrabber.getUnitPrice('F'));
		assertThat(PriceGrabber.getUnitPrice('A'), is(50));
	}

	@Test
	void testGetPrice()
	{
		// Single purchases are allowed
		assertThat(PriceGrabber.getPrice("A"), is(50));
		assertThat(PriceGrabber.getPrice("D"), is(15));
		assertThat(PriceGrabber.getPrice("AA"), is(100));
		assertThat(PriceGrabber.getPrice("AAA"), is(130));
		assertThat(PriceGrabber.getPrice("BB"), is(45));
		assertThat(PriceGrabber.getPrice("BBB"), is(75));
		// 6 a's
		assertThat(PriceGrabber.getPrice("AAAAAA"), is(250));
		// 4 a's, 2 b's
		assertThat(PriceGrabber.getPrice("ABAABA"), is(130 + 50 + 45));
		assertThat(PriceGrabber.getPrice("ABCDE"), is(50 + 30 + 20 + 15 + 40));
		// 3 a's, 2 b's
		assertThat(PriceGrabber.getPrice("ABACDBA"), is(130 + 45 + 20 + 15));
		// 9 a's, 4 b's
		assertThat(PriceGrabber.getPrice("ABAABAAAABBAA"), is(200 + 130 + 50 + 90));
		// 1 free Bs from buying Es
		assertThat(PriceGrabber.getPrice("EEB"), is(80));
		assertThat(PriceGrabber.getPrice("EEEB"), is(120));
		// 2 free Bs from buying Es
		assertThat(PriceGrabber.getPrice("EEEEBB"), is(160));
		// USer doesn't add a B to the basket
		assertThat(PriceGrabber.getPrice("EE"), is(80));
	}
}
