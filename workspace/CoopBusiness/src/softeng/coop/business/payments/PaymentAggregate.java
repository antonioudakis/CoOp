/**
 * 
 */
package softeng.coop.business.payments;

import softeng.coop.dataaccess.Student;
import softeng.coop.dataaccess.FinancialSource;
import java.math.BigDecimal;

/** 
 * <!-- begin-UML-doc -->
 * <p>Represents a result row of an aggregate search for payments.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class PaymentAggregate
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The aggregating student, if specified, else null.</p>
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
	 * <p>The aggregating source, if specified, else null.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private FinancialSource source;

	/** 
	 * @return the source
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public FinancialSource getSource()
	{
		// begin-user-code
		return source;
		// end-user-code
	}

	/** 
	 * @param source the source to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setSource(FinancialSource source)
	{
		// begin-user-code
		this.source = source;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The total aggregated payment amount.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private BigDecimal totalAmount;

	/** 
	 * @return the totalAmount
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public BigDecimal getTotalAmount()
	{
		// begin-user-code
		return totalAmount;
		// end-user-code
	}

	/** 
	 * @param totalAmount the totalAmount to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setTotalAmount(BigDecimal totalAmount)
	{
		// begin-user-code
		this.totalAmount = totalAmount;
		// end-user-code
	}
}