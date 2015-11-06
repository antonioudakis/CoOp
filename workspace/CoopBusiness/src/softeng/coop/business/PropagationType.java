/**
 * 
 */
package softeng.coop.business;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public enum PropagationType
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Support a current transaction; throw an exception if no current transaction exists.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	Mandatory,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Execute within a nested transaction if a current transaction exists, behave like "Required" else.</p><p>Note: Currently not supported by underlying JPA.<a href="../../../org/springframework/transaction/TransactionDefinition.html#PROPAGATION_REQUIRED"></a></p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	Nested,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Support a current transaction; create a new one if none exists.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	Required,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Create a new transaction, suspending the current transaction if one exists.</p><p>Note: Currently not supported by underlying JPA.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	RequiresNew
}