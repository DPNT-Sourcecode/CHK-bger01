package befaster.solutions.CHK.offers;

import lombok.Data;

@Data
public class MultiBuy extends MultiBuyOffer{

	private int totalPrice;
	private int charPriority;
	
	public MultiBuy(int totalPrice, int charPriority) {
		super();
		this.totalPrice = totalPrice;
		this.charPriority = charPriority;
	}

}

