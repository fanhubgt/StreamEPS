/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2011.
 * 
 *  Distributed under the Modified BSD License.
 *  Copyright notice: The copyright for this software and a full listing
 *  of individual contributors are as shown in the packaged copyright.txt
 *  file.
 *  All rights reserved.
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 
 *  - Neither the name of the ORGANIZATION nor the names of its contributors may
 *  be used to endorse or promote products derived from this software without
 *  specific prior written permission.
 * 
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 *  FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 *  USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  =============================================================================
 */
package org.streameps.io.netty.server;

import org.streameps.core.EPSRuntimeClient;
import org.streameps.core.IEPSRuntimeClient;
import org.streameps.io.netty.EPSCommandLineProp;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;

/**
 *
 * @author Frank Appiah
 */
public class ServerProxy {

    private IServerConfigurator configurator;
    private static ILogger logger = LoggerUtil.getLogger(ServerProxy.class);
    private boolean isGroupServer = false;
    private String version = "version 1.5.8";

    public ServerProxy() {
        configurator = new ServerConfigurator();
    }

    public static void main(String[] args) {
        boolean isGroupServer = false;

        isGroupServer = Boolean.getBoolean(System.getProperty(EPSCommandLineProp.GROUP_SERVER));

        ServerProxy serverProxy = new ServerProxy();
        serverProxy.setGroupServer(isGroupServer);
        //loadConfiguration();
        IServerConnectParam connectParam = (IServerConnectParam) new ServerConnectParam();
        connectParam.setKeepAliveFlag(Boolean.getBoolean(System.getProperty(EPSCommandLineProp.CHILD_KEEP_ALIVE)));
        connectParam.setReuseAddress(Boolean.getBoolean(System.getProperty(EPSCommandLineProp.REUSE_ADDRESS)));
        connectParam.setTcpNoDelay(Boolean.getBoolean(System.getProperty(EPSCommandLineProp.CHILD_TCP_NO_DELAY)));
        connectParam.setServerAddress(System.getProperty(EPSCommandLineProp.LOCAL_ADDRESS));
        connectParam.setServerPort(Integer.parseInt(System.getProperty(EPSCommandLineProp.PORT)));
        connectParam.setReceiveBufferSize(Integer.parseInt(System.getProperty(EPSCommandLineProp.RECEIVE_BUFFER_SIZE)));

        IServerConfigurator configurator = new ServerConfigurator();
        configurator.setCorePoolSize(Integer.parseInt(System.getProperty(EPSCommandLineProp.CORE_POOL_SIZE)));

        IEPSNettyServer nettyServer = configurator.createServer(null);
        nettyServer.setServerProperty(connectParam);

        configurator.setServerConnectionParameter(connectParam);
        configurator.setEPSServer(nettyServer);
        configurator.setCorePoolSize(Integer.parseInt(System.getProperty(EPSCommandLineProp.CORE_POOL_SIZE)));

        EPSRuntimeService.setEnginePerClient(Boolean.parseBoolean(System.getProperty(EPSCommandLineProp.ENGINE_PER_CLIENT)));
        
        serverProxy.setConfigurator(configurator);
        serverProxy.configure(serverProxy.getConfigurator());
        printStatus();
        logger.info("[Version] " + serverProxy.getVersion());
    }

    private static void loadConfiguration() {
       IEPSRuntimeClient client=new EPSRuntimeClient();
       client.loadBuilders(System.getProperty(EPSCommandLineProp.PROPERTY_FILE));
       EPSRuntimeService.setClientInstance(client);
    }

    public void setGroupServer(boolean groupServer) {
        this.isGroupServer = groupServer;
    }

    private static void printStatus() {
        logger.info("");
        logger.info("[Started]  Running ..........");
        logger.info("[StreamEPS] Server Proxy to the StreamEPS runtime is up....");
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void configure(IServerConfigurator configurator) {
        IServerConfigurator isc = configurator;

        isc.setGroupServer(isGroupServer);
        if (isGroupServer) {
            isc.createGroupServer(null);
            logger.info("[Configuration] Creating the netty group server.");
            isc.configureGroupServer();
            logger.debug("Configuring the netty group server.");
        } else {
            isc.createServer(null);
            logger.info("[Configuration] Creating the netty server.");
            isc.configure();
            logger.debug("[Configuration] Configuring the netty server.");
        }
    }

    public void configure() {
        IServerConfigurator isc = getConfigurator();

        isc.setGroupServer(isGroupServer);
        if (isGroupServer) {
            isc.createGroupServer(null);
            logger.info("[Configuration] Creating the netty group server.");
            isc.configureGroupServer();
            logger.debug("Configuring the netty group server.");
        } else {
            isc.createServer(null);
            logger.info("[Configuration] Creating the netty server.");
            isc.configure();
            logger.debug("[Configuration] Configuring the netty server.");
        }
    }

    public IServerConfigurator getConfigurator() {
        return configurator;
    }

    public void setConfigurator(IServerConfigurator configurator) {
        this.configurator = configurator;
    }
}
