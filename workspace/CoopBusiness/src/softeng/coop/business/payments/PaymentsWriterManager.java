/**
 * 
 */
package softeng.coop.business.payments;

import softeng.coop.business.WriterManagerBase;
import softeng.coop.dataaccess.Payment;
import softeng.coop.dataaccess.FinancialSource;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-write business tasks pertaining to payments.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface PaymentsWriterManager extends PaymentsManager, WriterManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist the given payment.</p>
	 * <!-- end-UML-doc -->
	 * @param payment
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistPayment(Payment payment);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a payment specified by the given id. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deletePayment(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a specified payment. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param payment
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deletePayment(Payment payment);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param source
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistFinancialSource(FinancialSource source);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the financial source specified by the given id. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteFinancialSource(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified financial source. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param source
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteFinancialSource(FinancialSource source);
}