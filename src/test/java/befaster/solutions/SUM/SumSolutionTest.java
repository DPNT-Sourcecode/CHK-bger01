package befaster.solutions.SUM;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Before;
import org.junit.Test;

import accelerate.io.App;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SumSolutionTest {
    private SumSolution sum;

    @Before
    public void setUp() {

        sum = new SumSolution();
    }

    @Test
    public void compute_sum() {
        assertThat(sum.compute(1, 1), equalTo(2));
		assertNull(sum.compute(-1, 0), "Param 0-100 check");
		assertNull(sum.compute(0, -1), "Param 0-100 check");
		assertNull(sum.compute(101, 0), "Param 0-100 check");
		assertNull(sum.compute(0, 101), "Param 0-100 check");
		assertEquals(1, sum.compute(1, 0), "Working params");
		assertEquals(100, sum.compute(0, 100), "Working params");
		assertEquals(42, sum.compute(40, 2), "Working params");
    }
}
