package befaster.solutions.CHK.offers;

import java.util.List;

import lombok.Data;

@Data
public class MultiBuy extends MultiBuyOffer {

	private List<Character> applicableItems;
	private int numRequired;
	private int totalPrice;
	private int charPriority;

	public MultiBuy(List<Character> applicableItems, int numRequired, int totalPrice, int charPriority) {
		super();
		this.applicableItems = applicableItems;
		this.numRequired = numRequired;
		this.totalPrice = totalPrice;
		this.charPriority = charPriority;
	}

	public boolean isApplicable(String skus)
	{
		return false;
	}

}