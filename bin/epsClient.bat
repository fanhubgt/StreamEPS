@echo off

set EPSConfigDIR=%~dp0%..

set CLASSPATH=%EPSConfigDIR%

set CLASSPATH=%~dp0..\*;%~dp0..\lib\*;%CLASSPATH%

REM Change the following to suit your needs.
REM By default the group server is turn off to provide a general client-server model.

set GROUPSERVER_BOOL=false
set CHILD_KEEP_ALIVE=true
set CHILD_TCP_NO_DELAY=true
set REUSE_ADDRESS=true
set RECEIVE_BUFFER_SIZE=8124
set LOCAL_ADDRESS=127.0.0.1
set REMOTE_ADDRESS=localhost
set CORE_POOL_SIZE=2
set PORT=5455
set REC