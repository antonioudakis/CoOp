package softeng.coop.business.authentication.mock;

import softeng.coop.business.authentication.IUserAttribute;

public class MockUserAttribute implements IUserAttribute
{
	private String name;
	private String value;
	
	public MockUserAttribute(String name, String value)
	{
		if (name == null) throw new IllegalArgumentException("Argument 'name' must not be null.");
		if (value == null) throw new IllegalArgumentException("Argument 'value' must not be null.");
		
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public String getValue()
	{
		return this.value;
	}

	@Override
	public String getFriendlyName()
	{
		return this.name;
	}
}
