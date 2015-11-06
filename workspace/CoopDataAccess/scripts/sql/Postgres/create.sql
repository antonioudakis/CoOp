
    create table "ActivitySector" (
        "id" int4 not null,
        "code" varchar(255),
        "description" varchar(1023) not null,
        parentActivitySector_id int4,
        primary key ("id")
    );

    create table "Address" (
        "id" int4 not null,
        "city" varchar(255),
        "country" varchar(255),
        "latitude" float8 not null,
        "longtitude" float8 not null,
        "number" varchar(255),
        "poBox" varchar(255),
        "street" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        "type" int4,
        location_id int4,
        person_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "Attachment" (
        "id" int4 not null,
        "content" bytea not null,
        "contentType" varchar(255),
        "name" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        insuranceContract_id int4,
        report_id int4,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "AuthenticatedUser_Role" (
        users_id int4 not null,
        roles_id int4 not null,
        primary key (users_id, roles_id)
    );

    create table "AuthenticatedUser" (
        "userName" varchar(255),
        id int4 not null,
        defaultCoOp_id int4,
        department_id int4 not null,
        primary key (id)
    );

    create table "Branch_CompanyPerson" (
        branches_id int4 not null,
        persons_id int4 not null,
        primary key (branches_id, persons_id)
    );

    create table "Branch" (
        "id" int4 not null,
        "city" varchar(255),
        "country" varchar(255),
        "latitude" float8 not null,
        "longtitude" float8 not null,
        "number" varchar(255),
        "poBox" varchar(255),
        "street" varchar(255),
        "type" int4,
        "fax" varchar(255),
        "telephone" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        location_id int4,
        company_id int4 not null,
        name_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (name_id)
    );

    create table "Category" (
        "id" int4 not null,
        "path" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        name_id int4 not null,
        parentCategory_id int4,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (name_id)
    );

    create table "CoOp_Company" (
        coOps_id int4 not null,
        companies_id int4 not null,
        primary key (coOps_id, companies_id)
    );

    create table "CoOp_FinancialSource" (
        coOps_id int4 not null,
        financialSources_id int4 not null,
        primary key (coOps_id, financialSources_id)
    );

    create table "CoOp_Professor" (
        supervisedCoOps_id int4 not null,
        supervisingProfessors_id int4 not null,
        primary key (supervisedCoOps_id, supervisingProfessors_id)
    );

    create table "CoOp" (
        "id" int4 not null,
        "academicYear" int4 not null,
        "active" bool not null,
        "allowCategoryPreferences" bool not null,
        "allowJobPostingsPreferences" bool not null,
        "allowLocationPreferences" bool not null,
        "endDate" date,
        "gradePolicy" int4,
        "hasGroupGrade" bool not null,
        "inRegistration" bool not null,
        "isGroupCoOp" bool not null,
        "isInsideUniversity" bool not null,
        "jobDurationDays" int4 not null,
        "maxGroupSize" int4 not null,
        "paymentOrderAmount" numeric(19, 2),
        "paymentOrderDate" timestamp,
        "semester" int4 not null,
        "setup" bool not null,
        "startDate" date,
        "supportingInvitations" bool not null,
        "created" timestamp,
        "modified" timestamp,
        academicDirector_id int4 not null,
        institutionalDirector_id int4 not null,
        lesson_id int4 not null,
        name_id int4 not null,
        scientificDirector_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (name_id)
    );

    create table "CompanyPerson" (
        "active" bool not null,
        "position" varchar(255),
        "salutation" varchar(255),
        id int4 not null,
        company_id int4 not null,
        primary key (id)
    );

    create table "Company" (
        "id" int4 not null,
        "email" varchar(255),
        "offersCompensation" bool,
        "taxCode" varchar(255),
        "taxDivision" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        "webSite" varchar(255),
        activitySector_id int4 not null,
        category_id int4,
        centralBranch_id int4,
        comments_id int4,
        contactPerson_id int4,
        name_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (name_id)
    );

    create table "Department" (
        "id" int4 not null,
        "created" timestamp,
        "modified" timestamp,
        name_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        university_id int4,
        primary key ("id"),
        unique (name_id)
    );

    create table "Division" (
        "id" int4 not null,
        "created" timestamp,
        "modified" timestamp,
        department_id int4 not null,
        name_id int4,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "EMail" (
        "id" int4 not null,
        "address" varchar(255),
        "comment" varchar(255),
        primary key ("id")
    );

    create table "EntityAccess" (
        "id" int4 not null,
        "accessingAllDepartments" bool not null,
        "entityName" varchar(255),
        "ownReadable" bool not null,
        "ownWritable" bool not null,
        "readable" bool not null,
        "writable" bool not null,
        permission_id int4,
        primary key ("id")
    );

    create table "FacultyUser" (
        "faxNumber" varchar(255),
        id int4 not null,
        division_id int4,
        primary key (id)
    );

    create table "FinancialSource" (
        "id" int4 not null,
        "code" varchar(255),
        "description" varchar(255),
        "name" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "Group" (
        "id" int4 not null,
        "comments" varchar(4095),
        "grade" float4,
        "gradeDate" timestamp,
        "isPendingFormation" bool,
        "passed" bool not null,
        "created" timestamp,
        "modified" timestamp,
        coOp_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "InsuranceContract" (
        "id" int4 not null,
        "name" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        coop_id int4,
        insuranceCompany_id int4,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "Invitation_Registration" (
        receivedInvitations_id int4 not null,
        recepients_id int4 not null,
        primary key (receivedInvitations_id, recepients_id)
    );

    create table "Invitation" (
        "id" int4 not null,
        "date" timestamp,
        "created" timestamp,
        "modified" timestamp,
        group_id int4,
        sender_id int4 not null,
        text_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (text_id)
    );

    create table "JobPartSpecialPayable" (
        "id" int4 not null,
        "paidDays" int4 not null,
        financialSource_id int4 not null,
        jobPart_id int4 not null,
        primary key ("id")
    );

    create table "JobPart" (
        "id" int4 not null,
        "comments" varchar(255),
        "endDate" date not null,
        "latitude" float8 not null,
        "longtitude" float8 not null,
        "paidDays" int4 not null,
        "siteType" int4,
        "startDate" date not null,
        "created" timestamp,
        "modified" timestamp,
        branch_id int4,
        description_id int4 not null,
        expeditionLocation_id int4,
        job_id int4 not null,
        managingCompanyPerson_id int4,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (description_id)
    );

    create table "JobPostingPartSpecialPayable" (
        "id" int4 not null,
        "paidDays" int4 not null,
        financialSource_id int4 not null,
        jobPostingPart_id int4 not null,
        primary key ("id")
    );

    create table "JobPostingPart" (
        "id" int4 not null,
        "durationDays" int4 not null,
        "latitude" float8 not null,
        "longtitude" float8 not null,
        "paidDays" int4 not null,
        "siteType" int4,
        "startDay" int4 not null,
        "created" timestamp,
        "modified" timestamp,
        branch_id int4,
        description_id int4 not null,
        expeditionLocation_id int4,
        jobPosting_id int4 not null,
        managingCompanyPerson_id int4,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (description_id)
    );

    create table "JobPosting" (
        "id" int4 not null,
        "accommodationOffered" bool not null,
        "insuranceOffered" bool not null,
        "salaryOffered" bool not null,
        "transportationOffered" bool not null,
        "seatsNumber" int4 not null,
        "created" timestamp,
        "modified" timestamp,
        coOp_id int4,
        company_id int4 not null,
        description_id int4 not null,
        managingCompanyPerson_id int4,
        name_id int4 not null,
        supervisingProfessor_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (description_id),
        unique (name_id)
    );

    create table "Job" (
        "id" int4 not null,
        "comments" varchar(255),
        "endDate" timestamp,
        "startDate" timestamp,
        "state" int4,
        "created" timestamp,
        "modified" timestamp,
        group_id int4,
        jobPosting_id int4 not null,
        supervisingProfessor_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "Language" (
        "id" int4 not null,
        "_default" bool not null,
        "localeCode" varchar(255),
        "name" varchar(255),
        primary key ("id")
    );

    create table "Lesson" (
        "id" int4 not null,
        "created" timestamp,
        "modified" timestamp,
        department_id int4 not null,
        name_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (name_id)
    );

    create table "Literal" (
        "id" int4 not null,
        "isDefault" bool,
        "text" varchar(511),
        language_id int4,
        multilingual_id int4,
        primary key ("id")
    );

    create table "Location" (
        "id" int4 not null,
        "name" varchar(255),
        "path" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        "type" int4,
        parentLocation_id int4,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "Multilingual" (
        "id" int4 not null,
        primary key ("id")
    );

    create table "Nationality" (
        "id" int4 not null,
        "code" varchar(4) not null,
        name_id int4 not null,
        primary key ("id"),
        unique (name_id)
    );

    create table "Payment" (
        "id" int4 not null,
        "amount" numeric(19, 2),
        "comment" varchar(255),
        "endDate" date,
        "paymentDate" timestamp,
        "startDate" date,
        "state" int4,
        "created" timestamp,
        "modified" timestamp,
        "type" int4,
        jobPart_id int4 not null,
        registration_id int4 not null,
        source_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "Permission" (
        "id" int4 not null,
        "comment" varchar(255),
        "managerName" varchar(255),
        "name" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "Person" (
        "id" int4 not null,
        "dateOfBirth" date,
        "email" varchar(255),
        "fatherName" varchar(255),
        "gender" int4,
        "motherName" varchar(255),
        "name" varchar(255),
        "notes" varchar(255),
        "surname" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        preferredLanguage_id int4,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "Professor_Group" (
        supervisingProfessors_id int4 not null,
        supervisedGroups_id int4 not null,
        primary key (supervisingProfessors_id, supervisedGroups_id)
    );

    create table "Professor" (
        "rank" varchar(255),
        id int4 not null,
        primary key (id)
    );

    create table "Registration_Category" (
        preferredByRegistrations_id int4 not null,
        preferredCategories_id int4 not null,
        "preferredCategories_ORDER" int4 not null,
        primary key (preferredByRegistrations_id, "preferredCategories_ORDER")
    );

    create table "Registration_JobPosting" (
        preferredByRegistrations_id int4 not null,
        preferredJobPostings_id int4 not null,
        "preferredJobPostings_ORDER" int4 not null,
        primary key (preferredByRegistrations_id, "preferredJobPostings_ORDER")
    );

    create table "Registration_Location" (
        preferredByRegistrations_id int4 not null,
        preferredLocations_id int4 not null,
        "preferredLocations_ORDER" int4 not null,
        primary key (preferredByRegistrations_id, "preferredLocations_ORDER")
    );

    create table "Registration_Requirement" (
        fullfillingRegistrations_id int4 not null,
        meetsRequirements_id int4 not null,
        primary key (fullfillingRegistrations_id, meetsRequirements_id)
    );

    create table "Registration" (
        "id" int4 not null,
        "grade" float4,
        "gradeDate" timestamp,
        "hostSatisfactionRate" int4,
        "passed" bool not null,
        "preferredEnd" date,
        "preferredStart" date,
        "priority" float4,
        "qualifiedForAssigmnent" bool not null,
        "qualifiedForCompletion" bool not null,
        "registrationDate" timestamp,
        "subjectSatisfactionRate" int4,
        "supervisionSatisfactionRate" int4,
        "created" timestamp,
        "modified" timestamp,
        coop_id int4 not null,
        group_id int4,
        insuranceContract_id int4,
        student_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "ReportType" (
        "id" int4 not null,
        "_final" bool,
        "codeName" varchar(255),
        "factoryClassName" varchar(255),
        "isAggregate" bool,
        "scope" int4,
        "created" timestamp,
        "modified" timestamp,
        comments_id int4 not null,
        name_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (comments_id),
        unique (name_id)
    );

    create table "Report" (
        "id" int4 not null,
        "comments" varchar(2048),
        "dateSubmitted" timestamp,
        "grade" float4,
        "title" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        coOp_id int4,
        group_id int4,
        jobPart_id int4,
        registration_id int4,
        reportType_id int4,
        reportedBy_id int4,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "Requirement" (
        "id" int4 not null,
        "created" timestamp,
        "modified" timestamp,
        "type" int4,
        coOp_id int4 not null,
        name_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (name_id)
    );

    create table "Role_Permission" (
        roles_id int4 not null,
        permissions_id int4 not null,
        primary key (roles_id, permissions_id)
    );

    create table "Role_ReportType" (
        roles_id int4 not null,
        reportType_id int4 not null,
        primary key (roles_id, reportType_id)
    );

    create table "Role" (
        "id" int4 not null,
        "comment" varchar(255),
        "name" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "Student" (
        "admissionDate" date,
        "ama" varchar(255),
        "ethnicMinority" bool not null,
        "hasOtherDegree" bool not null,
        "hasSpecialNeeds" bool not null,
        "iban" varchar(255),
        "idIssueDate" date,
        "idIssuer" varchar(255),
        "idNumber" varchar(255),
        "idType" int4,
        "immigrant" bool not null,
        "publicClerk" bool not null,
        "religiousMinority" bool not null,
        "serialNumber" varchar(255),
        "socialSecurityId" varchar(255),
        "taxDepartment" varchar(255),
        "taxDivision" varchar(255),
        "taxId" varchar(255),
        "workExperience" varchar(1023),
        id int4 not null,
        activeRegistration_id int4,
        issuerLocation_id int4,
        nationality_id int4,
        primary key (id)
    );

    create table "Telephone" (
        "id" int4 not null,
        "comment" varchar(255),
        "number" varchar(255),
        "created" timestamp,
        "modified" timestamp,
        "type" int4,
        person_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id")
    );

    create table "University" (
        "id" int4 not null,
        "city" varchar(255),
        "country" varchar(255),
        "latitude" float8 not null,
        "longtitude" float8 not null,
        "number" varchar(255),
        "poBox" varchar(255),
        "street" varchar(255),
        "type" int4,
        "created" timestamp,
        "modified" timestamp,
        location_id int4,
        name_id int4 not null,
        createdBy_id int4,
        modifiedBy_id int4,
        primary key ("id"),
        unique (name_id)
    );

    alter table "ActivitySector" 
        add constraint FK346E3B75DBE47829 
        foreign key (parentActivitySector_id) 
        references "ActivitySector";

    alter table "Address" 
        add constraint FK1ED033D44B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Address" 
        add constraint FK1ED033D4B978E073 
        foreign key (person_id) 
        references "Person";

    alter table "Address" 
        add constraint FK1ED033D4CFF6B233 
        foreign key (location_id) 
        references "Location";

    alter table "Address" 
        add constraint FK1ED033D450DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Attachment" 
        add constraint FK1C935434B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Attachment" 
        add constraint FK1C9354374F7A013 
        foreign key (report_id) 
        references "Report";

    alter table "Attachment" 
        add constraint FK1C93543B16E41E1 
        foreign key (insuranceContract_id) 
        references "InsuranceContract";

    alter table "Attachment" 
        add constraint FK1C9354350DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "AuthenticatedUser_Role" 
        add constraint FK9F7FC53B86491B6C 
        foreign key (roles_id) 
        references "Role";

    alter table "AuthenticatedUser_Role" 
        add constraint FK9F7FC53BC40B6ED3 
        foreign key (users_id) 
        references "AuthenticatedUser";

    alter table "AuthenticatedUser" 
        add constraint FK7B37A47A28236ED3 
        foreign key (department_id) 
        references "Department";

    alter table "AuthenticatedUser" 
        add constraint FK7B37A47AE3176A72 
        foreign key (defaultCoOp_id) 
        references "CoOp";

    alter table "AuthenticatedUser" 
        add constraint FK7B37A47A869E5349 
        foreign key (id) 
        references "Person";

    alter table "Branch_CompanyPerson" 
        add constraint FK67538A55AF307275 
        foreign key (persons_id) 
        references "CompanyPerson";

    alter table "Branch_CompanyPerson" 
        add constraint FK67538A55349FDE65 
        foreign key (branches_id) 
        references "Branch";

    alter table "Branch" 
        add constraint FK771411C24B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Branch" 
        add constraint FK771411C27BBB74E1 
        foreign key (company_id) 
        references "Company";

    alter table "Branch" 
        add constraint FK771411C2CFF6B233 
        foreign key (location_id) 
        references "Location";

    alter table "Branch" 
        add constraint FK771411C250DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Branch" 
        add constraint FK771411C2666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    alter table "Category" 
        add constraint FK6DD211E4B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Category" 
        add constraint FK6DD211E77FE8AA9 
        foreign key (parentCategory_id) 
        references "Category";

    alter table "Category" 
        add constraint FK6DD211E50DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Category" 
        add constraint FK6DD211E666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    alter table "CoOp_Company" 
        add constraint FKFBB1BDAB75CA31BA 
        foreign key (coOps_id) 
        references "CoOp";

    alter table "CoOp_Company" 
        add constraint FKFBB1BDABE8856AC3 
        foreign key (companies_id) 
        references "Company";

    alter table "CoOp_FinancialSource" 
        add constraint FK956B593275CA31BA 
        foreign key (coOps_id) 
        references "CoOp";

    alter table "CoOp_FinancialSource" 
        add constraint FK956B5932B76A7F96 
        foreign key (financialSources_id) 
        references "FinancialSource";

    alter table "CoOp_Professor" 
        add constraint FKEE4CC41D5931D06F 
        foreign key (supervisingProfessors_id) 
        references "Professor";

    alter table "CoOp_Professor" 
        add constraint FKEE4CC41D9FCE1FDE 
        foreign key (supervisedCoOps_id) 
        references "CoOp";

    alter table "CoOp" 
        add constraint FK201F8D4B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "CoOp" 
        add constraint FK201F8DE8611C3D 
        foreign key (academicDirector_id) 
        references "Professor";

    alter table "CoOp" 
        add constraint FK201F8D873F67DB 
        foreign key (scientificDirector_id) 
        references "Professor";

    alter table "CoOp" 
        add constraint FK201F8DFB0A13E1 
        foreign key (institutionalDirector_id) 
        references "Professor";

    alter table "CoOp" 
        add constraint FK201F8D967A7193 
        foreign key (lesson_id) 
        references "Lesson";

    alter table "CoOp" 
        add constraint FK201F8D50DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "CoOp" 
        add constraint FK201F8D666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    alter table "CompanyPerson" 
        add constraint FK679CE212869E5349 
        foreign key (id) 
        references "Person";

    alter table "CompanyPerson" 
        add constraint FK679CE2127BBB74E1 
        foreign key (company_id) 
        references "Company";

    alter table "Company" 
        add constraint FK9BDFD45D4B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Company" 
        add constraint FK9BDFD45DF6DC077E 
        foreign key (centralBranch_id) 
        references "Branch";

    alter table "Company" 
        add constraint FK9BDFD45D9C66C193 
        foreign key (category_id) 
        references "Category";

    alter table "Company" 
        add constraint FK9BDFD45D7D39D3B0 
        foreign key (comments_id) 
        references "Multilingual";

    alter table "Company" 
        add constraint FK9BDFD45DFD3E5793 
        foreign key (activitySector_id) 
        references "ActivitySector";

    alter table "Company" 
        add constraint FK9BDFD45D50DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Company" 
        add constraint FK9BDFD45D666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    alter table "Company" 
        add constraint FK9BDFD45D3BC8EB5E 
        foreign key (contactPerson_id) 
        references "CompanyPerson";

    alter table "Department" 
        add constraint FKA9601F724B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Department" 
        add constraint FKA9601F721E95553 
        foreign key (university_id) 
        references "University";

    alter table "Department" 
        add constraint FKA9601F7250DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Department" 
        add constraint FKA9601F72666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    alter table "Division" 
        add constraint FK199794CD4B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Division" 
        add constraint FK199794CD28236ED3 
        foreign key (department_id) 
        references "Department";

    alter table "Division" 
        add constraint FK199794CD50DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Division" 
        add constraint FK199794CD666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    alter table "EntityAccess" 
        add constraint FK15E62347481501B3 
        foreign key (permission_id) 
        references "Permission";

    alter table "FacultyUser" 
        add constraint FK34A1D02FD3DFB53C 
        foreign key (id) 
        references "AuthenticatedUser";

    alter table "FacultyUser" 
        add constraint FK34A1D02F1EBD6F33 
        foreign key (division_id) 
        references "Division";

    alter table "FinancialSource" 
        add constraint FK20B89DE44B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "FinancialSource" 
        add constraint FK20B89DE450DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Group" 
        add constraint FK41E065F4B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Group" 
        add constraint FK41E065FCEDB9BD3 
        foreign key (coOp_id) 
        references "CoOp";

    alter table "Group" 
        add constraint FK41E065F50DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "InsuranceContract" 
        add constraint FK796EE1EC4B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "InsuranceContract" 
        add constraint FK796EE1ECCEDB9BD3 
        foreign key (coop_id) 
        references "CoOp";

    alter table "InsuranceContract" 
        add constraint FK796EE1EC6E3ED33B 
        foreign key (insuranceCompany_id) 
        references "Company";

    alter table "InsuranceContract" 
        add constraint FK796EE1EC50DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Invitation_Registration" 
        add constraint FKC322E79FC787FE56 
        foreign key (recepients_id) 
        references "Registration";

    alter table "Invitation_Registration" 
        add constraint FKC322E79FA6ED5593 
        foreign key (receivedInvitations_id) 
        references "Invitation";

    alter table "Invitation" 
        add constraint FKBE1153B94B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Invitation" 
        add constraint FKBE1153B9AB47F5D7 
        foreign key (text_id) 
        references "Multilingual";

    alter table "Invitation" 
        add constraint FKBE1153B97A2A7C37 
        foreign key (sender_id) 
        references "Registration";

    alter table "Invitation" 
        add constraint FKBE1153B94B578361 
        foreign key (group_id) 
        references "Group";

    alter table "Invitation" 
        add constraint FKBE1153B950DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "JobPartSpecialPayable" 
        add constraint FK900F545924E8C821 
        foreign key (jobPart_id) 
        references "JobPart";

    alter table "JobPartSpecialPayable" 
        add constraint FK900F5459A9E4DCA1 
        foreign key (financialSource_id) 
        references "FinancialSource";

    alter table "JobPart" 
        add constraint FKD81E5304B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "JobPart" 
        add constraint FKD81E5304008DC83 
        foreign key (managingCompanyPerson_id) 
        references "CompanyPerson";

    alter table "JobPart" 
        add constraint FKD81E530FFFC7692 
        foreign key (expeditionLocation_id) 
        references "Location";

    alter table "JobPart" 
        add constraint FKD81E5301A7610A8 
        foreign key (description_id) 
        references "Multilingual";

    alter table "JobPart" 
        add constraint FKD81E5304077A153 
        foreign key (branch_id) 
        references "Branch";

    alter table "JobPart" 
        add constraint FKD81E530B7C16861 
        foreign key (job_id) 
        references "Job";

    alter table "JobPart" 
        add constraint FKD81E53050DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "JobPostingPartSpecialPayable" 
        add constraint FK3BB0C3A144DB1093 
        foreign key (jobPostingPart_id) 
        references "JobPostingPart";

    alter table "JobPostingPartSpecialPayable" 
        add constraint FK3BB0C3A1A9E4DCA1 
        foreign key (financialSource_id) 
        references "FinancialSource";

    alter table "JobPostingPart" 
        add constraint FK3AF832784B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "JobPostingPart" 
        add constraint FK3AF832784008DC83 
        foreign key (managingCompanyPerson_id) 
        references "CompanyPerson";

    alter table "JobPostingPart" 
        add constraint FK3AF83278FFFC7692 
        foreign key (expeditionLocation_id) 
        references "Location";

    alter table "JobPostingPart" 
        add constraint FK3AF83278D9FDB9D3 
        foreign key (jobPosting_id) 
        references "JobPosting";

    alter table "JobPostingPart" 
        add constraint FK3AF832781A7610A8 
        foreign key (description_id) 
        references "Multilingual";

    alter table "JobPostingPart" 
        add constraint FK3AF832784077A153 
        foreign key (branch_id) 
        references "Branch";

    alter table "JobPostingPart" 
        add constraint FK3AF8327850DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "JobPosting" 
        add constraint FKF70154E54B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "JobPosting" 
        add constraint FKF70154E54008DC83 
        foreign key (managingCompanyPerson_id) 
        references "CompanyPerson";

    alter table "JobPosting" 
        add constraint FKF70154E57BBB74E1 
        foreign key (company_id) 
        references "Company";

    alter table "JobPosting" 
        add constraint FKF70154E5CEDB9BD3 
        foreign key (coOp_id) 
        references "CoOp";

    alter table "JobPosting" 
        add constraint FKF70154E51A7610A8 
        foreign key (description_id) 
        references "Multilingual";

    alter table "JobPosting" 
        add constraint FKF70154E5A18A515E 
        foreign key (supervisingProfessor_id) 
        references "Professor";

    alter table "JobPosting" 
        add constraint FKF70154E550DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "JobPosting" 
        add constraint FKF70154E5666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    alter table "Job" 
        add constraint FK1239D4B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Job" 
        add constraint FK1239DD9FDB9D3 
        foreign key (jobPosting_id) 
        references "JobPosting";

    alter table "Job" 
        add constraint FK1239D4B578361 
        foreign key (group_id) 
        references "Group";

    alter table "Job" 
        add constraint FK1239DA18A515E 
        foreign key (supervisingProfessor_id) 
        references "Professor";

    alter table "Job" 
        add constraint FK1239D50DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Lesson" 
        add constraint FK877599584B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Lesson" 
        add constraint FK8775995828236ED3 
        foreign key (department_id) 
        references "Department";

    alter table "Lesson" 
        add constraint FK8775995850DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Lesson" 
        add constraint FK87759958666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    alter table "Literal" 
        add constraint FK6E18B88F41C7A833 
        foreign key (multilingual_id) 
        references "Multilingual";

    alter table "Literal" 
        add constraint FK6E18B88F9777EB53 
        foreign key (language_id) 
        references "Language";

    alter table "Location" 
        add constraint FK752A03D54B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Location" 
        add constraint FK752A03D5AB8E7B49 
        foreign key (parentLocation_id) 
        references "Location";

    alter table "Location" 
        add constraint FK752A03D550DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Nationality" 
        add constraint FK68F2659C666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    alter table "Payment" 
        add constraint FK3454C9E64B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Payment" 
        add constraint FK3454C9E65DCFAF0A 
        foreign key (source_id) 
        references "FinancialSource";

    alter table "Payment" 
        add constraint FK3454C9E624E8C821 
        foreign key (jobPart_id) 
        references "JobPart";

    alter table "Payment" 
        add constraint FK3454C9E65FA7EF33 
        foreign key (registration_id) 
        references "Registration";

    alter table "Payment" 
        add constraint FK3454C9E650DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Permission" 
        add constraint FK57F7A1EF4B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Permission" 
        add constraint FK57F7A1EF50DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Person" 
        add constraint FK8E4887754B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Person" 
        add constraint FK8E48877550DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Person" 
        add constraint FK8E488775E77DB432 
        foreign key (preferredLanguage_id) 
        references "Language";

    alter table "Professor_Group" 
        add constraint FK7A46286FEB4C3A48 
        foreign key (supervisedGroups_id) 
        references "Group";

    alter table "Professor_Group" 
        add constraint FK7A46286F5931D06F 
        foreign key (supervisingProfessors_id) 
        references "Professor";

    alter table "Professor" 
        add constraint FK3B4FF64F9DE36A31 
        foreign key (id) 
        references "FacultyUser";

    alter table "Registration_Category" 
        add constraint FKA49779C4D6A5B60A 
        foreign key (preferredByRegistrations_id) 
        references "Registration";

    alter table "Registration_Category" 
        add constraint FKA49779C49FAE48D4 
        foreign key (preferredCategories_id) 
        references "Category";

    alter table "Registration_JobPosting" 
        add constraint FKF881C0BD6A5B60A 
        foreign key (preferredByRegistrations_id) 
        references "Registration";

    alter table "Registration_JobPosting" 
        add constraint FKF881C0BA0F944AB 
        foreign key (preferredJobPostings_id) 
        references "JobPosting";

    alter table "Registration_Location" 
        add constraint FK12E45C7BD6A5B60A 
        foreign key (preferredByRegistrations_id) 
        references "Registration";

    alter table "Registration_Location" 
        add constraint FK12E45C7B5C52C0EB 
        foreign key (preferredLocations_id) 
        references "Location";

    alter table "Registration_Requirement" 
        add constraint FK7164A1BD4CF08782 
        foreign key (fullfillingRegistrations_id) 
        references "Registration";

    alter table "Registration_Requirement" 
        add constraint FK7164A1BD37431DA8 
        foreign key (meetsRequirements_id) 
        references "Requirement";

    alter table "Registration" 
        add constraint FKB94F3CD94B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Registration" 
        add constraint FKB94F3CD9CEDB9BD3 
        foreign key (coop_id) 
        references "CoOp";

    alter table "Registration" 
        add constraint FKB94F3CD9B16E41E1 
        foreign key (insuranceContract_id) 
        references "InsuranceContract";

    alter table "Registration" 
        add constraint FKB94F3CD9C80FB421 
        foreign key (student_id) 
        references "Student";

    alter table "Registration" 
        add constraint FKB94F3CD94B578361 
        foreign key (group_id) 
        references "Group";

    alter table "Registration" 
        add constraint FKB94F3CD950DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "ReportType" 
        add constraint FKE9F07AAE4B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "ReportType" 
        add constraint FKE9F07AAE7D39D3B0 
        foreign key (comments_id) 
        references "Multilingual";

    alter table "ReportType" 
        add constraint FKE9F07AAE50DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "ReportType" 
        add constraint FKE9F07AAE666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    alter table "Report" 
        add constraint FK91B141544B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Report" 
        add constraint FK91B1415458A760E4 
        foreign key (coOp_id) 
        references "JobPart";

    alter table "Report" 
        add constraint FK91B14154CEDB9BD3 
        foreign key (coOp_id) 
        references "CoOp";

    alter table "Report" 
        add constraint FK91B1415424E8C821 
        foreign key (jobPart_id) 
        references "JobPart";

    alter table "Report" 
        add constraint FK91B141544B578361 
        foreign key (group_id) 
        references "Group";

    alter table "Report" 
        add constraint FK91B1415464643D73 
        foreign key (reportType_id) 
        references "ReportType";

    alter table "Report" 
        add constraint FK91B14154CACE4131 
        foreign key (reportedBy_id) 
        references "AuthenticatedUser";

    alter table "Report" 
        add constraint FK91B141545FA7EF33 
        foreign key (registration_id) 
        references "Registration";

    alter table "Report" 
        add constraint FK91B1415450DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Requirement" 
        add constraint FK791284234B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Requirement" 
        add constraint FK79128423CEDB9BD3 
        foreign key (coOp_id) 
        references "CoOp";

    alter table "Requirement" 
        add constraint FK7912842350DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Requirement" 
        add constraint FK79128423666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    alter table "Role_Permission" 
        add constraint FKF8A5693886491B6C 
        foreign key (roles_id) 
        references "Role";

    alter table "Role_Permission" 
        add constraint FKF8A5693847C35ADE 
        foreign key (permissions_id) 
        references "Permission";

    alter table "Role_ReportType" 
        add constraint FK8A9E41F786491B6C 
        foreign key (roles_id) 
        references "Role";

    alter table "Role_ReportType" 
        add constraint FK8A9E41F764643D73 
        foreign key (reportType_id) 
        references "ReportType";

    alter table "Role" 
        add constraint FK26F4964B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Role" 
        add constraint FK26F49650DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "Student" 
        add constraint FKF3371A1BD3DFB53C 
        foreign key (id) 
        references "AuthenticatedUser";

    alter table "Student" 
        add constraint FKF3371A1BB715C36D 
        foreign key (activeRegistration_id) 
        references "Registration";

    alter table "Student" 
        add constraint FKF3371A1BD5ECFD01 
        foreign key (nationality_id) 
        references "Nationality";

    alter table "Student" 
        add constraint FKF3371A1B58E481DA 
        foreign key (issuerLocation_id) 
        references "Location";

    alter table "Telephone" 
        add constraint FKA620D3E44B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "Telephone" 
        add constraint FKA620D3E4B978E073 
        foreign key (person_id) 
        references "Person";

    alter table "Telephone" 
        add constraint FKA620D3E450DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "University" 
        add constraint FK821AC6AE4B71AC3C 
        foreign key (createdBy_id) 
        references "AuthenticatedUser";

    alter table "University" 
        add constraint FK821AC6AECFF6B233 
        foreign key (location_id) 
        references "Location";

    alter table "University" 
        add constraint FK821AC6AE50DC6CFB 
        foreign key (modifiedBy_id) 
        references "AuthenticatedUser";

    alter table "University" 
        add constraint FK821AC6AE666D5BB9 
        foreign key (name_id) 
        references "Multilingual";

    create sequence "hibernate_sequence";
