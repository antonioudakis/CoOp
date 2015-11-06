package softeng.coop.dataaccess;

import java.util.*;

/**
 * Constructor methods to create the objects in the domain model package.
 * This is necessary because the IBM RSA code generation from UML of the 
 * domain classes produces empty constructors, ignores UML information,
 * leaving everything uninitialized...
 * This class itself cannot be created.
 * It only offers static methods.
 */
public class Constructor
{
	/**
	 * This class itself cannot be created.
	 * It only offers static methods.
	 */
	private Constructor()
	{
	}
	
	public static Company createCompany()
	{
		Company company = new Company();
		
		initializeCompany(company);
		
		return company;
	}
	
	public static Invitation createInvitation()
	{
		Invitation invitation = new Invitation();
		
		initializeInvitation(invitation);
		
		return invitation;
	}
	
	public static JobPartSpecialPayable createJobPartSpecialPayable()
	{
		JobPartSpecialPayable jobPartSpecialPayable =
			new JobPartSpecialPayable();
		
		initializeJobPartSpecialPayable(jobPartSpecialPayable);
		
		return jobPartSpecialPayable;
	}
	
	public static JobPostingPartSpecialPayable createJobPostingPartSpecialPayable()
	{
		JobPostingPartSpecialPayable jobPostingPartSpecialPayable =
			new JobPostingPartSpecialPayable();
		
		initializeJobPostingPartSpecialPayable(jobPostingPartSpecialPayable);
		
		return jobPostingPartSpecialPayable;
	}
	
	public static JobPostingPart createJobPostingPart()
	{
		JobPostingPart jobPostingPart = new JobPostingPart();
		
		initializeJobPostingPart(jobPostingPart);
		
		return jobPostingPart;
	}
	
	public static Group createGroup()
	{
		Group group = new Group();
		
		initializeGroup(group);
		
		return group;
	}
	
	public static Telephone createTelephone()
	{
		Telephone telephone = new Telephone();
		
		initializeTelephone(telephone);
		
		return telephone;
	}
	
	public static Payment createPayment()
	{
		Payment payment = new Payment();
		
		initializePayment(payment);
		
		return payment;
	}
	
	public static Registration createRegistration()
	{
		Registration registration = new Registration();
		
		initializeRegistration(registration);
		
		return registration;
	}
	
	public static CompanyPerson createCompanyPerson()
	{
		CompanyPerson companyPerson = new CompanyPerson();
		
		initializeCompanyPerson(companyPerson);
		
		return companyPerson;
	}
	
	public static Job createJob()
	{
		Job job = new Job();
		
		initializeJob(job);
		
		return job;
	}
	
	public static JobPart createJobPart()
	{
		JobPart jobPart = new JobPart();
		
		initializeJobPart(jobPart);
		
		return jobPart;
	}
	
	public static JobPosting createJobPosting()
	{
		JobPosting jobPosting = new JobPosting();
		
		initializeJobPosting(jobPosting);
		
		return jobPosting;
	}
	
	public static Professor createProfessor()
	{
		Professor professor = new Professor();
		
		initializeProfessor(professor);
		
		return professor;
	}
	
	public static CoOp createCoop()
	{
		CoOp coop = new CoOp();
		
		initializeCoop(coop);
		
		return coop;
	}
	
	public static Student createStudent()
	{
		Student student = new Student();
		
		initializeStudent(student);
		
		return student;
	}
	
	public static FacultyUser createFacultyUser()
	{
		FacultyUser facultyUser = new FacultyUser();
		
		initializeFacultyUser(facultyUser);
		
		return facultyUser;
	}
	
	private static void initializeFacultyUser(FacultyUser facultyUser) 
	{
		initializeAuthenticatedUser(facultyUser);
		
	}

	private static void initializeStudent(Student student) 
	{
		initializeAuthenticatedUser(student);
		
		student.setRegistrations(new HashSet<Registration>());
		
	}

	private static void initializeCoop(CoOp coop)
	{
		coop.setAllowCategoryPreferences(true);
		coop.setAllowJobPostingsPreferences(true);
		coop.setAllowLocationPreferences(true);
		
		coop.setCompanies(new HashSet<Company>());
		coop.setFinancialSources(new HashSet<FinancialSource>());
		coop.setGroups(new HashSet<Group>());
		coop.setInsuranceContracts(new HashSet<InsuranceContract>());
		coop.setJobPostings(new LinkedHashSet<JobPosting>());
		coop.setName(new Multilingual());
		coop.getName().setLiterals(new HashSet<Literal>());
		coop.setRegistrations(new HashSet<Registration>());
		coop.setReports(new LinkedHashSet<Report>());
		coop.setRequirements(new LinkedHashSet<Requirement>());
		coop.setSupervisingProfessors(new LinkedHashSet<Professor>());
	}

