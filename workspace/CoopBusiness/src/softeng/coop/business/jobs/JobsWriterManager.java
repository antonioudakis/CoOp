/**
 * 
 */
package softeng.coop.business.jobs;

import softeng.coop.business.WriterManagerBase;
import softeng.coop.dataaccess.JobPosting;
import softeng.coop.dataaccess.Group;
import java.util.Date;
import softeng.coop.dataaccess.Job;
import softeng.coop.dataaccess.JobStateType;

/** 
 * <!-- begin-UML-doc -->
 * <p>A manager to perform read-write business tasks pertaining to jobs.</p>
 * <!-- end-UML-doc -->
 * @author Master
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface JobsWriterManager extends JobsManager, WriterManagerBase
{
	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Create and assign a job to a group based on the perscription provided by a job posting. Add job parts as perscribed by corresponding job posting parts. Further editing of the created job and job parts are typically required. If the job posting quota is met, returns null.</p>
	 * <!-- end-UML-doc -->
	 * @param jobPosting
	 * @param group
	 * @param startDate
	 * @return
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Job createJob(JobPosting jobPosting, Group group, Date startDate);

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @param job
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void persistJob(Job job);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the job specified by the given id. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param id
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteJob(int id);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Delete the specified job. An IntegrityConstrainException will be thrown if it is in use by other entities.</p>
	 * <!-- end-UML-doc -->
	 * @param job
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void deleteJob(Job job);

	/** 
	 * <!-- begin-UML-doc -->
	 * <p>Change the state of a job.</p>
	 * <!-- end-UML-doc -->
	 * @param job
	 * @param state
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void changeState(Job job, JobStateType state);
}