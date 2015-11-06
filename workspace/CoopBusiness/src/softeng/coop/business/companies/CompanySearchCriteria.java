/**
 * 
 */
package softeng.coop.business.companies;

import softeng.coop.business.SearchCriteria;
import softeng.coop.dataaccess.Location;
import softeng.coop.dataaccess.Category;

/** 
 * <!-- begin-UML-doc -->
 * <p>Criteria for companies search.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class CompanySearchCriteria extends SearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Optionally, the location of the company (or branch if searching for such), else null. The location will be searched recursively, i.e. companies residing in sub-locations of the given location will match the search.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Location location;

	/** 
	 * @return the location
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Location getLocation()
	{
		// begin-user-code
		return location;
		// end-user-code
	}

	/** 
	 * @param location the location to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setLocation(Location location)
	{
		// begin-user-code
		this.location = location;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Optionally, the category of the company, else null. The category will be searched recursively, i.e. companies tagged with sub-categories of the given category will match the search.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Category category;

	/** 
	 * @return the category
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Category getCategory()
	{
		// begin-user-code
		return category;
		// end-user-code
	}

	/** 
	 * @param category the category to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setCategory(Category category)
	{
		// begin-user-code
		this.category = category;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String companyName;

	/** 
	 * @return the companyName
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getCompanyName()
	{
		// begin-user-code
		return companyName;
		// end-user-code
	}

	/** 
	 * @param companyName the companyName to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setCompanyName(String companyName)
	{
		// begin-user-code
		this.companyName = companyName;
		// end-user-code
	}
}