	private static void initializeCompanyPerson(CompanyPerson companyPerson)
	{
		initializePerson(companyPerson);
		
		companyPerson.setActive(true);
		companyPerson.setBranches(new LinkedHashSet<Branch>());
		companyPerson.setManagedJobPostingParts(new LinkedHashSet<JobPostingPart>());
		companyPerson.setManagedJobPostings(new LinkedHashSet<JobPosting>());
	}
	
	private static void initializePerson(Person person)
	{
		person.setAddresses(new LinkedHashSet<Address>());
		person.setTelephones(new LinkedHashSet<Telephone>());
	}
	
	private static void initializeAuthenticatedUser(AuthenticatedUser authenticatedUser)
	{
		initializePerson(authenticatedUser);
		
		authenticatedUser.setReports(new HashSet<Report>());
		authenticatedUser.setRoles(new HashSet<Role>());
	}
	
	private static void initializeJob(Job job)
	{
		job.setJobParts(new LinkedHashSet<JobPart>());
	}
	
	private static void initializeJobPart(JobPart jobPart)
	{
		jobPart.setReports(new LinkedHashSet<Report>());
		jobPart.setPayments(new LinkedHashSet<Payment>());
		jobPart.setDescription(new Multilingual());
		jobPart.getDescription().setLiterals(new HashSet<Literal>());
		jobPart.setSpecialPayables(new HashSet<JobPartSpecialPayable>());
	}
	
	private static void initializeJobPosting(JobPosting jobPosting)
	{
		jobPosting.setBenefits(new Benefits());
		jobPosting.setDescription(new Multilingual());
		jobPosting.getDescription().setLiterals(new HashSet<Literal>());
		jobPosting.setJobPostingParts(new ArrayList<JobPostingPart>());
		jobPosting.setJobs(new LinkedHashSet<Job>());
		jobPosting.setName(new Multilingual());
		jobPosting.getName().setLiterals(new HashSet<Literal>());
		jobPosting.setPreferredByRegistrations(new HashSet<Registration>());
	}
	
	private static void initializeProfessor(Professor professor)
	{
		initializeAuthenticatedUser(professor);
		
		professor.setAcademicallyDirectedCoOps(new HashSet<CoOp>());
		professor.setInstitutionallyDirectedCoOps(new HashSet<CoOp>());
		professor.setScientificallyDirectedCoOps(new HashSet<CoOp>());
		professor.setSupervisedCoOps(new HashSet<CoOp>());
		professor.setSupervisedGroups(new HashSet<Group>());
		professor.setSupervisedJobPostings(new HashSet<JobPosting>());
		professor.setSupervisedJobs(new HashSet<Job>());
	}

	private static void initializeRegistration(Registration registration)
	{
		registration.setMeetsRequirements(new HashSet<Requirement>());
		registration.setPayments(new LinkedHashSet<Payment>());
		registration.setPreferredCategories(new ArrayList<Category>());
		registration.setPreferredLocations(new ArrayList<Location>());
		registration.setPreferredJobPostings(new ArrayList<JobPosting>());
		registration.setReceivedInvitations(new LinkedHashSet<Invitation>());
		registration.setSentInvitations(new LinkedHashSet<Invitation>());
	}

	private static void initializePayment(Payment payment)
	{
		
	}

	private static void initializeTelephone(Telephone telephone)
	{

	}

	private static void initializeGroup(Group group)
	{
		group.setInvitations(new ArrayList<Invitation>());
		group.setRegistrations(new HashSet<Registration>());
		group.setReports(new LinkedHashSet<Report>());
		group.setSupervisingProfessors(new HashSet<Professor>());
	}

	private static void initializeJobPostingPart(JobPostingPart jobPostingPart)
	{
		jobPostingPart.setSpecialPayables(new HashSet<JobPostingPartSpecialPayable>());
		
		jobPostingPart.setDescription(new Multilingual());
		jobPostingPart.getDescription().setLiterals(new HashSet<Literal>());
	}

	private static void initializeJobPostingPartSpecialPayable(JobPostingPartSpecialPayable jobPostingPartSpecialPayable)
	{
	}

	private static void initializeJobPartSpecialPayable(JobPartSpecialPayable jobPartSpecialPayable)
	{
	}

	private static void initializeInvitation(Invitation invitation)
	{
		invitation.setDate(new Date());
		invitation.setRecepients(new HashSet<Registration>());
		invitation.setText(new Multilingual());
		invitation.getText().setLiterals(new HashSet<Literal>());
	}

	private static void initializeCompany(Company company)
	{
		company.setName(new Multilingual());
		company.getName().setLiterals(new HashSet<Literal>());
		
		company.setBranches(new HashSet<Branch>());
		
		company.setPersons(new HashSet<CompanyPerson>());
		
		company.setComments(new Multilingual());
		company.getComments().setLiterals(new HashSet<Literal>());
		
		company.setCoOps(new HashSet<CoOp>());
		
		company.setInsuranceContracts(new HashSet<InsuranceContract>());
		
		company.setJobPostings(new HashSet<JobPosting>());
	}

}
