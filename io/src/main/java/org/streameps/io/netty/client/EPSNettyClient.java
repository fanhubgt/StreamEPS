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

import org.streameps.io.netty.client.IClientReqChannelHandler;
import org.streameps.io.netty.client.IEPSNettyClient;
import java.util.concurrent.ExecutorService;
import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.streameps.io.netty.IServerConnectParam;

/**
 *
 * @author Frank Appiah
 */
public class EPSNettyClient implements IEPSNettyClient {

    private ChannelFactory channelFactory;
    private ExecutorService executorService;
    private Bootstrap bootstrap;
    private IClientReqChannelHandler requestChannelHandler;
    private IServerConnectParam serverParameter;

    public EPSNettyClient() {
    }

    public EPSNettyClient(ChannelFactory channelFactory, ExecutorService executorService, Bootstrap bootstrap, IClientReqChannelHandler requestChannelHandler, IServerConnectParam serverParameter) {
        this.channelFactory = channelFactory;
        this.executorService = executorService;
        this.bootstrap = bootstrap;
        this.requestChannelHandler = requestChannelHandler;
        this.serverParameter = serverParameter;
    }
    
    public void setChannelFactory(ChannelFactory channelFactory) {
        this.channelFactory=channelFactory;
    }

    public ChannelFactory getChannelFactory() {
       return this.channelFactory;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService=executorService;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public void setBoostrap(Bootstrap bootstrap) {
        this.bootstrap=bootstrap;
    }

    public Bootstrap getBootstrap() {
        return this.bootstrap;
    }

    public void setChannelHandler(IClientReqChannelHandler handler) {
        this.requestChannelHandler=handler;
    }

    public IClientReqChannelHandler getChannelHandler() {
        return this.requestChannelHandler;
    }

    public void setServerProperty(IServerConnectParam serverParam) {
        this.serverParameter=serverParam;
    }

    public IServerConnectParam getServerProperty() {
       return this.serverParameter;
    }
    
}
