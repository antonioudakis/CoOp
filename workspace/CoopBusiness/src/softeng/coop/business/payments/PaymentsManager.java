/**
 * 
 */
package softeng.coop.business.payments;

import softeng.coop.business.ManagerBase;
import softeng.coop.dataaccess.Payment;
import softeng.coop.business.SearchResult;
import java.util.Set;
import softeng.coop.dataaccess.FinancialSource;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-only business tasks pertaining to payments.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface PaymentsManager extends ManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a payment specified by the given id.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Payment getPayment(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for payments using given criteria.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<Payment> searchPayments(PaymentsSearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Search for payments using given criteria and aggregate the results using given groupings.</p>
	 * <!-- end-UML-doc -->
	 * @param criteria
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SearchResult<PaymentAggregate> aggregatePayments(PaymentAggregateSearchCriteria criteria);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get a manager allowing write operations. If the appropriate permission is not granted to the user associated with the manager's session, it returns null.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public PaymentsWriterManager getWriterManager();

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Set<FinancialSource> getFinancialSources();
}