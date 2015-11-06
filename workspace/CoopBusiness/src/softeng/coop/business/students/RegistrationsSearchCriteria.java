/**
 * 
 */
package softeng.coop.business.students;

import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Student;
import softeng.coop.dataaccess.Lesson;
import softeng.coop.dataaccess.Location;
import softeng.coop.dataaccess.Category;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class RegistrationsSearchCriteria extends StudentsSearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>If not null, search for registrations where the qualifiedForAssignment field matches this value.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean qualifiedForAssignment;

	/** 
	 * @return the qualifiedForAssignment
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isQualifiedForAssignment()
	{
		// begin-user-code
		return qualifiedForAssignment;
		// end-user-code
	}

	/** 
	 * @param qualifiedForAssignment the qualifiedForAssignment to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setQualifiedForAssignment(Boolean qualifiedForAssignment)
	{
		// begin-user-code
		this.qualifiedForAssignment = qualifiedForAssignment;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>If not null, search for registrations where the qualifiedForCompletion field matches this value.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean qualifiedForCompletion;

	/** 
	 * @return the qualifiedForCompletion
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isQualifiedForCompletion()
	{
		// begin-user-code
		return qualifiedForCompletion;
		// end-user-code
	}

	/** 
	 * @param qualifiedForCompletion the qualifiedForCompletion to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setQualifiedForCompletion(Boolean qualifiedForCompletion)
	{
		// begin-user-code
		this.qualifiedForCompletion = qualifiedForCompletion;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>If not null, search for registrations for the specified coop.</p>
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
	 * <p>If not null, search for registrations that belong to the specified student.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Student student;

	/** 
	 * @return the student
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Student getStudent()
	{
		// begin-user-code
		return student;
		// end-user-code
	}

	/** 
	 * @param student the student to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setStudent(Student student)
	{
		// begin-user-code
		this.student = student;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>If not null, search for registrations where the 'passed' field matches this value.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean passed;

	/** 
	 * @return the passed
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isPassed()
	{
		// begin-user-code
		return passed;
		// end-user-code
	}

	/** 
	 * @param passed the passed to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setPassed(Boolean passed)
	{
		// begin-user-code
		this.passed = passed;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Float minGrade;

	/** 
	 * @return the minGrade
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Float getMinGrade()
	{
		// begin-user-code
		return minGrade;
		// end-user-code
	}

	/** 
	 * @param minGrade the minGrade to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setMinGrade(Float minGrade)
	{
		// begin-user-code
		this.minGrade = minGrade;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Float maxGrade;

	/** 
	 * @return the maxGrade
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Float getMaxGrade()
	{
		// begin-user-code
		return maxGrade;
		// end-user-code
	}

	/** 
	 * @param maxGrade the maxGrade to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setMaxGrade(Float maxGrade)
	{
		// begin-user-code
		this.maxGrade = maxGrade;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>If not null, search for registrations for coops belonging to the specified lesson.</p>
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
	private Boolean active;

	/** 
	 * @return the active
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isActive()
	{
		// begin-user-code
		return active;
		// end-user-code
	}

	/** 
	 * @param active the active to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setActive(Boolean active)
	{
		// begin-user-code
		this.active = active;
		// end-user-code
	}
}