@REM (C) Copyright 2011, Frank Appiah
@REM StreamEPS, The Stream Event Processing System
@REM Find the copyright license in the main project
@REM or at the project site http://github.com/fanhubgt/StreamEPS

@echo off

@REM Call and set the properties of the server.

setlocal

call "%~dp0MSQ-Env.bat"

echo ==============================================================
echo --------------------------------------------------------------
echo ---         StreamEPS MSQ Server v1.5.8                    ---
echo ---      The Stream Event Processing System.               ---
echo --------------------------------------------------------------
echo ===============================================================

@REM echo on 
goto verifyPath

:verifyPath
set STREAMEPS_CLASSPATH=%CLASSPATH%;
echo %STREAMEPS_CLASSPATH%
set STREAMEPS_MSQ=org.streameps.msq.hornetq.EPSHornetQEmbedded 
set STREAMEPS_OPTS=%STREAMEPS_PARAMS%
@REM echo %STREAM_OPTS%
goto runDaemon

:runDaemon
"%JAVA_HOME%\bin\java" %JAVA_OPTS% %STREAM_OPTS% -DPROPERTIES_LOC="%PROPERTIES_LOC%" -cp "%STREAMEPS_CLASSPATH%" org.streameps.msq.hornetq.EPSHornetQEmbedded

goto finally

:err
echo JAVA_HOME environment variable must be set!
pause

:finally

ENDLOCAL