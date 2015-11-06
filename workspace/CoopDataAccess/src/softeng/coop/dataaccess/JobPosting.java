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
import javax.persistence.OneToMany;
import java.util.List;
import javax.persistence.OrderBy;
import java.math.BigDecimal;
import javax.persistence.ManyToMany;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
public class JobPosting implements Serializable, ITrackedEntity
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
	public JobPosting()
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
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	CoOp coOp;

	/** 
	 * @return the coOp
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public CoOp getCoOp()
	{
		// begin-user-code
		return coOp;
		// end-user-code
	}

	/** 
	 * @param coOp the coOp to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setCoOp(CoOp coOp)
	{
		// begin-user-code
		this.coOp = coOp;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	CompanyPerson managingCompanyPerson;

	/** 
	 * @return the managingCompanyPerson
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public CompanyPerson getManagingCompanyPerson()
	{
		// begin-user-code
		return managingCompanyPerson;
		// end-user-code
	}

	/** 
	 * @param managingCompanyPerson the managingCompanyPerson to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setManagingCompanyPerson(CompanyPerson managingCompanyPerson)
	{
		// begin-user-code
		this.managingCompanyPerson = managingCompanyPerson;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToOne
	private Multilingual description;

	/** 
	 * @return the description
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Multilingual getDescription()
	{
		// begin-user-code
		return description;
		// end-user-code
	}

	/** 
	 * @param description the description to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setDescription(Multilingual description)
	{
		// begin-user-code
		this.description = description;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private int seatsNumber;

	/** 
	 * @return the seatsNumber
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public int getSeatsNumber()
	{
		// begin-user-code
		return seatsNumber;
		// end-user-code
	}

	/** 
	 * @param seatsNumber the seatsNumber to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSeatsNumber(int seatsNumber)
	{
		// begin-user-code
		this.seatsNumber = seatsNumber;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "jobPosting")
	Set<Job> jobs;

	/** 
	 * @return the jobs
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Job> getJobs()
	{
		// begin-user-code
		return jobs;
		// end-user-code
	}

	/** 
	 * @param jobs the jobs to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setJobs(Set<Job> jobs)
	{
		// begin-user-code
		this.jobs = jobs;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OrderBy(value = "startDay")
	@OneToMany(mappedBy = "jobPosting")
	List<JobPostingPart> jobPostingParts;

	/** 
	 * @return the jobPostingParts
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public List<JobPostingPart> getJobPostingParts()
	{
		// begin-user-code
		return jobPostingParts;
		// end-user-code
	}

	/** 
	 * @param jobPostingParts the jobPostingParts to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setJobPostingParts(List<JobPostingPart> jobPostingParts)
	{
		// begin-user-code
		this.jobPostingParts = jobPostingParts;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	Professor supervisingProfessor;

	/** 
	 * @return the supervisingProfessor
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Professor getSupervisingProfessor()
	{
		// begin-user-code
		return supervisingProfessor;
		// end-user-code
	}

	/** 
	 * @param supervisingProfessor the supervisingProfessor to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSupervisingProfessor(Professor supervisingProfessor)
	{
		// begin-user-code
		this.supervisingProfessor = supervisingProfessor;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	Company company;

	/** 
	 * @return the company
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Company getCompany()
	{
		// begin-user-code
		return company;
		// end-user-code
	}

	/** 
	 * @param company the company to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setCompany(Company company)
	{
		// begin-user-code
		this.company = company;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Benefits benefits;

	/** 
	 * @return the benefits
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Benefits getBenefits()
	{
		// begin-user-code
		return benefits;
		// end-user-code
	}

	/** 
	 * @param benefits the benefits to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setBenefits(Benefits benefits)
	{
		// begin-user-code
		this.benefits = benefits;
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
	@ManyToMany(mappedBy = "preferredJobPostings")
	Set<Registration> preferredByRegistrations;

	/** 
	 * @return the preferredByRegistrations
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Registration> getPreferredByRegistrations()
	{
		// begin-user-code
		return preferredByRegistrations;
		// end-user-code
	}

	/** 
	 * @param preferredByRegistrations the preferredByRegistrations to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPreferredByRegistrations(Set<Registration> preferredByRegistrations)
	{
		// begin-user-code
		this.preferredByRegistrations = preferredByRegistrations;
		// end-user-code
	}
}