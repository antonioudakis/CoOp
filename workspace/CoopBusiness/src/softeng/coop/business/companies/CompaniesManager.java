/**
 * 
 */
package softeng.coop.business.companies;

import softeng.coop.business.ManagerBase;
import java.util.List;
import softeng.coop.dataaccess.Category;
import softeng.coop.dataaccess.Company;
import softeng.coop.dataaccess.Branch;
import softeng.coop.dataaccess.CompanyPerson;
import softeng.coop.business.SearchResult;
import java.util.SortedSet;
import softeng.coop.dataaccess.ActivitySector;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-only business tasks pertaining to companies.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface CompaniesManager extends ManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public List<Category> getRootCategories();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a category by its id. Returns the category, if found, else null.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Category getCategory(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a company by its id. Returns the company, if found, else null.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Company getCompany(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a company by its tax code. Returns the company, if found, else null.</p>
	 * <!-- end-UML-doc -->
	 * @param taxCode
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Company getCompany(String taxCode);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a company branch by its id. Returns the branch, if found, else null.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Branch getBranch(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a company person by its id. Returns the person, if found, else null.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public CompanyPerson getCompanyPerson(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for companies using given search criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<Company> searchCompanies(CompanySearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for company branches using given search criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<Branch> searchBranches(CompanySearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a manager allowing write operations. If the appropriate permission is not granted to the user associated with the manager's session, it returns null.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public CompaniesWriterManager getWriterManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the root-level activity sectors.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public List<ActivitySector> getRootActivitySectors();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get an activity sector by its ID, else return null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public ActivitySector getActivitySector(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get an activity sector by its code, else return null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param code
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public ActivitySector getActivitySector(String code);
}