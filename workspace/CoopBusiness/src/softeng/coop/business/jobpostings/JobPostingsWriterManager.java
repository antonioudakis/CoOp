/**
 * 
 */
package softeng.coop.business.jobpostings;

import softeng.coop.business.WriterManagerBase;
import softeng.coop.dataaccess.JobPosting;
import softeng.coop.dataaccess.JobPostingPart;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-write business tasks pertaining to job postings.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface JobPostingsWriterManager extends JobPostingsManager, WriterManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Persist the given job posting.</p>
	 * <!-- end-UML-doc -->
	 * @param jobPosting
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistJobPosting(JobPosting jobPosting);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete a job posting having the given id. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteJobPosting(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified job posting. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param jobPosting
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteJobPosting(JobPosting jobPosting);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param jobPostingPart
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistJobPostingPart(JobPostingPart jobPostingPart);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the job posting part specified by the given id. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteJobPostingPart(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified job posting part. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param jobPostingPart
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteJobPostingPart(JobPostingPart jobPostingPart);
}