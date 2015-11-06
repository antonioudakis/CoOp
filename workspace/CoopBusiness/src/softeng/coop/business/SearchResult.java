/**
 * 
 */
package softeng.coop.business;

import java.util.List;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class SearchResult<T>
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private int totalCount;

	/** 
	 * @return the totalCount
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public int getTotalCount()
	{
		// begin-user-code
		return totalCount;
		// end-user-code
	}

	/** 
	 * @param totalCount the totalCount to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setTotalCount(int totalCount)
	{
		// begin-user-code
		this.totalCount = totalCount;
		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
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
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private List<T> list;

	/** 
	 * @return the list
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public List<T> getList()
	{
		// begin-user-code
		return list;
		// end-user-code
	}

	/** 
	 * @param list the list to set
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setList(List<T> list)
	{
		// begin-user-code
		this.list = list;
		// end-user-code
	}
}