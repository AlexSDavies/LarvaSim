package larvae;
import java.lang.reflect.Field;

public abstract class Parameters {

	
	// Returns a string of line separated parameter names and values
	// This allows parameters to be e.g. dumped to a file
	public String getParamString()
	{
		
		String paramString = "";
		
		// Loops through all fields of this class (which should all be parameters)
		// and print them along with their names.
		for(Field f : this.getClass().getFields())
		{
			
			paramString += f.getName() + " ";
			
			try
				{paramString += f.get(this).toString() + "\n";}
			catch (Exception e)
				{e.printStackTrace();}
			
		}
		return paramString;
	
	}
	
}
