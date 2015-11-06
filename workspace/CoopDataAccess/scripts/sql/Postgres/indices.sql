ALTER SEQUENCE hibernate_sequence RESTART 65536;

-- This constraint should be dropped because it is introduced by Hibernate schema 
-- generator bug.
ALTER TABLE "Report" DROP CONSTRAINT FK91B1415458A760E4;

ALTER TABLE "ActivitySector"
  ADD CONSTRAINT "CS_ActivitySector_code" UNIQUE(code) DEFERRABLE INITIALLY DEFERRED;
  
CREATE INDEX "IX_ActivitySector_parentActivitySector_id"
  ON "ActivitySector"
  USING hash
  (parentactivitysector_id);
  
CREATE INDEX "IX_Address_location_id"
  ON "Address"
  USING hash
  (location_id);

CREATE INDEX "IX_Address_person_id"
  ON "Address"
  USING hash
  (person_id);
  
CREATE INDEX "IX_Attachment_report_id"
  ON "Attachment"
  USING hash
  (report_id);

CREATE INDEX "IX_Attachment_insurancecontract_id"
  ON "Attachment"
  USING hash
  (insurancecontract_id);

CREATE INDEX "IX_AuthenticatedUser_department_coop"
  ON "AuthenticatedUser"
  USING btree
  (department_id, defaultcoop_id);


CREATE INDEX "AuthenticatedUser_Role_users_id"
  ON "AuthenticatedUser_Role"
  USING hash
  (users_id);

CREATE INDEX "IX_AuthenticatedUser_defaultCoop_id"
  ON "AuthenticatedUser"
  USING hash
  (defaultcoop_id);

ALTER TABLE "AuthenticatedUser"
  ADD CONSTRAINT "CS_AuthenticatedUser_userName" UNIQUE("userName");

CREATE INDEX "IX_Branch_location_id"
  ON "Branch"
  USING btree
  (location_id);

CREATE INDEX "IX_Branch_company_id"
  ON "Branch"
  USING hash
  (company_id);

CREATE INDEX "IX_Branch_CompanyPerson_branches_id"
  ON "Branch_CompanyPerson"
  USING hash
  (branches_id);
  
CREATE INDEX "IX_Category_parentCategory_id"
  ON "Category"
  USING hash
  (parentcategory_id);

CREATE INDEX "IX_Category_path"
  ON "Category"
  USING btree
  (path);

CREATE INDEX "IX_CompanyPerson_company_id"
  ON "CompanyPerson"
  USING hash
  (company_id);

CREATE INDEX "IX_Coop_academicYear"
  ON "CoOp"
  USING btree
  ("academicYear");
ALTER TABLE "CoOp" CLUSTER ON "IX_Coop_academicYear";

CREATE INDEX "IX_Coop_lesson_id"
  ON "CoOp"
  USING hash
  (lesson_id);

CREATE INDEX "IX_Coop_endDate"
  ON "CoOp"
  USING btree
  ("endDate");

CREATE INDEX "IX_Coop_setup_active"
  ON "CoOp"
  ("setup", "active");

CREATE INDEX "IX_Coop_setup_inRegistration"
  ON "CoOp"
  ("setup", "inRegistration");

CREATE INDEX "IX_Coop_active"
  ON "CoOp"
  ("active");

CREATE INDEX "IX_Coop_setup_active_inRegistration"
  ON "CoOp"
  ("setup", "active", "inRegistration");

CREATE INDEX "IX_Coop_scientificDirector_id"
  ON "CoOp"
  USING btree
  (scientificDirector_id);

CREATE INDEX "IX_Coop_academicDirector_id"
  ON "CoOp"
  USING btree
  (academicDirector_id);

CREATE INDEX "IX_Coop_institutionalDirector_id"
  ON "CoOp"
  USING btree
  (institutionalDirector_id);

CREATE INDEX "IX_Coop_startDate"
  ON "CoOp"
  USING btree
  ("startDate");

CREATE INDEX "IX_Coop_Company_coops_id"
  ON "CoOp_Company"
  USING hash
  (coops_id);

CREATE INDEX "IX_Coop_FinancialSource_coops_id"
  ON "CoOp_FinancialSource"
  USING hash
  (coops_id);

CREATE INDEX "IX_Coop_FinancialSource_financialsources_id"
  ON "CoOp_FinancialSource"
  USING hash
  (financialsources_id);

CREATE INDEX "IX_Coop_Professor_supervisedcoops_id"
  ON "CoOp_Professor"
  USING hash
  (supervisedcoops_id);

ALTER TABLE "Registration"
  ADD CONSTRAINT "CS_Registration_coop_student" UNIQUE(coop_id, student_id) DEFERRABLE INITIALLY DEFERRED;
  
