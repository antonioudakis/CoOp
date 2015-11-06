/**
 * 
 */
package softeng.coop.business.reporting;

import softeng.coop.business.WriterManagerBase;
import softeng.coop.dataaccess.ReportType;
import softeng.coop.dataaccess.Report;
import softeng.coop.dataaccess.Attachment;
import java.util.Set;
import softeng.coop.dataaccess.ReportScope;
import java.util.List;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-write business tasks pertaining to companies.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface ReportsWriterManager extends ReportsManager, WriterManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Create a report of a given type on behalf of the session's user, supplying a dictionary of parameters as specified by method getParametersSpecification. Depending on the type, sets report fields and attachments automatically or leaves the report blank if a generic report type is specified. Returns null if the report type is not allowed to the user. The report is only created in-memory, and a 'persist' method should be called, either directly on this manager or indirectly through cascade persisting on it's containing a coop, a registration, a group, a job part.</p>
	 * <!-- end-UML-doc -->
	 * @param reportType
	 * @param parameters
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Report createReport(ReportType reportType, Object parameters);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist a given report to the database.</p>
	 * <!-- end-UML-doc -->
	 * @param report
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistReport(Report report);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist a given attachment to the database. Note that the attachment can also be persisted by persisting its containing report because the relationship from a report to its attachments is cascade-all.</p>
	 * <!-- end-UML-doc -->
	 * @param attachment
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistAttachment(Attachment attachment);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a given report having the specified id. An IntegrityConstraintException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteReport(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified report and its attachments. An IntegrityConstraintException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param report
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteReport(Report report);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the report types that are allowed to be issued by the session's user.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Set<ReportType> getAllowedReportTypes();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the report types of the given scope that are allowed to be issued by the session's user.</p>
	 * <!-- end-UML-doc -->
	 * @param scope
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Set<ReportType> getAllowedReportTypes(ReportScope scope);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the list of parameters required for createReport method.</p>
	 * <!-- end-UML-doc -->
	 * @param reportType
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public List<IParameterType> getParametersSpecification(ReportType reportType);
}