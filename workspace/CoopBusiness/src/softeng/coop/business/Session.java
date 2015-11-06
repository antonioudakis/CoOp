/**
 * 
 */
package softeng.coop.business;

import java.util.List;
import softeng.coop.dataaccess.Permission;
import softeng.coop.business.companies.CompaniesManager;
import softeng.coop.business.locations.LocationsManager;
import softeng.coop.business.students.StudentsManager;
import softeng.coop.business.universities.UniversitiesManager;
import softeng.coop.business.faculties.FacultyUsersManager;
import softeng.coop.business.coops.CoOpsManager;
import softeng.coop.business.jobpostings.JobPostingsManager;
import softeng.coop.business.jobs.JobsManager;
import softeng.coop.business.payments.PaymentsManager;
import softeng.coop.business.reporting.ReportsManager;
import softeng.coop.dataaccess.CoOp;
import java.util.Locale;
import softeng.coop.dataaccess.AuthenticatedUser;
import java.util.Set;
import javax.persistence.EntityManager;
import softeng.coop.dataaccess.Role;

/** 
 * <!-- begin-UML-doc -->
 * <p>This class is a handle to access all business operations. Cannot be constructed directly, user the SessionFactory's methods instead.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public abstract class Session implements IDisposable
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The collection of listeners to model actions performed by the session.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	protected List<SessionListener> sessionListeners = new java.util.ArrayList<SessionListener>();

	/** 
	 * @return the sessionListeners
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public List<SessionListener> getSessionListeners()
	{
		// begin-user-code
		return sessionListeners;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	protected AuthorizationContext authorizationContext;

	/** 
	 * @return the authorizationContext
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public AuthorizationContext getAuthorizationContext()
	{
		// begin-user-code
		return authorizationContext;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Releases the current session and its resources. Must be called only once after acquiring the session. Typically, it should be invoked in a 'finally' block.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract void dispose();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Checks if a permission is granted to the authenticated user associated with this session. If the permission name does not exist, returns null.</p>
	 * <!-- end-UML-doc -->
	 * @param name
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract PermissionCheck hasPermission(String name);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Batch check of a collection of permissions. Returns an array that describes if the corresponding permission is granted.</p>
	 * <!-- end-UML-doc -->
	 * @param permissions
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract List<PermissionCheck> hasPermissions(Permission... permissions);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Batch check of a collection of permissions. Returns an array that describes if the corresponding permission is granted. If the some names do not exist, the elements of the returned list are null.</p>
	 * <!-- end-UML-doc -->
	 * @param permissionNames
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract List<PermissionCheck> hasPermissions(String... permissionNames);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Checks if a manager is granted to the authenticated user associated with this session. If the manager name does not exist, returns null.</p>
	 * <!-- end-UML-doc -->
	 * @param managerName
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract ManagerCheck hasManager(String managerName);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Batch check of a collection of managers. Returns an array that describes if the corresponding manager is granted. If the some names do not exist, the elements of the returned list are null.</p>
	 * <!-- end-UML-doc -->
	 * @param managerNames
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract List<ManagerCheck> hasManagers(String... managerNames);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract ManagerBase getBaseManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Obtains a manager to perform business tasks pertaining to companies. Returns null if the authenticated user associated with the session does not have the relevant permission.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract CompaniesManager getCompaniesManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Obtains a manager to perform business tasks pertaining to geographical locations. Returns null if the authenticated user associated with the session does not have the relevant permission.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract LocationsManager getLocationsManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Obtains a manager to perform business tasks pertaining to students. Returns null if the authenticated user associated with the session does not have the relevant permission.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract StudentsManager getStudentsManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Obtains a manager to perform business tasks pertaining to universities. Returns null if the authenticated user associated with the session does not have the relevant permission.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract UniversitiesManager getUniversitiesManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Obtains a manager to perform business tasks pertaining to faculty members. Returns null if the authenticated user associated with the session does not have the relevant permission.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract FacultyUsersManager getFacultyUsersManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Obtains a manager to perform business tasks pertaining to coops. Returns null if the authenticated user associated with the session does not have the relevant permission.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract CoOpsManager getCoOpsManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Obtains a manager to perform business tasks pertaining to job postings. Returns null if the authenticated user associated with the session does not have the relevant permission.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract JobPostingsManager getJobPostingsManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract JobsManager getJobsManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Obtains a manager to perform business tasks pertaining to payments. Returns null if the authenticated user associated with the session does not have the relevant permission.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract PaymentsManager getPaymentsManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract ReportsManager getReportsManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the default coop for the authenticated user associated with this session.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract CoOp getDefaultCoop();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Set the default coop for the authenticated user associated with this session.</p>
	 * <!-- end-UML-doc -->
	 * @param coop
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract void setDefaultCoop(CoOp coop);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param locale
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract boolean isLocaleAvailable(Locale locale);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Attempt to set the locale of the user's preferred language. Returns true if a system language is available for the requested locale, else returns false and the system default language is assigned.</p>
	 * <!-- end-UML-doc -->
	 * @param locale
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract boolean setUserLocale(Locale locale);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the user's preferred language locale.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Locale getUserLocale();

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract AuthenticatedUser getAuthenticatedUser();

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract List<Locale> getAvailableLocales();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Discard any changes in the entity and refresh from the database.</p>
	 * <!-- end-UML-doc -->
	 * @param entity
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract void refreshEntity(Object entity);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Returns true if the entity is attached to the database, regardless of being changed or not. Returns false for non-persisted or irrelevant instances.</p>
	 * <!-- end-UML-doc -->
	 * @param entity
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract boolean containsEntity(Object entity);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Returns true if an entity is managed by the session and its specified property is loaded.</p>
	 * <!-- end-UML-doc -->
	 * @param entity
	 * @param propertyName
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract boolean isLoaded(Object entity, String propertyName);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	abstract void enterTransactionScope();

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	abstract void enterMandatoryTransactionScope();

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param shouldCommit
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	abstract boolean exitTransactionScope(boolean shouldCommit);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Set of entities attempted to be added but not yet succeeded transaction.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Set<Object> getOutstandingAdded();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Set of entities attempted to be deleted but not yet succeeded transaction.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Set<Object> getOutstandingDeleted();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Set of entities attempted to be edited but not yet succeeded transaction.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Set<Object> getOutstandingEdited();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Attempt to revert all outstanding changed entities that did not succeed transaction.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract boolean revertOutstanding();

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	abstract EntityManager getEntityManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get all roles defined.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Set<Role> getRoles();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the role specified by the given id, else return null if not found.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Role getRole(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param name
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Role getRole(String name);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Disconnect all loaded entities from the current session.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract void clean();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Attach a detached entity to the session. Returns the managed entity.</p>
	 * <!-- end-UML-doc -->
	 * @param entity
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Object attach(Object entity);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Detach an entity from the session.</p>
	 * <!-- end-UML-doc -->
	 * @param entity
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract void detach(Object entity);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>True if the session has at least one level of nested transaction active.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract boolean isInTransaction();
}