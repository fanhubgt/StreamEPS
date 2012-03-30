@REM (C) Copyright 2011, Frank Appiah
@REM StreamEPS, The Stream Event Processing System
@REM Find the copyright license in the main project
@REM or at the project site http://github.com/fanhubgt/StreamEPS

@echo off

@REM Call and set the properties of the server.

setlocal

call "%~dp0EPS-Env.bat"

@REM ***** CLASSPATH library setting *****
set CLASSPATH="%STREAMEPS_HOME%"

set CLASSPATH=%~dp0..\*;%~dp0..\lib\*;%CLASSPATH%
echo off
echo off
echo ==============================================================
echo --------------------------------------------------------------
echo ---         StreamEPS Server v1.5.8                        ---
echo ---      The Stream Event Processing System.               ---
echo --------------------------------------------------------------
echo ===============================================================

@REM echo on 
goto verifyPath

:verifyPath
@REM echo %JAVA_OPTS%
if %GROUPSERVER_BOOL% == false (
 echo Starting a remote server with address: "%LOCAL_ADDRESS%"
 ) else (
 echo Starting a remote group server with address: "%REMOTE_ADDRESS%" 
)

set STREAMEPS_CLASSPATH=%CLASSPATH%;
echo %STREAMEPS_CLASSPATH%
set STREAM_SERVER=org.streameps.io.netty.server.ServerProxy
set STREAM_OPTS=%STREAMEPS_PARAMS%
@REM echo %STREAM_OPTS%
goto runDaemon

:runDaemon
echo Starting StreamEPS Server on "%LOCAL_ADDRESS%" : "%PORT%".
"%JAVA_HOME%\bin\java" %JAVA_OPTS% %STREAM_OPTS% -cp "%STREAMEPS_CLASSPATH%" org.streameps.io.netty.server.ServerProxy

goto finally

:err
echo JAVA_HOME environment variable must be set!
pause

:finally

ENDLOCAL