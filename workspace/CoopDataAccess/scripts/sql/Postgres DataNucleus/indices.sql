--ALTER SEQUENCE hibernate_sequence RESTART 65536;

ALTER TABLE "ACTIVITYSECTOR"
  ADD CONSTRAINT "CS_ActivitySector_code" UNIQUE("CODE") DEFERRABLE INITIALLY DEFERRED;

CREATE INDEX "IX_AuthenticatedUser_department_coop"
  ON "AUTHENTICATEDUSER"
  USING btree
  ("DEPARTMENT_ID", "DEFAULTCOOP_ID");

ALTER TABLE "AUTHENTICATEDUSER"
  ADD CONSTRAINT "CS_AuthenticatedUser_userName" UNIQUE("USERNAME");

CREATE INDEX "IX_Category_path"
  ON "CATEGORY"
  USING btree
  ("PATH");

CREATE INDEX "IX_Coop_academicYear"
  ON "COOP"
  USING btree
  ("ACADEMICYEAR");
ALTER TABLE "COOP" CLUSTER ON "IX_Coop_academicYear";

CREATE INDEX "IX_Coop_endDate"
  ON "COOP"
  USING btree
  ("ENDDATE");

CREATE INDEX "IX_Coop_setup_active"
  ON "COOP"
  ("SETUP", "ACTIVE");

CREATE INDEX "IX_Coop_setup_inRegistration"
  ON "COOP"
  ("SETUP", "INREGISTRATION");

CREATE INDEX "IX_Coop_active"
  ON "COOP"
  ("ACTIVE");

CREATE INDEX "IX_Coop_setup_active_inRegistration"
  ON "COOP"
  ("SETUP", "ACTIVE", "INREGISTRATION");

CREATE INDEX "IX_Coop_startDate"
  ON "COOP"
  USING btree
  ("STARTDATE");

ALTER TABLE "COMPANY"
  ADD CONSTRAINT "CS_Company_taxCode" UNIQUE("TAXCODE");

CREATE INDEX "IX_EntityAccess_entityName"
  ON "ENTITYACCESS"
  USING hash
  ("ENTITYNAME");

CREATE INDEX "IX_FinancialSource_name"
  ON "FINANCIALSOURCE"
  USING btree
  ("NAME");

CREATE INDEX "IX_Group_isPendingFormation"
  ON "GROUP"
  USING btree
  ("ISPENDINGFORMATION");

CREATE INDEX "IX_Job_endDate"
  ON "JOB"
  USING btree
  ("ENDDATE");

CREATE INDEX "IX_Job_startDate"
  ON "JOB"
  USING btree
  ("STARTDATE");

CREATE INDEX "IX_Job_state"
  ON "JOB"
  USING hash
  ("STATE");

CREATE INDEX "IX_JobPart_endDate"
  ON "JOBPART"
  USING btree
  ("ENDDATE");

CREATE INDEX "IX_JobPart_startDate"
  ON "JOBPART"
  USING btree
  ("STARTDATE");

CREATE INDEX "IX_JobPosting_coop_id_expeditionLocation_id"
  ON "JOBPOSTING"
  USING btree
  ("COOP_ID", "EXPEDITIONLOCATION_ID");

CREATE INDEX "IX_JobPosting_coop_id_isSeatPerGroup"
  ON "JOBPOSTING"
  USING btree
  ("COOP_ID", "ISSEATPERGROUP");

CREATE INDEX "IX_JobPosting_coop_id_remainingSeats"
  ON "JOBPOSTING"
  USING btree
  ("COOP_ID", "REMAININGSEATS");

CREATE INDEX "IX_JobPosting_coop_id_salary"
  ON "JOBPOSTING"
  USING btree
  ("COOP_ID", "SALARY");

CREATE INDEX "IX_JobPosting_coop_id_seatsNumber"
  ON "JOBPOSTING"
  USING btree
  ("COOP_ID", "SEATSNUMBER");

CREATE INDEX "IX_JobPosting_coop_id_supervisingProfessor_id"
  ON "JOBPOSTING"
  USING btree
  ("COOP_ID", "SUPERVISINGPROFESSOR_ID");

ALTER TABLE "LANGUAGE"
  ADD CONSTRAINT "CS_Language_localeCode" UNIQUE("LOCALECODE");

CREATE INDEX "IX_Location_path"
  ON "LOCATION"
  USING btree
  ("PATH");

CREATE INDEX "IX_Location_type"
  ON "LOCATION"
  USING btree
  ("TYPE");

CREATE INDEX "IX_Payment_paymentDate_state"
  ON "PAYMENT"
  USING btree
  ("PAYMENTDATE", "STATE");

