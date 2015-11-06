/**
 * 
 */
package softeng.coop.business;

import softeng.coop.dataaccess.Multilingual;
import softeng.coop.dataaccess.Language;
import java.util.Set;

/** 
 * <!-- begin-UML-doc -->
 * <p>Base for all business managers.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface ManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Determine if the authenticated user associated with the manager's session can perform changes to the system. If this returns true, the 'getWriterManager' method can be called to obtain a manager that provides the additional operations.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean isWriteable();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the literal of the given multilingual text using the currently authenticated user's preferred language.</p>
	 * <!-- end-UML-doc -->
	 * @param multilingual
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getLiteral(Multilingual multilingual);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get the literal of the given multilingual text using the specified preferred language.</p>
	 * <!-- end-UML-doc -->
	 * @param multilingual
	 * @param preferredLanguage
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getLiteral(Multilingual multilingual, Language preferredLanguage);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Session getSession();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Get all the languages supported in the system.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Set<Language> getAvailableLanguages();
}