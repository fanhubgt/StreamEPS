@echo off

setlocal

call "%~dp0epsEnv.cmd"

set EPSMAIN=org.streameps.io.netty.ServerProxy
echo on

java "-DgroupServer=%GROUPSERVER_BOOL%" "-DkeepAlive=%CHILD_KEEP_ALIVE%" "-DtcpNoDelay=%CHILD_TCP_NO_DELAY%" "-DreuseAddress=%REUSE_ADDRESS%" "-DreceiverBufferSize=%RECEIVE_BUFFER_SIZE%" "-DremoteAddress=%REMOTE_ADDRESS%" "-DcorePoolSize=%CORE_POOL_SIZE%" "-Dport=%PORT%" "-Ddebug=%DEBUG%" "-DpropertyFile=%PROPERTIES_FILE%" "-DlocalAddress=%LOCAL_ADDRESS%" -cp "%CLASSPATH%" %EPSMAIN% %*

endlocal