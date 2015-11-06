/**
 * 
 */
package softeng.coop.business.reporting;

/** 
 * <!-- begin-UML-doc -->
 * <p>Data type specification of parameters passed in createReport methods of classes ReportsManager and ReportFactory.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public enum DataType
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Integer.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	Int,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>String.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	String,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Boolean.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	Boolean,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Date.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	Date,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Category.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	Category,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Location.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	Location,
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>When the user has permissions of a student supervisor, this parameter offers a selection of a supervised student.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	SupervisedStudent, /** 
											 * <!-- begin-UML-doc -->
											 * <p>A financial source from those assigned to the current coop, if any.</p>
											 * <!-- end-UML-doc -->
											 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
											 */
	CoopFinancialSource, /** 
												 * <!-- begin-UML-doc -->
												 * <p>A choice from JobSiteType enumeration.</p>
												 * <!-- end-UML-doc -->
												 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
												 */
	JobSiteType, /** 
								 * <!-- begin-UML-doc -->
								 * <p>A choice from PaymentType enumeration.</p>
								 * <!-- end-UML-doc -->
								 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
								 */
	PaymentType, /** 
								 * <!-- begin-UML-doc -->
								 * <p>A choice from Gender enumeration.</p>
								 * <!-- end-UML-doc -->
								 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
								 */
	Gender, /** 
					 * <!-- begin-UML-doc -->
					 * <p>A division of the department where the current coop is defined.</p>
					 * <!-- end-UML-doc -->
					 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
					 */
	Division, /** 
						 * <!-- begin-UML-doc -->
						 * <p>Double precision floating point.</p>
						 * <!-- end-UML-doc -->
						 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
						 */
	Double
}