package softeng.coop.business.jobpostings.test;

import softeng.coop.business.jobpostings.JobPostingsManager;
import softeng.coop.business.jobpostings.JobPostingsSearchCriteria;
import softeng.coop.business.test.ManagerTestBase;
import org.junit.*;

public class JobPostingsManagerTest extends ManagerTestBase
{
	@Test
	public void searchJobPostings()
	{
		JobPostingsManager jobPostingsManager = getJobPostingManager();
		
		JobPostingsSearchCriteria criteria = new JobPostingsSearchCriteria();
		
		criteria.setCoop(this.session.getAuthenticatedUser().getDefaultCoOp());
		
		jobPostingsManager.searchJobPostings(criteria);
	}
	
	
	private JobPostingsManager getJobPostingManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);
		
		JobPostingsManager manager = session.getJobPostingsManager();
		
		Assert.assertNotNull("aqcuisition of jobpostings manager failed", manager);

		return manager;
	}
	
	
}
