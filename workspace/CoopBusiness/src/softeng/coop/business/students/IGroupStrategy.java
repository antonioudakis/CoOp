/**
 * 
 */
package softeng.coop.business.students;

import softeng.coop.dataaccess.Group;
import javax.persistence.EntityManager;

/** 
 * <!-- begin-UML-doc -->
 * <p>Listener specification for group open and close events.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface IGroupStrategy
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Invoked when a group is opened for membership changes.</p>
	 * <!-- end-UML-doc -->
	 * @param group
	 * @param entityManager
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void onOpenGroup(Group group, EntityManager entityManager);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Invoked when a group is closed for membership changes.</p>
	 * <!-- end-UML-doc -->
	 * @param group
	 * @param entityManager
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void onCloseGroup(Group group, EntityManager entityManager);
}