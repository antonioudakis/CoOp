/**
 * 
 */
package softeng.coop.dataaccess;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Embeddable
public class Tracking implements Serializable
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
	public Tracking()
	{
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date created;

	/** 
	 * @return the created
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Date getCreated()
	{
		// begin-user-code
		return created;
		// end-user-code
	}

	/** 
	 * @param created the created to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setCreated(Date created)
	{
		// begin-user-code
		this.created = created;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date modified;

	/** 
	 * @return the modified
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Date getModified()
	{
		// begin-user-code
		return modified;
		// end-user-code
	}

	/** 
	 * @param modified the modified to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setModified(Date modified)
	{
		// begin-user-code
		this.modified = modified;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private AuthenticatedUser createdBy;

	/** 
	 * @return the createdBy
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public AuthenticatedUser getCreatedBy()
	{
		// begin-user-code
		return createdBy;
		// end-user-code
	}

	/** 
	 * @param createdBy the createdBy to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setCreatedBy(AuthenticatedUser createdBy)
	{
		// begin-user-code
		this.createdBy = createdBy;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private AuthenticatedUser modifiedBy;

	/** 
	 * @return the modifiedBy
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public AuthenticatedUser getModifiedBy()
	{
		// begin-user-code
		return modifiedBy;
		// end-user-code
	}

	/** 
	 * @param modifiedBy the modifiedBy to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setModifiedBy(AuthenticatedUser modifiedBy)
	{
		// begin-user-code
		this.modifiedBy = modifiedBy;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		if (!(obj instanceof Tracking)) return false;
		Tracking pk = (Tracking) obj;
		if ((created == null && pk.created != null) || (created != null && !created.equals(pk.created))) return false;
		if ((modified == null && pk.modified != null) || (modified != null && !modified.equals(pk.modified))) return false;
		if ((createdBy == null && pk.createdBy != null) || (createdBy != null && !createdBy.equals(pk.createdBy))) return false;
		if ((modifiedBy == null && pk.modifiedBy != null) || (modifiedBy != null && !modifiedBy.equals(pk.modifiedBy))) return false;
		return true;
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public int hashCode()
	{
		int hashcode = 0;
		if (created != null)
		{
			hashcode += created.hashCode();
		}
		if (modified != null)
		{
			hashcode += modified.hashCode();
		}
		if (createdBy != null)
		{
			hashcode += createdBy.hashCode();
		}
		if (modifiedBy != null)
		{
			hashcode += modifiedBy.hashCode();
		}
		return hashcode;
	}
}