ALTER TABLE "Company"
  ADD CONSTRAINT "CS_Company_taxCode" UNIQUE("taxCode");

CREATE INDEX "IX_Department_university_id"
  ON "Department"
  USING btree
  (university_id);
ALTER TABLE "Department" CLUSTER ON "IX_Department_university_id";

CREATE INDEX "IX_Division_department_id"
  ON "Division"
  USING btree
  (department_id);
ALTER TABLE "Division" CLUSTER ON "IX_Division_department_id";

CREATE INDEX "IX_EntityAccess_entityName"
  ON "EntityAccess"
  USING hash
  ("entityName");

CREATE INDEX "IX_EntityAccess_permission_id"
  ON "EntityAccess"
  USING btree
  (permission_id);
ALTER TABLE "EntityAccess" CLUSTER ON "IX_EntityAccess_permission_id";

CREATE INDEX "IX_FacultyUser_division_id"
  ON "FacultyUser"
  USING btree
  (division_id);
ALTER TABLE "FacultyUser" CLUSTER ON "IX_FacultyUser_division_id";

CREATE INDEX "IX_FinancialSource_name"
  ON "FinancialSource"
  USING btree
  (name);

CREATE INDEX "IX_Group_coop_id"
  ON "Group"
  USING btree
  (coop_id);
ALTER TABLE "Group" CLUSTER ON "IX_Group_coop_id";

CREATE INDEX "IX_Group_coop_id_grade"
  ON "Group"
  USING btree
  (coop_id, "grade");

CREATE INDEX "IX_Group_coop_id_gradeDate"
  ON "Group"
  USING btree
  (coop_id, "gradeDate");

CREATE INDEX "IX_Group_coop_id_passed"
  ON "Group"
  USING btree
  (coop_id, passed);

CREATE INDEX "IX_Group_isPendingFormation"
  ON "Group"
  USING btree
  ("isPendingFormation");

CREATE INDEX "IX_InsuranceContract_coop_id"
  ON "InsuranceContract"
  USING btree
  (coop_id);
ALTER TABLE "InsuranceContract" CLUSTER ON "IX_InsuranceContract_coop_id";

CREATE INDEX "IX_InsuranceContract_insuranceCompany_id"
  ON "InsuranceContract"
  USING hash
  (insurancecompany_id);

CREATE INDEX "IX_Invitation_group_id"
  ON "Invitation"
  USING hash
  (group_id);

CREATE INDEX "IX_Invitation_sender_id"
  ON "Invitation"
  USING hash
  (sender_id);

CREATE INDEX "IX_Invitation_Registration_receivedInvitations_id"
  ON "Invitation_Registration"
  USING hash
  (receivedinvitations_id);

CREATE INDEX "IX_Invitation_Registration_recepients_id"
  ON "Invitation_Registration"
  USING hash
  (recepients_id);

CREATE INDEX "IX_Job_endDate"
  ON "Job"
  USING btree
  ("endDate");

CREATE INDEX "IX_Job_group_id"
  ON "Job"
  USING hash
  (group_id);

CREATE INDEX "IX_Job_jobPosting_id"
  ON "Job"
  USING btree
  (jobposting_id);
ALTER TABLE "Job" CLUSTER ON "IX_Job_jobPosting_id";

CREATE INDEX "IX_Job_startDate"
  ON "Job"
  USING btree
  ("startDate");

CREATE INDEX "IX_Job_state"
  ON "Job"
  USING hash
  (state);

CREATE INDEX "IX_Job_supervisingprofessor_id"
  ON "Job"
  USING hash
  (supervisingprofessor_id);

CREATE INDEX "IX_JobPart_branch_id"
  ON "JobPart"
  USING btree
  (branch_id);

CREATE INDEX "IX_JobPart_endDate"
  ON "JobPart"
  USING btree
  ("endDate");

CREATE INDEX "IX_JobPart_expeditionLocation_id"
  ON "JobPart"
  USING hash
  (expeditionlocation_id);

CREATE INDEX "IX_JobPart_job_id"
  ON "JobPart"
  USING btree
  (job_id);
ALTER TABLE "JobPart" CLUSTER ON "IX_JobPart_job_id";

CREATE INDEX "IX_JobPart_managingcompanyperson_id"
  ON "JobPart"
  USING hash
  (managingcompanyperson_id);

CREATE INDEX "IX_JobPart_startDate"
  ON "JobPart"
  USING btree
  ("startDate");

CREATE INDEX "IX_JobPartSpecialPayable_jobPart_id"
  ON "JobPartSpecialPayable"
  USING btree
  (jobpart_id);
ALTER TABLE "JobPartSpecialPayable" CLUSTER ON "IX_JobPartSpecialPayable_jobPart_id";

CREATE INDEX "IX_JobPartSpecialPayable_financialSource_id"
  ON "JobPartSpecialPayable"
  USING hash
  (financialsource_id);

