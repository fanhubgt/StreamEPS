@REM (C) Copyright 2011, Frank Appiah
@REM StreamEPS, The Stream Event Processing System
@REM Find the copyright license in the main project
@REM or at the project site http://github.com/fanhubgt/StreamEPS

if NOT DEFINED STREAMEPS_HOME set STREAMEPS_HOME=%~dp0%..
if NOT DEFINED STREAM_SERVER set STREAM_SERVER=org.streameps.io.netty.server.ServerProxy
if NOT DEFINED JAVA_HOME goto err

@REM Change the following to suit your needs.
@REM By default the group server is turn off to provide a general queueing model.

set DEBUG=true
set PROPERTIES_LOC=%STREAMEPS_HOME%\bin

@REM Setting StreamEPS parameter values
set STREAMEPS_PARAMS=-Ddebug="%DEBUG%"
 
@REM ***** JAVA options *****

set JAVA_OPTS=-ea -Xms128M -Xmx512M -XX:+HeapDumpOnOutOfMemoryError -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -Dcom.sun.management.jmxremote.port=5080 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
 
@REM ***** CLASSPATH library setting *****
set CLASSPATH="%STREAMEPS_HOME%"

set CLASSPATH=%~dp0..\*;%~dp0..\lib\*;%CLASSPATH%

goto :eof