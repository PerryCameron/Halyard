@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Halyard startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and HALYARD_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\sign-7.2.6.jar;%APP_HOME%\lib\forms-7.2.6.jar;%APP_HOME%\lib\svg-7.2.6.jar;%APP_HOME%\lib\styled-xml-parser-7.2.6.jar;%APP_HOME%\lib\layout-7.2.6.jar;%APP_HOME%\lib\barcodes-7.2.6.jar;%APP_HOME%\lib\font-asian-7.2.6.jar;%APP_HOME%\lib\hyph-7.2.6.jar;%APP_HOME%\lib\pdfa-7.2.6.jar;%APP_HOME%\lib\kernel-7.2.6.jar;%APP_HOME%\lib\io-7.2.6.jar;%APP_HOME%\lib\commons-7.2.6.jar
set MODULE_PATH=%APP_HOME%\lib\Halyard.jar;%APP_HOME%\lib\logback-classic-1.5.6.jar;%APP_HOME%\lib\log4j-to-slf4j-2.20.0.jar;%APP_HOME%\lib\mariadb-java-client-3.2.0.jar;%APP_HOME%\lib\waffle-jna-3.2.0.jar;%APP_HOME%\lib\jcl-over-slf4j-2.0.5.jar;%APP_HOME%\lib\slf4j-api-2.0.13.jar;%APP_HOME%\lib\logback-core-1.5.6.jar;%APP_HOME%\lib\jsch-0.2.7.jar;%APP_HOME%\lib\spring-jdbc-6.1.8.jar;%APP_HOME%\lib\javafx-controls-21-mac-aarch64.jar;%APP_HOME%\lib\log4j-api-2.20.0.jar;%APP_HOME%\lib\spring-tx-6.1.8.jar;%APP_HOME%\lib\spring-beans-6.1.8.jar;%APP_HOME%\lib\spring-core-6.1.8.jar;%APP_HOME%\lib\javafx-graphics-21-mac-aarch64.jar;%APP_HOME%\lib\jna-platform-5.12.1.jar;%APP_HOME%\lib\jna-5.12.1.jar;%APP_HOME%\lib\caffeine-2.9.3.jar;%APP_HOME%\lib\checker-qual-3.23.0.jar;%APP_HOME%\lib\spring-jcl-6.1.8.jar;%APP_HOME%\lib\bcpkix-jdk18on-1.75.jar;%APP_HOME%\lib\bcutil-jdk18on-1.75.jar;%APP_HOME%\lib\bcprov-jdk18on-1.75.jar;%APP_HOME%\lib\javafx-base-21-mac-aarch64.jar;%APP_HOME%\lib\error_prone_annotations-2.10.0.jar

@rem Execute Halyard
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %HALYARD_OPTS%  -classpath "%CLASSPATH%" --module-path "%MODULE_PATH%" --module Halyard/org.ecsail.BaseApplication %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable HALYARD_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%HALYARD_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
