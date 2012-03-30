@REM (C) Copyright 2011, Frank Appiah
@REM StreamEPS, The Stream Event Processing System
@REM Find the copyright license in the main project
@REM or at the project site http://github.com/fanhubgt/StreamEPS

@echo off

if NOT DEFINED STREAMEPS_HOME set STREAMEPS_HOME=%~dp0%..
if NOT DEFINED STREAM_SERVER set STREAM_SERVER=org.streameps.io.netty.server.ServerProxy
if NOT DEFINED JAVA_HOME goto err

@REM Change the following to suit your needs.
@REM By default the group server is turn off to provide a general client-server model.

set GROUPSERVER_BOOL=false
set CHILD_KEEP_ALIVE=true
set CHILD_TCP_NO_DELAY=true
set REUSE_ADDRESS=true
set RECEIVE_BUFFER_SIZE=8124
set LOCAL_ADDRESS=127.0.0.1
set REMOTE_ADDRESS=localhost
set CORE_POOL_SIZE=2
set PORT=5455
set DEBUG=true
set PROPERTIES_FILE=
set ENGINE_PER_CLIENT=false
set STORE_LOCATION=%STREAMEPS_HOME%\store
@REM Engine Properties
set SEQUENCE_SIZE=1
set ASYNCHRONOUS=false
set QUEUED=true
set DISPATCHER_SIZE=10
set INITIAL_DELAY=0
set PERIODIC_DELAY=1
set THREAD_FACTORY_NAME=EPS
set SAVE_ON_DECIDE=true
set SAVE_ON_RECEIVE=true
@REM THE PERSIST TIMESTAMP IS IN SECONDS TIME UNIT.
set PERSIST_TIMESTAMP=60
@REM THIS IS IN MINUTES
set PERSIST_STREAM_TIMESTAMP=60
set EVENT_SAVE_COUNT=20
@REM supported: DAYS, HOURS, SECONDS, NANOSECONDS, MINUTES, MICROSECONDS
set TIME_UNIT=SECONDS


@REM -Djava.util.logging.config.file=%STREAMEPS_HOME%\logging.properties

@REM Setting StreamEPS parameter values
set STREAMEPS_PARAMS=-DeventSaveCount="%EVENT_SAVE_COUNT%" -DpersistStreamTimestamp="%PERSIST_STREAM_TIMESTAMP%" -DtimeUnit="%TIME_UNIT%" -DpersistTimestamp="%PERSIST_TIMESTAMP%" -DsaveOnReceive="%SAVE_ON_RECEIVE%" -DsaveOnDecide="%SAVE_ON_DECIDE%" -DthreadFactoryName=%THREAD_FACTORY_NAME% -DcorePoolSize="%CORE_POOL_SIZE%" -DdispatcherSize="%DISPATCHER_SIZE%" -DinitialDelay="%INITIAL_DELAY%" -DperiodicDelay="%PERIODIC_DELAY%" -DsequenceSize="%SEQUENCE_SIZE%" -Dasynchronous="%ASYNCHRONOUS%" -Dqueued="%QUEUED%" -DenginePerClient="%ENGINE_PER_CLIENT%" -DstoreLocation="%STORE_LOCATION%" -DgroupServer="%GROUPSERVER_BOOL%" -DkeepAlive="%CHILD_KEEP_ALIVE%" -DtcpNoDelay="%CHILD_TCP_NO_DELAY%" -DreuseAddress="%REUSE_ADDRESS%" -DreceiverBufferSize="%RECEIVE_BUFFER_SIZE%" -DremoteAddress="%REMOTE_ADDRESS%" -DcorePoolSize="%CORE_POOL_SIZE%" -Dport="%PORT%" -Ddebug="%DEBUG%" -DlocalAddress="%LOCAL_ADDRESS%"
 
@REM ***** JAVA options *****

set JAVA_OPTS=-ea -Xms128M -Xmx512M -XX:+HeapDumpOnOutOfMemoryError -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -Dcom.sun.management.jmxremote.port=5080 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
 
@REM ***** CLASSPATH library setting *****
set CLASSPATH="%STREAMEPS_HOME%"

set CLASSPATH=%~dp0..\*;%~dp0..\lib\*;%CLASSPATH%

goto :eof