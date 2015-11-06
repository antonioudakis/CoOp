/**
 * 
 */
package softeng.coop.business.payments;

import softeng.coop.business.SearchCriteria;
import softeng.coop.dataaccess.Student;
import softeng.coop.dataaccess.PaymentType;
import java.util.Date;
import softeng.coop.dataaccess.FinancialSource;
import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Registration;

/** 
 * <!-- begin-UML-doc -->
 * <p>Criteria for payments search.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class PaymentsSearchCriteria extends SearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The student involved in payments, or null if unspecified.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Student student;

	/** 
	 * @return the student
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Student getStudent()
	{
		// begin-user-code
		return student;
		// end-user-code
	}

	/** 
	 * @param student the student to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setStudent(Student student)
	{
		// begin-user-code
		this.student = student;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The payments type, if specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private PaymentType paymentType;

	/** 
	 * @return the paymentType
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public PaymentType getPaymentType()
	{
		// begin-user-code
		return paymentType;
		// end-user-code
	}

	/** 
	 * @param paymentType the paymentType to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setPaymentType(PaymentType paymentType)
	{
		// begin-user-code
		this.paymentType = paymentType;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The lower bound of payments date, if specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Date fromDate;

	/** 
	 * @return the fromDate
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Date getFromDate()
	{
		// begin-user-code
		return fromDate;
		// end-user-code
	}

	/** 
	 * @param fromDate the fromDate to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setFromDate(Date fromDate)
	{
		// begin-user-code
		this.fromDate = fromDate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The upper bound of payments date, if specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Date toDate;

	/** 
	 * @return the toDate
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Date getToDate()
	{
		// begin-user-code
		return toDate;
		// end-user-code
	}

	/** 
	 * @param toDate the toDate to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setToDate(Date toDate)
	{
		// begin-user-code
		this.toDate = toDate;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The financial source of the payments, if specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private FinancialSource financialSource;

	/** 
	 * @return the financialSource
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public FinancialSource getFinancialSource()
	{
		// begin-user-code
		return financialSource;
		// end-user-code
	}

	/** 
	 * @param financialSource the financialSource to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setFinancialSource(FinancialSource financialSource)
	{
		// begin-user-code
		this.financialSource = financialSource;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The coop involved in payments, if specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private CoOp coOp;

	/** 
	 * @return the coOp
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public CoOp getCoOp()
	{
		// begin-user-code
		return coOp;
		// end-user-code
	}

	/** 
	 * @param coOp the coOp to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setCoOp(CoOp coOp)
	{
		// begin-user-code
		this.coOp = coOp;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Registration registration;

	/** 
	 * @return the registration
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Registration getRegistration()
	{
		// begin-user-code
		return registration;
		// end-user-code
	}

	/** 
	 * @param registration the registration to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setRegistration(Registration registration)
	{
		// begin-user-code
		this.registration = registration;
		// end-user-code
	}
}