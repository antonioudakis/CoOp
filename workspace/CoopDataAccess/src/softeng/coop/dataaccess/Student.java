/**
 * 
 */
package softeng.coop.dataaccess;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
public class Student extends AuthenticatedUser implements Serializable
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
	public Student()
	{
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String serialNumber;

	/** 
	 * @return the serialNumber
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getSerialNumber()
	{
		// begin-user-code
		return serialNumber;
		// end-user-code
	}

	/** 
	 * @param serialNumber the serialNumber to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSerialNumber(String serialNumber)
	{
		// begin-user-code
		this.serialNumber = serialNumber;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Temporal(value = TemporalType.DATE)
	private Date admissionDate;

	/** 
	 * @return the admissionDate
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Date getAdmissionDate()
	{
		// begin-user-code
		return admissionDate;
		// end-user-code
	}

	/** 
	 * @param admissionDate the admissionDate to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAdmissionDate(Date admissionDate)
	{
		// begin-user-code
		this.admissionDate = admissionDate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean immigrant;

	/** 
	 * @return the immigrant
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isImmigrant()
	{
		// begin-user-code
		return immigrant;
		// end-user-code
	}

	/** 
	 * @param immigrant the immigrant to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setImmigrant(boolean immigrant)
	{
		// begin-user-code
		this.immigrant = immigrant;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean hasSpecialNeeds;

	/** 
	 * @return the hasSpecialNeeds
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isHasSpecialNeeds()
	{
		// begin-user-code
		return hasSpecialNeeds;
		// end-user-code
	}

	/** 
	 * @param hasSpecialNeeds the hasSpecialNeeds to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setHasSpecialNeeds(boolean hasSpecialNeeds)
	{
		// begin-user-code
		this.hasSpecialNeeds = hasSpecialNeeds;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String taxId;

	/** 
	 * @return the taxId
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getTaxId()
	{
		// begin-user-code
		return taxId;
		// end-user-code
	}

	/** 
	 * @param taxId the taxId to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setTaxId(String taxId)
	{
		// begin-user-code
		this.taxId = taxId;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String taxDivision;

	/** 
	 * @return the taxDivision
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getTaxDivision()
	{
		// begin-user-code
		return taxDivision;
		// end-user-code
	}

	/** 
	 * @param taxDivision the taxDivision to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setTaxDivision(String taxDivision)
	{
		// begin-user-code
		this.taxDivision = taxDivision;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>AMKA.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String socialSecurityId;

	/** 
	 * @return the socialSecurityId
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getSocialSecurityId()
	{
		// begin-user-code
		return socialSecurityId;
		// end-user-code
	}

	/** 
	 * @param socialSecurityId the socialSecurityId to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setSocialSecurityId(String socialSecurityId)
	{
		// begin-user-code
		this.socialSecurityId = socialSecurityId;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>AMA for IKA.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String ama;

	/** 
	 * @return the ama
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getAma()
	{
		// begin-user-code
		return ama;
		// end-user-code
	}

	/** 
	 * @param ama the ama to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setAma(String ama)
	{
		// begin-user-code
		this.ama = ama;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String iban;

	/** 
	 * @return the iban
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getIban()
	{
		// begin-user-code
		return iban;
		// end-user-code
	}

	/** 
	 * @param iban the iban to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setIban(String iban)
	{
		// begin-user-code
		this.iban = iban;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	Nationality nationality;

	/** 
	 * @return the nationality
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Nationality getNationality()
	{
		// begin-user-code
		return nationality;
		// end-user-code
	}

	/** 
	 * @param nationality the nationality to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setNationality(Nationality nationality)
	{
		// begin-user-code
		this.nationality = nationality;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String idNumber;

	/** 
	 * @return the idNumber
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getIdNumber()
	{
		// begin-user-code
		return idNumber;
		// end-user-code
	}

	/** 
	 * @param idNumber the idNumber to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setIdNumber(String idNumber)
	{
		// begin-user-code
		this.idNumber = idNumber;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private IdentificationType idType;

	/** 
	 * @return the idType
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public IdentificationType getIdType()
	{
		// begin-user-code
		return idType;
		// end-user-code
	}

	/** 
	 * @param idType the idType to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setIdType(IdentificationType idType)
	{
		// begin-user-code
		this.idType = idType;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String idIssuer;

	/** 
	 * @return the idIssuer
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getIdIssuer()
	{
		// begin-user-code
		return idIssuer;
		// end-user-code
	}

	/** 
	 * @param idIssuer the idIssuer to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setIdIssuer(String idIssuer)
	{
		// begin-user-code
		this.idIssuer = idIssuer;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	Location issuerLocation;

	/** 
	 * @return the issuerLocation
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Location getIssuerLocation()
	{
		// begin-user-code
		return issuerLocation;
		// end-user-code
	}

	/** 
	 * @param issuerLocation the issuerLocation to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setIssuerLocation(Location issuerLocation)
	{
		// begin-user-code
		this.issuerLocation = issuerLocation;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Temporal(value = TemporalType.DATE)
	private Date idIssueDate;

	/** 
	 * @return the idIssueDate
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Date getIdIssueDate()
	{
		// begin-user-code
		return idIssueDate;
		// end-user-code
	}

	/** 
	 * @param idIssueDate the idIssueDate to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setIdIssueDate(Date idIssueDate)
	{
		// begin-user-code
		this.idIssueDate = idIssueDate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@Column(length = 1023)
	private String workExperience;

	/** 
	 * @return the workExperience
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getWorkExperience()
	{
		// begin-user-code
		return workExperience;
		// end-user-code
	}

	/** 
	 * @param workExperience the workExperience to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setWorkExperience(String workExperience)
	{
		// begin-user-code
		this.workExperience = workExperience;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToMany(mappedBy = "student")
	Set<Registration> registrations;

	/** 
	 * @return the registrations
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Set<Registration> getRegistrations()
	{
		// begin-user-code
		return registrations;
		// end-user-code
	}

	/** 
	 * @param registrations the registrations to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setRegistrations(Set<Registration> registrations)
	{
		// begin-user-code
		this.registrations = registrations;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	Registration activeRegistration;

	/** 
	 * @return the activeRegistration
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Registration getActiveRegistration()
	{
		// begin-user-code
		return activeRegistration;
		// end-user-code
	}

	/** 
	 * @param activeRegistration the activeRegistration to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setActiveRegistration(Registration activeRegistration)
	{
		// begin-user-code
		this.activeRegistration = activeRegistration;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private String taxDepartment;

	/** 
	 * @return the taxDepartment
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public String getTaxDepartment()
	{
		// begin-user-code
		return taxDepartment;
		// end-user-code
	}

	/** 
	 * @param taxDepartment the taxDepartment to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setTaxDepartment(String taxDepartment)
	{
		// begin-user-code
		this.taxDepartment = taxDepartment;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean religiousMinority;

	/** 
	 * @return the religiousMinority
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isReligiousMinority()
	{
		// begin-user-code
		return religiousMinority;
		// end-user-code
	}

	/** 
	 * @param religiousMinority the religiousMinority to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setReligiousMinority(boolean religiousMinority)
	{
		// begin-user-code
		this.religiousMinority = religiousMinority;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean hasOtherDegree;

	/** 
	 * @return the hasOtherDegree
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isHasOtherDegree()
	{
		// begin-user-code
		return hasOtherDegree;
		// end-user-code
	}

	/** 
	 * @param hasOtherDegree the hasOtherDegree to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setHasOtherDegree(boolean hasOtherDegree)
	{
		// begin-user-code
		this.hasOtherDegree = hasOtherDegree;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean ethnicMinority;

	/** 
	 * @return the ethnicMinority
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isEthnicMinority()
	{
		// begin-user-code
		return ethnicMinority;
		// end-user-code
	}

	/** 
	 * @param ethnicMinority the ethnicMinority to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setEthnicMinority(boolean ethnicMinority)
	{
		// begin-user-code
		this.ethnicMinority = ethnicMinority;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private boolean publicClerk;

	/** 
	 * @return the publicClerk
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public boolean isPublicClerk()
	{
		// begin-user-code
		return publicClerk;
		// end-user-code
	}

	/** 
	 * @param publicClerk the publicClerk to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setPublicClerk(boolean publicClerk)
	{
		// begin-user-code
		this.publicClerk = publicClerk;
		// end-user-code
	}
}