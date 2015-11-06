/**
 * 
 */
package softeng.coop.business;

/** 
 * <!-- begin-UML-doc -->
 * <p>Transaction scope obtained through a persister. As an IDisposable, it should always be freed by calling 'dispose' method, typically in a finally block. If we reach the 'dispose' method without having called 'commit' previously, the transaction is voted for rollback. In other words, the transaction vote is initially rollback unless we invoke 'commit'.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public abstract class TransactionScope implements IDisposable
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Mark the transaction as to be committed, in other words, vote for a commit.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract void commit();
}