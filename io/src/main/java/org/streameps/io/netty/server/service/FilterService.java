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
package org.streameps.io.netty.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.PartitionWindow;
import org.streameps.core.util.IDUtil;
import org.streameps.core.util.XMLUtil;
import org.streameps.engine.IFilterContext;
import org.streameps.filter.IEPSFilter;
import org.streameps.filter.IFilterValueSet;
import org.streameps.filter.listener.IFilterObservable;
import org.streameps.filter.listener.IFilteredEventObserver;
import org.streameps.filter.listener.IUnFilteredEventObserver;
import org.streameps.io.netty.ChannelComponent;
import org.streameps.io.netty.EPSChannelWrite;
import org.streameps.io.netty.EPSThreadExecutor;
import org.streameps.io.netty.MatchInterest;
import org.streameps.io.netty.FilterResponse;
import org.streameps.io.netty.HandlerType;
import org.streameps.io.netty.IFilterRequest;
import org.streameps.io.netty.IServiceEvent;
import org.streameps.io.netty.ServiceEvent;
import org.streameps.io.netty.ServiceType;
import org.streameps.io.netty.server.AbstractServerHandler;
import org.streameps.io.netty.server.EPSRuntimeService;
import org.streameps.io.netty.server.IEPSChannelWrite;
import org.streameps.io.netty.server.IServiceCallback;
import org.streameps.io.netty.server.listener.FilterServiceListener;
import org.streameps.io.netty.server.listener.IFilterServiceListener;
import org.streameps.io.netty.server.listener.IUnFilterServiceListener;
import org.streameps.io.netty.server.listener.UnFilterServiceListener;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.thread.IWorkerCallable;

/**
 *
 * @author Frank Appiah
 */
public class FilterService extends AbstractServerHandler implements IServiceCallback<IFilterValueSet<ISortedAccumulator>>, IFilterService {

    private IFilterServiceListener filterServiceListener;
    private IUnFilterServiceListener unFilterServiceListener;
    private IFilterObservable observablePrototype;
    private IFilterValueSet<ISortedAccumulator> matchFilterSet, unMatchFilterSet;
    private IEPSFilter filter;
    private ILogger logger = LoggerUtil.getLogger(FilterService.class);
    private MatchInterest filterInterest = MatchInterest.MATCH;

    public FilterService() {
        filterServiceListener = new FilterServiceListener();
        unFilterServiceListener = new UnFilterServiceListener();
    }

    public FilterService(IFilterServiceListener filterServiceListener, IUnFilterServiceListener unFilterServiceListener) {
        this.filterServiceListener = filterServiceListener;
        this.unFilterServiceListener = unFilterServiceListener;
        setCallBack();
    }

    public FilterService(IFilterServiceListener filterServiceListener, IUnFilterServiceListener unFilterServiceListener, IFilterObservable observablePrototype) {
        this.filterServiceListener = filterServiceListener;
        this.unFilterServiceListener = unFilterServiceListener;
        this.observablePrototype = observablePrototype;
        setCallBack();
    }

    public void setCallBack() {
        this.filterServiceListener.setServiceCallback(this);
        this.unFilterServiceListener.setServiceCallback(this);
    }

    private void createPrototype() {
        observablePrototype.addFilteredEventObserver(filterServiceListener);
    }

