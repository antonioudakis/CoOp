/**
 * 
 */
package softeng.coop.business.authentication;

import java.util.List;
import softeng.coop.business.InformationMessage;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class RegistrationResult
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The status of the registration attempt.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private RegistrationStatus status;

	/** 
	 * @return the status
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public RegistrationStatus getStatus()
	{
		// begin-user-code
		return status;
		// end-user-code
	}

	/** 
	 * @param status the status to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setStatus(RegistrationStatus status)
	{
		// begin-user-code
		this.status = status;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private List<InformationMessage> informationMessages = new java.util.ArrayList<InformationMessage>();

	/** 
	 * @return the informationMessages
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public List<InformationMessage> getInformationMessages()
	{
		// begin-user-code
		return informationMessages;
		// end-user-code
	}
}