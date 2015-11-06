/**
 * 
 */
package softeng.coop.business;

/** 
 * <!-- begin-UML-doc -->
 * <p>An information message returned by an operation.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class InformationMessage
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The severity of the message.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private InformationMessageLevel level;

	/** 
	 * @return the level
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public InformationMessageLevel getLevel()
	{
		// begin-user-code
		return level;
		// end-user-code
	}

	/** 
	 * @param level the level to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setLevel(InformationMessageLevel level)
	{
		// begin-user-code
		this.level = level;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The text of the message.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String text;

	/** 
	 * @return the text
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getText()
	{
		// begin-user-code
		return text;
		// end-user-code
	}

	/** 
	 * @param text the text to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setText(String text)
	{
		// begin-user-code
		this.text = text;
		// end-user-code
	}
}