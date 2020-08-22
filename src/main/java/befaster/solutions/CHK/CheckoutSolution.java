package befaster.solutions.CHK;

public class CheckoutSolution {
	
	public Integer checkout(String skus)
	{
		return PriceGrabber.getPrice(skus);
	}
	
}