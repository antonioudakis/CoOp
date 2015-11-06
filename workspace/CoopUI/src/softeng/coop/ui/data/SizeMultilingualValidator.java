package softeng.coop.ui.data;

import java.util.*;

/**
 * Abstract base validator for checking sizes of arrays, collections and strings.
 */
@SuppressWarnings("serial")
public abstract class SizeMultilingualValidator
extends MultilingualValidator
{
	public SizeMultilingualValidator()
	{
	}

	protected Integer getSize(Object value)
	{
		if (value == null) return null;
		
		if (value instanceof Collection)
		{
			Collection<?> collection = (Collection<?>)value;
			
			return collection.size();
		}
		else if (value instanceof CharSequence)
		{
			CharSequence sequence = (CharSequence)value;
			
			return sequence.length();
		}
		else if (value instanceof Object[])
		{
			Object[] array = (Object[])value;
			
			return array.length;
		}
		
		return null;
	}
	
}
