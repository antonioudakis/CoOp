/**
 * 
 */
package softeng.coop.dataaccess;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
public class CoOp implements Serializable, ITrackedEntity
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private static final long serialVersionUID = 0;

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public CoOp()
	{
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@GeneratedValue
	@Id
	private int id;

	/** 
	 * @return the id
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public int getId()
	{
		return id;
	}

	/** 
	 * @param id the id to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToOne
	private Multilingual name;

	/** 
	 * @return the name
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Multilingual getName()
	{
		// begin-user-code
		return name;
		// end-user-code
	}

	/** 
	 * @param name the name to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setName(Multilingual name)
	{
		// begin-user-code
		this.name = name;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private int academicYear;

	/** 
	 * @return the academicYear
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public int getAcademicYear()
	{
		// begin-user-code
		return academicYear;
		// end-user-code
	}

	/** 
	 * @param academicYear the academicYear to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAcademicYear(int academicYear)
	{
		// begin-user-code
		this.academicYear = academicYear;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private int semester;

	/** 
	 * @return the semester
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public int getSemester()
	{
		// begin-user-code
		return semester;
		// end-user-code
	}

	/** 
	 * @param semester the semester to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSemester(int semester)
	{
		// begin-user-code
		this.semester = semester;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private GradePolicyType gradePolicy;

	/** 
	 * @return the gradePolicy
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public GradePolicyType getGradePolicy()
	{
		// begin-user-code
		return gradePolicy;
		// end-user-code
	}

	/** 
	 * @param gradePolicy the gradePolicy to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setGradePolicy(GradePolicyType gradePolicy)
	{
		// begin-user-code
		this.gradePolicy = gradePolicy;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean active;

	/** 
	 * @return the active
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isActive()
	{
		// begin-user-code
		return active;
		// end-user-code
	}

	/** 
	 * @param active the active to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setActive(boolean active)
	{
		// begin-user-code
		this.active = active;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean isGroupCoOp;

	/** 
	 * @return the isGroupCoOp
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isGroupCoOp()
	{
		// begin-user-code
		return isGroupCoOp;
		// end-user-code
	}

	/** 
	 * @param isGroupCoOp the isGroupCoOp to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setGroupCoOp(boolean isGroupCoOp)
	{
		// begin-user-code
		this.isGroupCoOp = isGroupCoOp;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean hasGroupGrade;

	/** 
	 * @return the hasGroupGrade
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isHasGroupGrade()
	{
		// begin-user-code
		return hasGroupGrade;
		// end-user-code
	}

	/** 
	 * @param hasGroupGrade the hasGroupGrade to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setHasGroupGrade(boolean hasGroupGrade)
	{
		// begin-user-code
		this.hasGroupGrade = hasGroupGrade;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	Professor academicDirector;

	/** 
	 * @return the academicDirector
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Professor getAcademicDirector()
	{
		// begin-user-code
		return academicDirector;
		// end-user-code
	}

	/** 
	 * @param academicDirector the academicDirector to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAcademicDirector(Professor academicDirector)
	{
		// begin-user-code
		this.academicDirector = academicDirector;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	Set<Professor> supervisingProfessors;

	/** 
	 * @return the supervisingProfessors
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Professor> getSupervisingProfessors()
	{
		// begin-user-code
		return supervisingProfessors;
		// end-user-code
	}

	/** 
	 * @param supervisingProfessors the supervisingProfessors to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSupervisingProfessors(Set<Professor> supervisingProfessors)
	{
		// begin-user-code
		this.supervisingProfessors = supervisingProfessors;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "coOp")
	Set<Requirement> requirements;

	/** 
	 * @return the requirements
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Requirement> getRequirements()
	{
		// begin-user-code
		return requirements;
		// end-user-code
	}

	/** 
	 * @param requirements the requirements to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setRequirements(Set<Requirement> requirements)
	{
		// begin-user-code
		this.requirements = requirements;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Temporal(value = TemporalType.DATE)
	private Date startDate;

	/** 
	 * @return the startDate
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Date getStartDate()
	{
		// begin-user-code
		return startDate;
		// end-user-code
	}

	/** 
	 * @param startDate the startDate to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setStartDate(Date startDate)
	{
		// begin-user-code
		this.startDate = startDate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Temporal(value = TemporalType.DATE)
	private Date endDate;

	/** 
	 * @return the endDate
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Date getEndDate()
	{
		// begin-user-code
		return endDate;
		// end-user-code
	}

	/** 
	 * @param endDate the endDate to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setEndDate(Date endDate)
	{
		// begin-user-code
		this.endDate = endDate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private int jobDurationDays;

	/** 
	 * @return the jobDurationDays
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public int getJobDurationDays()
	{
		// begin-user-code
		return jobDurationDays;
		// end-user-code
	}

	/** 
	 * @param jobDurationDays the jobDurationDays to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setJobDurationDays(int jobDurationDays)
	{
		// begin-user-code
		this.jobDurationDays = jobDurationDays;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean isInsideUniversity;

	/** 
	 * @return the isInsideUniversity
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isInsideUniversity()
	{
		// begin-user-code
		return isInsideUniversity;
		// end-user-code
	}

	/** 
	 * @param isInsideUniversity the isInsideUniversity to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setInsideUniversity(boolean isInsideUniversity)
	{
		// begin-user-code
		this.isInsideUniversity = isInsideUniversity;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private int maxGroupSize;

	/** 
	 * @return the maxGroupSize
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public int getMaxGroupSize()
	{
		// begin-user-code
		return maxGroupSize;
		// end-user-code
	}

	/** 
	 * @param maxGroupSize the maxGroupSize to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setMaxGroupSize(int maxGroupSize)
	{
		// begin-user-code
		this.maxGroupSize = maxGroupSize;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "coOp")
	Set<JobPosting> jobPostings;

	/** 
	 * @return the jobPostings
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<JobPosting> getJobPostings()
	{
		// begin-user-code
		return jobPostings;
		// end-user-code
	}

	/** 
	 * @param jobPostings the jobPostings to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setJobPostings(Set<JobPosting> jobPostings)
	{
		// begin-user-code
		this.jobPostings = jobPostings;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	Set<FinancialSource> financialSources;

	/** 
	 * @return the financialSources
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<FinancialSource> getFinancialSources()
	{
		// begin-user-code
		return financialSources;
		// end-user-code
	}

	/** 
	 * @param financialSources the financialSources to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setFinancialSources(Set<FinancialSource> financialSources)
	{
		// begin-user-code
		this.financialSources = financialSources;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "coOp")
	Set<Group> groups;

	/** 
	 * @return the groups
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Group> getGroups()
	{
		// begin-user-code
		return groups;
		// end-user-code
	}

	/** 
	 * @param groups the groups to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setGroups(Set<Group> groups)
	{
		// begin-user-code
		this.groups = groups;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	Set<Company> companies;

	/** 
	 * @return the companies
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Company> getCompanies()
	{
		// begin-user-code
		return companies;
		// end-user-code
	}

	/** 
	 * @param companies the companies to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setCompanies(Set<Company> companies)
	{
		// begin-user-code
		this.companies = companies;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "defaultCoOp")
	Set<AuthenticatedUser> authenticatedUsers;

	/** 
	 * @return the authenticatedUsers
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<AuthenticatedUser> getAuthenticatedUsers()
	{
		// begin-user-code
		return authenticatedUsers;
		// end-user-code
	}

	/** 
	 * @param authenticatedUsers the authenticatedUsers to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAuthenticatedUsers(Set<AuthenticatedUser> authenticatedUsers)
	{
		// begin-user-code
		this.authenticatedUsers = authenticatedUsers;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "coop")
	Set<InsuranceContract> insuranceContracts;

	/** 
	 * @return the insuranceContracts
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<InsuranceContract> getInsuranceContracts()
	{
		// begin-user-code
		return insuranceContracts;
		// end-user-code
	}

	/** 
	 * @param insuranceContracts the insuranceContracts to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setInsuranceContracts(Set<InsuranceContract> insuranceContracts)
	{
		// begin-user-code
		this.insuranceContracts = insuranceContracts;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Tracking tracking;

	/** 
	 * @return the tracking
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Tracking getTracking()
	{
		// begin-user-code
		return tracking;
		// end-user-code
	}

	/** 
	 * @param tracking the tracking to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setTracking(Tracking tracking)
	{
		// begin-user-code
		this.tracking = tracking;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "coop")
	Set<Registration> registrations;

	/** 
	 * @return the registrations
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Registration> getRegistrations()
	{
		// begin-user-code
		return registrations;
		// end-user-code
	}

	/** 
	 * @param registrations the registrations to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setRegistrations(Set<Registration> registrations)
	{
		// begin-user-code
		this.registrations = registrations;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean setup;

	/** 
	 * @return the setup
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isSetup()
	{
		// begin-user-code
		return setup;
		// end-user-code
	}

	/** 
	 * @param setup the setup to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSetup(boolean setup)
	{
		// begin-user-code
		this.setup = setup;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean inRegistration;

	/** 
	 * @return the inRegistration
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isInRegistration()
	{
		// begin-user-code
		return inRegistration;
		// end-user-code
	}

	/** 
	 * @param inRegistration the inRegistration to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setInRegistration(boolean inRegistration)
	{
		// begin-user-code
		this.inRegistration = inRegistration;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	Lesson lesson;

	/** 
	 * @return the lesson
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Lesson getLesson()
	{
		// begin-user-code
		return lesson;
		// end-user-code
	}

	/** 
	 * @param lesson the lesson to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
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
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	Professor institutionalDirector;

	/** 
	 * @return the institutionalDirector
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Professor getInstitutionalDirector()
	{
		// begin-user-code
		return institutionalDirector;
		// end-user-code
	}

	/** 
	 * @param institutionalDirector the institutionalDirector to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setInstitutionalDirector(Professor institutionalDirector)
	{
		// begin-user-code
		this.institutionalDirector = institutionalDirector;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "coOp")
	Set<Report> reports;

	/** 
	 * @return the reports
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Report> getReports()
	{
		// begin-user-code
		return reports;
		// end-user-code
	}

	/** 
	 * @param reports the reports to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setReports(Set<Report> reports)
	{
		// begin-user-code
		this.reports = reports;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	Professor scientificDirector;

	/** 
	 * @return the scientificDirector
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Professor getScientificDirector()
	{
		// begin-user-code
		return scientificDirector;
		// end-user-code
	}

	/** 
	 * @param scientificDirector the scientificDirector to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setScientificDirector(Professor scientificDirector)
	{
		// begin-user-code
		this.scientificDirector = scientificDirector;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private BigDecimal paymentOrderAmount;

	/** 
	 * @return the paymentOrderAmount
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public BigDecimal getPaymentOrderAmount()
	{
		// begin-user-code
		return paymentOrderAmount;
		// end-user-code
	}

	/** 
	 * @param paymentOrderAmount the paymentOrderAmount to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPaymentOrderAmount(BigDecimal paymentOrderAmount)
	{
		// begin-user-code
		this.paymentOrderAmount = paymentOrderAmount;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date paymentOrderDate;

	/** 
	 * @return the paymentOrderDate
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Date getPaymentOrderDate()
	{
		// begin-user-code
		return paymentOrderDate;
		// end-user-code
	}

	/** 
	 * @param paymentOrderDate the paymentOrderDate to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPaymentOrderDate(Date paymentOrderDate)
	{
		// begin-user-code
		this.paymentOrderDate = paymentOrderDate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean allowLocationPreferences;

	/** 
	 * @return the allowLocationPreferences
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isAllowLocationPreferences()
	{
		// begin-user-code
		return allowLocationPreferences;
		// end-user-code
	}

	/** 
	 * @param allowLocationPreferences the allowLocationPreferences to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAllowLocationPreferences(boolean allowLocationPreferences)
	{
		// begin-user-code
		this.allowLocationPreferences = allowLocationPreferences;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean allowCategoryPreferences;

	/** 
	 * @return the allowCategoryPreferences
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isAllowCategoryPreferences()
	{
		// begin-user-code
		return allowCategoryPreferences;
		// end-user-code
	}

	/** 
	 * @param allowCategoryPreferences the allowCategoryPreferences to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAllowCategoryPreferences(boolean allowCategoryPreferences)
	{
		// begin-user-code
		this.allowCategoryPreferences = allowCategoryPreferences;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean allowJobPostingsPreferences;

	/** 
	 * @return the allowJobPostingsPreferences
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isAllowJobPostingsPreferences()
	{
		// begin-user-code
		return allowJobPostingsPreferences;
		// end-user-code
	}

	/** 
	 * @param allowJobPostingsPreferences the allowJobPostingsPreferences to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAllowJobPostingsPreferences(boolean allowJobPostingsPreferences)
	{
		// begin-user-code
		this.allowJobPostingsPreferences = allowJobPostingsPreferences;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean supportingInvitations;

	/** 
	 * @return the supportingInvitations
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isSupportingInvitations()
	{
		// begin-user-code
		return supportingInvitations;
		// end-user-code
	}

	/** 
	 * @param supportingInvitations the supportingInvitations to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSupportingInvitations(boolean supportingInvitations)
	{
		// begin-user-code
		this.supportingInvitations = supportingInvitations;
		// end-user-code
	}
}