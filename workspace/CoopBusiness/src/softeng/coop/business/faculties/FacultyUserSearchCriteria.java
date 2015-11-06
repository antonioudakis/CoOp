/**
 * 
 */
package softeng.coop.business.faculties;

import softeng.coop.business.SearchCriteria;
import softeng.coop.dataaccess.University;
import softeng.coop.dataaccess.Department;
import softeng.coop.dataaccess.Division;

/** 
 * <!-- begin-UML-doc -->
 * <p>Criteria for faculty users search.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class FacultyUserSearchCriteria extends SearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Prefix of name, if specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String name;

	/** 
	 * @return the name
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getName()
	{
		// begin-user-code
		return name;
		// end-user-code
	}

	/** 
	 * @param name the name to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setName(String name)
	{
		// begin-user-code
		this.name = name;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Prefix of surname, if specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String surname;

	/** 
	 * @return the surname
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getSurname()
	{
		// begin-user-code
		return surname;
		// end-user-code
	}

	/** 
	 * @param surname the surname to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setSurname(String surname)
	{
		// begin-user-code
		this.surname = surname;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The faculty users' university, if specified, else null. If department or division are specified, this is ignored.</p>
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

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The faculty users' department, if specified, else null. If division is specified, this is ignored.</p>
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

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The faculty users' division, if specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Division division;

	/** 
	 * @return the division
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Division getDivision()
	{
		// begin-user-code
		return division;
		// end-user-code
	}

	/** 
	 * @param division the division to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setDivision(Division division)
	{
		// begin-user-code
		this.division = division;
		// end-user-code
	}
}