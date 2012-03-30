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
package org.streameps.io.netty.client;

import java.net.InetSocketAddress;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.streameps.IStreamEPS;
import org.streameps.io.netty.EPSThreadExecutor;
import org.streameps.io.netty.factory.EPSClientPipelineFactory;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.thread.EPSExecutorManager;
import org.streameps.thread.IEPSExecutorManager;

/**
 *
 * @author Frank Appiah
 */
public class ClientConfigurator implements IClientConfigurator {

    private IClientConnectParam clientConnectParam;
    private int corePoolSize = 1;
    private IEPSNettyClient nettyClient;
    private IEPSExecutorManager executorManager;
    private ChannelFuture future;
    private ClientBootstrap bootstrap;
    private Channel channel;
    private IStreamEPS streamEPS;
    private ILogger logger = LoggerUtil.getLogger(ClientConfigurator.class);

    public ClientConfigurator() {
        executorManager = new EPSExecutorManager(corePoolSize);
        nettyClient = new EPSNettyClient();
    }

    public ClientConfigurator(IClientConnectParam clientConnectParam, int corePoolSize, IEPSNettyClient nettyClient) {
        this.clientConnectParam = clientConnectParam;
        this.corePoolSize = corePoolSize;
        this.nettyClient = nettyClient;
        executorManager = new EPSExecutorManager(corePoolSize);
    }

    public void setClientConnectionParameter(IClientConnectParam connectParam) {
        this.clientConnectParam = connectParam;
    }

    public IClientConnectParam getClientConnectionParameter() {
        return this.clientConnectParam;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public IEPSNettyClient createClient(IStreamEPS streamEPS) {
        if (nettyClient == null) {
            this.nettyClient = new EPSNettyClient();
            this.nettyClient.setClientProperty(clientConnectParam);
            this.nettyClient.setStreamEPS(streamEPS);
        }
        return nettyClient;
    }

    public IEPSNettyClient getEPSClient() {
        return this.nettyClient;
    }

    public void setEPSClient(IEPSNettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    public void configure() {
        executorManager = EPSThreadExecutor.createInstance(corePoolSize);
        nettyClient.setExecutorService(executorManager.getExecutorService());

        NioClientSocketChannelFactory factory = new NioClientSocketChannelFactory(
                executorManager.getExecutorService(),
                executorManager.getExecutorService());
        bootstrap = new ClientBootstrap(factory);

        bootstrap.setPipelineFactory(new EPSClientPipelineFactory(nettyClient));

        future = bootstrap.connect(new InetSocketAddress(clientConnectParam.getServerAddress(),
                clientConnectParam.getServerPort()));

        channel = future.awaitUninterruptibly().getChannel();
        future.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture cf) throws Exception {
                Channel ch = cf.getChannel();
                if (ch.getId() == channel.getId()) {
                    logger.info("Channel handle has being acquired...");
                }
            }
        });
        nettyClient.setChannelFactory(factory);
        nettyClient.setBoostrap(bootstrap);
    }

    public void close() {
        bootstrap.releaseExternalResources();
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void addClientListener(IClientChannelHandler channelHandler) {
        nettyClient.getChannelHandler().addClientReqHandler(channelHandler);
    }

}
