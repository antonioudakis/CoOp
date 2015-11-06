/**
 * 
 */
package softeng.coop.business.authentication;

import javax.persistence.EntityManager;
import java.util.Locale;

/** 
 * <!-- begin-UML-doc -->
 * <p>A contract for enrollment of a user into the system based on a user's affiliation. For example, different enrollment processes exist for students and professors.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface IEnrollmentByAffiliationStrategy
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Enroll the user into the system.</p>
	 * <!-- end-UML-doc -->
	 * @param entityManager
	 * @param user
	 * @param locale
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public RegistrationResult enroll(EntityManager entityManager, IUser user, Locale locale);
}