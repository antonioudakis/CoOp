package softeng.coop.business.authentication.sso;

import java.util.*;

import softeng.coop.business.authentication.*;

import dk.itst.oiosaml.sp.*;

public class SsoUser implements IUser
{
	private String userName;
	private String commonName;
	private String firstName;
	private String lastName;
	private String email;
	private String organizationUnit;
	private String businessCategory;
	private String affiliation;
	private String primaryAffiliation;
	
	private HashMap<String, IUserAttribute> attributesMap;
	
	SsoUser(UserAssertion userAssertion)
	{
		if (userAssertion == null) 
			throw new IllegalArgumentException("Argument 'userAssertion' must not be null.");
		
		setupBasicFields(userAssertion);
		
		setupAttributesMap(userAssertion);
	}

	private void setupAttributesMap(UserAssertion userAssertion)
	{
		this.attributesMap = new HashMap<String, IUserAttribute>();
		
		Collection<UserAttribute> attributes = userAssertion.getAllAttributes();
		
		for (UserAttribute attribute : attributes)
		{
			this.attributesMap.put(attribute.getName(), new SsoAttribute(attribute));
		}
	}

	private void setupBasicFields(UserAssertion userAssertion)
	{
		UserAttribute principalNameAttribute = // eduPersonPrincipalName
			userAssertion.getAttribute("urn:oid:1.3.6.1.4.1.5923.1.1.1.6"); 
		
		if (principalNameAttribute == null)
			throw new IllegalArgumentException("principal must have eduPersonPrincipalName attribute set.");
		
		this.userName = principalNameAttribute.getValue();
		
		this.commonName = userAssertion.getCommonName();
		
		UserAttribute firstNameAttribute = // givenName
			userAssertion.getAttribute("urn:oid:2.5.4.42");
		
		if (firstNameAttribute != null) this.firstName = firstNameAttribute.getValue();
		
		UserAttribute lastNameAttribute = // sn
			userAssertion.getAttribute("urn:oid:2.5.4.4");
		
		if (lastNameAttribute != null) this.lastName = lastNameAttribute.getValue();

		this.email = userAssertion.getMail();
		
		this.organizationUnit = userAssertion.getOrganizationUnit();
		
		UserAttribute businessCategoryAtribute = // businessCategory
			userAssertion.getAttribute("urn:oid:2.5.4.15");
		
		if (businessCategoryAtribute != null)
			this.businessCategory = businessCategoryAtribute.getValue();
		
		UserAttribute affiliationAttribute = // eduPersonAffiliation
			userAssertion.getAttribute("urn:oid:1.3.6.1.4.1.5923.1.1.1.1");
		
		if (affiliationAttribute != null)
			this.affiliation = affiliationAttribute.getValue();
		
		UserAttribute primaryAffiliationAttribute = // eduPersonPrimaryAffiliation
			userAssertion.getAttribute("urn:oid:1.3.6.1.4.1.5923.1.1.1.5");
		
		this.primaryAffiliation = primaryAffiliationAttribute.getValue();
	}
	
	@Override
	public IUserAttribute getAttribute(String name)
	{
		return this.attributesMap.get(name);
	}

	@Override
	public Collection<IUserAttribute> getAttributes()
	{
		return this.attributesMap.values();
	}

	@Override
	public String getUserName()
	{		
		return this.userName;
	}

	@Override
	public String getCommonName()
	{
		return this.commonName;
	}

	@Override
	public String getFirstName()
	{
		return this.firstName;
	}

	@Override
	public String getLastName()
	{
		return this.lastName;
	}

	@Override
	public String getEmail()
	{
		return this.email;
	}

	@Override
	public String getOrganizationUnit()
	{
		return this.organizationUnit;
	}

	@Override
	public String getBusinessCategory()
	{
		return this.businessCategory;
	}

	@Override
	public String getAffiliation()
	{
		return this.affiliation;
	}

	@Override
	public String getPrimaryAffiliation()
	{
		return this.primaryAffiliation;
	}

}
