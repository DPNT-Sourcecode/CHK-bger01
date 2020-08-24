package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import befaster.solutions.CHK.offers.BonusBuy;
import befaster.solutions.CHK.offers.MultiBuy;
import befaster.solutions.CHK.offers.MultiBuyOffer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PriceGrabber {

	private static final List<MultiBuy> MULTI_PURCHASE_LIST = createMultiBuyDirectory();
	private static final List<BonusBuy> BONUS_BUY_LIST = createBonusBuyDirectory();

	// TODO: Load from file
	public static Integer getUnitPrice(char cha)
	{
		Integer price = null;
		switch (cha) {
			case 'F' : 
			case 'O' : 
			case 'H' : {
				price = 10;
				break;
			}
			case 'D' : 
			case 'M' : {
				price = 15;
				break;
			}
			case 'X' : {
				price = 17;
				break;
			}
			case 'C' : 
			case 'G' : 
			case 'S' : 
			case 'T' : 
			case 'W' : 
			case 'Y' : {
				price = 20;
				break;
			}
			case 'Z' : {
				price = 21;
				break;
			}
			case 'B' :
			case 'Q' : {
				price = 30;
				break;
			}
			case 'I' : {
				price = 35;
				break;
			}
			case 'E' :
			case 'N' :
			case 'U' : {
				price = 40;
				break;
			}
			case 'A' :
			case 'P' :
			case 'R' :
			case 'V' : {
				price = 50;
				break;
			}
			case 'J' : {
				price = 60;
				break;
			}
			case 'K' : {
				price = 70;
				break;
			}
			case 'L' : {
				price = 90;
				break;
			}

			// Ensure we cater for all items in CheckoutSolution.createItemDirectory on add
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
		directory.add(new MultiBuy(Collections.singletonList('A'), 5, 200, 1));
		directory.add(new MultiBuy(Collections.singletonList('A'), 3, 130, 2));
		directory.add(new MultiBuy(Collections.singletonList('B'), 2, 45, 1));
		directory.add(new MultiBuy(Collections.singletonList('H'), 10, 80, 1));
		directory.add(new MultiBuy(Collections.singletonList('H'), 5, 45, 2));
		directory.add(new MultiBuy(Collections.singletonList('K'), 2, 150, 1));
		directory.add(new MultiBuy(Collections.singletonList('P'), 5, 200, 1));
		directory.add(new MultiBuy(Collections.singletonList('Q'), 3, 80, 1));
		directory.add(new MultiBuy(Collections.singletonList('V'), 3, 130, 1));
		directory.add(new MultiBuy(Arrays.asList('S', 'T', 'X', 'Y', 'Z'), 3, 45, 1));
		return directory;
	}

	private static List<BonusBuy> createBonusBuyDirectory()
	{
		List<BonusBuy> directory = new ArrayList<>(1);
		directory.add(new BonusBuy('E', 2, 'B', 1));
		directory.add(new BonusBuy('F', 2, 'F', 1));
		directory.add(new BonusBuy('N', 3, 'M', 1));
		directory.add(new BonusBuy('R', 3, 'Q', 1));
		directory.add(new BonusBuy('U', 3, 'U', 1));
		return directory;
	}

	// TODO: Method too big
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
		Map<Character, Integer> freeItems = new HashMap<>(CheckoutSolution.AVAILABLE_ITEMS.size());
		for (BonusBuy bonusBuy : BONUS_BUY_LIST) {
			Character freeItem = bonusBuy.getFreeItem();
			int numApplicable = isApplicable(itemCounts, bonusBuy);
			// Keep tally of free items
			freeItems.putIfAbsent(freeItem, 0);
			freeItems.computeIfPresent(freeItem, (key, val) -> val += numApplicable * bonusBuy.getNumFree());
		}
		for (Entry<Character, Integer> entry : freeItems.entrySet()) {
			int numInBasket = itemCounts.get(entry.getKey()) - entry.getValue();
			// Don't allow negative vals.
			itemCounts.put(entry.getKey(), Math.max(numInBasket, 0));
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

	/**
	 * 
	 * @param itemCounts
	 * @param multiBuy
	 * @return int the number of multibuys of this flavour that are applicable
	 */
	private static int isApplicable(Map<Character, Integer> itemCounts, MultiBuyOffer multiBuy)
	{
		int numPromosAchieved;
		int numInBasket = itemCounts.get(multiBuy.getItem());
		if (multiBuy instanceof BonusBuy) {
			numPromosAchieved = numBonusBuyApplicable(multiBuy, numInBasket);
		}
		// We can only be BonusBuy or MultiBuy
		else {
			List<Character> offerProducts = ((MultiBuy) multiBuy).getApplicableItems();
			boolean isGroupOffer = offerProducts.size() > 1;
			if (isGroupOffer) {
				numPromosAchieved = numMultiBuysApplicable(((MultiBuy) multiBuy), itemCounts);
			}
			else {
				numPromosAchieved = Math.floorDiv(numInBasket, multiBuy.getNumRequired());
			}
		}
		return numPromosAchieved;
	}

	private static int numMultiBuysApplicable(MultiBuy multiBuy, Map<Character, Integer> itemCounts)
	{
		List<Character> offerProducts = multiBuy.getApplicableItems();
		int numInBasket = 0;
		for (Character cha : offerProducts) {
			numInBasket += itemCounts.get(cha);
		}
		return Math.floorDiv(numInBasket, multiBuy.getNumRequired());
	}

	private static int numBonusBuyApplicable(MultiBuyOffer multiBuy,  int numInBasket)
	{
		int numApplicable = 0;
		BonusBuy theOffer = ((BonusBuy) multiBuy);
		// If the free item is already being purchased, ensure it's in the basket
		int numSameItemFree = theOffer.getItem().equals(theOffer.getFreeItem()) ? theOffer.getNumFree() : 0;
		// This logic needs tidying, we only check for additional same items if the above is > 0
		int numNeededInBasket = theOffer.getNumRequired() + numSameItemFree;
		if (numInBasket >= numNeededInBasket) {
			numApplicable = Math.floorDiv(numInBasket, numNeededInBasket);
		}
		// Otherwise we aren't entitled to the free one.
		return numApplicable;
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



