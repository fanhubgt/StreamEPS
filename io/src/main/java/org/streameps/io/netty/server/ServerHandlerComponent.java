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
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.streameps.core.IStreamEvent;
import org.streameps.core.util.IDUtil;
import org.streameps.engine.IEPSEngine;
import org.streameps.io.netty.ChannelComponent;
import org.streameps.io.netty.server.listener.FilterServiceListener;
import org.streameps.io.netty.server.listener.UnFilterServiceListener;
import org.streameps.io.netty.server.service.AggregateService;
import org.streameps.io.netty.server.service.ConfigurationService;
import org.streameps.io.netty.server.service.DeciderServiceCallback;
import org.streameps.io.netty.server.service.FilterService;
import org.streameps.io.netty.server.service.IFilterService;
import org.streameps.io.netty.server.service.PatternService;
import org.streameps.io.netty.server.service.ServerService;
import org.streameps.io.netty.server.service.StoreService;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.preference.IPreference;
import org.streameps.preference.PreferenceInstance;

/**
 *
 * @author Frank Appiah
 */
public class ServerHandlerComponent<T> extends SimpleChannelHandler implements IServerHandlerComponent<T> {

    private List<IServerChannelHandler<T>> channelHandlers;
    private MessageEvent messageEvent;
    private ChannelHandlerContext channelHandlerContext;
    private T attachment;
    private ILogger logger = LoggerUtil.getLogger(ServerHandlerComponent.class);
    private static IServerHandlerComponent handlerComponent = null;
    private IEPSEngine engine = null;

    public ServerHandlerComponent() {
        channelHandlers = new ArrayList<IServerChannelHandler<T>>();
        setDefaultHandlers();
    }

    public static IServerHandlerComponent getInstance() {
        if (handlerComponent == null) {
            handlerComponent = new ServerHandlerComponent();
        }
        return handlerComponent;
    }

    public ServerHandlerComponent(List<IServerChannelHandler<T>> channelHandlers) {
        this.channelHandlers = channelHandlers;
        setDefaultHandlers();
    }

    public void setChildHandlers(List<IServerChannelHandler<T>> handlers) {
        this.channelHandlers = handlers;
    }

    public List<IServerChannelHandler<T>> getChildHandlers() {
        return this.channelHandlers;
    }

    public IServerHandlerComponent addServerReqHandler(IServerChannelHandler<T> channelHandler) {
        getChildHandlers().add(channelHandler);
        return this;
    }

    public void handleRequest() {
        if (channelHandlers.size() > 0) {
            for (IServerChannelHandler<T> handler : getChildHandlers()) {
                handler.setChannelHandlerContext(channelHandlerContext);
                handler.setMessageEvent(messageEvent);
                handler.setAttachment(attachment);
                handler.handleRequest();
            }
        }
        logger.debug("Handling the message received from the channel.");
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ChannelComponent.getInstance().addChannelIfAbsent(e.getChannel());
        if (channelHandlers.size() > 0) {
            for (IServerChannelHandler<T> handler : getChildHandlers()) {
                handler.setChannelHandlerContext(ctx);
            }
        }
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        messageEvent = e;
        channelHandlerContext = ctx;
        logger.info("[Receiver Handler] New message from a client located @" + e.getRemoteAddress().toString());
        if (EPSRuntimeService.isEnginePerClient()) {
            createEngineChannel(e.getChannel());
        }
        if (e.getMessage() instanceof IStreamEvent) {
            IEventReceiverHandler handler = ServerService.getInstance().getEventReceiverHandler();
            handler.setMessageEvent(messageEvent);
            handler.handleRequest();
        } else {
            handleRequest();
        }
        //ctx.sendUpstream(e);
    }

    private void createEngineChannel(Channel channel) {
        Map<String, IEngineChannel> ec = EPSRuntimeService.getEngineChannelMap();

        for (IEngineChannel engineChannel : ec.values()) {
            Channel c = engineChannel.getChannel();
            if (c.getId() == channel.getId()) {
                break;
            } else {
                try {
                    if (engine == null) {
                        while (PreferenceInstance.getInstance().hasNext()) {
                            IPreference preference = PreferenceInstance.getInstance().next();
                            engine = preference.getRuntimePreference().getEngine();
                            if (engine != null) {
                                break;
                            }
                        }
                    }
                    EPSRuntimeService.createInstance(engine, channel.getRemoteAddress().toString());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
                IEngineChannel iec = new EngineChannel(channel, engine, IDUtil.getUniqueID(new Date().toString()));
                EPSRuntimeService.getEngineChannelMap().put(iec.getIdentifier(), iec);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        logger.error("[Service Handler] " + e.getCause().getMessage());
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

    private void setDefaultHandlers() {
        IFilterService filterService = new FilterService(new FilterServiceListener(), new UnFilterServiceListener());
        filterService.setCallBack();
        ServerService.getInstance().setFilterService(filterService);
        ServerService.getInstance().setAggregateService(new AggregateService());
        ServerService.getInstance().setPatternService(new PatternService());
        ServerService.getInstance().setStoreService(new StoreService());
        ServerService.getInstance().setConfigurationService(new ConfigurationService());
        ServerService.getInstance().setDeciderService(new DeciderServiceCallback());
        ServerService.getInstance().setEventReceiverHandler(new EventReceiverHandler());

        logger.info("");
        logger.info("");
        getChildHandlers().add(ServerService.getInstance().getFilterService());
        logger.info("[Handlers] Configuring the pattern service handler.");
        getChildHandlers().add(ServerService.getInstance().getAggregateService());
        logger.info("[Handlers] Configuring the aggregate service handler.");
        getChildHandlers().add(ServerService.getInstance().getPatternService());
        getChildHandlers().add(ServerService.getInstance().getStoreService());
        logger.info("[Handlers] Configuring the store service handler.");
        getChildHandlers().add(ServerService.getInstance().getConfigurationService());
        logger.info("[Handlers] Configuring the configuration service handler.");
        getChildHandlers().add(ServerService.getInstance().getDeciderService());
        logger.info("[Handlers] Configuring the decider service handler.");
        getChildHandlers().add(ServerService.getInstance().getEventReceiverHandler());
    }

    public void handleResponse() {
    }
}
