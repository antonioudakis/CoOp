/**
 * 
 */
package softeng.coop.dataaccess;

import java.io.Serializable;
import javax.persistence.Embeddable;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Embeddable
public class EmbeddableEMail implements IEMail, Serializable
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
	public EmbeddableEMail()
	{
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String address;

	/** 
	 * @return the address
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getAddress()
	{
		// begin-user-code
		return address;
		// end-user-code
	}

	/** 
	 * @param address the address to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAddress(String address)
	{
		// begin-user-code
		this.address = address;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String comment;

	/** 
	 * @return the comment
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getComment()
	{
		// begin-user-code
		return comment;
		// end-user-code
	}

	/** 
	 * @param comment the comment to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setComment(String comment)
	{
		// begin-user-code
		this.comment = comment;
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
		if (!(obj instanceof EmbeddableEMail)) return false;
		EmbeddableEMail pk = (EmbeddableEMail) obj;
		if ((address == null && pk.address != null) || (address != null && !address.equals(pk.address))) return false;
		if ((comment == null && pk.comment != null) || (comment != null && !comment.equals(pk.comment))) return false;
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
		if (address != null)
		{
			hashcode += address.hashCode();
		}
		if (comment != null)
		{
			hashcode += comment.hashCode();
		}
		return hashcode;
	}
}