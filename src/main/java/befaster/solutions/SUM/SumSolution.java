package befaster.solutions.SUM;

import befaster.runner.SolutionNotImplementedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SumSolution {

    public Integer compute(int x, int y) {
		Integer result = null;
		if (x < 0 || x > 100 || y < 0 || y > 100) {
			log.info("Paramters must be 0-100");
			return result;
		}

		result = x + y;
		log.info("Result is {}", result);
		return result;
	}

}


