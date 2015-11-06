/**
 * 
 */
package softeng.coop.dataaccess;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
public class CompanyPerson extends Person implements Serializable
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
	public CompanyPerson()
	{
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String position;

	/** 
	 * @return the position
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getPosition()
	{
		// begin-user-code
		return position;
		// end-user-code
	}

	/** 
	 * @param position the position to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPosition(String position)
	{
		// begin-user-code
		this.position = position;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String salutation;

	/** 
	 * @return the salutation
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getSalutation()
	{
		// begin-user-code
		return salutation;
		// end-user-code
	}

	/** 
	 * @param salutation the salutation to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSalutation(String salutation)
	{
		// begin-user-code
		this.salutation = salutation;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "managingCompanyPerson")
	Set<JobPosting> managedJobPostings;

	/** 
	 * @return the managedJobPostings
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<JobPosting> getManagedJobPostings()
	{
		// begin-user-code
		return managedJobPostings;
		// end-user-code
	}

	/** 
	 * @param managedJobPostings the managedJobPostings to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setManagedJobPostings(Set<JobPosting> managedJobPostings)
	{
		// begin-user-code
		this.managedJobPostings = managedJobPostings;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "managingCompanyPerson")
	Set<JobPart> managedJobParts;

	/** 
	 * @return the managedJobParts
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<JobPart> getManagedJobParts()
	{
		// begin-user-code
		return managedJobParts;
		// end-user-code
	}

	/** 
	 * @param managedJobParts the managedJobParts to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setManagedJobParts(Set<JobPart> managedJobParts)
	{
		// begin-user-code
		this.managedJobParts = managedJobParts;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "managingCompanyPerson")
	Set<JobPostingPart> managedJobPostingParts;

	/** 
	 * @return the managedJobPostingParts
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<JobPostingPart> getManagedJobPostingParts()
	{
		// begin-user-code
		return managedJobPostingParts;
		// end-user-code
	}

	/** 
	 * @param managedJobPostingParts the managedJobPostingParts to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setManagedJobPostingParts(Set<JobPostingPart> managedJobPostingParts)
	{
		// begin-user-code
		this.managedJobPostingParts = managedJobPostingParts;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToMany(mappedBy = "persons")
	Set<Branch> branches;

	/** 
	 * @return the branches
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Branch> getBranches()
	{
		// begin-user-code
		return branches;
		// end-user-code
	}

	/** 
	 * @param branches the branches to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setBranches(Set<Branch> branches)
	{
		// begin-user-code
		this.branches = branches;
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
}