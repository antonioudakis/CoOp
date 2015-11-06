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

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
public class Professor extends FacultyUser implements Serializable
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
	public Professor()
	{
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String rank;

	/** 
	 * @return the rank
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getRank()
	{
		// begin-user-code
		return rank;
		// end-user-code
	}

	/** 
	 * @param rank the rank to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setRank(String rank)
	{
		// begin-user-code
		this.rank = rank;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "academicDirector")
	Set<CoOp> academicallyDirectedCoOps;

	/** 
	 * @return the academicallyDirectedCoOps
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<CoOp> getAcademicallyDirectedCoOps()
	{
		// begin-user-code
		return academicallyDirectedCoOps;
		// end-user-code
	}

	/** 
	 * @param academicallyDirectedCoOps the academicallyDirectedCoOps to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAcademicallyDirectedCoOps(Set<CoOp> academicallyDirectedCoOps)
	{
		// begin-user-code
		this.academicallyDirectedCoOps = academicallyDirectedCoOps;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToMany(mappedBy = "supervisingProfessors")
	Set<CoOp> supervisedCoOps;

	/** 
	 * @return the supervisedCoOps
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<CoOp> getSupervisedCoOps()
	{
		// begin-user-code
		return supervisedCoOps;
		// end-user-code
	}

	/** 
	 * @param supervisedCoOps the supervisedCoOps to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSupervisedCoOps(Set<CoOp> supervisedCoOps)
	{
		// begin-user-code
		this.supervisedCoOps = supervisedCoOps;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "supervisingProfessor")
	Set<Job> supervisedJobs;

	/** 
	 * @return the supervisedJobs
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Job> getSupervisedJobs()
	{
		// begin-user-code
		return supervisedJobs;
		// end-user-code
	}

	/** 
	 * @param supervisedJobs the supervisedJobs to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSupervisedJobs(Set<Job> supervisedJobs)
	{
		// begin-user-code
		this.supervisedJobs = supervisedJobs;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "supervisingProfessor")
	Set<JobPosting> supervisedJobPostings;

	/** 
	 * @return the supervisedJobPostings
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<JobPosting> getSupervisedJobPostings()
	{
		// begin-user-code
		return supervisedJobPostings;
		// end-user-code
	}

	/** 
	 * @param supervisedJobPostings the supervisedJobPostings to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSupervisedJobPostings(Set<JobPosting> supervisedJobPostings)
	{
		// begin-user-code
		this.supervisedJobPostings = supervisedJobPostings;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	Set<Group> supervisedGroups;

	/** 
	 * @return the supervisedGroups
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Group> getSupervisedGroups()
	{
		// begin-user-code
		return supervisedGroups;
		// end-user-code
	}

	/** 
	 * @param supervisedGroups the supervisedGroups to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSupervisedGroups(Set<Group> supervisedGroups)
	{
		// begin-user-code
		this.supervisedGroups = supervisedGroups;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "institutionalDirector")
	Set<CoOp> institutionallyDirectedCoOps;

	/** 
	 * @return the institutionallyDirectedCoOps
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<CoOp> getInstitutionallyDirectedCoOps()
	{
		// begin-user-code
		return institutionallyDirectedCoOps;
		// end-user-code
	}

	/** 
	 * @param institutionallyDirectedCoOps the institutionallyDirectedCoOps to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setInstitutionallyDirectedCoOps(Set<CoOp> institutionallyDirectedCoOps)
	{
		// begin-user-code
		this.institutionallyDirectedCoOps = institutionallyDirectedCoOps;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "scientificDirector")
	Set<CoOp> scientificallyDirectedCoOps;

	/** 
	 * @return the scientificallyDirectedCoOps
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<CoOp> getScientificallyDirectedCoOps()
	{
		// begin-user-code
		return scientificallyDirectedCoOps;
		// end-user-code
	}

	/** 
	 * @param scientificallyDirectedCoOps the scientificallyDirectedCoOps to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setScientificallyDirectedCoOps(Set<CoOp> scientificallyDirectedCoOps)
	{
		// begin-user-code
		this.scientificallyDirectedCoOps = scientificallyDirectedCoOps;
		// end-user-code
	}
}