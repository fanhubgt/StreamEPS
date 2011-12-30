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

import org.streameps.io.netty.server.IServerConfigurator;
import org.streameps.io.netty.server.IEPSNettyGroupServer;
import org.streameps.io.netty.server.IEPSNettyServer;
import java.net.InetSocketAddress;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.streameps.io.netty.IServerConnectParam;
import org.streameps.io.netty.factory.EPSGroupServerPipelineFactory;
import org.streameps.io.netty.factory.EPSServerPipelineFactory;
import org.streameps.thread.EPSExecutorManager;
import org.streameps.thread.IEPSExecutorManager;

/**
 *
 * @author Frank Appiah
 */
public class ServerConfigurator implements IServerConfigurator {

    private IEPSNettyServer nettyServer = null;
    private IEPSNettyGroupServer groupServer = null;
    private IEPSExecutorManager executorManager;
    private int corePoolSize = 1;
    private boolean isGroupServer = false;
    private IServerConnectParam serverConnectParam;

    public ServerConfigurator() {
        executorManager = new EPSExecutorManager();
    }

    public IEPSNettyServer createServer(IEPSNettyServer netServer) {
        if (netServer == null) {
            nettyServer = new EPSNettyServer();
        } else {
            nettyServer = netServer;
        }
        return nettyServer;
    }

    public IEPSNettyServer getEPSServer() {
        return nettyServer;
    }

    public void configure() {
        nettyServer = getEPSServer();
        executorManager.setPoolSize(getCorePoolSize());
        nettyServer.setExecutorService(executorManager.getExecutorService());

        ChannelFactory channelFactory = new NioServerSocketChannelFactory(
                nettyServer.getExecutorService(),
                nettyServer.getExecutorService());

        ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);
        bootstrap.setPipelineFactory(new EPSServerPipelineFactory(nettyServer));
        bootstrap.setParentHandler(nettyServer);

        nettyServer.setServerProperty(serverConnectParam);

        bootstrap.setOption("child.tcpNoDelay", nettyServer.getServerProperty().getTcpNoDelay());
        bootstrap.setOption("child.keepAlive", nettyServer.getServerProperty().getKeepAliveFlag());
        bootstrap.setOption("reuseAddress", nettyServer.getServerProperty().getReuseAddress());

        nettyServer.setChannelFactory(channelFactory);
        nettyServer.setBoostrap(bootstrap);

        bootstrap.bind(new InetSocketAddress(nettyServer.getServerProperty().getServerAddress(),
                nettyServer.getServerProperty().getServerPort()));
    }

    public void setServerConnectionParameter(IServerConnectParam connectParam) {
        nettyServer.setServerProperty(connectParam);
        this.serverConnectParam = connectParam;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void configureGroupServer() {
        groupServer = getEPSGroupServer();
        ChannelFactory channelFactory = new NioServerSocketChannelFactory(
                groupServer.getExecutorService(),
                groupServer.getExecutorService());

        ServerBootstrap bootstrap = new ServerBootstrap(
                channelFactory);
        bootstrap.setParentHandler(groupServer);
        bootstrap.setFactory(channelFactory);
        bootstrap.setPipelineFactory(new EPSGroupServerPipelineFactory(groupServer));

        IServerConnectParam connectParam = groupServer.getServerProperty().getGlobalServerParam();
        bootstrap.setOption("child.keekAlive", connectParam.getKeepAliveFlag());
        bootstrap.setOption("child.tcpNoDelay", connectParam.getTcpNoDelay());
        bootstrap.setOption("reuseAddress", connectParam.getReuseAddress());

        groupServer.setChannelFactory(channelFactory);
        groupServer.setBoostrap(bootstrap);

        bootstrap.bind(new InetSocketAddress(connectParam.getServerAddress(), connectParam.getServerPort()));
    }

    public IEPSNettyGroupServer createGroupServer(IEPSNettyGroupServer groupServer) {
        if (groupServer == null) {
            groupServer = new EPSNettyGroupServer();
        }
        return groupServer;
    }

    public IEPSNettyGroupServer getEPSGroupServer() {
        return this.groupServer;
    }

    public void setGroupServer(boolean groupServer) {
        this.isGroupServer = groupServer;
    }

    public boolean getGroupServer() {
        return this.isGroupServer;
    }

    public IServerConnectParam getServerConnectionParameter() {
        return this.serverConnectParam;
    }

    public void setEPSServer(IEPSNettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }
}
