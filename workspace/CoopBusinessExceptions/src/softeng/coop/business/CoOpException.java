/**
 * 
 */
package softeng.coop.business;

/** 
 * <!-- begin-UML-doc -->
 * <p>Base for all exceptions thrown from the business layer.</p>
 * <!-- end-UML-doc -->
 */
public class CoOpException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2309609055103850539L;

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param message
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public CoOpException(String message)
	{
		super(message);
	}
	
	public CoOpException(String message, Throwable cause)
	{
		super(message, cause);
	}
}