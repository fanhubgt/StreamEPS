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
import java.util.List;
import org.streameps.context.PredicateOperator;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.processor.pattern.IPatternParameter;
import org.streameps.processor.pattern.PatternParameter;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;
import org.streameps.processor.pattern.policy.PatternPolicy;

/**
 *
 * @author Frank Appiah
 */
public class PatternBuilder implements IPatternBuilder {

    private IBasePattern basePattern;
    private List<IBasePattern> basePatterns;
    private IPatternParameter parameter;
    private PatternPolicy patternPolicy;

    public PatternBuilder() {
        basePatterns = new ArrayList<IBasePattern>();
    }

    public PatternBuilder(IBasePattern basePattern) {
        this.basePattern = basePattern;
        basePatterns = new ArrayList<IBasePattern>();
    }

    public PatternBuilder(IBasePattern basePattern, PatternPolicy patternPolicy) {
        this.basePattern = basePattern;
        this.patternPolicy = patternPolicy;
        basePatterns = new ArrayList<IBasePattern>();
    }

    public PatternBuilder buildParameter(String property, Object paramValue, PredicateOperator operator) {
        parameter = new PatternParameter(property, operator.getSymbol(), paramValue);
        basePattern.getParameters().add(parameter);
        return this;
    }

    public PatternBuilder buildParameter(String property) {
        parameter = new PatternParameter(property, null, null);
        basePattern.getParameters().add(parameter);
        return this;
    }

    public PatternBuilder buildParameter(String property, Object paramValue) {
        parameter = new PatternParameter(property, null, paramValue);
        basePattern.getParameters().add(parameter);
        return this;
    }

    public PatternBuilder buildPatternMatchListener(IPatternMatchListener listener) {
        basePattern.getMatchListeners().add(listener);
        return this;
    }

    public PatternBuilder buildPatternUnMatchListener(IPatternUnMatchListener listener) {
        basePattern.getUnMatchListeners().add(listener);
        return this;
    }

    public PatternBuilder buildPatternMatchListener(IPatternMatchListener listener, IPatternUnMatchListener unlistener) {
        basePattern.getUnMatchListeners().add(unlistener);
        basePattern.getMatchListeners().add(listener);
        return this;
    }

    public PatternBuilder buildPatternPolicy(PatternPolicy policy) {
        basePattern.getPatternPolicies().add(policy);
        return this;
    }

    public IBasePattern getBasePattern() {
        if (!getBasePatterns().contains(basePattern)) {
            getBasePatterns().add(basePattern);
        }
        return basePattern;
    }

    public IPatternParameter getParameter() {
        return parameter;
    }

    public void setBasePattern(IBasePattern basePattern) {
        this.basePattern = basePattern;
    }

    public void setBasePatterns(List<IBasePattern> basePatterns) {
        this.basePatterns = basePatterns;
    }

    public List<IBasePattern> getBasePatterns() {
        return basePatterns;
    }

    public void setParameter(IPatternParameter parameter) {
        this.parameter = parameter;
    }

    public PatternPolicy getPatternPolicy() {
        return patternPolicy;
    }

    public void setPatternPolicy(PatternPolicy patternPolicy) {
        this.patternPolicy = patternPolicy;
    }
    
}