CREATE INDEX "IX_JobPosting_company_id"
  ON "JobPosting"
  USING hash
  (company_id);

CREATE INDEX "IX_JobPosting_coop_id"
  ON "JobPosting"
  USING btree
  (coop_id);
ALTER TABLE "JobPosting" CLUSTER ON "IX_JobPosting_coop_id";

CREATE INDEX "IX_JobPosting_coop_id_seatsNumber"
  ON "JobPosting"
  USING btree
  (coop_id, "seatsNumber");

CREATE INDEX "IX_JobPosting_coop_id_supervisingProfessor_id"
  ON "JobPosting"
  USING btree
  (coop_id, supervisingprofessor_id);

CREATE INDEX "IX_JobPosting_managingCompanyPerson_id"
  ON "JobPosting"
  USING hash
  (managingcompanyperson_id);

CREATE INDEX "IX_JobPosting_supervisingProfessor"
  ON "JobPosting"
  USING hash
  (supervisingprofessor_id);

CREATE INDEX "IX_JobPostingPart_branch_id"
  ON "JobPostingPart"
  USING hash
  (branch_id);

CREATE INDEX "IX_JobPostingPart_expeditionLocation_id"
  ON "JobPostingPart"
  USING hash
  (expeditionlocation_id);

CREATE INDEX "IX_JobPostingPart_jobPosting_d"
  ON "JobPostingPart"
  USING btree
  (jobposting_id);
ALTER TABLE "JobPostingPart" CLUSTER ON "IX_JobPostingPart_jobPosting_d";

CREATE INDEX "IX_JobPostingPart_managingCompanyPerson_id"
  ON "JobPostingPart"
  USING hash
  (managingcompanyperson_id);
  
CREATE INDEX "IX_JobPostingPartSpecialPayable_jobPostingPart_id"
  ON "JobPostingPartSpecialPayable"
  USING btree
  (jobpostingpart_id);
ALTER TABLE "JobPostingPartSpecialPayable" CLUSTER ON "IX_JobPostingPartSpecialPayable_jobPostingPart_id";

CREATE INDEX "IX_JobPostingPartSpecialPayable_financialSource_id"
  ON "JobPostingPartSpecialPayable"
  USING hash
  (financialsource_id);

ALTER TABLE "Language"
  ADD CONSTRAINT "CS_Language_localeCode" UNIQUE("localeCode");

CREATE INDEX "IX_Location_parentLocation_id"
  ON "Location"
  USING btree
  (parentlocation_id);
ALTER TABLE "Location" CLUSTER ON "IX_Location_parentLocation_id";

CREATE INDEX "IX_Location_path"
  ON "Location"
  USING btree
  (path);

CREATE INDEX "IX_Location_type"
  ON "Location"
  USING btree
  (type);
  
CREATE INDEX "IX_Payment_jobpart_id"
	ON "Payment"
	USING hash
	(jobpart_id);

CREATE INDEX "IX_Payment_paymentDate_state"
  ON "Payment"
  USING btree
  ("paymentDate", state);

CREATE INDEX "IX_Payment_source_id"
  ON "Payment"
  USING btree
  (source_id);

CREATE INDEX "IX_Payment_source_id_paymentDate"
  ON "Payment"
  USING btree
  (source_id, "paymentDate");
ALTER TABLE "Payment" CLUSTER ON "IX_Payment_source_id_paymentDate";

CREATE INDEX "IX_Payment_source_id_paymentDate_registration_id"
  ON "Payment"
  USING btree
  (source_id, "paymentDate", "registration_id");
  
CREATE INDEX "IX_Payment_source_id_startDate"
  ON "Payment"
  USING btree
  (source_id, "startDate");

CREATE INDEX "IX_Payment_source_id_endDate"
  ON "Payment"
  USING btree
  (source_id, "endDate");

CREATE INDEX "IX_Payment_startDate"
  ON "Payment"
  USING btree
  ("startDate");

CREATE INDEX "IX_Payment_endDate"
  ON "Payment"
  USING btree
  ("endDate");

CREATE INDEX "IX_Payment_registration_id"
  ON "Payment"
  USING hash
  (registration_id);

CREATE INDEX "IX_Payment_type_paymentDate"
  ON "Payment"
  USING btree
  (type, "paymentDate");

CREATE INDEX "IX_Payment_type_source_id_paymentDate"
  ON "Payment"
  USING btree
  (type, source_id, "paymentDate");

CREATE INDEX "IX_Person_surname"
  ON "Person"
  USING btree
  (surname);

CREATE INDEX "IX_Professor_Group_supervisedGroups_id"
  ON "Professor_Group"
  USING hash
  (supervisedgroups_id);

