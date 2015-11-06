/**
 * 
 */
package softeng.coop.business.students;

import softeng.coop.business.SearchCriteria;
import softeng.coop.dataaccess.Department;
import java.util.Set;
import softeng.coop.dataaccess.Location;
import softeng.coop.dataaccess.CoOp;
import java.util.Date;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class StudentsSearchCriteria extends SearchCriteria
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

	/** 
	 * <!-- begin-UML-doc -->
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
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Set<Location> preferedLocations;

	/** 
	 * @return the preferedLocations
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Set<Location> getPreferedLocations()
	{
		// begin-user-code
		return preferedLocations;
		// end-user-code
	}

	/** 
	 * @param preferedLocations the preferedLocations to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setPreferedLocations(Set<Location> preferedLocations)
	{
		// begin-user-code
		this.preferedLocations = preferedLocations;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasIBAN;

	/** 
	 * @return the hasIBAN
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasIBAN()
	{
		// begin-user-code
		return hasIBAN;
		// end-user-code
	}

	/** 
	 * @param hasIBAN the hasIBAN to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasIBAN(Boolean hasIBAN)
	{
		// begin-user-code
		this.hasIBAN = hasIBAN;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasAMA;

	/** 
	 * @return the hasAMA
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasAMA()
	{
		// begin-user-code
		return hasAMA;
		// end-user-code
	}

	/** 
	 * @param hasAMA the hasAMA to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasAMA(Boolean hasAMA)
	{
		// begin-user-code
		this.hasAMA = hasAMA;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasContract;

	/** 
	 * @return the hasContract
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasContract()
	{
		// begin-user-code
		return hasContract;
		// end-user-code
	}

	/** 
	 * @param hasContract the hasContract to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasContract(Boolean hasContract)
	{
		// begin-user-code
		this.hasContract = hasContract;
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
	private Boolean graded;

	/** 
	 * @return the graded
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isGraded()
	{
		// begin-user-code
		return graded;
		// end-user-code
	}

	/** 
	 * @param graded the graded to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setGraded(Boolean graded)
	{
		// begin-user-code
		this.graded = graded;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasSocialSecurityId;

	/** 
	 * @return the hasSocialSecurityId
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasSocialSecurityId()
	{
		// begin-user-code
		return hasSocialSecurityId;
		// end-user-code
	}

	/** 
	 * @param hasSocialSecurityId the hasSocialSecurityId to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasSocialSecurityId(Boolean hasSocialSecurityId)
	{
		// begin-user-code
		this.hasSocialSecurityId = hasSocialSecurityId;
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
	private Boolean hasUserName;

	/** 
	 * @return the hasUserName
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasUserName()
	{
		// begin-user-code
		return hasUserName;
		// end-user-code
	}

	/** 
	 * @param hasUserName the hasUserName to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasUserName(Boolean hasUserName)
	{
		// begin-user-code
		this.hasUserName = hasUserName;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Integer academicYear;

	/** 
	 * @return the academicYear
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Integer getAcademicYear()
	{
		// begin-user-code
		return academicYear;
		// end-user-code
	}

	/** 
	 * @param academicYear the academicYear to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setAcademicYear(Integer academicYear)
	{
		// begin-user-code
		this.academicYear = academicYear;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Date createdAfter;

	/** 
	 * @return the createdAfter
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Date getCreatedAfter()
	{
		// begin-user-code
		return createdAfter;
		// end-user-code
	}

	/** 
	 * @param createdAfter the createdAfter to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setCreatedAfter(Date createdAfter)
	{
		// begin-user-code
		this.createdAfter = createdAfter;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Date createdBefore;

	/** 
	 * @return the createdBefore
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Date getCreatedBefore()
	{
		// begin-user-code
		return createdBefore;
		// end-user-code
	}

	/** 
	 * @param createdBefore the createdBefore to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setCreatedBefore(Date createdBefore)
	{
		// begin-user-code
		this.createdBefore = createdBefore;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasFatherName;

	/** 
	 * @return the hasFatherName
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasFatherName()
	{
		// begin-user-code
		return hasFatherName;
		// end-user-code
	}

	/** 
	 * @param hasFatherName the hasFatherName to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasFatherName(Boolean hasFatherName)
	{
		// begin-user-code
		this.hasFatherName = hasFatherName;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasMotherName;

	/** 
	 * @return the hasMotherName
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasMotherName()
	{
		// begin-user-code
		return hasMotherName;
		// end-user-code
	}

	/** 
	 * @param hasMotherName the hasMotherName to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasMotherName(Boolean hasMotherName)
	{
		// begin-user-code
		this.hasMotherName = hasMotherName;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasDateOfBirth;

	/** 
	 * @return the hasDateOfBirth
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasDateOfBirth()
	{
		// begin-user-code
		return hasDateOfBirth;
		// end-user-code
	}

	/** 
	 * @param hasDateOfBirth the hasDateOfBirth to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasDateOfBirth(Boolean hasDateOfBirth)
	{
		// begin-user-code
		this.hasDateOfBirth = hasDateOfBirth;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Integer minAddressesCount;

	/** 
	 * @return the minAddressesCount
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Integer getMinAddressesCount()
	{
		// begin-user-code
		return minAddressesCount;
		// end-user-code
	}

	/** 
	 * @param minAddressesCount the minAddressesCount to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setMinAddressesCount(Integer minAddressesCount)
	{
		// begin-user-code
		this.minAddressesCount = minAddressesCount;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Integer minTelephonesCount;

	/** 
	 * @return the minTelephonesCount
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Integer getMinTelephonesCount()
	{
		// begin-user-code
		return minTelephonesCount;
		// end-user-code
	}

	/** 
	 * @param minTelephonesCount the minTelephonesCount to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setMinTelephonesCount(Integer minTelephonesCount)
	{
		// begin-user-code
		this.minTelephonesCount = minTelephonesCount;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasSerialNumber;

	/** 
	 * @return the hasSerialNumber
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasSerialNumber()
	{
		// begin-user-code
		return hasSerialNumber;
		// end-user-code
	}

	/** 
	 * @param hasSerialNumber the hasSerialNumber to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasSerialNumber(Boolean hasSerialNumber)
	{
		// begin-user-code
		this.hasSerialNumber = hasSerialNumber;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasIdNumber;

	/** 
	 * @return the hasIdNumber
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasIdNumber()
	{
		// begin-user-code
		return hasIdNumber;
		// end-user-code
	}

	/** 
	 * @param hasIdNumber the hasIdNumber to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasIdNumber(Boolean hasIdNumber)
	{
		// begin-user-code
		this.hasIdNumber = hasIdNumber;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasIdIssuer;

	/** 
	 * @return the hasIdIssuer
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasIdIssuer()
	{
		// begin-user-code
		return hasIdIssuer;
		// end-user-code
	}

	/** 
	 * @param hasIdIssuer the hasIdIssuer to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasIdIssuer(Boolean hasIdIssuer)
	{
		// begin-user-code
		this.hasIdIssuer = hasIdIssuer;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasIdIssueDate;

	/** 
	 * @return the hasIdIssueDate
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasIdIssueDate()
	{
		// begin-user-code
		return hasIdIssueDate;
		// end-user-code
	}

	/** 
	 * @param hasIdIssueDate the hasIdIssueDate to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasIdIssueDate(Boolean hasIdIssueDate)
	{
		// begin-user-code
		this.hasIdIssueDate = hasIdIssueDate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasTaxId;

	/** 
	 * @return the hasTaxId
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasTaxId()
	{
		// begin-user-code
		return hasTaxId;
		// end-user-code
	}

	/** 
	 * @param hasTaxId the hasTaxId to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasTaxId(Boolean hasTaxId)
	{
		// begin-user-code
		this.hasTaxId = hasTaxId;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasTaxDivision;

	/** 
	 * @return the hasTaxDivision
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasTaxDivision()
	{
		// begin-user-code
		return hasTaxDivision;
		// end-user-code
	}

	/** 
	 * @param hasTaxDivision the hasTaxDivision to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasTaxDivision(Boolean hasTaxDivision)
	{
		// begin-user-code
		this.hasTaxDivision = hasTaxDivision;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Boolean hasNationality;

	/** 
	 * @return the hasNationality
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Boolean isHasNationality()
	{
		// begin-user-code
		return hasNationality;
		// end-user-code
	}

	/** 
	 * @param hasNationality the hasNationality to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setHasNationality(Boolean hasNationality)
	{
		// begin-user-code
		this.hasNationality = hasNationality;
		// end-user-code
	}
}