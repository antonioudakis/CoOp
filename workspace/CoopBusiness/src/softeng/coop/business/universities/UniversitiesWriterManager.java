/**
 * 
 */
package softeng.coop.business.universities;

import softeng.coop.business.WriterManagerBase;
import softeng.coop.dataaccess.University;
import softeng.coop.dataaccess.Department;
import softeng.coop.dataaccess.Division;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-write business tasks pertaining to universities.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface UniversitiesWriterManager extends UniversitiesManager, WriterManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist a university.</p>
	 * <!-- end-UML-doc -->
	 * @param university
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistUniversity(University university);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a university having the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteUniversity(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified university. An IntegrityConstraintException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param university
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteUniversity(University university);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist a department.</p>
	 * <!-- end-UML-doc -->
	 * @param department
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistDepartment(Department department);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a department having the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteDepartment(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified department. An IntegrityConstraintException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param department
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteDepartment(Department department);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist a division.</p>
	 * <!-- end-UML-doc -->
	 * @param division
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistDivision(Division division);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a division having the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteDivision(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified division. An IntegrityConstraintException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param division
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteDivision(Division division);
}