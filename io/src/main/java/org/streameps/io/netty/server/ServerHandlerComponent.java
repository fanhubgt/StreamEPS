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

import java.util.ArrayList;
import java.util.List;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.streameps.io.netty.IServerHandlerComponent;

/**
 *
 * @author Frank Appiah
 */
public class ServerHandlerComponent<T> extends SimpleChannelHandler implements IServerHandlerComponent<T> {

    private List<IServerReqChannelHandler<T>> channelHandlers;
    private MessageEvent messageEvent;
    private ChannelHandlerContext channelHandlerContext;
    private T attachment;

    public ServerHandlerComponent() {
        channelHandlers = new ArrayList<IServerReqChannelHandler<T>>();
    }

    public ServerHandlerComponent(List<IServerReqChannelHandler<T>> channelHandlers) {
        this.channelHandlers = channelHandlers;
    }

    public void setChildHandlers(List<IServerReqChannelHandler<T>> handlers) {
        this.channelHandlers = handlers;
    }

    public List<IServerReqChannelHandler<T>> getChildHandlers() {
        return this.channelHandlers;
    }

    public IServerHandlerComponent addServerReqHandler(IServerReqChannelHandler<T> channelHandler) {
        getChildHandlers().add(channelHandler);
        return this;
    }

    public void handleRequest() {
        if (channelHandlers != null) {
            for (IServerReqChannelHandler<T> handler : getChildHandlers()) {
                handler.setChannelHandlerContext(channelHandlerContext);
                handler.setMessageEvent(messageEvent);
                handler.setAttachment(attachment);
                handler.handleRequest();
            }
        }
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        this.messageEvent = e;
        this.channelHandlerContext = ctx;
        setAttachment((T) ctx.getAttachment());
        handleRequest();
    }

    public ChannelHandlerContext getHandlerContext() {
        return this.channelHandlerContext;
    }

    public MessageEvent getMessageEvent() {
        return this.messageEvent;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public void setMessageEvent(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
    }

    public void setAttachment(T attachment) {
        this.attachment = attachment;
    }

    public T getAttachment() {
        return this.attachment;
    }
    
}
