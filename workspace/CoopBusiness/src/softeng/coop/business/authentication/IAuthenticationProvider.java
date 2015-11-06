/**
 * 
 */
package softeng.coop.business.authentication;

/** 
 * <!-- begin-UML-doc -->
 * <p>A contract for an authentication provider.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface IAuthenticationProvider
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Returns the currently authenticated user, if one exists, else returns null.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public IUser getAthenticatedUser();
}