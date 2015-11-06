DELETE FROM "Attachment" att WHERE att.report_id IS NOT NULL;

DELETE FROM "Report";

DELETE FROM "Role_ReportType";

DELETE FROM "ReportType";

DELETE FROM "Literal" lit WHERE lit.multilingual_id >= 4000 AND lit.multilingual_id < 8000;

DELETE FROM "Multilingual" mul WHERE mul.id >= 4000 AND mul.id < 8000;