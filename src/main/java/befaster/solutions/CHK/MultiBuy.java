package befaster.solutions.CHK;

import lombok.Data;

@Data
public class MultiBuy {

	private Character item;
	private int numRequired;
	private int totalPrice;

	public MultiBuy(Character item, int numRequired, int totalPrice) {
		super();
		this.item = item;
		this.numRequired = numRequired;
		this.totalPrice = totalPrice;
	}

}
