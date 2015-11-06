package softeng.coop.ui.data;

import java.util.*;

import softeng.coop.dataaccess.*;

/**
 * When applied to a collection of telephones,
 * it requires that at least one of them be of type 'Mobile'.
 */
@SuppressWarnings("serial")
public class AtLeastOneMobilePhoneValidator
extends MultilingualValidator
{

	@SuppressWarnings("unchecked")
	@Override
	public boolean isValid(Object value)
	{
		if (value == null) return false;
		
		Collection<Telephone> telephones = (Collection<Telephone>)value;
		
		for (Telephone telephone : telephones)
		{
			if (telephone.getType() == TelephoneType.Mobile) return true;
		}
		
		return false;
	}

}
