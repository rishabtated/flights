@REM ----------------------------------------------------------------------------
@REM Maven Wrapper startup batch script (mvnw.cmd)
@REM Flight Booking API project
@REM ----------------------------------------------------------------------------
@echo off

SET MAVEN_PROJECTBASEDIR=%~dp0

FOR /F "tokens=2 delims==" %%I IN ('findstr /i "distributionUrl" "%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties"') DO SET DISTRIBUTION_URL=%%I

SET MAVEN_USER_HOME=%USERPROFILE%\.m2
SET MAVEN_HOME=%MAVEN_USER_HOME%\wrapper\dists\apache-maven-3.9.6

IF NOT EXIST "%MAVEN_HOME%\bin\mvn.cmd" (
    echo Downloading Maven 3.9.6...
    powershell -Command "& { Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip' -OutFile '%TEMP%\maven.zip'; Expand-Archive -Path '%TEMP%\maven.zip' -DestinationPath '%MAVEN_USER_HOME%\wrapper\dists' -Force; Rename-Item '%MAVEN_USER_HOME%\wrapper\dists\apache-maven-3.9.6' 'apache-maven-3.9.6' -ErrorAction SilentlyContinue }"
)

SET JAVA_HOME=%USERPROFILE%\.jdks\openjdk-26
SET PATH=%JAVA_HOME%\bin;%PATH%

"%MAVEN_HOME%\bin\mvn.cmd" %*
