@echo off
echo Building the application...
rem mvn clean 
rem mvn package

echo build successful.

timeout /t 2 /nobreak >nul

echo Deploying application...
rem copy target\session.war E:\tomcat11\webapps\session.war

echo Deployment completed successfully.
timeout /t 2 /nobreak >nul
echo Starting Tomcat server...
rem E:\tomcat11\bin\startup.bat
echo Tomcat server started. Access the application at http://localhost:8081/manager/html