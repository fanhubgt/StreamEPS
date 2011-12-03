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

import java.util.List;
import org.streameps.context.ContextDetail;
import org.streameps.context.ContextDimType;
import org.streameps.context.ContextParam;
import org.streameps.context.IContextDetail;
import org.streameps.context.IContextInitiatorPolicy;
import org.streameps.context.IContextParam;
import org.streameps.context.IPredicateExpr;
import org.streameps.context.IPredicateTerm;
import org.streameps.context.PredicateOperator;
import org.streameps.context.PredicateTerm;
import org.streameps.context.segment.ISegmentParam;
import org.streameps.context.segment.SegmentParam;
import org.streameps.engine.IReceiverContext;

/**
 *
 * @author Frank Appiah
 */
public class ReceiverContextBuilder {

    private IReceiverContext context;
    private ISegmentParam segmentParam;
    private List<String> attributes;
    private List<IPredicateExpr> predicateExprs;

    public ReceiverContextBuilder(IReceiverContext contextRef) {
        this.context = contextRef;
        segmentParam = new SegmentParam();
    }

    public ReceiverContextBuilder(IReceiverContext context, ISegmentParam segmentParam) {
        this.context = context;
        this.segmentParam = segmentParam;
    }

    public ReceiverContextBuilder buildIdentifier(String identifier) {
        context.setIdentifier(identifier);
        return this;
    }

    public <T> ReceiverContextBuilder buildContextParameter(String name, T contextParam) {
        IContextParam<T> param = new ContextParam<T>();
        param.setName(name);
        param.setContextParameter(contextParam);
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

    public ReceiverContextBuilder buildPredicateTerm(String propertyName, PredicateOperator operator, Object propertyValue) {
        IPredicateTerm predicateTerm = new PredicateTerm(propertyName, operator, propertyValue);
        context.setPredicateTerm(predicateTerm);
        return this;
    }

    public ReceiverContextBuilder buildSegmentParameter(String attribute, IPredicateExpr predicateExpr, boolean flagPredicate) {
        segmentParam.getAttributes().add(attribute);
        segmentParam.getPartitionExpr().add(predicateExpr);
        segmentParam.setPredicateEnabled(flagPredicate);
        return this;
    }

    public ReceiverContextBuilder buildSegmentParamAttribute(String attribute, boolean flagPredicate) {
        segmentParam.getAttributes().add(attribute);
        segmentParam.setPredicateEnabled(flagPredicate);
        return this;
    }

    public ReceiverContextBuilder buildSegmentParamAttribute(String attribute) {
        segmentParam.getAttributes().add(attribute);
        segmentParam.setPredicateEnabled(false);
        return this;
    }

    public IReceiverContext getContext() {
        return context;
    }

    public ISegmentParam getSegmentParam() {
        return segmentParam;
    }
}
