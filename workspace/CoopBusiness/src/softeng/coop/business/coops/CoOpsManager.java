/**
 * 
 */
package softeng.coop.business.coops;

import softeng.coop.business.ManagerBase;
import softeng.coop.dataaccess.CoOp;
import softeng.coop.business.SearchResult;
import softeng.coop.dataaccess.Lesson;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-only business tasks pertaining to coops.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface CoOpsManager extends ManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a coop by its id. Returns the coop or null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public CoOp getCoOp(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for coops using given search criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<CoOp> searchCoOps(CoOpSearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a manager allowing write operations. If the appropriate permission is not granted to the user associated with the manager's session, it returns null.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public CoOpsWriterManager getWriterManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a Lesson by its id. Returns the Lesson or null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Lesson getLesson(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search Lessons given specific search criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<Lesson> searchLessons(LessonSearchCriteria criteria);
}