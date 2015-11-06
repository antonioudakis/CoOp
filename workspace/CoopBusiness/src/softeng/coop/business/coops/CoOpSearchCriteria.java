/**
 * 
 */
package softeng.coop.business.coops;

import softeng.coop.business.SearchCriteria;
import softeng.coop.dataaccess.Department;
import java.util.Date;
import softeng.coop.dataaccess.Student;
import softeng.coop.dataaccess.Lesson;
import softeng.coop.dataaccess.University;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class CoOpSearchCriteria extends SearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>True if only active coops should be returned.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private boolean activeOnly;

	/** 
	 * @return the activeOnly
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean isActiveOnly()
	{
		// begin-user-code
		return activeOnly;
		// end-user-code
	}

	/** 
	 * @param activeOnly the activeOnly to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setActiveOnly(boolean activeOnly)
	{
		// begin-user-code
		this.activeOnly = activeOnly;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The year of the coops, else 0 if unspecified.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private int year;

	/** 
	 * @return the year
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public int getYear()
	{
		// begin-user-code
		return year;
		// end-user-code
	}

	/** 
	 * @param year the year to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setYear(int year)
	{
		// begin-user-code
		this.year = year;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The semester of the coops, 1 for winter, 2 for spring, else 0 if unspecified.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private int semester;

	/** 
	 * @return the semester
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public int getSemester()
	{
		// begin-user-code
		return semester;
		// end-user-code
	}

	/** 
	 * @param semester the semester to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setSemester(int semester)
	{
		// begin-user-code
		this.semester = semester;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The department of the coops, else null if unspecified.</p>
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
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Date startingAfter;

	/** 
	 * @return the startingAfter
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Date getStartingAfter()
	{
		// begin-user-code
		return startingAfter;
		// end-user-code
	}

	/** 
	 * @param startingAfter the startingAfter to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setStartingAfter(Date startingAfter)
	{
		// begin-user-code
		this.startingAfter = startingAfter;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Date endingBefore;

	/** 
	 * @return the endingBefore
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Date getEndingBefore()
	{
		// begin-user-code
		return endingBefore;
		// end-user-code
	}

	/** 
	 * @param endingBefore the endingBefore to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setEndingBefore(Date endingBefore)
	{
		// begin-user-code
		this.endingBefore = endingBefore;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private boolean setupOnly;

	/** 
	 * @return the setupOnly
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean isSetupOnly()
	{
		// begin-user-code
		return setupOnly;
		// end-user-code
	}

	/** 
	 * @param setupOnly the setupOnly to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setSetupOnly(boolean setupOnly)
	{
		// begin-user-code
		this.setupOnly = setupOnly;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private boolean inRegistrationOnly;

	/** 
	 * @return the inRegistrationOnly
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean isInRegistrationOnly()
	{
		// begin-user-code
		return inRegistrationOnly;
		// end-user-code
	}

	/** 
	 * @param inRegistrationOnly the inRegistrationOnly to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setInRegistrationOnly(boolean inRegistrationOnly)
	{
		// begin-user-code
		this.inRegistrationOnly = inRegistrationOnly;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Student registeredByStudent;

	/** 
	 * @return the registeredByStudent
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Student getRegisteredByStudent()
	{
		// begin-user-code
		return registeredByStudent;
		// end-user-code
	}

	/** 
	 * @param registeredByStudent the registeredByStudent to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setRegisteredByStudent(Student registeredByStudent)
	{
		// begin-user-code
		this.registeredByStudent = registeredByStudent;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Lesson lesson;

	/** 
	 * @return the lesson
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Lesson getLesson()
	{
		// begin-user-code
		return lesson;
		// end-user-code
	}

	/** 
	 * @param lesson the lesson to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setLesson(Lesson lesson)
	{
		// begin-user-code
		this.lesson = lesson;
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