    public void handleRequest() {

        EPSThreadExecutor.createInstance().execute(new IWorkerCallable<String>() {

            MessageEvent messageEvent = getMessageEvent();

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String name) {
            }

            public String call() {
                IFilterContext filterContext = null;
                try {
                    Object message = messageEvent.getMessage();
                    if (message instanceof IFilterRequest) {
                        IFilterRequest filterRequest = (IFilterRequest) message;
                        filterInterest = filterRequest.getFilterInterest();

                        filterContext = filterRequest.getFilterContext();
                        filterContext.getEPSFilter().setFilterEventObservers(new ArrayList<IFilteredEventObserver>());
                        filterContext.getEPSFilter().setUnFilterEventObservers(new ArrayList<IUnFilteredEventObserver>());

                        filterContext.getEPSFilter().getFilterEventObservers().add(filterServiceListener);
                        filterContext.getEPSFilter().getUnFilterEventObservers().add(unFilterServiceListener);

                        EPSRuntimeService.setFilterContext(filterContext);
                        logger.info("Handling the request " + filterRequest.getIdentifier() + "of the filter context:" + filterContext.getIdentifier());
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                return filterContext.getIdentifier();
            }
        });
    }

    public void handleResponse() {

        EPSThreadExecutor.createInstance().execute(new IWorkerCallable<String>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String name) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public String call() throws Exception {
                Channel channel = getMessageEvent().getChannel();
                FilterResponse filterResponse = null;
                switch (filterInterest) {
                    case MATCH: {
                        filterResponse = new FilterResponse(IDUtil.getUniqueID(new Date().toString()));
                        IPartitionWindow<Set> ipw = new PartitionWindow<Set>(new HashSet());
                        ipw.setAnnotation(getMatchFilterSet().getValueIdentifier());
                        ISortedAccumulator accumulator = matchFilterSet.getValueSet().getWindow();
                        for (Object matchEvent : accumulator.getMap().values()) {
                            ipw.getWindow().add(matchEvent);
                        }
                        byte[] response = XMLUtil.encode(filterResponse);
                        ChannelHandlerContext context = getHandlerContext();
                        IServiceEvent event = new ServiceEvent();
                        event.setHandlerType(HandlerType.RESPONSE);
                        event.setServiceType(ServiceType.FILTER);
                        context.setAttachment(event);
                        IEPSChannelWrite write = new EPSChannelWrite();
                        write.write(ChannelComponent.getInstance().getChannelList(), filterResponse);
                    }
                    break;
                    case UN_MATCH: {
                        filterResponse = new FilterResponse(IDUtil.getUniqueID(new Date().toString()));
                        IPartitionWindow<Set> partitionWindow = new PartitionWindow<Set>();
                        partitionWindow.setAnnotation(getUnMatchFilterSet().getValueIdentifier());
                        ISortedAccumulator unmatchAcc = unMatchFilterSet.getValueSet().getWindow();
                        for (Object matchEvent : unmatchAcc.getMap().values()) {
                            partitionWindow.getWindow().add(matchEvent);
                        }
                        filterResponse.setUnMatchWindow(partitionWindow);
                        byte[] response = XMLUtil.encode(filterResponse);
                        ChannelHandlerContext context = getHandlerContext();
                        IServiceEvent event = new ServiceEvent();
                        event.setHandlerType(HandlerType.RESPONSE);
                        event.setServiceType(ServiceType.FILTER);
                        context.setAttachment(event);
                        IEPSChannelWrite write = new EPSChannelWrite();
                        write.write(ChannelComponent.getInstance().getChannelList(), filterResponse);
                    }
                    break;
                    case BOTH: {
                        filterResponse = new FilterResponse(IDUtil.getUniqueID(new Date().toString()));
                        IPartitionWindow<Set> partitionWindow = new PartitionWindow<Set>(new HashSet());
                        partitionWindow.setAnnotation(getUnMatchFilterSet().getValueIdentifier());
                        ISortedAccumulator unmatchAcc = unMatchFilterSet.getValueSet().getWindow();
                        for (Object matchEvent : unmatchAcc.getMap().values()) {
                            partitionWindow.getWindow().add(matchEvent);
                        }
                        filterResponse.setUnMatchWindow(partitionWindow);

                        IPartitionWindow<Set> ipw = new PartitionWindow<Set>(new HashSet());
                        ipw.setAnnotation(getMatchFilterSet().getValueIdentifier());
                        ISortedAccumulator accumulator = matchFilterSet.getValueSet().getWindow();
                        for (Object matchEvent : accumulator.getMap().values()) {
                            ipw.getWindow().add(matchEvent);
                        }
                        filterResponse.setMatchWindow(ipw);

                        ChannelHandlerContext context = getHandlerContext();
                        IServiceEvent event = new ServiceEvent();
                        event.setHandlerType(HandlerType.RESPONSE);
                        event.setServiceType(ServiceType.FILTER);
                        context.setAttachment(event);

                        byte[] response = XMLUtil.encode(filterResponse);
                        IEPSChannelWrite write = new EPSChannelWrite();
                        write.write(ChannelComponent.getInstance().getChannelList(), filterResponse);
                    }
                    break;
                    default: {
                    }
                }
                return filterResponse.getIdentifier();
            }
        });

    }

    public void setFilterInterest(MatchInterest filterInterest) {
        this.filterInterest = filterInterest;
    }

    public MatchInterest getFilterInterest() {
        return filterInterest;
    }

    public void onServiceCall(IFilterValueSet<ISortedAccumulator> value, boolean isMatchedFilterSet) {
        if (isMatchedFilterSet) {
            setMatchFilterSet(value);
        } else {
            setUnMatchFilterSet(value);
        }
        handleResponse();
    }

    public void setFilterServiceListener(IFilterServiceListener filterServiceListener) {
        this.filterServiceListener = filterServiceListener;
    }

    public void setUnFilterServiceListener(IUnFilterServiceListener unFilterServiceListener) {
        this.unFilterServiceListener = unFilterServiceListener;
    }

    public IFilterServiceListener getFilterServiceListener() {
        return filterServiceListener;
    }

    public IUnFilterServiceListener getUnFilterServiceListener() {
        return unFilterServiceListener;
    }

    public IFilterValueSet getMatchFilterSet() {
        return matchFilterSet;
    }

    public IFilterValueSet getUnMatchFilterSet() {
        return unMatchFilterSet;
    }

    public void setMatchFilterSet(IFilterValueSet matchFilterSet) {
        this.matchFilterSet = matchFilterSet;
    }

    public void setUnMatchFilterSet(IFilterValueSet unMatchFilterSet) {
        this.unMatchFilterSet = unMatchFilterSet;
    }

    public void setEPSFilter(IEPSFilter filter) {
        this.filter = filter;
    }

    public IEPSFilter getEPSFilter() {
        return this.filter;
    }
}
