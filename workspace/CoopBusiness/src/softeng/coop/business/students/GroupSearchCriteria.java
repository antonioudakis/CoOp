/**
 * 
 */
package softeng.coop.business.students;

import softeng.coop.business.SearchCriteria;
import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Location;
import softeng.coop.dataaccess.Category;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class GroupSearchCriteria extends SearchCriteria
{
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
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean assignedToJob;

	/** 
	 * @return the assignedToJob
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isAssignedToJob()
	{
		// begin-user-code
		return assignedToJob;
		// end-user-code
	}

	/** 
	 * @param assignedToJob the assignedToJob to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setAssignedToJob(Boolean assignedToJob)
	{
		// begin-user-code
		this.assignedToJob = assignedToJob;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
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
}