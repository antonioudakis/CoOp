/**
 * 
 */
package softeng.coop.business.payments;

/** 
 * <!-- begin-UML-doc -->
 * <p>Specifies the groupings used for an aggregate search for payments.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class PaymentAggregateSearchCriteria extends PaymentsSearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>True if search results are grouped by student.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private boolean groupByStudent;

	/** 
	 * @return the groupByStudent
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean isGroupByStudent()
	{
		// begin-user-code
		return groupByStudent;
		// end-user-code
	}

	/** 
	 * @param groupByStudent the groupByStudent to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setGroupByStudent(boolean groupByStudent)
	{
		// begin-user-code
		this.groupByStudent = groupByStudent;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>True if search results are grouped by financial source.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private boolean groupByFinancialSource;

	/** 
	 * @return the groupByFinancialSource
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean isGroupByFinancialSource()
	{
		// begin-user-code
		return groupByFinancialSource;
		// end-user-code
	}

	/** 
	 * @param groupByFinancialSource the groupByFinancialSource to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setGroupByFinancialSource(boolean groupByFinancialSource)
	{
		// begin-user-code
		this.groupByFinancialSource = groupByFinancialSource;
		// end-user-code
	}
}