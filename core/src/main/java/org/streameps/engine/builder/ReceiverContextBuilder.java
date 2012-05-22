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
package org.streameps.engine.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.streameps.context.ContextDetail;
import org.streameps.context.ContextDimType;
import org.streameps.context.ContextEntry;
import org.streameps.context.ContextParam;
import org.streameps.context.IContextDetail;
import org.streameps.context.IContextEntry;
import org.streameps.context.IContextInitiatorPolicy;
import org.streameps.context.IContextParam;
import org.streameps.context.IPredicateExpr;
import org.streameps.context.IPredicateTerm;
import org.streameps.context.PredicateOperator;
import org.streameps.context.PredicateTerm;
import org.streameps.context.TemporalOrder;
import org.streameps.context.segment.ISegmentParam;
import org.streameps.context.segment.SegmentParam;
import org.streameps.context.temporal.EventIntervalParam;
import org.streameps.context.temporal.FixedIntervalContextParam;
import org.streameps.context.temporal.FrequencyRepeatType;
import org.streameps.context.temporal.IEventIntervalParam;
import org.streameps.context.temporal.IFixedIntervalContextParam;
import org.streameps.context.temporal.IInitiatorEventList;
import org.streameps.context.temporal.ISlidingEventIntervalParam;
import org.streameps.context.temporal.ISlidingFixedIntervalParam;
import org.streameps.context.temporal.ITerminatorEventList;
import org.streameps.context.temporal.InitiatorEventList;
import org.streameps.context.temporal.SlidingEventIntervalParam;
import org.streameps.context.temporal.SlidingFixedIntervalParam;
import org.streameps.context.temporal.TerminatorEventList;
import org.streameps.core.util.IDUtil;
import org.streameps.engine.IReceiverContext;
import org.streameps.engine.ReceiverContext;

/**
 *
 * @author Frank Appiah
 */
public class ReceiverContextBuilder implements IReceiverContextBuilder {

    private IReceiverContext context;
    private ISegmentParam segmentParam;
    private IFixedIntervalContextParam fixedIntervalContextParam;
    private ISlidingFixedIntervalParam slidingFixedIntervalParam;
    private ISlidingEventIntervalParam slidingEventIntervalParam;
    private IEventIntervalParam eventIntervalParam;
    private List<String> attributes;
    private IInitiatorEventList iInitiatorEventList;
    private ITerminatorEventList terminatorEventList;
    private List<IPredicateExpr> predicateExprs;
    private IPredicateExpr predicateExpr;
    private IPredicateTerm predicateTerm;
    private List<IPredicateTerm> predicateTerms;
    private boolean flagPredicate = false;

    public ReceiverContextBuilder() {
        context = new ReceiverContext();
        iInitiatorEventList = new InitiatorEventList();
        predicateTerms = new ArrayList<IPredicateTerm>();
        predicateExprs = new ArrayList<IPredicateExpr>();
    }

    public ReceiverContextBuilder(IReceiverContext contextRef) {
        this.context = contextRef;
        segmentParam = new SegmentParam();
        iInitiatorEventList = new InitiatorEventList();
        predicateTerms = new ArrayList<IPredicateTerm>();
        predicateExprs = new ArrayList<IPredicateExpr>();
    }

    public ReceiverContextBuilder(ISegmentParam segmentParam) {
        this.context = new ReceiverContext();
        this.segmentParam = segmentParam;
        iInitiatorEventList = new InitiatorEventList();
        predicateTerms = new ArrayList<IPredicateTerm>();
        predicateExprs = new ArrayList<IPredicateExpr>();
    }

    public ReceiverContextBuilder(ISlidingFixedIntervalParam slidingFixedIntervalParam) {
        this.slidingFixedIntervalParam = slidingFixedIntervalParam;
        this.segmentParam = new SegmentParam();
        this.context = new ReceiverContext();
        iInitiatorEventList = new InitiatorEventList();
        predicateTerms = new ArrayList<IPredicateTerm>();
        predicateExprs = new ArrayList<IPredicateExpr>();
    }

