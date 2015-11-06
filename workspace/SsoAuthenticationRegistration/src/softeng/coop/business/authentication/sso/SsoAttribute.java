package softeng.coop.business.authentication.sso;

import softeng.coop.business.authentication.*;

import dk.itst.oiosaml.sp.*;

class SsoAttribute implements IUserAttribute
{
	private String name;
	private String value;
	private String friendlyName;
	
	SsoAttribute(UserAttribute attribute)
	{
		if (attribute == null) 
			throw new IllegalArgumentException("Argument 'attribute' must not be null.");
		
		this.name = attribute.getName();
		this.friendlyName = attribute.getFriendlyName();
		this.value = attribute.getValue();
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public String getValue()
	{
		return value;
	}

	@Override
	public String getFriendlyName()
	{
		return friendlyName;
	}

}
