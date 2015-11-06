/**
 * 
 */
package softeng.coop.business.coops;

import softeng.coop.business.SearchCriteria;
import softeng.coop.dataaccess.Department;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class LessonSearchCriteria extends SearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Department department;

	/** 
	 * @return the department
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Department getDepartment()
	{
		// begin-user-code
		return department;
		// end-user-code
	}

	/** 
	 * @param department the department to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setDepartment(Department department)
	{
		// begin-user-code
		this.department = department;
		// end-user-code
	}
}