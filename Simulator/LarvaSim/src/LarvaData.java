

// This class stores a snapshot of the larva data
public class LarvaData{

	private double t;
	private double odourValueHead;
	private double odourValueMid;
	private double headAngle;
	private Point headPos;
	private Point midPos;
	private Point tailPos;
	private double bearing;
	private double perception;
	private double turnProb;
	private double headCastProb;
	private double angle;
	private double dAngle;
	
	// Create a larva data point with larva and a time
	// (Rates of change default to 0
	public LarvaData(double t,Larva l)
	{
		
		this.t = t;
		odourValueHead = l.getOdourValueHead();
		odourValueMid = l.getOdourValueMid();
		headAngle = l.getRelativeHeadAngle();
		angle = l.getBodyAngle();
		headPos = l.getPos().head;
		midPos = l.getPos().mid;
		tailPos = l.getPos().tail;
		bearing = l.getBearing();
		perception = l.getPerception();
		turnProb = l.getTurnProbability();
		headCastProb = l.getHeadCastStopProbability();
				
	}
	
	// Create a larva data point with larva, time and previous data point
	// (This allows setting rates of change)
	public LarvaData(double t,Larva l,LarvaData prevData)
	{
		
		this(t,l);
		
		dAngle = (angle - prevData.getAngle())/(t - prevData.getT());
		
	}
	
	
	private double getAngle() {
		return angle;
	}

	public double getT() {
		return t;
	}

	public double getOdourValueHead() {
		return odourValueHead;
	}
	
	public double getOdourValueMid() {
		return odourValueMid;
	}

	public double getHeadAngle() {
		return headAngle;
	}
	
	public double getBearing(){
		return bearing;
	}

	public Point getHeadPos() {
		return headPos;
	}

	public Point getMidPos() {
		return midPos;
	}

	public Point getTailPos() {
		return tailPos;
	}

	public double getPerception() {
		return perception;
	}

	public double getTurnProb() {
		return turnProb;
	}


	public double getHeadCastProb() {
		return headCastProb;
	}

	public double getDeltaAngle() {
		return dAngle;
	}

	
	
	// Headings for output
	// (Note: these should match representation given by toString!)
	public static String getOutputHeadings() {
		String headings = "time perception angle headAngle dAngle bearing";
		return headings;
	}
	
	public String toString()
	{
		String output = "";
		
		output += getT() + " "
				+ getPerception() + " "
				+ getAngle() + " "
				+ getHeadAngle() + " "
				+ getDeltaAngle() + " "
				+ getBearing() + " ";
		
		return output;
	}
	
	
}
