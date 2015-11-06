/**
 * 
 */
package softeng.coop.business.coops;

import softeng.coop.business.WriterManagerBase;
import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Lesson;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-write business tasks pertaining to coops.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface CoOpsWriterManager extends CoOpsManager, WriterManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist the given coop.</p>
	 * <!-- end-UML-doc -->
	 * @param coOp
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistCoOp(CoOp coOp);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the coop specified by the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteCoOp(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified coop.</p>
	 * <!-- end-UML-doc -->
	 * @param coOp
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteCoOp(CoOp coOp);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist a given Lesson.</p>
	 * <!-- end-UML-doc -->
	 * @param lesson
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistLesson(Lesson lesson);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the Lesson specified by the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteLesson(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified lesson.</p>
	 * <!-- end-UML-doc -->
	 * @param lesson
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteLesson(Lesson lesson);
}