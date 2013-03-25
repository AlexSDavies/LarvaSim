import java.util.ArrayList;
import java.util.List;

public class MultiOdourSource extends OdourSource {

	public List<OdourSource> odourSources;
	
	public MultiOdourSource(ArrayList<OdourSource> odourSources)
	{
		this.odourSources = odourSources;
	}
	
	public double getValue(Point p)
	{
		double sum = 0;
		
		for(OdourSource s : odourSources)
			{sum += s.getValue(p);}
			
		return sum;
	}
	
	
}
