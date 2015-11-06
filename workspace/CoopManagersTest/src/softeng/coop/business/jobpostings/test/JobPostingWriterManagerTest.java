package softeng.coop.business.jobpostings.test;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.*;

import softeng.coop.business.SearchResult;
import softeng.coop.business.TransactionScope;
import softeng.coop.business.companies.CompaniesManager;
import softeng.coop.business.companies.CompaniesWriterManager;
import softeng.coop.business.companies.CompanySearchCriteria;
import softeng.coop.business.jobpostings.JobPostingsManager;
import softeng.coop.business.jobpostings.JobPostingsWriterManager;
import softeng.coop.business.test.ManagerTestBase;
import softeng.coop.dataaccess.ActivitySector;
import softeng.coop.dataaccess.Branch;
import softeng.coop.dataaccess.CoOp;
import softeng.coop.dataaccess.Company;
import softeng.coop.dataaccess.CompanyPerson;
import softeng.coop.dataaccess.EmbeddableAddress;
import softeng.coop.dataaccess.Gender;
import softeng.coop.dataaccess.GeoLocation;
import softeng.coop.dataaccess.JobPosting;
import softeng.coop.dataaccess.JobPostingPart;
import softeng.coop.dataaccess.Literal;
import softeng.coop.dataaccess.Multilingual;

public class JobPostingWriterManagerTest extends ManagerTestBase
{
	@Test
	public void createCompany()
	{
		CompaniesManager companiesManager = session.getCompaniesManager();
		CompaniesWriterManager companiesWriterManager = companiesManager.getWriterManager();
		
		TransactionScope transactionScope = companiesWriterManager.beginTransaction();
		
		try
		{
			Company company = new Company();
			
			ActivitySector activitySector = new ActivitySector();
			activitySector.setCode("1234");
			activitySector.setDescription("Βαριά Βιομηχανία");
			activitySector.setParentActivitySector(null);
			companiesWriterManager.persistActivitySector(activitySector);
			
			company.setActivitySector(activitySector);
			company.setBranches(new HashSet<Branch>());
			
			Branch branch = new Branch();
			branch.setCompany(company);
			
			Multilingual multilingualBranch = new Multilingual();
			companiesWriterManager.setDefaultLiteral(multilingualBranch, "Εργοτάξιο Εθνικής Οδού Αθηνών-Λαμίας");
			
			branch.setName(multilingualBranch);
			branch.setTelephone("2107722477");
			
			EmbeddableAddress branchAddress = new EmbeddableAddress();
			branchAddress.setCity("Athens");
			GeoLocation geoLocation = new GeoLocation();
			geoLocation.setLatitude(0);
			geoLocation.setLongtitude(0);
			
			branchAddress.setGeoLocation(geoLocation);
			
			branch.setAddress(branchAddress);
			
			companiesWriterManager.persistBranch(branch);
			
			company.getBranches().add(branch);
			
			CompanyPerson contactPerson = new CompanyPerson();
			contactPerson.setBranches(new HashSet<Branch>());
			contactPerson.getBranches().add(branch);
			contactPerson.setCompany(company);
			contactPerson.setName("Τάκης");
			contactPerson.setSurname("Βόβολας");
			contactPerson.setGender(Gender.Male);
			contactPerson.setPreferredLanguage(this.session.getAuthenticatedUser().getPreferredLanguage());

			companiesWriterManager.persistCompanyPerson(contactPerson);
			
			company.setContactPerson(contactPerson);
			
			company.setCoOps(new HashSet<CoOp>());
			company.getCoOps().add(session.getAuthenticatedUser().getDefaultCoOp());
			
			Multilingual multilingualCompanyName = new Multilingual();
			companiesWriterManager.setDefaultLiteral(multilingualCompanyName, "ΒΑΚΤΩΡ");
			
			company.setName(multilingualCompanyName);
			
			companiesWriterManager.persistCompany(company);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
		
		
		
	}
	
	@Test
	public void createAJobPosting()
	{
		JobPostingsWriterManager jobPostingsWriterManager = getJobPostingManager();
		
		//create a job posting part
		TransactionScope transactionScope = jobPostingsWriterManager.beginTransaction();
		
		try {
			CompaniesManager companiesManager = this.session.getCompaniesManager();
			
			CompanySearchCriteria criteria = new CompanySearchCriteria();
			//criteria.setCategory(category);
			
			SearchResult<Company> companies = companiesManager.searchCompanies(criteria);
			
			Company company = companies.getList().get(0);
			
			JobPosting jobPosting = new JobPosting();
			
			jobPostingsWriterManager.persistJobPosting(jobPosting);
			
			JobPostingPart jobPostingPart1 = new JobPostingPart();
			jobPostingPart1.setStartDay(1);
			jobPostingPart1.setDurationDays(20);
			jobPostingPart1.setBranch(company.getBranches().iterator().next());
			jobPostingPart1.setJobPosting(jobPosting);
			
			Multilingual multilingual = new Multilingual();
			multilingual.setLiterals(new HashSet<Literal>());	
			jobPostingsWriterManager.setDefaultLiteral(multilingual, "Εργασία στην Εταιρία ΒΑΚΤΩΡ");
			jobPostingPart1.setDescription(multilingual);
			//jobPostingPart1.setBranch(getABranch());
			jobPostingsWriterManager.persistJobPostingPart(jobPostingPart1);
			
			JobPostingPart jobPostingPart2 = new JobPostingPart();
			jobPostingPart2.setStartDay(21);
			jobPostingPart2.setDurationDays(10);
			jobPostingPart2.setJobPosting(jobPosting);
			
			Multilingual multilingual2 = new Multilingual();
			multilingual2.setLiterals(new HashSet<Literal>());	
			jobPostingsWriterManager.setDefaultLiteral(multilingual2, "Συγγραφή Αναφοράς στο ΕΜΠ");
			jobPostingPart1.setDescription(multilingual);
			//jobPostingPart1.setBranch(getABranch());
			jobPostingsWriterManager.persistJobPostingPart(jobPostingPart2);
			
			
			jobPosting.setCoOp(this.session.getAuthenticatedUser().getDefaultCoOp());
			jobPosting.setJobPostingParts(new ArrayList<JobPostingPart>());
			jobPosting.getJobPostingParts().add(jobPostingPart1);
			jobPosting.getJobPostingParts().add(jobPostingPart2);
			jobPosting.setCompany(company);
			
			Multilingual multilingual3 = new Multilingual();
			multilingual3.setLiterals(new HashSet<Literal>());
			
			jobPostingsWriterManager.setDefaultLiteral(multilingual3, "Πρακτική ’σκηση Επίβλεξης στην BAKTOR");
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
				
	}
	


	private JobPostingsWriterManager getJobPostingManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);
		
		JobPostingsManager manager = session.getJobPostingsManager();
		
		Assert.assertNotNull("aqcuisition of jobpostings manager failed", manager);
		
		JobPostingsWriterManager manager2 = manager.getWriterManager();

		return manager2;
	}
}
