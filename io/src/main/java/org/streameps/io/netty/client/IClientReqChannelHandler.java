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

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

/**
 * An interface for the client request channel handler.
 * 
 * @author  Frank Appiah
 */
public interface IClientReqChannelHandler<T> extends ChannelHandler {

    /**
     * It handles the client request.
     */
    public void handleRequest();

    /**
     * It returns the channel handler context for this client request handler.
     * @return The channel handler context for this request.
     */
    public ChannelHandlerContext getHandlerContext();

    /**
     * It returns the message event that was received from the server.
     * @return An instance of the message event.
     */
    public MessageEvent getMessageEvent();

    /**
     * It sets the channel handler context for this request handler.
     * @param channelHandlerContext The channel handler context for this request.
     */
    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext);

    /**
     * It sets the message event that was received from the request handler.
     * @param messageEvent The message event that was received from the server.
     */
    public void setMessageEvent(MessageEvent messageEvent);

        /**
     * It sets the attachment object for the client handlers.
     * @param attachment The attachment object for the client handlers.
     */
    public void setAttachment(T attachment);

    /**
     *  It returns the attachment object for the client handlers.
     * @return The attachment object for the client handlers.
     */
    public T getAttachment();
}
