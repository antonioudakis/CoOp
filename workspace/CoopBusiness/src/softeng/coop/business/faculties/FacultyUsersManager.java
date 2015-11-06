/**
 * 
 */
package softeng.coop.business.faculties;

import softeng.coop.business.ManagerBase;
import softeng.coop.dataaccess.FacultyUser;
import softeng.coop.dataaccess.Professor;
import softeng.coop.business.SearchResult;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-only business tasks pertaining to faculties.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface FacultyUsersManager extends ManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the faculty user specified by the given id. Returns null if not found. This includes professors. When the returned FacultyUser object corresponds to a professor, it can be cast to Professor.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public FacultyUser getFacultyUser(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the professor specified by the given id. Returns null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Professor getProfessor(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for faculty users using given criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<FacultyUser> searchFacultyUsers(FacultyUserSearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for professors using given criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<Professor> searchProfessors(ProfessorSearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a manager allowing write operations. If the appropriate permission is not granted to the user associated with the manager's session, it returns null.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public FacultyUsersWriterManager getWriterManager();
}