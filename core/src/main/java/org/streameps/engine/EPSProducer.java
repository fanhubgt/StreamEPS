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
package org.streameps.engine;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.streameps.aggregation.IAggregatePolicy;
import org.streameps.aggregation.IAggregation;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.context.IContextPartition;
import org.streameps.core.IMatchedEventSet;
import org.streameps.util.IDUtil;
import org.streameps.decider.IDeciderContextListener;
import org.streameps.epn.channel.EventChannelManager;
import org.streameps.epn.channel.IEventChannelManager;
import org.streameps.exception.FilterException;
import org.streameps.filter.FilterManager;
import org.streameps.filter.FilterType;
import org.streameps.filter.FilterValueSetWrapper;
import org.streameps.filter.IComparisonValueSet;
import org.streameps.filter.IEPSFilter;
import org.streameps.filter.IExprEvaluatorContext;
import org.streameps.filter.IFilterManager;
import org.streameps.filter.IFilterValueSet;
import org.streameps.filter.IFilterValueSetWrapper;
import org.streameps.filter.IInNotValueSet;
import org.streameps.filter.IRangeValueSet;
import org.streameps.processor.AggregatorListener;
import org.streameps.processor.EventAggregatorPE;

/**
 *
 * @author Frank Appiah
 */
public class EPSProducer<C extends IContextPartition>
        implements IEPSProducer<C> {

    private IEPSForwarder forwarder;
    private IEventChannelManager channelManager;
    private IFilterManager filterManager;
    private IFilterContext<IFilterValueSet> filterContext = null;
    private IDeciderContext<IMatchedEventSet> deciderContext;
    private AggregatorListener<IAggregation> aggregatorListener;
    private ISortedAccumulator sortedAccumulator;
    private IEPSFilter epsFilter;
    private FilterType filterType;
    private boolean aggregateEnabled = false;
    private IAggregateContext aggregateContext;
    private IDeciderContextListener deciderContextListener;

    public EPSProducer() {
        channelManager = new EventChannelManager();
        forwarder = new EPSForwarder();
        filterManager = new FilterManager();
        filterContext = new FilterContext<IFilterValueSet>();
        deciderContext = new DeciderContext<IMatchedEventSet>();
    }

    public boolean isAggregateEnabled() {
        return aggregateEnabled;
    }

    public void setAggregateEnabled(boolean aggregateEnabled) {
        this.aggregateEnabled = aggregateEnabled;
    }

    public void setFilterContext(IFilterContext filterContext) {
        this.filterContext = filterContext;
    }

    public void setForwarder(IEPSForwarder forwarder) {
        this.forwarder = forwarder;
    }

    public IEPSForwarder getForwarder() {
        return this.forwarder;
    }

    public void setChannelManager(IEventChannelManager channel) {
        this.channelManager = channel;
    }

    public IEventChannelManager getChannelManager() {
        return this.channelManager;
    }

    public void setFilterManager(IFilterManager filterManager) {
        this.filterManager = filterManager;
    }

    public IFilterManager getFilterManager() {
        return this.filterManager;
    }

    public void setAggregatorListener(AggregatorListener<IAggregation> aggregatorListener) {
        this.aggregatorListener = aggregatorListener;
    }

    public void sendFilterContext() {
        if (filterContext != null) {
            produceFilterContext(filterContext);
            getForwarder().onContextReceive(filterContext);
        }
    }

    public void onDeciderContextReceive(IDeciderContext deciderContext) {
        this.deciderContext = deciderContext;
        if (deciderContext == null) {
            return;
        }
        produceDeciderContext(deciderContext);
        sendFilterContext();
        if (isAggregateEnabled()) {
            produceAggregate(this.aggregateContext);
        }
    }

    public void produceAggregate(IAggregateContext aggregateContext) {
        this.aggregateContext = aggregateContext;
        this.aggregateContext.setIdentifier("producer:=" + IDUtil.getUniqueID(new Date().toString()));
        String attribute = aggregateContext.getAggregateProperty();
        EventAggregatorPE eape = new EventAggregatorPE(IDUtil.getUniqueID(new Date().toString()), attribute);
        IAggregation aggregator = aggregateContext.getAggregator();
        eape.setAggregation(aggregator);

        if (this.aggregatorListener != null) {
            this.aggregatorListener.setAggregateContext(aggregateContext);
            eape.setAggregatorListener(this.aggregatorListener);
        }
        IAggregatePolicy aggregatePolicy = aggregateContext.getPolicy();
        if (aggregatePolicy != null) {
            eape.setAggregatePolicy(aggregatePolicy);
        }
        for (Object event : deciderContext.getDeciderValue()) {
            eape.process(event);
        }
        eape.output();

    }

    public void produceFilterContext(IFilterContext context) {
        IExprEvaluatorContext evaluatorContext = context.getEvaluatorContext();
        this.filterContext = context;
        this.filterType = context.getEvaluatorContext().getFilterType();
        IFilterValueSetWrapper wrapper = new FilterValueSetWrapper(deciderContext.getDeciderValue(), filterType);
        epsFilter = context.getEPSFilter();
        switch (filterType) {
            case COMPARISON:
                IComparisonValueSet<ISortedAccumulator> comparisonValueSet = wrapper.getComparisonValueSet();
                comparisonValueSet.setValueIdentifier(IDUtil.getUniqueID(new Date().toString()));
                evaluatorContext.setEventContainer(comparisonValueSet);
                break;
            case IN_NOT_VALUES:
                IInNotValueSet<ISortedAccumulator> inNotValueSet = wrapper.getInNotValueSet();
                inNotValueSet.setValueIdentifier(IDUtil.getUniqueID(new Date().toString()));
                evaluatorContext.setEventContainer(inNotValueSet);
                break;
            case RANGE:
                IRangeValueSet<ISortedAccumulator> rangeValueSet = wrapper.getRangeValueSet();
                rangeValueSet.setValueIdentifier(IDUtil.getUniqueID(new Date().toString()));
                evaluatorContext.setEventContainer(rangeValueSet);
                break;
            default:
        }
        epsFilter.setExprEvaluatorContext(evaluatorContext);
        try {
            getFilterManager().processFilter(epsFilter);
        } catch (FilterException ex) {
            Logger.getLogger(EPSProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        filterContext.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        filterContext.setFilteredValue((IFilterValueSet) getFilterManager().getFilterValueSet());
    }

    public void produceDeciderContext(IDeciderContext deciderContext) {
        this.deciderContextListener.onDeciderReceive(deciderContext);
    }

    public IFilterContext getFilterContext() {
        return this.filterContext;
    }

    public void setAggregateContext(IAggregateContext aggregateContext) {
        this.aggregateContext = aggregateContext;
    }

    public IAggregateContext getAggregateContext() {
        return this.aggregateContext;
    }

    public void setDeciderContextListener(IDeciderContextListener contextListener) {
        this.deciderContextListener = contextListener;
    }

    public IDeciderContextListener getDeciderContextListener() {
        return this.deciderContextListener;
    }
}
