package larvae;

public class WeathervaneParameters extends MinForwardParameters
{

	public double
		wvKernelStartVal,
		wvKernelEndVal,
		wvKernelDuration,
		wvSpeed,
		wvAngle;
	
	public WeathervaneParameters()
	{
		
		super();
		
		wvKernelDuration = 1;
		wvKernelStartVal = 1;
		wvKernelEndVal = 1;
		wvSpeed = 0.25*castSpeed;
		wvAngle = Math.PI / 9;
		
	}
	
	
}
