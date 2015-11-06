/**
 * 
 */
package softeng.coop.business.students;

import softeng.coop.business.WriterManagerBase;
import softeng.coop.dataaccess.Student;
import softeng.coop.dataaccess.Group;
import java.util.Collection;
import softeng.coop.dataaccess.Multilingual;
import softeng.coop.dataaccess.Invitation;
import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Registration;
import java.util.Set;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-write business tasks pertaining to students.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface StudentsWriterManager extends StudentsManager, WriterManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist a student.</p>
	 * <!-- end-UML-doc -->
	 * @param student
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistStudent(Student student);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a student having the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteStudent(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified student. An IntegrityConstraintException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param student
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteStudent(Student student);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist a group.</p>
	 * <!-- end-UML-doc -->
	 * @param group
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistGroup(Group group);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a group having the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteGroup(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified group. An IntegrityConstraintException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param group
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteGroup(Group group);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Post invitation on behalf of a student to a list of student recepients to join a group.</p>
	 * <!-- end-UML-doc -->
	 * @param invitation
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void postInvitation(Invitation invitation);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Accept an invitation on behalf of a recepient student.</p>
	 * <!-- end-UML-doc -->
	 * @param receiver
	 * @param invitation
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void acceptInvitation(Registration receiver, Invitation invitation);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Mark the group as closed. Closed groups have fixed members.</p>
	 * <!-- end-UML-doc -->
	 * @param group
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void closeGroup(Group group);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Mark the group as open. Open groups can have their members changed.</p>
	 * <!-- end-UML-doc -->
	 * @param group
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void openGroup(Group group);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Close all groups belonging to a given coop. Closed groups have fixed members.</p>
	 * <!-- end-UML-doc -->
	 * @param coOp
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void closeGroups(CoOp coOp);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param registration
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistRegistration(Registration registration);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteRegistration(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified registration. An IntegrityConstraintException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param registration
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteRegistration(Registration registration);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Registers a student for the given coop and creates her default group. Checks if the student already has an active registration to an active coop or already passed the lesson. If this holds true, it throws a BusinessRuleViolationException.</p>
	 * <!-- end-UML-doc -->
	 * @param coop
	 * @param student
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Registration register(CoOp coop, Student student);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Merge a collection of groups to one group. If the groups collection is empty, it throws IllegalArgumentException. If the mergedGroupComments parameter is null, the resulting group will form its comments field by concatenation of the merged groups.</p>
	 * <!-- end-UML-doc -->
	 * @param groups
	 * @param mergedGroupComments
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Group mergeGroups(Collection<Group> groups, String mergedGroupComments);
}