package softeng.coop.business.authentication.mock;

import softeng.coop.business.authentication.*;
import java.util.*;

public class MockUndergraduateUser implements IUser
{
	private String userName = "el96696@ntua.gr";
	private String commonName = "Εμμανουηλ Λιοσσης";
	private String firstName = "Εμμανουηλ";
	private String lastName = "Λιοσσης";
	private String email = "el96696@central.ntua.gr";
	private String organizationUnit = "School of Electrical & Computer Engineering";
	private String businessCategory = "Undergraduate Student";
	private String affiliation = "student";
	private String primaryAffiliation = "student";
	
	private HashMap<String, IUserAttribute> attributes;
	
	public MockUndergraduateUser()
	{
//		buildFields();
		buildAttributes();
	}

	private void buildAttributes()
	{
		this.attributes = new HashMap<String, IUserAttribute>();

		this.addAttribute("eduPersonTargetedID", "w85d6eMFPGcD7YADkPH02r6GHCo=");
		this.addAttribute("businessCategory", "Undergraduate Student");
		this.addAttribute("eduPersonAffiliation", "student");
		this.addAttribute("ou", "School of Electrical & Computer Engineering");
		this.addAttribute("givenName;lang-el", "Εμμανουηλ");
		this.addAttribute("mail", "el96696@central.ntua.gr");
		this.addAttribute("eduPersonPrimaryAffiliation", "student");
		this.addAttribute("cn;lang-el", "Εμμανουηλ Λιοσσης");
		this.addAttribute("eduPersonPrincipalName", "el96696@ntua.gr");
		this.addAttribute("eduPersonOrgUnitDN", "ou=Dep4,ou=units,dc=ntua,dc=gr");
		this.addAttribute("eduPersonScopedAffiliation", "student@ntua.gr");
		this.addAttribute("eduPersonEntitlement", "urn:mace:dir:entitlement:shared:common-lib-terms");
		this.addAttribute("sn;lang-el", "Λιοσσης");
		this.addAttribute("eduPersonPrimaryOrgUnitDN", "ou=Dep4,ou=units,dc=ntua,dc=gr");
	}
	
	private void addAttribute(String name, String value)
	{
		this.attributes.put(name, new MockUserAttribute(name, value));
	}

//	private void buildFields()
//	{
//		this.userName = "el96696@ntua.gr";
//		this.commonName = "Εμμανουηλ Λιοσσης";
//		this.firstName = "Εμμανουηλ";
//		this.lastName = "Λιοσσης";
//		this.email = "el96696@central.ntua.gr";
//		this.organizationUnit = "School of Electrical & Computer Engineering";
//		this.businessCategory = "Undergraduate Student";
//		this.affiliation = "student";
//		this.primaryAffiliation = "student";
//	}

	@Override
	public IUserAttribute getAttribute(String name)
	{
		return this.attributes.get(name);
	}

	@Override
	public Collection<IUserAttribute> getAttributes()
	{
		return this.attributes.values();
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