CREATE INDEX "IX_Payment_source_id_paymentDate"
  ON "PAYMENT"
  USING btree
  ("SOURCE_ID", "PAYMENTDATE");
ALTER TABLE "PAYMENT" CLUSTER ON "IX_Payment_source_id_paymentDate";

CREATE INDEX "IX_Payment_source_id_paymentDate_registration_id"
  ON "PAYMENT"
  USING btree
  ("SOURCE_ID", "PAYMENTDATE", "REGISTRATION_ID");
  
CREATE INDEX "IX_Payment_source_id_startDate"
  ON "PAYMENT"
  USING btree
  ("SOURCE_ID", "STARTDATE");

CREATE INDEX "IX_Payment_source_id_endDate"
  ON "PAYMENT"
  USING btree
  ("SOURCE_ID", "ENDDATE");

CREATE INDEX "IX_Payment_startDate"
  ON "PAYMENT"
  USING btree
  ("STARTDATE");

CREATE INDEX "IX_Payment_endDate"
  ON "PAYMENT"
  USING btree
  ("ENDDATE");

CREATE INDEX "IX_Payment_type_paymentDate"
  ON "PAYMENT"
  USING btree
  ("TYPE", "PAYMENTDATE");

CREATE INDEX "IX_Payment_type_source_id_paymentDate"
  ON "PAYMENT"
  USING btree
  ("TYPE", "SOURCE_ID", "PAYMENTDATE");

CREATE INDEX "IX_Person_surname"
  ON "PERSON"
  USING btree
  ("SURNAME");

CREATE INDEX "IX_Registration_coop_id_grade"
  ON "REGISTRATION"
  USING btree
  ("COOP_ID", "GRADE");

CREATE INDEX "IX_Registration_coop_id_gradeDate"
  ON "REGISTRATION"
  USING btree
  ("COOP_ID", "GRADEDATE");

CREATE INDEX "IX_Registration_coop_id_passed"
  ON "REGISTRATION"
  USING btree
  ("COOP_ID", "PASSED");

CREATE INDEX "IX_Registration_coop_id_qualifiedForAssignment"
  ON "REGISTRATION"
  USING btree
  ("COOP_ID", "QUALIFIEDFORASSIGMNENT");

CREATE INDEX "IX_Registration_coop_id_qualifiedForCompletion"
  ON "REGISTRATION"
  USING btree
  ("COOP_ID", "QUALIFIEDFORCOMPLETION");

CREATE INDEX "IX_Registration_preferredEnd"
  ON "REGISTRATION"
  USING btree
  ("PREFERREDEND");

CREATE INDEX "IX_Registration_preferredStart"
  ON "REGISTRATION"
  USING btree
  ("PREFERREDSTART");

CREATE INDEX "IX_Report_dateSubmitted"
  ON "REPORT"
  USING btree
  ("DATESUBMITTED");

CREATE INDEX "IX_Report_grade"
  ON "REPORT"
  USING btree
  ("GRADE");

CREATE INDEX "IX_ReportType_codeName"
  ON "REPORTTYPE"
  USING btree
  ("CODENAME");

ALTER TABLE "STUDENT"
  ADD CONSTRAINT "CS_Student_serialNumber" UNIQUE("SERIALNUMBER");

ALTER TABLE "STUDENT"
  ADD CONSTRAINT "CS_Student_socialSecurityId" UNIQUE("SOCIALSECURITYID");

CREATE INDEX "IX_Student_iban"
  ON "STUDENT"
  USING hash
  ("IBAN");

-- Remedy date columns which should be nullable
ALTER TABLE "PERSON"
  ALTER COLUMN "DATEOFBIRTH" DROP NOT NULL;
ALTER TABLE "PERSON"
  ALTER COLUMN "CREATED" DROP NOT NULL;
ALTER TABLE "PERSON"
  ALTER COLUMN "MODIFIED" DROP NOT NULL;

ALTER TABLE "LESSON"
  ALTER COLUMN "CREATED" DROP NOT NULL;
ALTER TABLE "LESSON"
  ALTER COLUMN "MODIFIED" DROP NOT NULL;

ALTER TABLE "COOP"
  ALTER COLUMN "CREATED" DROP NOT NULL;
ALTER TABLE "COOP"
  ALTER COLUMN "MODIFIED" DROP NOT NULL;
ALTER TABLE "COOP"
  ALTER COLUMN "STARTDATE" DROP NOT NULL;
ALTER TABLE "COOP"
  ALTER COLUMN "ENDDATE" DROP NOT NULL;
