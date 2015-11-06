/**
 * 
 */
package softeng.coop.dataaccess;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
public class ReportType implements Serializable, ITrackedEntity
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
	public ReportType()
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
	@OneToOne
	private Multilingual comments;

	/** 
	 * @return the comments
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Multilingual getComments()
	{
		// begin-user-code
		return comments;
		// end-user-code
	}

	/** 
	 * @param comments the comments to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setComments(Multilingual comments)
	{
		// begin-user-code
		this.comments = comments;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "reportType")
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
	private Boolean _final;

	/** 
	 * @return the _final
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Boolean is_final()
	{
		// begin-user-code
		return _final;
		// end-user-code
	}

	/** 
	 * @param _final the _final to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void set_final(Boolean _final)
	{
		// begin-user-code
		this._final = _final;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String factoryClassName;

	/** 
	 * @return the factoryClassName
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getFactoryClassName()
	{
		// begin-user-code
		return factoryClassName;
		// end-user-code
	}

	/** 
	 * @param factoryClassName the factoryClassName to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setFactoryClassName(String factoryClassName)
	{
		// begin-user-code
		this.factoryClassName = factoryClassName;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Boolean isAggregate;

	/** 
	 * @return the isAggregate
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Boolean isAggregate()
	{
		// begin-user-code
		return isAggregate;
		// end-user-code
	}

	/** 
	 * @param isAggregate the isAggregate to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAggregate(Boolean isAggregate)
	{
		// begin-user-code
		this.isAggregate = isAggregate;
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
	 * <p>A string identifier for the report type.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String codeName;

	/** 
	 * @return the codeName
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getCodeName()
	{
		// begin-user-code
		return codeName;
		// end-user-code
	}

	/** 
	 * @param codeName the codeName to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setCodeName(String codeName)
	{
		// begin-user-code
		this.codeName = codeName;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private ReportScope scope;

	/** 
	 * @return the scope
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public ReportScope getScope()
	{
		// begin-user-code
		return scope;
		// end-user-code
	}

	/** 
	 * @param scope the scope to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setScope(ReportScope scope)
	{
		// begin-user-code
		this.scope = scope;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToMany(mappedBy = "reportType")
	Set<Role> roles;

	/** 
	 * @return the roles
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Role> getRoles()
	{
		// begin-user-code
		return roles;
		// end-user-code
	}

	/** 
	 * @param roles the roles to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setRoles(Set<Role> roles)
	{
		// begin-user-code
		this.roles = roles;
		// end-user-code
	}
}