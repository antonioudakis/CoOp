/**
 * 
 */
package softeng.coop.business.students;

import softeng.coop.business.ManagerBase;
import softeng.coop.dataaccess.Student;
import softeng.coop.business.SearchResult;
import softeng.coop.dataaccess.Group;
import softeng.coop.dataaccess.Invitation;
import softeng.coop.dataaccess.Registration;
import softeng.coop.dataaccess.CoOp;
import java.util.Set;
import softeng.coop.dataaccess.Nationality;
import java.util.List;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-only business tasks pertaining to students.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface StudentsManager extends ManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a student having the given id. Returns null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Student getStudent(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for students using given criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<Student> searchStudents(StudentsSearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a student by student code. Returns null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param code
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Student getStudentByCode(String code);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a student by user name. Returns null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param userName
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Student getStudentByUserName(String userName);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a group having the given id. Returns null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Group getGroup(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for groups satisfying the given criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<Group> searchGroups(GroupSearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get an invitation having the given id. Returns null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Invitation getInvitation(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param code
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Invitation getInvitationByCode(String code);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a manager allowing write operations. If the appropriate permission is not granted to the user associated with the manager's session, it returns null.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public StudentsWriterManager getWriterManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Registration getRegistration(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the active registration of the student having the specified code. If the student does not exist or does not have an active registration, it returns null.</p>
	 * <!-- end-UML-doc -->
	 * @param studentCode
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Registration getActiveRegistration(String studentCode);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the registration of a given student to a given coop, if such a registration exists. Returns null if there is no student by the specified code or if there is no registration of the student to the given coop.</p>
	 * <!-- end-UML-doc -->
	 * @param studentCode
	 * @param coop
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Registration getRegistration(String studentCode, CoOp coop);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for registrations satisfying the given criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<Registration> searchRegistrations(RegistrationsSearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Suggests coops which the the user might register to. Searches for ready and under registration coops which belong to lessons that the student is not currently involved with and has not passed.</p>
	 * <!-- end-UML-doc -->
	 * @param student
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Set<CoOp> suggestCoopsForRegistration(Student student);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get nationality by standard UN code, or return null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param code
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Nationality getNationality(String code);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get all the nationalities.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public List<Nationality> getNationalities();
}