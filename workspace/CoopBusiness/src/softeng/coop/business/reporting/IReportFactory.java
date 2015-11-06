/**
 * 
 */
package softeng.coop.business.reporting;

import softeng.coop.business.Session;
import softeng.coop.dataaccess.ReportType;
import softeng.coop.dataaccess.Report;
import java.util.List;

/** 
 * <!-- begin-UML-doc -->
 * <p>Contract for a report factory.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface IReportFactory
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Create a report of a given report type on behalf of a session's user, supplying a dictionary of parameters as specified by method getParametersSpecification.</p>
	 * <!-- end-UML-doc -->
	 * @param session
	 * @param reportType
	 * @param parameters
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Report createReport(Session session, ReportType reportType, Object parameters);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the list of parameters required for createReport method.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public List<IParameterType> getParametersSpecification();
}