    public ReceiverContextBuilder(IEventIntervalParam eventIntervalParam) {
        this.eventIntervalParam = eventIntervalParam;
        this.context = new ReceiverContext();
        iInitiatorEventList = new InitiatorEventList();
        terminatorEventList = new TerminatorEventList();
        predicateTerms = new ArrayList<IPredicateTerm>();
        predicateExprs = new ArrayList<IPredicateExpr>();
    }

    public ReceiverContextBuilder(IFixedIntervalContextParam fixedIntervalContextParam) {
        this.fixedIntervalContextParam = fixedIntervalContextParam;
        this.context = new ReceiverContext();
        this.segmentParam = new SegmentParam();
        iInitiatorEventList = new InitiatorEventList();
        predicateTerms = new ArrayList<IPredicateTerm>();
        predicateExprs = new ArrayList<IPredicateExpr>();
    }

    public ReceiverContextBuilder(IReceiverContext context, ISegmentParam segmentParam) {
        this.context = context;
        this.segmentParam = segmentParam;
        iInitiatorEventList = new InitiatorEventList();
        predicateTerms = new ArrayList<IPredicateTerm>();
        predicateExprs = new ArrayList<IPredicateExpr>();
    }

    public ReceiverContextBuilder(SlidingEventIntervalParam slidingEventIntervalParam) {
        this.slidingEventIntervalParam = slidingEventIntervalParam;
        this.context = new ReceiverContext();
        this.segmentParam = new SegmentParam();
        iInitiatorEventList = new InitiatorEventList();
        predicateTerms = new ArrayList<IPredicateTerm>();
        predicateExprs = new ArrayList<IPredicateExpr>();
    }

    public ReceiverContextBuilder buildIdentifier(String identifier) {
        context.setIdentifier(identifier);
        return this;
    }

    public <T> ReceiverContextBuilder buildContextParameter(String name, T contextParam) {
        IContextParam<T> param = new ContextParam<T>();
        param.setName(name);
        param.setContextParameter(contextParam);
        context.setAttribute(name);
        context.setPredicateTerm(predicateTerm);
        context.setContextParam(param);
        return this;
    }

    public ReceiverContextBuilder buildContextDetail(String identifier, ContextDimType dimType, IContextInitiatorPolicy policy) {
        IContextDetail contextDetail = new ContextDetail(identifier, dimType, policy);
        context.setContextDetail(contextDetail);
        return this;
    }

    public ReceiverContextBuilder buildContextDetail(String identifier, ContextDimType dimType) {
        IContextDetail contextDetail = new ContextDetail(identifier, dimType);
        context.setContextDetail(contextDetail);
        return this;
    }

     public ReceiverContextBuilder buildContextDetail(String identifier,String dimType) {
        IContextDetail contextDetail = new ContextDetail(identifier, ContextDimType.valueOf(dimType));
        context.setContextDetail(contextDetail);
        return this;
    }

    public ReceiverContextBuilder buildSlidingFixedIntervalParam(long intervalPeriod, long duration, long intervalSize, TemporalOrder temporalOrder) {
        slidingFixedIntervalParam = new SlidingFixedIntervalParam(intervalPeriod, duration, intervalSize, temporalOrder);
        return this;
    }

    public ReceiverContextBuilder buildSlidingEventIntervalParam(long intervalSize, long eventCount, TemporalOrder temporalOrder, IInitiatorEventList eventList) {
        slidingEventIntervalParam = new SlidingEventIntervalParam();
        slidingEventIntervalParam.setEventList(eventList);
        slidingEventIntervalParam.setEventPeriod(eventCount);
        slidingEventIntervalParam.setIntervalSize(intervalSize);
        slidingEventIntervalParam.setOrdering(temporalOrder);
        return this;
    }

    public ReceiverContextBuilder buildEventIntervalParam(long timeOffSet, long eventCount, TemporalOrder temporalOrder, IInitiatorEventList eventList, ITerminatorEventList terminatorEventList) {
        eventIntervalParam = new EventIntervalParam();
        eventIntervalParam.setInitiatorList(eventList);
        eventIntervalParam.setExpirationEventCount(eventCount);
        eventIntervalParam.setExpirationTimeOffset(timeOffSet);
        eventIntervalParam.setTerminatorList(terminatorEventList);
        eventIntervalParam.setTemporalOrder(temporalOrder);
        return this;
    }

