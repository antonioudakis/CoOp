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
public class GeoLocation implements Serializable
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
	public GeoLocation()
	{
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private double latitude;

	/** 
	 * @return the latitude
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public double getLatitude()
	{
		// begin-user-code
		return latitude;
		// end-user-code
	}

	/** 
	 * @param latitude the latitude to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setLatitude(double latitude)
	{
		// begin-user-code
		this.latitude = latitude;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private double longtitude;

	/** 
	 * @return the longtitude
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public double getLongtitude()
	{
		// begin-user-code
		return longtitude;
		// end-user-code
	}

	/** 
	 * @param longtitude the longtitude to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setLongtitude(double longtitude)
	{
		// begin-user-code
		this.longtitude = longtitude;
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
		if (!(obj instanceof GeoLocation)) return false;
		GeoLocation pk = (GeoLocation) obj;
		if (!(latitude == pk.latitude)) return false;
		if (!(longtitude == pk.longtitude)) return false;
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
		hashcode += latitude;
		hashcode += longtitude;
		return hashcode;
	}
}