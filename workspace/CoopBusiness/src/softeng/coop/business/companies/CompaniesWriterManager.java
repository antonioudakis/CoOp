/**
 * 
 */
package softeng.coop.business.companies;

import softeng.coop.business.WriterManagerBase;
import softeng.coop.dataaccess.Company;
import softeng.coop.dataaccess.Category;
import softeng.coop.dataaccess.Branch;
import softeng.coop.dataaccess.CompanyPerson;
import softeng.coop.dataaccess.ActivitySector;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-write business tasks pertaining to companies.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface CompaniesWriterManager extends CompaniesManager, WriterManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the company specified by the given id, its branches and its company persons. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteCompany(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified company, its branches and its company persons. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param company
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteCompany(Company company);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist the given company.</p>
	 * <!-- end-UML-doc -->
	 * @param company
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistCompany(Company company);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the category specified by the given id and all its sub-categories. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void cascadeDeleteCategory(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified category and all its sub-categories. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param category
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void cascadeDeleteCategory(Category category);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Move a given category and its sub-categories under a new parent. if the new parent is null, the category becomes a root category.</p>
	 * <!-- end-UML-doc -->
	 * @param category
	 * @param newParent
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean moveCategory(Category category, Category newParent);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist the given category.</p>
	 * <!-- end-UML-doc -->
	 * @param category
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistCategory(Category category);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist the given branch.</p>
	 * <!-- end-UML-doc -->
	 * @param branch
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistBranch(Branch branch);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the branch specified by the given id. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteBranch(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified branch. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param branch
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteBranch(Branch branch);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist the given company person.</p>
	 * <!-- end-UML-doc -->
	 * @param person
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistCompanyPerson(CompanyPerson person);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the company person specified by the given id. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteCompanyPerson(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified company person. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param person
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteCompanyPerson(CompanyPerson person);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist the given activity sector.</p>
	 * <!-- end-UML-doc -->
	 * @param activitySector
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistActivitySector(ActivitySector activitySector);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the activity sector specified by the given id and all its sub-sectors. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void cascadeDeleteActivitySector(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified activity sector and all its sub-sectors. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param activitySector
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void cascadeDeleteActivitySector(ActivitySector activitySector);
}