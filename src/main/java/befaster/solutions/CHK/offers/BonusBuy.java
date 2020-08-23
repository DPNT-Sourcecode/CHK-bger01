package befaster.solutions.CHK.offers;

import lombok.Data;

@Data
public class BonusBuy extends MultiBuyOffer {

	private int freeItem;
	private int numFree;

	public BonusBuy(int freeItem, int numFree) {
		super();
		this.freeItem = freeItem;
		this.numFree = numFree;
	}
}

