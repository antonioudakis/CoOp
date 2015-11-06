/**
 * 
 */
package softeng.coop.business.authentication;

/** 
 * <!-- begin-UML-doc -->
 * <p>Describes the status of a registration attempt.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public enum RegistrationStatus
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Registration for the given authenticated user principal was successful. A valid session is returned.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	RegistrationSucceeded,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The given authenticated user principal is already registered. A valid session is returned.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	AlreadyRegistered,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Registration failed for the given authenticated user principal. No session is returned.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	RegistrationFailed,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>No authenticated user principal was supplied. No session is returned.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	NoAuthenticatedUser
}