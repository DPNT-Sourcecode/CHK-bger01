package befaster.solutions.CHK;

public class CheckoutSolution {
	
	public Integer checkout(String skus)
	{
		if (skus == null || skus.length() == 0) {
			return -1;
		}
		return PriceGrabber.getPrice(skus);
	}
	
}
