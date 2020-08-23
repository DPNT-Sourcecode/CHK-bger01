package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.List;
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
			case 'E': {
				price =  40;
				break;
			}
		}
		return price;
	}

	public static Integer getPrice(String skus)
	{
		Integer price = null;
		if (skus == null) {
			return price;
		}
		price = 0;
		if (skus.length() == 0) {
			return price;
		}
		
		Map<Character, Integer> itemCounts = countItems(skus);
		List<MultiBuy> multiBuys = getMultiBuys(skus);
		
		
		price += aMultiBuyCount * A_MULTI;
		price += bMultiBuyCount * B_MULTI;

		// Remove multibuy products
		itemCounts.put('A', itemCounts.get('A') - aMultiBuyCount * 3);
		itemCounts.put('B', itemCounts.get('B') - bMultiBuyCount * 2);
		
		// Sums individual unit price against how many there are.
		int remainingProductPrice = itemCounts.entrySet().stream()
				.mapToInt(x -> getUnitPrice(x.getKey()) * x.getValue()).sum();
		return price + remainingProductPrice;
	}

	private static List<MultiBuy> getMultiBuys(String skus)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private static int getMultiBuyCount(char c, Integer num)
	{
		int numGroups = 0;
		if (c == 'A') {
			numGroups = Math.floorDiv(num, 3);
		}
		if (c == 'B') {
			numGroups = Math.floorDiv(num, 2);
		}
		return numGroups;
	}

	private static Map<Character, Integer> countItems(String skus)
	{
		// Init a map with Every possible itemcode and 0 total
		Map<Character, Integer> itemMap = "ABCD".chars().collect(HashMap::new,
            (newMap, cha) -> newMap.put((char) cha, 0), HashMap<Character,Integer>::putAll
        );

		List<Character> charList = skus.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
		for (Character c : itemMap.keySet()) {
			itemMap.put(c, charList.stream().mapToInt(x -> c.equals(x) ? 1 : 0).sum());
		}
		return itemMap;
	}

}


