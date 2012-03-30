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

import java.util.concurrent.ExecutorService;
import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.streameps.IStreamEPS;

/**
 * A client to connect to the remote Netty client bootstrap.
 * 
 * @author  Frank Appiah
 */
public interface IEPSNettyClient {

        /**
     * It sets the channel factory of the server.
     * @param channelFactory A netty channel factory instance.
     */
    public void setChannelFactory(ChannelFactory channelFactory);

    /**
     * It returns the channel factory of the server.
     * @return A netty channel factory instance.
     */
    public ChannelFactory getChannelFactory();

    /**
     * It sets the executor service for the server channel factory.
     * @param executorService The service for executing the threaded process.
     */
    public void setExecutorService(ExecutorService executorService);

    /**
     *  It returns the executor service for the server channel factory.
     * @return The service for executing the threaded process.
     */
    public ExecutorService getExecutorService();

    /**
     * It sets the bootstrap used to start the server channel.
     * @param bootstrap An instance of the server bootstrap channel.
     */
    public void setBoostrap(Bootstrap bootstrap);

    /**
     * It returns the bootstrap used to start the server channel.
     * @return An instance of the server bootstrap channel.
     */
    public Bootstrap getBootstrap();

    /**
     * It sets the channel handler for serving request from the client.
     * @param handler The channel handler for serving request from the client.
     */
    public void setChannelHandler(IClientHandlerComponent handler);

    /**
     * It returns the channel handler for serving request from the client.
     * @return The channel handler for serving request from the client.
     */
    public IClientHandlerComponent getChannelHandler();

    /**
     * It sets the TCP parameter for the server.
     * @param serverParam The parameters for the server connection.
     */
    public void setClientProperty(IClientConnectParam serverParam);

    /**
     * It returns the server properties that includes the port, server address,
     * tcpNoDelay, an indicator whether to reuse the address etc.
     * @return The parameters for the server connection.
     */
    public IClientConnectParam getClientProperty();

    public void sendEvent(Object event);

    public void setStreamEPS(IStreamEPS streamEPS);
    
    public IStreamEPS getStreamEPS();
}
