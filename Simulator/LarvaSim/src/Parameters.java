import java.lang.reflect.Field;

public abstract class Parameters {

	public String getParamString()
	{
		
		String paramString = "";
		
		for(Field f : this.getClass().getFields())
		{
			
			paramString += f.getName() + " ";
			
			try
			{
				paramString += f.get(this).toString() + "\n";
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return paramString;
	
	}
	
}
