package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
//		if (skus == null || skus.length() < 2) {
//			return price;
//		}
//		price = 0;
//		
//		Map<Char, Integer> itemCoutns = countItems(skus);
//		
//		if (aSplits.length == 0 || bSplits.length == 00 || !aSplits[0].equals(bSplits[0])) {
//			price = aSplits.length * A_MULTI + bSplits.length * B_MULTI;
//			skus = 	skus.replaceAll("AAA", "").replaceAll("BB", "");
//		}
//		
//		Stream<Character> charStream = skus.chars().mapToObj(c -> (char) c);
//		// TODO: Reduction from long to int
//		price += (int) charStream.collect(Collectors.summarizingInt(c -> getUnitPrice(c))).getSum();
		return price;
	}

	// todo: private 
	public static Map<Character, Integer> countItems(String skus)
	{
		// Init a map with Every possible itemcode and 0 total
		Map<Character, Integer> itemMap = "ABCD".chars().collect(HashMap::new,
            (newMap, cha) -> newMap.put((char) cha, 0), HashMap<Character,Integer>::putAll
        );
		
		itemMap.entrySet().stream().forEach(x -> log.debug(x.getKey() + " -> " + x.getValue()));
		return itemMap;
	}

}
