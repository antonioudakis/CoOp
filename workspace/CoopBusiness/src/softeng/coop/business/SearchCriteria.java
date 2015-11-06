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
public class SearchCriteria
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The zero-based number of the page to retrieved.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private int pageIndex;

	/** 
	 * @return the pageIndex
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public int getPageIndex()
	{
		// begin-user-code
		return pageIndex;
		// end-user-code
	}

	/** 
	 * @param pageIndex the pageIndex to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setPageIndex(int pageIndex)
	{
		// begin-user-code
		this.pageIndex = pageIndex;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>The maximum number of items per page. If this is zero, all items are retrieved.</p>
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private int pageSize;

	/** 
	 * @return the pageSize
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public int getPageSize()
	{
		// begin-user-code
		return pageSize;
		// end-user-code
	}

	/** 
	 * @param pageSize the pageSize to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setPageSize(int pageSize)
	{
		// begin-user-code
		this.pageSize = pageSize;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private boolean retrievingTotalCount;

	/** 
	 * @return the retrievingTotalCount
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean isRetrievingTotalCount()
	{
		// begin-user-code
		return retrievingTotalCount;
		// end-user-code
	}

	/** 
	 * @param retrievingTotalCount the retrievingTotalCount to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setRetrievingTotalCount(boolean retrievingTotalCount)
	{
		// begin-user-code
		this.retrievingTotalCount = retrievingTotalCount;
		// end-user-code
	}
}