/**
 * 
 */
package softeng.coop.dataaccess;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
public class ActivitySector implements Serializable
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
	public ActivitySector()
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
	@Column(nullable = false, length = 1023)
	private String description;

	/** 
	 * @return the description
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getDescription()
	{
		// begin-user-code
		return description;
		// end-user-code
	}

	/** 
	 * @param description the description to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setDescription(String description)
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
	private String code;

	/** 
	 * @return the code
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getCode()
	{
		// begin-user-code
		return code;
		// end-user-code
	}

	/** 
	 * @param code the code to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setCode(String code)
	{
		// begin-user-code
		this.code = code;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OrderBy(value = "code")
	@OneToMany(mappedBy = "parentActivitySector")
	Set<ActivitySector> childActivitySectors;

	/** 
	 * @return the childActivitySectors
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<ActivitySector> getChildActivitySectors()
	{
		// begin-user-code
		return childActivitySectors;
		// end-user-code
	}

	/** 
	 * @param childActivitySectors the childActivitySectors to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setChildActivitySectors(Set<ActivitySector> childActivitySectors)
	{
		// begin-user-code
		this.childActivitySectors = childActivitySectors;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "activitySector")
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
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	ActivitySector parentActivitySector;

	/** 
	 * @return the parentActivitySector
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public ActivitySector getParentActivitySector()
	{
		// begin-user-code
		return parentActivitySector;
		// end-user-code
	}

	/** 
	 * @param parentActivitySector the parentActivitySector to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setParentActivitySector(ActivitySector parentActivitySector)
	{
		// begin-user-code
		this.parentActivitySector = parentActivitySector;
		// end-user-code
	}
}