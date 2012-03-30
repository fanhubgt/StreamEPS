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

import java.util.ArrayList;
import java.util.List;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;

/**
 *
 * @author Frank Appiah
 */
public class ClientHandlerComponent<T> extends SimpleChannelHandler implements IClientHandlerComponent<T> {

    private ChannelHandlerContext channelHandlerContext;
    private MessageEvent messageEvent;
    private List<IClientChannelHandler<T>> channelHandlers;
    private T attachment;
    private ILogger logger = LoggerUtil.getLogger(ClientHandlerComponent.class);

    public ClientHandlerComponent() {
        channelHandlers = new ArrayList<IClientChannelHandler<T>>();
    }

    public void setChildHandlers(List<IClientChannelHandler<T>> handlers) {
        this.channelHandlers = handlers;
    }

    public List<IClientChannelHandler<T>> getChildHandlers() {
        return this.channelHandlers;
    }

    public IClientHandlerComponent<T> addClientReqHandler(IClientChannelHandler<T> channelHandler) {
        channelHandlers.add(channelHandler);
        return this;
    }

    public void handleResponse() {
        if (getChildHandlers().size() > 0) {
            for (IClientChannelHandler<T> handler : getChildHandlers()) {
                handler.setChannelHandlerContext(channelHandlerContext);
                handler.setMessageEvent(messageEvent);
                handler.setAttachment(attachment);
                handler.handleResponse();
            }
        }
        logger.info("Handling the resquest of the client with the handlers.");
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.info("StreamEPS Server located @+" + e.getChannel().getRemoteAddress() + " is down...");
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelConnected(ctx, e);
        setChannelHandlerContext(channelHandlerContext);
        logger.info("StreamEPS Server located @+" + e.getChannel().getRemoteAddress() + " is up...");
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        setMessageEvent(messageEvent);
        setChannelHandlerContext(channelHandlerContext);
        //setAttachment((T) ctx.getAttachment());
        handleResponse();
        logger.info("New message received from the @server :=" + e.getRemoteAddress().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        logger.error(e.getCause().getMessage());
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
        return attachment;
    }
}
