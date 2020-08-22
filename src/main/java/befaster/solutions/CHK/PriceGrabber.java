package befaster.solutions.CHK;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PriceGrabber {

	public static final int A_MULTI = 130;
	public static final int B_MULTI = 45;
	
	public static Integer getUnitPrice(char cha) {
		Integer price = null;
		switch (cha) {
			case 'A': {
				price =  50;
				break;
			}
			case 'B': {
				price =  30;
				break;
			}
			case 'C': {
				price =  20;
				break;
			}
			case 'D': {
				price =  15;
				break;
			}
		}
		return price;
	}

	public static Integer getPrice(String skus)
	{
		Integer price = null;
		if (skus == null || skus.length() < 2) {
			return price;
		}
		String[] aSplits = skus.split("AAA");
		String[] bSplits = skus.split("BB");
		
		price = aSplits.length * A_MULTI + bSplits.length * B_MULTI;
		skus = 	skus.replaceAll("AAA", "").replaceAll("BB", "");
		Stream<Character> charStream = skus.chars().mapToObj(c -> (char) c);
		// TODO: Reduction from long to int
		price += (int) charStream.collect(Collectors.summarizingInt(c -> getUnitPrice(c))).getSum();
		return price;
	}

}

