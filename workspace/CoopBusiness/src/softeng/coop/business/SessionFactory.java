/**
 * 
 */
package softeng.coop.business;

import java.util.Locale;
import softeng.coop.business.authentication.RegistrationResult;

/** 
 * <!-- begin-UML-doc -->
 * <p>This class provides static methods for session acquiring and user registration.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public abstract class SessionFactory
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Attempt to log in to the system as the current principal. If the current principal is null or not registered in the system, returns null, else returns a session.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Session login();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Checks if the current principal is registered in the system.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Boolean isUserRegistered();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Attempts to register the current user principal in the system.</p>
	 * <!-- end-UML-doc -->
	 * @param locale
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract RegistrationResult register(Locale locale);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Boolean isUserAuthenticated();
}