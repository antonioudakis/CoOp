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
public class EmbeddableAddress implements IAddress, Serializable
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
	public EmbeddableAddress()
	{
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String street;

	/** 
	 * @return the street
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getStreet()
	{
		// begin-user-code
		return street;
		// end-user-code
	}

	/** 
	 * @param street the street to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setStreet(String street)
	{
		// begin-user-code
		this.street = street;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String number;

	/** 
	 * @return the number
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getNumber()
	{
		// begin-user-code
		return number;
		// end-user-code
	}

	/** 
	 * @param number the number to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setNumber(String number)
	{
		// begin-user-code
		this.number = number;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String poBox;

	/** 
	 * @return the poBox
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getPoBox()
	{
		// begin-user-code
		return poBox;
		// end-user-code
	}

	/** 
	 * @param poBox the poBox to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPoBox(String poBox)
	{
		// begin-user-code
		this.poBox = poBox;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String city;

	/** 
	 * @return the city
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getCity()
	{
		// begin-user-code
		return city;
		// end-user-code
	}

	/** 
	 * @param city the city to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setCity(String city)
	{
		// begin-user-code
		this.city = city;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String country;

	/** 
	 * @return the country
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getCountry()
	{
		// begin-user-code
		return country;
		// end-user-code
	}

	/** 
	 * @param country the country to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setCountry(String country)
	{
		// begin-user-code
		this.country = country;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	Location location;

	/** 
	 * @return the location
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Location getLocation()
	{
		// begin-user-code
		return location;
		// end-user-code
	}

	/** 
	 * @param location the location to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setLocation(Location location)
	{
		// begin-user-code
		this.location = location;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private AddressType type;

	/** 
	 * @return the type
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public AddressType getType()
	{
		// begin-user-code
		return type;
		// end-user-code
	}

	/** 
	 * @param type the type to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setType(AddressType type)
	{
		// begin-user-code
		this.type = type;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private GeoLocation geoLocation;

	/** 
	 * @return the geoLocation
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public GeoLocation getGeoLocation()
	{
		// begin-user-code
		return geoLocation;
		// end-user-code
	}

	/** 
	 * @param geoLocation the geoLocation to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setGeoLocation(GeoLocation geoLocation)
	{
		// begin-user-code
		this.geoLocation = geoLocation;
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
		if (!(obj instanceof EmbeddableAddress)) return false;
		EmbeddableAddress pk = (EmbeddableAddress) obj;
		if ((street == null && pk.street != null) || (street != null && !street.equals(pk.street))) return false;
		if ((number == null && pk.number != null) || (number != null && !number.equals(pk.number))) return false;
		if ((poBox == null && pk.poBox != null) || (poBox != null && !poBox.equals(pk.poBox))) return false;
		if ((city == null && pk.city != null) || (city != null && !city.equals(pk.city))) return false;
		if ((country == null && pk.country != null) || (country != null && !country.equals(pk.country))) return false;
		if ((location == null && pk.location != null) || (location != null && !location.equals(pk.location))) return false;
		if ((type == null && pk.type != null) || (type != null && !type.equals(pk.type))) return false;
		if ((geoLocation == null && pk.geoLocation != null) || (geoLocation != null && !geoLocation.equals(pk.geoLocation))) return false;
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
		if (street != null)
		{
			hashcode += street.hashCode();
		}
		if (number != null)
		{
			hashcode += number.hashCode();
		}
		if (poBox != null)
		{
			hashcode += poBox.hashCode();
		}
		if (city != null)
		{
			hashcode += city.hashCode();
		}
		if (country != null)
		{
			hashcode += country.hashCode();
		}
		if (location != null)
		{
			hashcode += location.hashCode();
		}
		if (type != null)
		{
			hashcode += type.hashCode();
		}
		if (geoLocation != null)
		{
			hashcode += geoLocation.hashCode();
		}
		return hashcode;
	}
}