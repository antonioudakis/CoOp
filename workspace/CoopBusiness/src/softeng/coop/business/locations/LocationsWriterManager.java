/**
 * 
 */
package softeng.coop.business.locations;

import softeng.coop.business.WriterManagerBase;
import softeng.coop.dataaccess.Location;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-write business tasks pertaining to locations.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface LocationsWriterManager extends LocationsManager, WriterManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a location having a given id and its sub-locations. An IntegrityConstraintException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void cascadeDeleteLocation(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified location including sub-locations. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param location
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void cascadeDeleteLocation(Location location);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Move a location and its subtree of sub-locations and place them under a new parent. If the parent is root, make the location a root location.</p>
	 * <!-- end-UML-doc -->
	 * @param location
	 * @param newParent
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean moveLocation(Location location, Location newParent);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist a given location.</p>
	 * <!-- end-UML-doc -->
	 * @param location
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistLocation(Location location);
}