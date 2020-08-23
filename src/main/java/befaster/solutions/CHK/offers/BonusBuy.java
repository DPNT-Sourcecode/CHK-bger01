package befaster.solutions.CHK.offers;

import lombok.Data;

@Data
public class BonusBuy extends MultiBuyOffer {

	private Character item;
	private int numRequired;
	private int freeItem;
	private int numFree;
	
	public BonusBuy(Character item, int numRequired, int freeItem, int numFree) {
		super();
		this.item = item;
		this.numRequired = numRequired;
		this.freeItem = freeItem;
		this.numFree = numFree;
	}

}
