/**
 * 
 */
package softeng.coop.business.faculties;

import softeng.coop.business.WriterManagerBase;
import softeng.coop.dataaccess.FacultyUser;
import softeng.coop.dataaccess.Professor;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-write business tasks pertaining to faculty users.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface FacultyUsersWriterManager extends FacultyUsersManager, WriterManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist a given faculty user. Can be used for professors too.</p>
	 * <!-- end-UML-doc -->
	 * @param facultyUser
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistFacultyUser(FacultyUser facultyUser);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a faculty user specified by the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteFacultyUser(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a faculty user.</p>
	 * <!-- end-UML-doc -->
	 * @param facultyUser
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteFacultyUser(Object facultyUser);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist a professor.</p>
	 * <!-- end-UML-doc -->
	 * @param professor
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistProfessor(Professor professor);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a professor specified by the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteProfessor(int id);
}