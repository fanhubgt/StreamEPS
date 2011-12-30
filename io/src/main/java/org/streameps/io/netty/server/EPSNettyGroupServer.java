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

import org.streameps.io.netty.server.IServerReqChannelHandler;
import org.streameps.io.netty.server.IEPSNettyGroupServer;
import java.nio.channels.Channel;
import java.util.concurrent.ExecutorService;
import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.streameps.io.netty.IServerConnectGroupParam;

/**
 *
 * @author Frank Appiah
 */
public class EPSNettyGroupServer extends SimpleChannelHandler implements IEPSNettyGroupServer {

    private ChannelFactory channelFactory;
    private ExecutorService executorService;
    private Bootstrap bootstrap;
    private IServerReqChannelHandler requestChannelHandler;
    private IServerConnectGroupParam serverGroupParameter;
    private Channel serverChannel;
    private ChannelGroup channelGroup;

    public EPSNettyGroupServer() {
        channelGroup = new DefaultChannelGroup();
    }

    public Channel getServerChannel() {
        return this.serverChannel;
    }

    public void setServerChannel(Channel channel) {
        this.serverChannel = channel;
    }

    public void setChannelFactory(ChannelFactory channelFactory) {
        this.channelFactory = channelFactory;
    }

    public ChannelFactory getChannelFactory() {
        return this.channelFactory;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public void setBoostrap(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public Bootstrap getBootstrap() {
        return this.bootstrap;
    }

    public void setChannelGroup(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    public ChannelGroup getChannelGroup() {
        return this.channelGroup;
    }

    public void setServerProperty(IServerConnectGroupParam serverParam) {
        this.serverGroupParameter = serverParam;
    }

    public IServerConnectGroupParam getServerProperty() {
        return this.serverGroupParameter;
    }

    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        channelGroup.add(e.getChannel());
    }
}
