/**
 * 
 */
package softeng.coop.business.locations;

import softeng.coop.business.ManagerBase;
import java.util.List;
import softeng.coop.dataaccess.Location;
import softeng.coop.business.SearchResult;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-only business tasks pertaining to locations.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface LocationsManager extends ManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public List<Location> getRootLocations();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a location having a given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Location getLocation(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for locations using given criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param searchCriteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<Location> searchLocations(LocationsSearchCriteria searchCriteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a manager allowing write operations. If the appropriate permission is not granted to the user associated with the manager's session, it returns null.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public LocationsWriterManager getWriterManager();
}