    public ReceiverContextBuilder addInitiatorContextEntry(String eventType, IPredicateExpr expr) {
        predicateExpr = expr;
        if (predicateTerms.size() < 0) {
            throw new IllegalArgumentException("Predicate terms are not properly set.");
        }
        IContextEntry entry = new ContextEntry(eventType, predicateExpr, predicateTerms);
        iInitiatorEventList.addContextEntry(entry);
        return this;
    }

    public ReceiverContextBuilder addInitiatorContextEntry(String eventType, IPredicateExpr expr, List<IPredicateTerm> predicateTerms) {
        this.predicateExpr = expr;
        this.predicateTerms = predicateTerms;
        IContextEntry entry = new ContextEntry(eventType, predicateExpr, predicateTerms);
        iInitiatorEventList.addContextEntry(entry);
        return this;
    }

    public ReceiverContextBuilder addInitiatorContextEntry(String eventType, IPredicateExpr expr, IPredicateTerm predicateTerm) {
        this.predicateExpr = expr;
        this.predicateTerms.add(predicateTerm);
        IContextEntry entry = new ContextEntry(eventType, predicateExpr, predicateTerms);
        iInitiatorEventList.addContextEntry(entry);
        return this;
    }

    public ReceiverContextBuilder addTerminatorContextEntry(String eventType, IPredicateExpr expr) {
        predicateExpr = expr;
        if (predicateTerms.size() < 0) {
            throw new IllegalArgumentException("Predicate terms are not properly set.");
        }
        IContextEntry entry = new ContextEntry(eventType, predicateExpr, predicateTerms);
        terminatorEventList.addContextEntry(entry);
        return this;
    }

    public void initializePredicates() {
        this.predicateTerms = new ArrayList<IPredicateTerm>();
    }

    public void initializeExprs() {
        this.predicateExprs = new ArrayList<IPredicateExpr>();
    }

    public ReceiverContextBuilder addTerminatorContextEntry(String eventType, IPredicateExpr expr, List<IPredicateTerm> predicateTerms) {
        this.predicateExpr = expr;
        this.predicateTerms = predicateTerms;
        if (predicateTerms.size() < 0) {
            throw new IllegalArgumentException("Predicate terms are not properly set.");
        }
        IContextEntry entry = new ContextEntry(eventType, predicateExpr, predicateTerms);
        terminatorEventList.addContextEntry(entry);
        return this;
    }

    public ReceiverContextBuilder addTerminatorContextEntry(String eventType, IPredicateExpr expr, IPredicateTerm predicateTerm) {
        this.predicateExpr = expr;
        this.predicateTerms.add(predicateTerm);
        IContextEntry entry = new ContextEntry(eventType, predicateExpr, predicateTerms);
        terminatorEventList.addContextEntry(entry);
        return this;
    }

    public ReceiverContextBuilder buildFixedIntervalParam(Long intervalStart, Long intervalEnd, TemporalOrder order, FrequencyRepeatType repeatType) {
        this.fixedIntervalContextParam = new FixedIntervalContextParam(intervalStart, intervalEnd, order, repeatType);
        return this;
    }

    public ReceiverContextBuilder buildPredicateTerm(String propertyName, PredicateOperator operator, Object propertyValue) {
        predicateTerm = new PredicateTerm(propertyName, operator, propertyValue);
        predicateTerms.add(predicateTerm);
        return this;
    }

    public ReceiverContextBuilder buildSegmentParameter(String attribute, IPredicateExpr predicateExpr, boolean flagPredicate) {
        segmentParam.getAttributes().add(attribute);
        segmentParam.getPartitionExprs().add(predicateExpr);
        segmentParam.setPredicateEnabled(this.flagPredicate |= flagPredicate);
        return this;
    }

    public ReceiverContextBuilder buildSegmentParameter(String attribute, IPredicateExpr predicateExpr) {
        segmentParam.getAttributes().add(attribute);
        segmentParam.getPartitionExprs().add(predicateExpr);
        segmentParam.setPredicateEnabled(this.flagPredicate |= true);
        return this;
    }

