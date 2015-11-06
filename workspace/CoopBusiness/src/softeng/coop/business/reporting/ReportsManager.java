/**
 * 
 */
package softeng.coop.business.reporting;

import softeng.coop.business.ManagerBase;
import softeng.coop.dataaccess.Report;
import softeng.coop.dataaccess.ReportType;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-only business tasks pertaining to reports.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface ReportsManager extends ManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a report having the given id. Returns null if it does not exist.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Report getReport(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a report type having the given id. Returns null if it does not exist.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public ReportType getReportType(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param codeName
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public ReportType getReportType(String codeName);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a manager allowing write operations. If the appropriate permission is not granted to the user associated with the manager's session, it returns null.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public ReportsWriterManager getWriterManager();
}