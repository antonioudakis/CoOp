/**
 * 
 */
package softeng.coop.business.authentication;

/** 
 * <!-- begin-UML-doc -->
 * <p>Strategy contract for registering a user principal.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface IRegistrationStrategy
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the appropriate enrollment strategy for a user according to his affiliation, for example student or professor.</p>
	 * <!-- end-UML-doc -->
	 * @param user
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public IEnrollmentByAffiliationStrategy getEnrollmentStrategy(IUser user);
}