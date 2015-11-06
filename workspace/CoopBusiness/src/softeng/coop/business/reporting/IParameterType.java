/**
 * 
 */
package softeng.coop.business.reporting;

import java.util.Locale;
import softeng.coop.dataaccess.Language;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface IParameterType
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The display name of the parameter in the given language.</p>
	 * <!-- end-UML-doc -->
	 * @param locale
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getName(Locale locale);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The description of the parameter in the given language.</p>
	 * <!-- end-UML-doc -->
	 * @param locale
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getDescription(Locale locale);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The key name of the parameter. It is the key of the dictionary entry of the parameter when the parameters dictionary is passed to createReport methods.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getKey();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The data type of the parameter.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public DataType getDataType();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>True if the parameter is required. Else, a null value is allowed.</p>
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean isRequired();

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Optionally suggest a default value for the parameter, else null.</p>
	 * <!-- end-UML-doc -->
	 * @param locale
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Object getDefaultValue(Locale locale);
}