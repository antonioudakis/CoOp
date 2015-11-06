package softeng.coop.business.authentication.mock;

import softeng.coop.business.*;
import softeng.coop.business.authentication.*;
import softeng.coop.dataaccess.*;

import javax.persistence.*;

import java.util.*;

import org.springframework.transaction.annotation.*;

public class MockStudentEnrollmentStrategy implements IEnrollmentByAffiliationStrategy
{
	private EntityManager entityManager;
	
	@Override
	public RegistrationResult enroll(EntityManager entityManager, IUser user, Locale locale)
	{
		this.entityManager = entityManager;
		
		RegistrationResult result = new RegistrationResult();
		
		this.entityManager.getTransaction().begin();
		
		try
		{
			if (userAlreadyExists(user))
			{
				result.setStatus(RegistrationStatus.AlreadyRegistered);
			}
			else
			{
				createStudent(user);
				
				result.setStatus(RegistrationStatus.RegistrationSucceeded);
			}

			this.entityManager.getTransaction().commit();
		}
		catch (RuntimeException ex)
		{
			result.setStatus(RegistrationStatus.RegistrationFailed);
			
			InformationMessage message = new InformationMessage();
			
			message.setLevel(InformationMessageLevel.Error);
			message.setText(ex.getMessage());
			
			result.getInformationMessages().add(message);
			
			this.entityManager.getTransaction().rollback();
		}
		
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	private void throwIfWeaving()
	{
		System.out.printf("Should not be here: ensureWeaving.");
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	private Student createStudent(IUser user)
	{
		Student student = new Student();
		
		student.setAddresses(new HashSet<Address>());
		
		Address address = createStudentAddress(user);
		
		student.getAddresses().add(address);
		
		student.setName(user.getFirstName());
		student.setSurname(user.getLastName());
		student.setDateOfBirth(new Date());
		student.setFatherName("unknown");
		student.setGender(Gender.Male);
		student.setTaxId("?");
		student.setDepartment(ensureDepartment());
		
		IUserAttribute idAttribute = user.getAttribute("eduPersonPrincipalName");
		
		if (idAttribute == null)
			throw new CoOpException("user has no eduPersonPrincipalName attribute.");
		
		String principalName = idAttribute.getValue();
		
		String studentCode = principalName.substring(0, principalName.indexOf('@'));
		
		student.setSerialNumber(studentCode);
		
		student.setUserName(principalName);
		
		student.setEmail(user.getEmail());
		
		student.setRoles(new HashSet<Role>());
		
		student.getRoles().add(ensureStudentRole());
		
		student.setPreferredLanguage(ensureLanguage());
		
		student.setNationality(ensureNationality());
		
		student.setDefaultCoOp(ensureCoop());

		student.setRegistrations(new HashSet<Registration>());
		
		student.getRegistrations().add(ensureRegistration(student));
		
		entityManager.persist(student);
		
		return student;
	}

	private Registration ensureRegistration(Student student) 
	{
		TypedQuery<Registration> query =
			entityManager.createQuery("SELECT ra FROM Registration ra", Registration.class)
			.setMaxResults(1);
		
		Registration registration =getFirstOrDefault(query);
		
		if (registration == null) registration = createRegistration(student);
		
		return registration;
	}

	private Registration createRegistration(Student student) 
	{
		Registration registration = new Registration();
		registration.setCoop(student.getDefaultCoOp());
		registration.setStudent(student);
		
		entityManager.persist(registration);
		
		return registration;
	}

	private CoOp ensureCoop() 
	{
		TypedQuery<CoOp> query =
			entityManager.createQuery("SELECT co FROM CoOp co", CoOp.class)
			.setMaxResults(1);
		
		CoOp coOp =getFirstOrDefault(query);
		
		if (coOp == null) coOp = createCoop();
		
		return coOp;
	}

	private CoOp createCoop() 
	{
		CoOp coOp = new CoOp();
		coOp.setAcademicYear(2011);
		coOp.setActive(true);
		coOp.setEndDate(null);
		coOp.setGradePolicy(GradePolicyType.PassOrFail);
		coOp.setGroupCoOp(true);
		coOp.setSemester(8);
		coOp.setStartDate(new Date());
		coOp.setAcademicDirector(ensureProfessor());
		coOp.setInstitutionalDirector(ensureProfessor());
		coOp.setScientificDirector(ensureProfessor());
		
		entityManager.persist(coOp);
		
		return coOp;
	}

	private Professor ensureProfessor() 
	{
		TypedQuery<Professor> query =
			entityManager.createQuery("SELECT pro FROM Professor pro", Professor.class).setMaxResults(1);
		
		Professor professor = getFirstOrDefault(query);
		
		if (professor == null) professor = createProfessor(ensureDepartment());
		
		return professor;
	}

	private Nationality ensureNationality()
	{
		TypedQuery<Nationality> query = 
			entityManager.createQuery("SELECT nat FROM Nationality nat", Nationality.class)
			.setMaxResults(1);
		
		Nationality nationality = getFirstOrDefault(query);
		
		if (nationality == null) nationality = createNationality();
		
		return nationality;
	}

	private Nationality createNationality()
	{
		Nationality nationality = new Nationality();
		
		Multilingual nationalityName = new Multilingual();
		
		nationalityName.setLiterals(new HashSet<Literal>());
		
		Literal nationalityLiteral = new Literal();
		
		nationalityLiteral.setLanguage(ensureLanguage());
		nationalityLiteral.setDefault(true);
		nationalityLiteral.setText("greek");
		
		nationalityName.getLiterals().add(nationalityLiteral);
		
		nationality.setName(nationalityName);
		
		entityManager.persist(nationality);
		
		return nationality;
	}

	@Transactional
	private Address createStudentAddress(IUser user)
	{
		Address address = new Address();
		
		address.setCity("Athens");
		address.setCountry("Greece");
		address.setNumber("7");
		address.setStreet("Akakias");
		address.setPoBox("173 335");
		address.setType(AddressType.Home);
		address.setLocation(createStudentLocation());
		
		GeoLocation geo = new GeoLocation();
		geo.setLatitude(5);
		geo.setLongtitude(5);
		
		address.setGeoLocation(geo);
	
		entityManager.persist(address);

		return address;

	}

	@Transactional
	private Location createStudentLocation()
	{
		Location cityLocation = new Location();
		
		cityLocation.setName("Student city");
		cityLocation.setType(LocationType.Settlement);
		cityLocation.setPath("Student city#");
		
		Location boroughLocation = new Location();
		
		boroughLocation.setType(LocationType.Municipality);
		boroughLocation.setName("Κάτω μαχαλάς");
		boroughLocation.setParentLocation(cityLocation);
		boroughLocation.setPath("Student city#Κάτω μαχαλάς#");
		
		entityManager.persist(cityLocation);
		entityManager.persist(boroughLocation);

		return boroughLocation;
	}

	private boolean userAlreadyExists(IUser user)
	{
		return this.getFirstOrDefault( 
			this.entityManager.createQuery(
					"SELECT st FROM Student st WHERE st.userName = :userName", 
					Student.class)
					.setParameter("userName", user.getUserName())) != null;
	}
	
	private University ensureUniversity()
	{
		TypedQuery<University> query  = 
			entityManager.createQuery("SELECT u FROM University u", University.class);
		
		University university = getFirstOrDefault(query);
		
		if (university == null) university = createUniversity();
		
		return university;
	}
	
	private Department ensureDepartment()
	{
		Department department = getFirstOrDefault(
				this.entityManager.createQuery(
				"SELECT dep FROM Department dep", 
				Department.class));
		
		if (department == null) department = createDepartment();
		
		return department;
	}
	
	@Transactional
	private Department createDepartment()
	{
		Language language = ensureLanguage(); 
		
		Multilingual name = new Multilingual();
		name.setLiterals(new HashSet<Literal>());
		entityManager.persist(name);

		LiteralsManager.setLiteral(
				name, language, "ΣΗΜΜΥ", true, entityManager);
		
		Department department = new Department();
		department.setName(name);
		department.setUniversity(ensureUniversity());
		
		entityManager.persist(department);
		
		entityManager.persist(name);
			
		Professor professor = createProfessor(department);
		
		CoOp coop = new CoOp();
		
		coop.setName(new Multilingual());
		LiteralsManager.setLiteral(
				coop.getName(), 
				ensureLanguage(), 
				"Κάτεργα", 
				true, 
				entityManager);
		
		coop.setAcademicYear(2011);
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(2011, 8, 1);
		coop.setStartDate(startCalendar.getTime());
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(2012, 7, 30);
		coop.setEndDate(endCalendar.getTime());
		coop.setActive(true);
		coop.setSupervisingProfessors(new HashSet<Professor>());
		coop.getSupervisingProfessors().add(professor);
		coop.setJobDurationDays(30);
		coop.setInstitutionalDirector(professor);
		coop.setScientificDirector(professor);
		coop.setAcademicDirector(professor);
		coop.setMaxGroupSize(6);
		
		Lesson lesson = new Lesson();
		lesson.setDepartment(department);
		
		lesson.setName(new Multilingual());
		LiteralsManager.setLiteral(
				lesson.getName(), 
				language, 
				"Περιήγηση στα γκούλαγκ", 
				true, 
				entityManager);
		
		entityManager.persist(lesson);
		
		coop.setLesson(lesson);
		
		entityManager.persist(coop);
			
		return department;
	}

	private Professor createProfessor(Department department)
	{
		Professor professor = new Professor();
		
		professor.setDepartment(department);
		professor.setDateOfBirth(new Date());
		professor.setFatherName("vito");
		professor.setGender(Gender.Male);
		professor.setName("michael");
		professor.setSurname("corleone");
		professor.setPreferredLanguage(ensureLanguage());
		professor.setRank("don");
		professor.setUserName("michael@corleone.com");
		
		entityManager.persist(professor);
		
		return professor;
	}

	@Transactional
	private University createUniversity()
	{
		Language language = ensureLanguage(); 
		
		EmbeddableAddress address = createUniversityAddress();
			
		Multilingual name = new Multilingual();
		name.setLiterals(new HashSet<Literal>());
		entityManager.persist(name);
		
		LiteralsManager.setLiteral(
				name, language, "NTUA", true, entityManager);
		
		University university = new University();
		university.setName(name);
		university.setAddress(address);

		entityManager.persist(university);
		
		entityManager.persist(name);
		
		return university;
	}

	private Language ensureLanguage()
	{
		Language language = getFirstOrDefault(
				entityManager.createQuery(
				"SELECT lang FROM Language lang", 
				Language.class));
		
		if (language == null) language = createLanguage();
		
		return language;
	}

	@Transactional
	private Language createLanguage()
	{
		Language language = new Language();
		language.setName("greek");
		language.setLocaleCode("el-GR");
		entityManager.persist(language);
		
		return language;
	}

	private EmbeddableAddress createUniversityAddress()
	{
		EmbeddableAddress address = new EmbeddableAddress();
		
		address.setCity("Athens");
		address.setCountry("Greece");
		address.setPoBox("174 55");
		address.setStreet("Salaminos");
		address.setNumber("7");
		address.setType(AddressType.Work);
		address.setLocation(createLocation("UNIV-LOC-MOCK"));
		
		GeoLocation geo = new GeoLocation();
		geo.setLatitude(5);
		geo.setLongtitude(5);
		
		address.setGeoLocation(geo);
		
		return address;
	}

	@Transactional
	private Location createLocation(String name)
	{
		Location location = new Location();
		
		location.setName(name);
		location.setPath(name + "#");
		
		entityManager.persist(location);
		
		return location;
	}
	
	private Role ensureStudentRole()
	{
		Role role = getFirstOrDefault(
			entityManager.createQuery(
					"SELECT r from Role r WHERE r.name = 'Student role'", 
					Role.class));
		
		if (role == null) role = createStudentRole();
		
		return role;
	}

	@Transactional
	private Role createStudentRole()
	{
		Role role = new Role();
		
		role.setName("Student role");
		role.setComment("Student role comment");
		role.setPermissions(new HashSet<Permission>());
		
		addLocationsPermissions(role);
		
		addUniversitiesPermissions(role);
		
		addStudentsPermissions(role);
		
		addFacultiesPermissions(role);
		
		addCoopsPermissions(role);
		
		addReportsPermissions(role);
		
		addPaymentsPermissions(role);
		
		addJobPostingsPermissions(role);
		
		addJobPermissions(role);
		
		addCompaniesPermissions(role);
		
		entityManager.persist(role);

		return role;
	}

	private void addCompaniesPermissions(Role role) 
	{
		Permission permission = new Permission();
		
		permission.setName("Companies Writer Permission");
		permission.setComment("...");
		permission.setManagerName("CompaniesWriterManager");
		
		entityManager.persist(permission);
		
		setReadWriteAccess(permission, Company.class);
		setReadWriteAccess(permission, Branch.class);
		setReadWriteAccess(permission, CompanyPerson.class);
		setReadWriteAccess(permission, ActivitySector.class);
		
		role.getPermissions().add(permission);
	}

	private void addJobPermissions(Role role)
	{
		Permission permission = new Permission();
		
		permission.setName("Jobs permission");
		permission.setComment("...");
		permission.setName("JobsWriterManager");
		
		entityManager.persist(permission);
		
		setReadWriteAccess(permission, Job.class);
		setReadWriteAccess(permission, JobPart.class);
		
		role.getPermissions().add(permission);
	}

	private void addJobPostingsPermissions(Role role) 
	{
//		Permission permission = new Permission();
//		permission.setName("JobPosting Reader Permission");
//		permission.setComment("...");
//		permission.setManagerName("JobPostingsManager");
//		
//		entityManager.persist(permission);
//		
//		role.getPermissions().add(permission);
		
		Permission writerPermission = new Permission();
		writerPermission.setName("JobPosting Write Permission");
		writerPermission.setComment("...");
		writerPermission.setManagerName("JobPostingsWriterManager");
		
		setReadWriteAccess(writerPermission, JobPosting.class);
		setReadWriteAccess(writerPermission, JobPostingPart.class);
		
		entityManager.persist(writerPermission);
		
		role.getPermissions().add(writerPermission);
		
		
	}

	private void addPaymentsPermissions(Role role)
	{
		Permission permission = new Permission();
		permission.setName("Payments permission");
		permission.setComment("...");
		permission.setManagerName("PaymentsWriterManager");
		
		entityManager.persist(permission);
		
		setReadWriteAccess(permission, Payment.class);
		setReadWriteAccess(permission, FinancialSource.class);
		
		role.getPermissions().add(permission);
	}

	private void addLocationsPermissions(Role role)
	{
		Permission locationsPermission = new Permission();
		locationsPermission.setName("Locations management");
		locationsPermission.setComment("...");
		locationsPermission.setManagerName("LocationsManager");
		
		entityManager.persist(locationsPermission);
		
		Permission locationsWriterPermission = new Permission();
		locationsWriterPermission.setName("Locations write management");
		locationsWriterPermission.setComment("...");
		locationsWriterPermission.setManagerName("LocationsWriterManager");
		
		setReadWriteAccess(locationsWriterPermission, Location.class);
		
		entityManager.persist(locationsWriterPermission);
		
		role.getPermissions().add(locationsWriterPermission);
	}

	private void addFacultiesPermissions(Role role)
	{
		Permission facultyUsersWriterPermission = new Permission();
		facultyUsersWriterPermission.setName("Faculty members writer management");
		facultyUsersWriterPermission.setComment("...");
		facultyUsersWriterPermission.setManagerName("FacultyUsersWriterManager");
		facultyUsersWriterPermission.setEntityAccesses(new HashSet<EntityAccess>());
		
		entityManager.persist(facultyUsersWriterPermission);
		
		setReadWriteAccess(facultyUsersWriterPermission, FacultyUser.class);
		setReadWriteAccess(facultyUsersWriterPermission, Professor.class);
		
		role.getPermissions().add(facultyUsersWriterPermission);
	}

	private <T> void setReadWriteAccess(Permission permission, Class<T> type)
	{
		EntityAccess access = new EntityAccess();
		
		access.setReadable(true);
		access.setWritable(true);
		
		setAccess(permission, type, access);
	}

	private <T> void setReadAccess(Permission permission, Class<T> type)
	{
		EntityAccess access = new EntityAccess();
		
		access.setReadable(true);
		
		setAccess(permission, type, access);
	}

	@SuppressWarnings("unused")
	private <T> void setOwnReadWriteAccess(Permission permission, Class<T> type)
	{
		EntityAccess access = new EntityAccess();
		
		access.setOwnReadable(true);
		access.setOwnWritable(true);

		setAccess(permission, type, access);
	}

	private <T> void setReadOwnWriteAccess(Permission permission, Class<T> type)
	{
		EntityAccess access = new EntityAccess();
		
		access.setReadable(true);
		access.setOwnWritable(true);
		
		setAccess(permission, type, access);
	}

	@SuppressWarnings("unused")
	private <T> void setOwnReadAccess(Permission permission, Class<T> type)
	{
		EntityAccess access = new EntityAccess();
		
		access.setOwnReadable(true);
		
		setAccess(permission, type, access);
	}
	
	private <T> void setAccess(Permission permission, Class<T> type, EntityAccess access)
	{
		if (permission.getEntityAccesses() == null)
		{
			permission.setEntityAccesses(new HashSet<EntityAccess>());
		}
		
		access.setEntityName(type.getSimpleName());
		entityManager.persist(access);
		permission.getEntityAccesses().add(access);
		access.setPermission(permission);
	}
	

	private void addStudentsPermissions(Role role)
	{
		Permission studentsManagerPermission = new Permission();
		studentsManagerPermission.setName("Student read-only management");
		studentsManagerPermission.setComment("...");
		studentsManagerPermission.setManagerName("StudentsManager");
		
		entityManager.persist(studentsManagerPermission);
		
		role.getPermissions().add(studentsManagerPermission);
		
		setReadAccess(studentsManagerPermission, Location.class);
		setReadAccess(studentsManagerPermission, Company.class);
		setReadAccess(studentsManagerPermission, Branch.class);
		setReadAccess(studentsManagerPermission, JobPosting.class);
		setReadOwnWriteAccess(studentsManagerPermission, Student.class);
		setReadOwnWriteAccess(studentsManagerPermission, Registration.class);
		setReadOwnWriteAccess(studentsManagerPermission, Address.class);
		setReadOwnWriteAccess(studentsManagerPermission, Telephone.class);
		setReadAccess(studentsManagerPermission, FacultyUser.class);
		setReadAccess(studentsManagerPermission, Department.class);
		setReadAccess(studentsManagerPermission, University.class);
		setReadAccess(studentsManagerPermission, Division.class);
		setReadOwnWriteAccess(studentsManagerPermission, Invitation.class);
		setReadWriteAccess(studentsManagerPermission, Group.class);
		setReadWriteAccess(studentsManagerPermission, Multilingual.class);
		setReadWriteAccess(studentsManagerPermission, Literal.class);
		setReadOwnWriteAccess(studentsManagerPermission, Address.class);
		setReadOwnWriteAccess(studentsManagerPermission, EMail.class);
		
		Permission studentsWriterManagerPermission = new Permission();
		studentsWriterManagerPermission.setName("Students write management");
		studentsWriterManagerPermission.setComment("...");
		studentsWriterManagerPermission.setManagerName("StudentsWriterManager");
		
		entityManager.persist(studentsWriterManagerPermission);
		
		setReadWriteAccess(studentsWriterManagerPermission, Student.class);
		setReadWriteAccess(studentsWriterManagerPermission, Invitation.class);
		setReadWriteAccess(studentsWriterManagerPermission, Group.class);
		setReadWriteAccess(studentsWriterManagerPermission, Registration.class);
		setReadWriteAccess(studentsManagerPermission, Address.class);
		setReadWriteAccess(studentsManagerPermission, EMail.class);
		
		role.getPermissions().add(studentsWriterManagerPermission);
	}

	private void addUniversitiesPermissions(Role role)
	{
		Permission universitiesManagerPermission = new Permission();
		universitiesManagerPermission.setName("Universities read-only management");
		universitiesManagerPermission.setComment("...");
		universitiesManagerPermission.setManagerName("UniversitiesManager");
		
		entityManager.persist(universitiesManagerPermission);
		
		role.getPermissions().add(universitiesManagerPermission);
		
		setReadAccess(universitiesManagerPermission, University.class);
		setReadAccess(universitiesManagerPermission, Department.class);
		setReadAccess(universitiesManagerPermission, Division.class);

		Permission universitiesWriterManagerPermission = new Permission();
		universitiesWriterManagerPermission.setName("Universities write management");
		universitiesWriterManagerPermission.setComment("...");
		universitiesWriterManagerPermission.setManagerName("UniversitiesWriterManager");
		
		entityManager.persist(universitiesWriterManagerPermission);
		
		role.getPermissions().add(universitiesWriterManagerPermission);

		setReadWriteAccess(universitiesWriterManagerPermission, University.class);
		setReadWriteAccess(universitiesWriterManagerPermission, Department.class);
		setReadWriteAccess(universitiesWriterManagerPermission, Division.class);
	}

	private void addCoopsPermissions(Role role)
	{
		Permission coOpsPermission = new Permission();
		coOpsPermission.setName("Co-ops management");
		coOpsPermission.setComment("...");
		coOpsPermission.setManagerName("CoOpsWriterManager");
		coOpsPermission.setEntityAccesses(new HashSet<EntityAccess>());
		
		setReadWriteAccess(coOpsPermission, CoOp.class);

		setReadWriteAccess(coOpsPermission, Requirement.class);
		
		setReadAccess(coOpsPermission, FinancialSource.class);
		
		entityManager.persist(coOpsPermission);
		role.getPermissions().add(coOpsPermission);
	}

	private void addReportsPermissions(Role role)
	{
		Permission reportsPermission = new Permission();
		reportsPermission.setName("Reports management");
		reportsPermission.setComment("...");
		reportsPermission.setManagerName("ReportsWriterManager");
		
		entityManager.persist(reportsPermission);
		
		role.getPermissions().add(reportsPermission);
		
		// Create a mock report type associated with the permission.
		ReportType reportType = new ReportType();
		reportType.setCodeName("mock");
		reportType.setFactoryClassName("mockReportFactory");
		reportType.setRoles(new HashSet<Role>());
		reportType.getRoles().add(role);
		LiteralsManager.setLiteral(reportType.getName(), this.ensureLanguage(), "mock report", true, entityManager);
		reportType.setComments(new Multilingual());
		LiteralsManager.setLiteral(reportType.getComments(), this.ensureLanguage(), "...", true, entityManager);
		
		entityManager.persist(reportType);
		
		setReadWriteAccess(reportsPermission, ReportType.class);
		setReadWriteAccess(reportsPermission, Report.class);
		setReadWriteAccess(reportsPermission, Attachment.class);
		
		// Create a default report type.
		ReportType defaultReportType = new ReportType();
		defaultReportType.setCodeName("default");
		defaultReportType.setFactoryClassName(null);
		defaultReportType.setRoles(new HashSet<Role>());
		defaultReportType.getRoles().add(role);
		defaultReportType.setName(new Multilingual());
		LiteralsManager.setLiteral(defaultReportType.getName(), this.ensureLanguage(), "default report", true, entityManager);
		defaultReportType.setComments(new Multilingual());
		LiteralsManager.setLiteral(defaultReportType.getComments(), this.ensureLanguage(), "...", true, entityManager);
		
		entityManager.persist(defaultReportType);
	}
	
	private <T> T getFirstOrDefault(TypedQuery<T> query)
	{
		List<T> result = 
			query
				.setMaxResults(1)
				.getResultList();
		
		if (result.size() == 0)
			return null;
		else
			return result.get(0);
	}

}
