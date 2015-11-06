/**
 * 
 */
package softeng.coop.business.jobpostings;

import softeng.coop.business.SearchCriteria;
import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Location;
import softeng.coop.dataaccess.Company;
import softeng.coop.dataaccess.Category;

/** 
 * <!-- begin-UML-doc -->
 * <p>Criteria used for search of job postings.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class JobPostingsSearchCriteria extends SearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private CoOp coop;

	/** 
	 * @return the coop
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public CoOp getCoop()
	{
		// begin-user-code
		return coop;
		// end-user-code
	}

	/** 
	 * @param coop the coop to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setCoop(CoOp coop)
	{
		// begin-user-code
		this.coop = coop;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The location of the companies branches that offer the job postings, if specified, else null.</p>
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
	 * <p>The company offering the job postings, is specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Company company;

	/** 
	 * @return the company
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Company getCompany()
	{
		// begin-user-code
		return company;
		// end-user-code
	}

	/** 
	 * @param company the company to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setCompany(Company company)
	{
		// begin-user-code
		this.company = company;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The category of the companies offering the job postings, else null.</p>
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
	private Boolean empty;

	/** 
	 * @return the empty
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isEmpty()
	{
		// begin-user-code
		return empty;
		// end-user-code
	}

	/** 
	 * @param empty the empty to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setEmpty(Boolean empty)
	{
		// begin-user-code
		this.empty = empty;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean full;

	/** 
	 * @return the full
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isFull()
	{
		// begin-user-code
		return full;
		// end-user-code
	}

	/** 
	 * @param full the full to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setFull(Boolean full)
	{
		// begin-user-code
		this.full = full;
		// end-user-code
	}
}