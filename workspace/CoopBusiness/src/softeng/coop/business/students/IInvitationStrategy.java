/**
 * 
 */
package softeng.coop.business.students;

import softeng.coop.dataaccess.Invitation;
import javax.persistence.EntityManager;
import softeng.coop.dataaccess.Registration;
import softeng.coop.dataaccess.Student;

/** 
 * <!-- begin-UML-doc -->
 * <p>Listener specification for invitation events.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface IInvitationStrategy
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Invoked when an invitation is posted from a sender to recepients.</p>
	 * <!-- end-UML-doc -->
	 * @param invitation
	 * @param entityManager
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void onPostInvitation(Invitation invitation, EntityManager entityManager);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Invoked when a recepient accepts an invitation.</p>
	 * <!-- end-UML-doc -->
	 * @param recepient
	 * @param invitation
	 * @param entityManager
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void onAcceptInvitation(Registration recepient, Invitation invitation, EntityManager entityManager);
}