    public ReceiverContextBuilder buildSegmentParameter(IPredicateExpr predicateExpr, IPredicateTerm predicateTerm) {
        segmentParam.getPartitionExprs().add(predicateExpr);
        segmentParam.setPredicateEnabled(this.flagPredicate |= true);
        this.predicateTerm = predicateTerm;
        return this;
    }

    public ReceiverContextBuilder buildSegmentParameter(String attribute, boolean flagPredicate) {
        segmentParam.getAttributes().add(attribute);
        segmentParam.setPredicateEnabled(this.flagPredicate |= flagPredicate);
        return this;
    }

    public ReceiverContextBuilder buildSegmentParamAttribute(String attribute) {
        segmentParam.getAttributes().add(attribute);
        segmentParam.setPredicateEnabled(this.flagPredicate |= false);
        return this;
    }

    public IReceiverContext getContext() {
        context.setPredicateTerm(predicateTerm);
        return context;
    }

    public ISegmentParam getSegmentParam() {
        if (predicateTerm != null) {
            segmentParam.setPredicateEnabled(this.flagPredicate |= true);
        }
        return segmentParam;
    }

    public ISlidingFixedIntervalParam getSlidingFixedIntervalParam() {
        return slidingFixedIntervalParam;
    }

    public ISlidingEventIntervalParam getSlidingEventIntervalParam() {
        slidingEventIntervalParam.setEventList(iInitiatorEventList);
        return slidingEventIntervalParam;
    }

    public IEventIntervalParam getEventIntervalParam() {
        eventIntervalParam.setInitiatorList(iInitiatorEventList);
        eventIntervalParam.setTerminatorList(terminatorEventList);
        return eventIntervalParam;
    }

    public IPredicateTerm getPredicateTerm() {
        return predicateTerm;
    }

    public List<IPredicateExpr> getPredicateExprs() {
        return predicateExprs;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public List<IPredicateTerm> getPredicateTerms() {
        return predicateTerms;
    }

    public IPredicateExpr getPredicateExpr() {
        return predicateExpr;
    }

    public IInitiatorEventList getiInitiatorEventList() {
        return iInitiatorEventList;
    }

    public ITerminatorEventList getTerminatorEventList() {
        return terminatorEventList;
    }

    public IFixedIntervalContextParam getFixedIntervalContextParam() {
        return fixedIntervalContextParam;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public void setContext(IReceiverContext context) {
        this.context = context;
        this.context.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
    }

    public void setEventIntervalParam(IEventIntervalParam eventIntervalParam) {
        this.eventIntervalParam = eventIntervalParam;
    }

    public void setFixedIntervalContextParam(IFixedIntervalContextParam fixedIntervalContextParam) {
        this.fixedIntervalContextParam = fixedIntervalContextParam;
    }

    public void setFlagPredicate(boolean flagPredicate) {
        this.flagPredicate = flagPredicate;
    }

    public void setPredicateExpr(IPredicateExpr predicateExpr) {
        this.predicateExpr = predicateExpr;
    }

    public void setPredicateExprs(List<IPredicateExpr> predicateExprs) {
        this.predicateExprs = predicateExprs;
    }

    public void setPredicateTerm(IPredicateTerm predicateTerm) {
        this.predicateTerm = predicateTerm;
    }

    public void setPredicateTerms(List<IPredicateTerm> predicateTerms) {
        this.predicateTerms = predicateTerms;
    }

    public void setSegmentParam(ISegmentParam segmentParam) {
        this.segmentParam = segmentParam;
    }

    public void setSlidingEventIntervalParam(ISlidingEventIntervalParam slidingEventIntervalParam) {
        this.slidingEventIntervalParam = slidingEventIntervalParam;
    }

    public void setSlidingFixedIntervalParam(ISlidingFixedIntervalParam slidingFixedIntervalParam) {
        this.slidingFixedIntervalParam = slidingFixedIntervalParam;
    }

    public void setTerminatorEventList(ITerminatorEventList terminatorEventList) {
        this.terminatorEventList = terminatorEventList;
    }

    public void setiInitiatorEventList(IInitiatorEventList iInitiatorEventList) {
        this.iInitiatorEventList = iInitiatorEventList;
    }

    
}
