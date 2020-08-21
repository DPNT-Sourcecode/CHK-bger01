package befaster.solutions.HLO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class HelloSolutionTest {
	private HelloSolution helloApp;

	@BeforeAll
	public void setUp()
	{
		helloApp = new HelloSolution();
	}

	@Test
	public void testHello()
	{
		assertEquals(null, helloApp.hello(null));
		assertEquals("Hello, World!", helloApp.hello(""));
	}

}


