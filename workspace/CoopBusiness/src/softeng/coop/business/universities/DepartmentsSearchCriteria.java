/**
 * 
 */
package softeng.coop.business.universities;

import softeng.coop.dataaccess.University;

/** 
 * <!-- begin-UML-doc -->
 * <p>Criteria for departments search.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class DepartmentsSearchCriteria extends UniversitiesSearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Prefix of name of the departments to match, if specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String departmentsName;

	/** 
	 * @return the departmentsName
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getDepartmentsName()
	{
		// begin-user-code
		return departmentsName;
		// end-user-code
	}

	/** 
	 * @param departmentsName the departmentsName to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setDepartmentsName(String departmentsName)
	{
		// begin-user-code
		this.departmentsName = departmentsName;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private University university;

	/** 
	 * @return the university
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public University getUniversity()
	{
		// begin-user-code
		return university;
		// end-user-code
	}

	/** 
	 * @param university the university to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setUniversity(University university)
	{
		// begin-user-code
		this.university = university;
		// end-user-code
	}
}