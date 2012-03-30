/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2012.
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
package org.streameps.client;

import java.net.SocketAddress;
import org.jboss.netty.channel.Channel;
import org.streameps.core.IStreamEvent;
import org.streameps.io.netty.client.ClientProxyQ;
import org.streameps.io.netty.client.IClientProxy;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;

/**
 *
 * @author Frank Appiah
 */
public class EPSNettyTransport implements IEPSTransport<IStreamEvent> {

    private boolean isConnected = false;
    private IClientProxy clientProxy;
    private Channel channel;
    private SocketAddress socketAddress;
    private ILogger logger = LoggerUtil.getLogger(EPSNettyTransport.class);

    public EPSNettyTransport() {
        clientProxy = new ClientProxyQ();
    }

    public EPSNettyTransport(IClientProxy clientProxy) {
        this.clientProxy = clientProxy;
    }

    public EPSNettyTransport(IClientProxy clientProxy, Channel channel) {
        this.clientProxy = clientProxy;
        this.channel = channel;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void open() {
        if (channel == null) {
            channel = clientProxy.getClientConfigurator().getChannel();
            socketAddress = channel.getRemoteAddress();
        }
        isConnected = true;
        logger.info("[Transport] Opening connection to the server...");
    }

    public void close() {
        clientProxy.getClientConfigurator().close();
        isConnected = false;
        logger.info("[Transport] Closing the connection to the server...");
    }

    public void write(IStreamEvent event) {
        channel.write(event);
    }

    public SocketAddress getSocketAddress() {
        return this.socketAddress;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setClientProxy(IClientProxy clientProxy) {
        this.clientProxy = clientProxy;
    }

    public IClientProxy getClientProxy() {
        return clientProxy;
    }
}
