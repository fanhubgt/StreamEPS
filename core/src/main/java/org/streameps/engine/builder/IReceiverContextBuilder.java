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

package org.streameps.engine.builder;

import java.io.Serializable;
import java.util.List;
import org.streameps.context.ContextDimType;
import org.streameps.context.IContextInitiatorPolicy;
import org.streameps.context.IPredicateExpr;
import org.streameps.context.IPredicateTerm;
import org.streameps.context.PredicateOperator;
import org.streameps.context.TemporalOrder;
import org.streameps.context.segment.ISegmentParam;
import org.streameps.context.temporal.FrequencyRepeatType;
import org.streameps.context.temporal.IEventIntervalParam;
import org.streameps.context.temporal.IFixedIntervalContextParam;
import org.streameps.context.temporal.IInitiatorEventList;
import org.streameps.context.temporal.ISlidingEventIntervalParam;
import org.streameps.context.temporal.ISlidingFixedIntervalParam;
import org.streameps.context.temporal.ITerminatorEventList;
import org.streameps.engine.IReceiverContext;

/**
 *
 * @author  Frank Appiah
 */
public interface IReceiverContextBuilder extends Serializable{

    ReceiverContextBuilder addInitiatorContextEntry(String eventType, IPredicateExpr expr);

    ReceiverContextBuilder addInitiatorContextEntry(String eventType, IPredicateExpr expr, List<IPredicateTerm> predicateTerms);

    ReceiverContextBuilder addInitiatorContextEntry(String eventType, IPredicateExpr expr, IPredicateTerm predicateTerm);

    ReceiverContextBuilder addTerminatorContextEntry(String eventType, IPredicateExpr expr);

    ReceiverContextBuilder addTerminatorContextEntry(String eventType, IPredicateExpr expr, List<IPredicateTerm> predicateTerms);

    ReceiverContextBuilder addTerminatorContextEntry(String eventType, IPredicateExpr expr, IPredicateTerm predicateTerm);

    ReceiverContextBuilder buildContextDetail(String identifier, ContextDimType dimType, IContextInitiatorPolicy policy);

    ReceiverContextBuilder buildContextDetail(String identifier, ContextDimType dimType);

    <T> ReceiverContextBuilder buildContextParameter(String name, T contextParam);

    ReceiverContextBuilder buildEventIntervalParam(long timeOffSet, long eventCount, TemporalOrder temporalOrder, IInitiatorEventList eventList, ITerminatorEventList terminatorEventList);

    ReceiverContextBuilder buildFixedIntervalParam(Long intervalStart, Long intervalEnd, TemporalOrder order, FrequencyRepeatType repeatType);

    ReceiverContextBuilder buildIdentifier(String identifier);

    ReceiverContextBuilder buildPredicateTerm(String propertyName, PredicateOperator operator, Object propertyValue);

    ReceiverContextBuilder buildSegmentParamAttribute(String attribute);

    ReceiverContextBuilder buildSegmentParameter(String attribute, IPredicateExpr predicateExpr, boolean flagPredicate);

    ReceiverContextBuilder buildSegmentParameter(String attribute, IPredicateExpr predicateExpr);

    ReceiverContextBuilder buildSegmentParameter(IPredicateExpr predicateExpr, IPredicateTerm predicateTerm);

    ReceiverContextBuilder buildSegmentParameter(String attribute, boolean flagPredicate);

    ReceiverContextBuilder buildSlidingEventIntervalParam(long intervalSize, long eventCount, TemporalOrder temporalOrder, IInitiatorEventList eventList);

    ReceiverContextBuilder buildSlidingFixedIntervalParam(long intervalPeriod, long duration, long intervalSize, TemporalOrder temporalOrder);

    List<String> getAttributes();

    IReceiverContext getContext();

    IEventIntervalParam getEventIntervalParam();

    IFixedIntervalContextParam getFixedIntervalContextParam();

    IPredicateExpr getPredicateExpr();

    List<IPredicateExpr> getPredicateExprs();

    IPredicateTerm getPredicateTerm();

    List<IPredicateTerm> getPredicateTerms();

    ISegmentParam getSegmentParam();

    ISlidingEventIntervalParam getSlidingEventIntervalParam();

    ISlidingFixedIntervalParam getSlidingFixedIntervalParam();

    ITerminatorEventList getTerminatorEventList();

    IInitiatorEventList getiInitiatorEventList();

    void initializeExprs();

    void initializePredicates();

    void setAttributes(List<String> attributes);

    void setContext(IReceiverContext context);

    void setEventIntervalParam(IEventIntervalParam eventIntervalParam);

    void setFixedIntervalContextParam(IFixedIntervalContextParam fixedIntervalContextParam);

    void setFlagPredicate(boolean flagPredicate);

    void setPredicateExpr(IPredicateExpr predicateExpr);

    void setPredicateExprs(List<IPredicateExpr> predicateExprs);

    void setPredicateTerm(IPredicateTerm predicateTerm);

    void setPredicateTerms(List<IPredicateTerm> predicateTerms);

    void setSegmentParam(ISegmentParam segmentParam);

    void setSlidingEventIntervalParam(ISlidingEventIntervalParam slidingEventIntervalParam);

    void setSlidingFixedIntervalParam(ISlidingFixedIntervalParam slidingFixedIntervalParam);

    void setTerminatorEventList(ITerminatorEventList terminatorEventList);

    void setiInitiatorEventList(IInitiatorEventList iInitiatorEventList);

}
