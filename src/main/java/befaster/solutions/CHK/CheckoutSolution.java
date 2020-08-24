package befaster.solutions.CHK;

import java.util.ArrayList;
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
		// Happily we sell A-Z at the moment
		List<Character> chas = new ArrayList<>(26);
		char c;
		for(c = 'A'; c <= 'Z'; ++c) {
			chas.add(c);
		}
		return chas;
	}

}

