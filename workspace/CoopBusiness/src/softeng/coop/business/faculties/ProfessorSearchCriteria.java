/**
 * 
 */
package softeng.coop.business.faculties;

/** 
 * <!-- begin-UML-doc -->
 * <p>Criteria for professors search.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class ProfessorSearchCriteria extends FacultyUserSearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The year where the professor supervised coops, else 0 if unspecified.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Integer supervisedCoOpYear;

	/** 
	 * @return the supervisedCoOpYear
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Integer getSupervisedCoOpYear()
	{
		// begin-user-code
		return supervisedCoOpYear;
		// end-user-code
	}

	/** 
	 * @param supervisedCoOpYear the supervisedCoOpYear to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setSupervisedCoOpYear(Integer supervisedCoOpYear)
	{
		// begin-user-code
		this.supervisedCoOpYear = supervisedCoOpYear;
		// end-user-code
	}
}