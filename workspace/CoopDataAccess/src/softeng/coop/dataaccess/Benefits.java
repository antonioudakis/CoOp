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
public class Benefits implements Serializable
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
	public Benefits()
	{
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean accommodationOffered;

	/** 
	 * @return the accommodationOffered
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isAccommodationOffered()
	{
		// begin-user-code
		return accommodationOffered;
		// end-user-code
	}

	/** 
	 * @param accommodationOffered the accommodationOffered to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAccommodationOffered(boolean accommodationOffered)
	{
		// begin-user-code
		this.accommodationOffered = accommodationOffered;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean salaryOffered;

	/** 
	 * @return the salaryOffered
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isSalaryOffered()
	{
		// begin-user-code
		return salaryOffered;
		// end-user-code
	}

	/** 
	 * @param salaryOffered the salaryOffered to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSalaryOffered(boolean salaryOffered)
	{
		// begin-user-code
		this.salaryOffered = salaryOffered;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean insuranceOffered;

	/** 
	 * @return the insuranceOffered
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isInsuranceOffered()
	{
		// begin-user-code
		return insuranceOffered;
		// end-user-code
	}

	/** 
	 * @param insuranceOffered the insuranceOffered to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setInsuranceOffered(boolean insuranceOffered)
	{
		// begin-user-code
		this.insuranceOffered = insuranceOffered;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean transportationOffered;

	/** 
	 * @return the transportationOffered
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isTransportationOffered()
	{
		// begin-user-code
		return transportationOffered;
		// end-user-code
	}

	/** 
	 * @param transportationOffered the transportationOffered to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setTransportationOffered(boolean transportationOffered)
	{
		// begin-user-code
		this.transportationOffered = transportationOffered;
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
		if (!(obj instanceof Benefits)) return false;
		Benefits pk = (Benefits) obj;
		if (!(accommodationOffered == pk.accommodationOffered)) return false;
		if (!(salaryOffered == pk.salaryOffered)) return false;
		if (!(insuranceOffered == pk.insuranceOffered)) return false;
		if (!(transportationOffered == pk.transportationOffered)) return false;
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
		hashcode += new Boolean(accommodationOffered).hashCode();
		hashcode += new Boolean(salaryOffered).hashCode();
		hashcode += new Boolean(insuranceOffered).hashCode();
		hashcode += new Boolean(transportationOffered).hashCode();
		return hashcode;
	}
}