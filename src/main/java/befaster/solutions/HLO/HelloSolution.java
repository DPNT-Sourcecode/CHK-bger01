package befaster.solutions.HLO;

public class HelloSolution {
	public String hello(String friendName)
	{
		String output = null;
		if (friendName != null) {
			output = "Hello, " + friendName + "!";
		}
		return output;
	}
}
