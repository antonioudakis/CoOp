@echo off
if "%1"=="" goto :usage
if "%2"=="" goto :usage

if "%3"=="" (
	set dialect=PostgreSQLDialect 
) else (
	set dialect=%3
)

set modelBase=%2
set extraPaths=%4

java -Dhibernate.dialect=org.hibernate.dialect.PostgreSQLDialect -cp "C:\Java libs\hibernate-distribution-3.6.7.Final\hibernate3.jar;C:\Java libs\hibernate-distribution-3.6.7.Final\lib\required\slf4j-api-1.6.1.jar;C:\Java libs\hibernate-distribution-3.6.7.Final\lib\required\dom4j-1.6.1.jar;C:\Java libs\hibernate-distribution-3.6.7.Final\lib\jpa\hibernate-jpa-2.0-api-1.0.1.Final.jar;C:\Java libs\slf4j-1.6.1\slf4j-simple-1.6.1.jar;%modelBase%;%extraPaths%"  org.hibernate.tool.hbm2ddl.SchemaExport --create --delimiter=";" --output=%1 --format --text %modelBase%\META-INF\orm.xml
@echo dialect: %dialect%

goto :end

:usage
echo Usage: sqlgen target_sql_file path_to_model [sql_dialect=PostgreSQLDialect [extraClasspaths='']]
:end
