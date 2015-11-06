/**
 * 
 */
package softeng.coop.business.universities;

import softeng.coop.business.SearchCriteria;
import softeng.coop.dataaccess.Location;

/** 
 * <!-- begin-UML-doc -->
 * <p>Criteria for universities search.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class UniversitiesSearchCriteria extends SearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The location of the university, if specified, else null. All sub-locations are matched too.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Location location;

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
	 * <p>Prefix of name of the universities to match, if specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String universitiesName;

	/** 
	 * @return the universitiesName
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getUniversitiesName()
	{
		// begin-user-code
		return universitiesName;
		// end-user-code
	}

	/** 
	 * @param universitiesName the universitiesName to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setUniversitiesName(String universitiesName)
	{
		// begin-user-code
		this.universitiesName = universitiesName;
		// end-user-code
	}
}