CREATE INDEX "IX_Professor_Group_supervisingProfessors_id"
  ON "Professor_Group"
  USING hash
  (supervisingprofessors_id);

CREATE INDEX "IX_Registration_coop_id"
  ON "Registration"
  USING btree
  (coop_id);
ALTER TABLE "Registration" CLUSTER ON "IX_Registration_coop_id";

CREATE INDEX "IX_Registration_coop_id_grade"
  ON "Registration"
  USING btree
  (coop_id, grade);

CREATE INDEX "IX_Registration_coop_id_gradeDate"
  ON "Registration"
  USING btree
  (coop_id, "gradeDate");

CREATE INDEX "IX_Registration_coop_id_passed"
  ON "Registration"
  USING btree
  (coop_id);

CREATE INDEX "IX_Registration_coop_id_qualifiedForAssignment"
  ON "Registration"
  USING btree
  (coop_id, "qualifiedForAssigmnent");

CREATE INDEX "IX_Registration_coop_id_qualifiedForCompletion"
  ON "Registration"
  USING btree
  (coop_id, "qualifiedForCompletion");

CREATE INDEX "IX_Registration_student_id"
  ON "Registration"
  USING hash
  (student_id);

CREATE INDEX "IX_Registration_insurancecontract_id"
  ON "Registration"
  USING hash
  (insurancecontract_id);

CREATE INDEX "IX_Registration_group_id"
  ON "Registration"
  USING hash
  (group_id);

CREATE INDEX "IX_Registration_preferredEnd"
  ON "Registration"
  USING btree
  ("preferredEnd");

CREATE INDEX "IX_Registration_preferredStart"
  ON "Registration"
  USING btree
  ("preferredStart");

CREATE INDEX "IX_Registration_JobPosting_preferredbyregistrations_id"
  ON "Registration_JobPosting"
  USING hash
  (preferredbyregistrations_id);

CREATE INDEX "IX_Registration_JobPosting_preferredjobpostings_id"
  ON "Registration_JobPosting"
  USING hash
  (preferredjobpostings_id);

CREATE INDEX "IX_Registration_Location_preferredbyregistrations_id"
  ON "Registration_Location"
  USING hash
  (preferredbyregistrations_id);

CREATE INDEX "IX_Registration_Location_preferredlocations_id"
  ON "Registration_Location"
  USING hash
  (preferredlocations_id);

CREATE INDEX "IX_Registration_Requirement_meetsRequirements_id"
  ON "Registration_Requirement"
  USING hash
  (meetsrequirements_id);

CREATE INDEX "IX_Report_coOp_id"
  ON "Report"
  USING hash
  (coOp_id);

CREATE INDEX "IX_Report_dateSubmitted"
  ON "Report"
  USING btree
  ("dateSubmitted");

CREATE INDEX "IX_Report_grade"
  ON "Report"
  USING btree
  (grade);

CREATE INDEX "IX_Report_group_id"
  ON "Report"
  USING hash
  (group_id);

CREATE INDEX "IX_Report_jobPart_id"
  ON "Report"
  USING hash
  (jobpart_id);

CREATE INDEX "IX_Report_reportType_id"
  ON "Report"
  USING hash
  (reporttype_id);

CREATE INDEX "IX_Report_reportedBy_id"
  ON "Report"
  USING hash
  (reportedby_id);

CREATE INDEX "IX_Report_registration_id"
  ON "Report"
  USING hash
  (registration_id);

CREATE INDEX "IX_ReportType_codeName"
  ON "ReportType"
  USING btree
  ("codeName");

CREATE INDEX "IX_ReportType_scope"
  ON "ReportType"
  USING btree
  ("scope");

CREATE INDEX "IX_Requirement_coop_id"
  ON "Requirement"
  USING btree
  (coop_id);
ALTER TABLE "Requirement" CLUSTER ON "IX_Requirement_coop_id";

CREATE INDEX "IX_Role_Permission_roles_id"
  ON "Role_Permission"
  USING hash
  (roles_id);

ALTER TABLE "Student"
  ADD CONSTRAINT "CS_Student_serialNumber" UNIQUE("serialNumber");

ALTER TABLE "Student"
  ADD CONSTRAINT "CS_Student_socialSecurityId" UNIQUE("socialSecurityId");

CREATE INDEX "IX_Student_iban"
  ON "Student"
  USING hash
  (iban);

CREATE INDEX "IX_Telephone_createdBy_id"
  ON "Telephone"
  USING hash
  (createdBy_id);

CREATE INDEX "IX_Telephone_modifiedBy_id"
  ON "Telephone"
  USING hash
  (modifiedBy_id);

CREATE INDEX "IX_Telephone_person_id"
  ON "Telephone"
  USING hash
  (person_id);

CREATE INDEX "IX_Lesson_department"
	ON "Lesson"
	USING hash
	(department_id);
