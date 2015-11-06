/**
 * 
 */
package softeng.coop.dataaccess;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.util.Set;
import javax.persistence.ManyToMany;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToMany;
import java.util.List;
import javax.persistence.OrderColumn;
import java.util.Collection;
import javax.persistence.OrderBy;
import javax.persistence.OneToOne;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
public class Registration implements Serializable, ITrackedEntity
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
	public Registration()
	{
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	CoOp coop;

	/** 
	 * @return the coop
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public CoOp getCoop()
	{
		// begin-user-code
		return coop;
		// end-user-code
	}

	/** 
	 * @param coop the coop to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
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
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	Student student;

	/** 
	 * @return the student
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Student getStudent()
	{
		// begin-user-code
		return student;
		// end-user-code
	}

	/** 
	 * @param student the student to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setStudent(Student student)
	{
		// begin-user-code
		this.student = student;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Meets requirements for coop participation, for example semester, remaining classes, etc.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean qualifiedForAssigmnent;

	/** 
	 * @return the qualifiedForAssigmnent
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isQualifiedForAssigmnent()
	{
		// begin-user-code
		return qualifiedForAssigmnent;
		// end-user-code
	}

	/** 
	 * @param qualifiedForAssigmnent the qualifiedForAssigmnent to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setQualifiedForAssigmnent(boolean qualifiedForAssigmnent)
	{
		// begin-user-code
		this.qualifiedForAssigmnent = qualifiedForAssigmnent;
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
	@ManyToMany
	Set<Requirement> meetsRequirements;

	/** 
	 * @return the meetsRequirements
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Requirement> getMeetsRequirements()
	{
		// begin-user-code
		return meetsRequirements;
		// end-user-code
	}

	/** 
	 * @param meetsRequirements the meetsRequirements to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setMeetsRequirements(Set<Requirement> meetsRequirements)
	{
		// begin-user-code
		this.meetsRequirements = meetsRequirements;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean qualifiedForCompletion;

	/** 
	 * @return the qualifiedForCompletion
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isQualifiedForCompletion()
	{
		// begin-user-code
		return qualifiedForCompletion;
		// end-user-code
	}

	/** 
	 * @param qualifiedForCompletion the qualifiedForCompletion to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setQualifiedForCompletion(boolean qualifiedForCompletion)
	{
		// begin-user-code
		this.qualifiedForCompletion = qualifiedForCompletion;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Float grade;

	/** 
	 * @return the grade
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Float getGrade()
	{
		// begin-user-code
		return grade;
		// end-user-code
	}

	/** 
	 * @param grade the grade to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setGrade(Float grade)
	{
		// begin-user-code
		this.grade = grade;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean passed;

	/** 
	 * @return the passed
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isPassed()
	{
		// begin-user-code
		return passed;
		// end-user-code
	}

	/** 
	 * @param passed the passed to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPassed(boolean passed)
	{
		// begin-user-code
		this.passed = passed;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date gradeDate;

	/** 
	 * @return the gradeDate
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Date getGradeDate()
	{
		// begin-user-code
		return gradeDate;
		// end-user-code
	}

	/** 
	 * @param gradeDate the gradeDate to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setGradeDate(Date gradeDate)
	{
		// begin-user-code
		this.gradeDate = gradeDate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	Group group;

	/** 
	 * @return the group
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Group getGroup()
	{
		// begin-user-code
		return group;
		// end-user-code
	}

	/** 
	 * @param group the group to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setGroup(Group group)
	{
		// begin-user-code
		this.group = group;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "sender")
	Set<Invitation> sentInvitations;

	/** 
	 * @return the sentInvitations
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Invitation> getSentInvitations()
	{
		// begin-user-code
		return sentInvitations;
		// end-user-code
	}

	/** 
	 * @param sentInvitations the sentInvitations to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSentInvitations(Set<Invitation> sentInvitations)
	{
		// begin-user-code
		this.sentInvitations = sentInvitations;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToMany(mappedBy = "recepients")
	Set<Invitation> receivedInvitations;

	/** 
	 * @return the receivedInvitations
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Invitation> getReceivedInvitations()
	{
		// begin-user-code
		return receivedInvitations;
		// end-user-code
	}

	/** 
	 * @param receivedInvitations the receivedInvitations to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setReceivedInvitations(Set<Invitation> receivedInvitations)
	{
		// begin-user-code
		this.receivedInvitations = receivedInvitations;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "registration")
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
	@OrderColumn
	@ManyToMany(fetch = FetchType.LAZY)
	List<Category> preferredCategories;

	/** 
	 * @return the preferredCategories
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public List<Category> getPreferredCategories()
	{
		// begin-user-code
		return preferredCategories;
		// end-user-code
	}

	/** 
	 * @param preferredCategories the preferredCategories to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPreferredCategories(List<Category> preferredCategories)
	{
		// begin-user-code
		this.preferredCategories = preferredCategories;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OrderColumn
	@ManyToMany(fetch = FetchType.LAZY)
	List<Location> preferredLocations;

	/** 
	 * @return the preferredLocations
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public List<Location> getPreferredLocations()
	{
		// begin-user-code
		return preferredLocations;
		// end-user-code
	}

	/** 
	 * @param preferredLocations the preferredLocations to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPreferredLocations(List<Location> preferredLocations)
	{
		// begin-user-code
		this.preferredLocations = preferredLocations;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OrderColumn
	@ManyToMany(fetch = FetchType.LAZY)
	List<JobPosting> preferredJobPostings;

	/** 
	 * @return the preferredJobPostings
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public List<JobPosting> getPreferredJobPostings()
	{
		// begin-user-code
		return preferredJobPostings;
		// end-user-code
	}

	/** 
	 * @param preferredJobPostings the preferredJobPostings to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPreferredJobPostings(List<JobPosting> preferredJobPostings)
	{
		// begin-user-code
		this.preferredJobPostings = preferredJobPostings;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date registrationDate;

	/** 
	 * @return the registrationDate
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Date getRegistrationDate()
	{
		// begin-user-code
		return registrationDate;
		// end-user-code
	}

	/** 
	 * @param registrationDate the registrationDate to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setRegistrationDate(Date registrationDate)
	{
		// begin-user-code
		this.registrationDate = registrationDate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Temporal(value = TemporalType.DATE)
	private Date preferredStart;

	/** 
	 * @return the preferredStart
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Date getPreferredStart()
	{
		// begin-user-code
		return preferredStart;
		// end-user-code
	}

	/** 
	 * @param preferredStart the preferredStart to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPreferredStart(Date preferredStart)
	{
		// begin-user-code
		this.preferredStart = preferredStart;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Temporal(value = TemporalType.DATE)
	private Date preferredEnd;

	/** 
	 * @return the preferredEnd
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Date getPreferredEnd()
	{
		// begin-user-code
		return preferredEnd;
		// end-user-code
	}

	/** 
	 * @param preferredEnd the preferredEnd to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPreferredEnd(Date preferredEnd)
	{
		// begin-user-code
		this.preferredEnd = preferredEnd;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OrderBy(value = "paymentDate")
	@OneToMany(mappedBy = "registration")
	Set<Payment> payments;

	/** 
	 * @return the payments
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Payment> getPayments()
	{
		// begin-user-code
		return payments;
		// end-user-code
	}

	/** 
	 * @param payments the payments to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPayments(Set<Payment> payments)
	{
		// begin-user-code
		this.payments = payments;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Integer subjectSatisfactionRate;

	/** 
	 * @return the subjectSatisfactionRate
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Integer getSubjectSatisfactionRate()
	{
		// begin-user-code
		return subjectSatisfactionRate;
		// end-user-code
	}

	/** 
	 * @param subjectSatisfactionRate the subjectSatisfactionRate to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSubjectSatisfactionRate(Integer subjectSatisfactionRate)
	{
		// begin-user-code
		this.subjectSatisfactionRate = subjectSatisfactionRate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Integer supervisionSatisfactionRate;

	/** 
	 * @return the supervisionSatisfactionRate
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Integer getSupervisionSatisfactionRate()
	{
		// begin-user-code
		return supervisionSatisfactionRate;
		// end-user-code
	}

	/** 
	 * @param supervisionSatisfactionRate the supervisionSatisfactionRate to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSupervisionSatisfactionRate(Integer supervisionSatisfactionRate)
	{
		// begin-user-code
		this.supervisionSatisfactionRate = supervisionSatisfactionRate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Integer hostSatisfactionRate;

	/** 
	 * @return the hostSatisfactionRate
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Integer getHostSatisfactionRate()
	{
		// begin-user-code
		return hostSatisfactionRate;
		// end-user-code
	}

	/** 
	 * @param hostSatisfactionRate the hostSatisfactionRate to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setHostSatisfactionRate(Integer hostSatisfactionRate)
	{
		// begin-user-code
		this.hostSatisfactionRate = hostSatisfactionRate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	InsuranceContract insuranceContract;

	/** 
	 * @return the insuranceContract
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public InsuranceContract getInsuranceContract()
	{
		// begin-user-code
		return insuranceContract;
		// end-user-code
	}

	/** 
	 * @param insuranceContract the insuranceContract to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setInsuranceContract(InsuranceContract insuranceContract)
	{
		// begin-user-code
		this.insuranceContract = insuranceContract;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Float priority;

	/** 
	 * @return the priority
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Float getPriority()
	{
		// begin-user-code
		return priority;
		// end-user-code
	}

	/** 
	 * @param priority the priority to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPriority(Float priority)
	{
		// begin-user-code
		this.priority = priority;
		// end-user-code
	}
}