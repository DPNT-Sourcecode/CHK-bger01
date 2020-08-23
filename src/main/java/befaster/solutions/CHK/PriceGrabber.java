package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import befaster.solutions.CHK.offers.BonusBuy;
import befaster.solutions.CHK.offers.MultiBuy;
import befaster.solutions.CHK.offers.MultiBuyOffer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PriceGrabber {

	public static final int A_MULTI = 130;
	public static final int B_MULTI = 45;
	private static final List<MultiBuy> MULTI_PURCHASE_LIST = createMultiBuyDirectory();
	
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

	/**
	 * This would usually be loaded from a database or something.
	 * @return
	 */
	private static List<MultiBuy> createMultiBuyDirectory()
	{
		List<MultiBuy> directory = new ArrayList<>(3);
		directory.add(new MultiBuy('A', 5, 200, 1));
		directory.add(new MultiBuy('A', 3, 130, 2));
		directory.add(new MultiBuy('B', 2, 45, 1));
		return directory;
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
		// An ordered set of offers available we should check for
		LinkedHashSet<MultiBuy> multiBuys = getApplicableMultiBuys();
		for (MultiBuy multiBuy : multiBuys) {
			int numApplicable = isApplicable(itemCounts, multiBuy);
			// Add to price and remove from basket
			price += numApplicable > 0 ? numApplicable * multiBuy.getTotalPrice() : 0;
			itemCounts.put('A', itemCounts.get('A') - numApplicable * multiBuy.getNumRequired());
		}
		
		// TODO: Throw any free extras in at the end??
//		List<BonusBuy> bonusBuys = getBonusBuys(skus);
		
		// Sums individual unit price against how many there are.
		int remainingProductPrice = itemCounts.entrySet().stream()
				.mapToInt(x -> getUnitPrice(x.getKey()) * x.getValue()).sum();
		return price + remainingProductPrice;
	}

	private static int isApplicable(Map<Character, Integer> itemCounts, MultiBuy multiBuy)
	{
		return Math.floorDiv(itemCounts.get(multiBuy.getItem()), multiBuy.getNumRequired());
	}

	private static List<BonusBuy> getBonusBuys(String skus)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private static LinkedHashSet<MultiBuy> getApplicableMultiBuys()
	{
		LinkedHashSet<MultiBuy> set = new LinkedHashSet<>(3);
		List<MultiBuy> priority1Offers = MULTI_PURCHASE_LIST.stream().filter(x -> x.getCharPriority() == 1).collect(Collectors.toList());
		List<MultiBuy> priority2Offers = MULTI_PURCHASE_LIST.stream().filter(x -> x.getCharPriority() == 2).collect(Collectors.toList());
		set.addAll(priority1Offers);
		set.addAll(priority2Offers);
		return set;
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
