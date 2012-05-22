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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.streameps.aggregation.IAggregatePolicy;
import org.streameps.aggregation.IAggregation;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.context.IContextPartition;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.util.IDUtil;
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
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
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
    private List<IFilterContext<IFilterValueSet>> filterContexts = null;
    private IDeciderContext<IMatchedEventSet> deciderContext;
    private AggregatorListener<IAggregation> aggregatorListener;
    private ISortedAccumulator sortedAccumulator;
    private IEPSFilter epsFilter;
    private FilterType filterType;
    private boolean aggregateEnabled = false;
    private boolean filterEnabled = false;
    private List<IAggregateContext> aggregateContexts;
    private IDeciderContextListener deciderContextListener;
    private ILogger logger = LoggerUtil.getLogger(EPSProducer.class);
    private boolean assertionEnabled = false;
    private IFilterChain filterChain;
    private boolean filterChainEnabled = true;
    private IKnowledgeBase knowledgeBase;

    public EPSProducer() {
        channelManager = new EventChannelManager();
        forwarder = new EPSForwarder();
        filterManager = new FilterManager();
        filterChain = new FilterChain();
        filterContexts = new ArrayList<IFilterContext<IFilterValueSet>>();
        filterChain = new FilterChain();
        //filterContext = new FilterContext<IFilterValueSet>();
        deciderContext = new DeciderContext<IMatchedEventSet>();
        aggregateContexts = new ArrayList<IAggregateContext>();
    }

    public boolean isAggregateEnabled() {
        return aggregateEnabled;
    }

    public void setAggregateEnabled(boolean aggregateEnabled) {
        this.aggregateEnabled = aggregateEnabled;
    }

    public IKnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    public void setKnowledgeBase(IKnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    public void setFilterContexts(List<IFilterContext<IFilterValueSet>> filterContext) {
        this.filterContexts = filterContext;
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

    public void setFilterChain(IFilterChain filterChain) {
        this.filterChain = filterChain;
    }

    public IFilterChain getFilterChain() {
        return filterChain;
    }

    public void setAggregatorListener(AggregatorListener<IAggregation> aggregatorListener) {
        this.aggregatorListener = aggregatorListener;
        setAggregateEnabled(true && (aggregateContexts != null ? true : false));
    }

    public void sendFilterContext() {
        if (filterContexts != null && isFilterEnabled()) {
            for (IFilterContext filterContext : getFilterContexts()) {
                produceFilterContext(filterContext);
                getKnowledgeBase().onFilterContextReceive(filterContext);
                getForwarder().onContextReceive(filterContext);
                logger.info("Sending the filter context with ID:=" + filterContext.getIdentifier());
            }
        }
    }

    public void onDeciderContextReceive(IDeciderContext deciderContext) {
        this.deciderContext = deciderContext;
        if (deciderContext == null) {
            return;
        }
        //send the threshold assertion decider context to the listener.
        if (isAssertionEnabled()) {
            produceDeciderContext(deciderContext);
        }
        if (deciderContext.getAnnotation().equalsIgnoreCase(IEPSDecider.PATTERN_MATCH_EVENTS)) {
            getForwarder().onPatternContextReceive(deciderContext);
        }
        sendFilterContext();
        if (isAggregateEnabled()) {
            for (IAggregateContext aggregateContext : getAggregateContexts()) {
                produceAggregate(aggregateContext);
                getForwarder().onAggregateContextReceive(aggregateContext);
            }
        }
        logger.info("The decider context with ID:=" + deciderContext.getIdentifier()
                + "is received and forwarding for further processing.");
    }

    public void produceAggregate(IAggregateContext aggregateContext) {
        aggregateContext.setIdentifier("producer:=" + IDUtil.getUniqueID(new Date().toString()));
        String attribute = aggregateContext.getAggregateProperty();
        EventAggregatorPE eape = new EventAggregatorPE(IDUtil.getUniqueID(new Date().toString()), attribute);
        List<IAggregation> aggregators = aggregateContext.getAggregatorList();
        for (IAggregation aggregator : aggregators) {
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
            if (getKnowledgeBase() != null) {
                getKnowledgeBase().onAggregateContextReceive(aggregateContext);
            }
            logger.info("Producing the aggregate result from context with ID:=" + aggregateContext.getIdentifier());
        }
    }

    public void addFilterContext(IFilterContext context) {
        getFilterContexts().add(context);
    }

    public void produceFilterContext(IFilterContext filterContext) {
        Map<FilterType, IFilterValueSet> chainFilterValueSet;
        IExprEvaluatorContext evaluatorContext = filterContext.getEvaluatorContext();
        this.filterType = filterContext.getEvaluatorContext().getFilterType();
        IFilterValueSetWrapper wrapper = new FilterValueSetWrapper(deciderContext.getDeciderValue(), filterType);
        epsFilter = filterContext.getEPSFilter();
        if (epsFilter.childrenSize() > 0) {
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
                case NULL:
                    break;
                default:
            }
            epsFilter.setExprEvaluatorContext(evaluatorContext);
            try {
                getFilterManager().processFilter(epsFilter);
                logger.info("Producing the filter context with ID:=" + filterContext.getIdentifier());
            } catch (FilterException ex) {
                logger.error(ex.getMessage());
            }
            filterContext.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
            filterContext.setFilteredValue((IFilterValueSet) getFilterManager().getFilterValueSet());
        } else if (isFilterChainEnabled()) {
            logger.info("Using the filter chain context.....");
            filterContext.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
            switch (filterType) {
                case COMPARISON:
                    IComparisonValueSet<ISortedAccumulator> comparisonValueSet = wrapper.getComparisonValueSet();
                    comparisonValueSet.setValueIdentifier(IDUtil.getUniqueID(new Date().toString()));
                    evaluatorContext.setEventContainer(comparisonValueSet);
                    filterContext.setEvaluatorContext(evaluatorContext);

                    epsFilter.setExprEvaluatorContext(evaluatorContext);
                    filterContext.setEPSFilter(epsFilter);

                    getFilterChain().addFilterContext(filterContext);
                    getFilterChain().executeVisitor();
                    chainFilterValueSet = getFilterChain().getFilterValueMap();
                    filterContext.setFilteredValue((IFilterValueSet) chainFilterValueSet.get(FilterType.COMPARISON));
                    break;
                case IN_NOT_VALUES:
                    IInNotValueSet<ISortedAccumulator> inNotValueSet = wrapper.getInNotValueSet();
                    inNotValueSet.setValueIdentifier(IDUtil.getUniqueID(new Date().toString()));
                    evaluatorContext.setEventContainer(inNotValueSet);
                    filterContext.setEvaluatorContext(evaluatorContext);

                    epsFilter.setExprEvaluatorContext(evaluatorContext);
                    filterContext.setEPSFilter(epsFilter);

                    getFilterChain().addFilterContext(filterContext);
                    getFilterChain().executeVisitor();
                    chainFilterValueSet = getFilterChain().getFilterValueMap();
                    filterContext.setFilteredValue((IFilterValueSet) chainFilterValueSet.get(FilterType.IN_NOT_VALUES));
                    break;
                case RANGE:
                    IRangeValueSet<ISortedAccumulator> rangeValueSet = wrapper.getRangeValueSet();
                    rangeValueSet.setValueIdentifier(IDUtil.getUniqueID(new Date().toString()));
                    evaluatorContext.setEventContainer(rangeValueSet);
                    filterContext.setEvaluatorContext(evaluatorContext);

                    epsFilter.setExprEvaluatorContext(evaluatorContext);
                    filterContext.setEPSFilter(epsFilter);

                    getFilterChain().addFilterContext(filterContext);
                    getFilterChain().executeVisitor();
                    chainFilterValueSet = getFilterChain().getFilterValueMap();
                    filterContext.setFilteredValue((IFilterValueSet) chainFilterValueSet.get(FilterType.RANGE));
                    break;
                case NULL:
                    break;
                default:
            }
        }
    }

    public List<IFilterContext<IFilterValueSet>> getFilterContexts() {
        return this.filterContexts;
    }

    public void setFilterChainEnabled(boolean filterChainEnabled) {
        this.filterChainEnabled = filterChainEnabled;
    }

    public boolean isFilterChainEnabled() {
        return filterChainEnabled;
    }

    public void produceDeciderContext(IDeciderContext deciderContext) {
        this.deciderContextListener.onDeciderReceive(deciderContext);
        logger.info("Sending the decider context with ID:=" + deciderContext.getIdentifier() + " to the listener.");
    }

    public void setAggregateContexts(List<IAggregateContext> aggregateContexts) {
        this.aggregateContexts = aggregateContexts;
    }

    public List<IAggregateContext> getAggregateContexts() {
        return this.aggregateContexts;
    }

    public void setDeciderContextListener(IDeciderContextListener contextListener) {
        this.deciderContextListener = contextListener;
        logger.debug("Setting the decider context listener");
    }

    public IDeciderContextListener getDeciderContextListener() {
        return this.deciderContextListener;
    }

    public void setFilterEnabled(boolean filterEnabled) {
        this.filterEnabled = filterEnabled;
    }

    public boolean isFilterEnabled() {
        return this.filterEnabled;
    }

    public void setAssertionEnabled(boolean assertionEnabled) {
        this.assertionEnabled = assertionEnabled;
    }

    public boolean isAssertionEnabled() {
        return assertionEnabled;
    }
}
