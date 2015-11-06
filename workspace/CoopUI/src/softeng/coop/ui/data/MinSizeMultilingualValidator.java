package softeng.coop.ui.data;

/**
 * A multilingual validator checking minimum size of 
 * collections, arrays and strings.
 */
@SuppressWarnings("serial")
public class MinSizeMultilingualValidator
extends SizeMultilingualValidator
{
	private int minimum;
	
	public MinSizeMultilingualValidator(int minimum)
	{
		this.minimum = minimum;
	}

	@Override
	public boolean isValid(Object value)
	{
		Integer size = getSize(value);
		
		if (size == null) return true;
		
		return size >= minimum;
	}
	
	public int getMinimum()
	{
		return minimum;
	}

	@Override
	protected String getMessage()
	{
		String template = super.getMessage();
		
		return String.format(template, minimum);
	}
}
