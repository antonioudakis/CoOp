/**
 * 
 */
package softeng.coop.business;

import softeng.coop.dataaccess.Multilingual;
import softeng.coop.dataaccess.Language;
import softeng.coop.dataaccess.ITrackedEntity;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface WriterManagerBase extends ManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Set a text literal of a multilingual string in a given user's preferred language. The literal will be the default for the multilingual string.</p>
	 * <!-- end-UML-doc -->
	 * @param multilingual
	 * @param value
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setDefaultLiteral(Multilingual multilingual, String value);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param multilingual
	 * @param value
	 * @param language
	 * @param isDefault
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setLiteral(Multilingual multilingual, String value, Language language, boolean isDefault);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public TransactionScope beginTransaction();

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param propagationType
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public TransactionScope beginTransaction(PropagationType propagationType);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param trackedEntity
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void markAsChanged(ITrackedEntity trackedEntity);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param entity
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void validate(Object entity);
}