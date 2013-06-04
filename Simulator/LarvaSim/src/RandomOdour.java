
public class RandomOdour extends OdourSource {

	private double maxVal;
	
	public RandomOdour()
	{
		maxVal = 0.1;
	}
	
	public void setMaxVal(double maxVal)
	{
		this.maxVal = maxVal;
	}

	@Override
	public double getValue(Point p) {
		return Math.random()*2*maxVal-maxVal;
	}

}
