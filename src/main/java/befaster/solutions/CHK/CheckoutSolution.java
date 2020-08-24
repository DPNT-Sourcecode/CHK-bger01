package befaster.solutions.CHK;

import java.util.Arrays;
import java.util.List;

public class CheckoutSolution {

	public static final List<Character> AVAILABLE_ITEMS = createItemDirectory();

	public Integer checkout(String skus)
	{
		if (skus == null || !skus.matches("[A-Z]*$")) {
			return -1;
		}
		return PriceGrabber.getPrice(skus);
	}

	private static List<Character> createItemDirectory()
	{
		return Arrays.asList('A', 'B', 'C', 'D', 'E', 'F');
	}

}