package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import befaster.solutions.CHK.offers.BonusBuy;
import befaster.solutions.CHK.offers.MultiBuy;
import befaster.solutions.CHK.offers.MultiBuyOffer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PriceGrabber {

	public static final int A_MULTI = 130;
	public static final int B_MULTI = 45;
	private static final List<MultiBuy> MULTI_PURCHASE_LIST = createMultiBuyDirectory();
	private static final List<BonusBuy> BONUS_BUY_LIST = createBonusBuyDirectory();

	public static Integer getUnitPrice(char cha)
	{
		Integer price = null;
		switch (cha) {
			case 'A' : {
				price = 50;
				break;
			}
			case 'B' : {
				price = 30;
				break;
			}
			case 'C' : {
				price = 20;
				break;
			}
			case 'D' : {
				price = 15;
				break;
			}
			case 'E' : {
				price = 40;
				break;
			}
		}
		return price;
	}

	/**
	 * This would usually be loaded from a database or something.
	 * 
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

	private static List<BonusBuy> createBonusBuyDirectory()
	{
		List<BonusBuy> directory = new ArrayList<>(1);
		directory.add(new BonusBuy('E', 2, 'B', 1));
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

		// (Optionally) take out any free items.
		for (BonusBuy bonusBuy : BONUS_BUY_LIST) {
			Character freeItem = bonusBuy.getFreeItem();
			int numApplicable = isApplicable(itemCounts, bonusBuy);
			// We simply remove the number of free items from the item counts
			itemCounts.put(freeItem, itemCounts.get(freeItem) - numApplicable * bonusBuy.getNumFree());
		}

		// An ordered set of offers available we should check for
		LinkedHashSet<MultiBuy> multiBuys = getApplicableMultiBuys();
		for (MultiBuy multiBuy : multiBuys) {
			Character multiBuyChar = multiBuy.getItem();
			int numApplicable = isApplicable(itemCounts, multiBuy);
			// Add to price and remove from basket
			price += numApplicable > 0 ? numApplicable * multiBuy.getTotalPrice() : 0;
			itemCounts.put(multiBuyChar, itemCounts.get(multiBuyChar) - numApplicable * multiBuy.getNumRequired());
		}

		// Sums individual unit price against how many there are.
		int remainingProductPrice = itemCounts.entrySet().stream().mapToInt(x -> getUnitPrice(x.getKey()) * x.getValue()).sum();
		return price + remainingProductPrice;
	}

	private static int isApplicable(Map<Character, Integer> itemCounts, MultiBuyOffer multiBuy)
	{
		return Math.floorDiv(itemCounts.get(multiBuy.getItem()), multiBuy.getNumRequired());
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

	private static Map<Character, Integer> countItems(String skus)
	{
		// Init a map with Every possible itemcode and 0 total
		Map<Character, Integer> itemMap = new HashMap(CheckoutSolution.AVAILABLE_ITEMS.size());
		for (Character cha : CheckoutSolution.AVAILABLE_ITEMS) {
			itemMap.put(cha, 0);
		}

		List<Character> charList = skus.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
		for (Character c : itemMap.keySet()) {
			itemMap.put(c, charList.stream().mapToInt(x -> c.equals(x) ? 1 : 0).sum());
		}
		return itemMap;
	}

}


