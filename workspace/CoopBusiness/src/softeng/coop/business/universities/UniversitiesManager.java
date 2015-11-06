/**
 * 
 */
package softeng.coop.business.universities;

import softeng.coop.business.ManagerBase;
import softeng.coop.dataaccess.University;
import softeng.coop.dataaccess.Department;
import softeng.coop.dataaccess.Division;
import softeng.coop.business.SearchResult;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-only business tasks pertaining to universities.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface UniversitiesManager extends ManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get an university having the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public University getUniversity(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a department having the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Department getDepartment(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a division having the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Division getDivision(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for universities having given criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<University> searchUniversities(UniversitiesSearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for departments using given criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<Department> searchDepartments(DepartmentsSearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a manager allowing write operations. If the appropriate permission is not granted to the user associated with the manager's session, it returns null.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public UniversitiesWriterManager getWriterManager();
}