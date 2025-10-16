@ECHO OFF
:: Gradle startup script for Windows

SET APP_BASE_NAME=%~n0
SET DIRNAME=%~dp0
SET APP_HOME=%DIRNAME%

SET DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

java %DEFAULT_JVM_OPTS% -jar "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" %*
