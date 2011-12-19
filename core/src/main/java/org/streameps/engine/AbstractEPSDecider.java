/*
 * ====================================================================
 *  StreamEPS Platform
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
 *  =============================================================================
 */
package org.streameps.engine;

import org.streameps.engine.builder.StoreContextBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.streameps.aggregation.IAggregatePolicy;
import org.streameps.aggregation.IAggregation;
import org.streameps.aggregation.collection.AssertionValuePair;
import org.streameps.context.IContextPartition;
import org.streameps.core.IMatchedEventSet;
import org.streameps.util.IDUtil;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.AggregatorListener;
import org.streameps.processor.EventAggregatorPE;
import org.streameps.processor.pattern.IBasePattern;

/**
 * Abstract implementation of the EPS decider interface. The specific functionality of
 * the <b>decide</b> method is left to be implemented by the developer.
 * 
 * @author Frank Appiah
 * @version 0.3.3
 */
public abstract class AbstractEPSDecider<C extends IContextPartition> implements IEPSDecider<C> {

    private List<IHistoryStore> historyStores = new ArrayList<IHistoryStore>();
    private IEPSProducer producer;
    private IDeciderPair<C> deciderPair;
    private IPatternChain<IBasePattern> patternChain;
    private IKnowledgeBase knowledgeBase;
    private IDeciderContext<IMatchedEventSet> deciderContext = null;
    private IAggregateContext aggregateContext;
    private boolean aggregateEnabled = false;
    private AggregatorListener aggregatorListener = null;
    private IStoreContext<IMatchedEventSet> storeContext;

    public AbstractEPSDecider() {
    }

    public void persistStoreContext(IStoreContext<IMatchedEventSet> storeContext) {
        this.storeContext = storeContext;
        StoreContextBuilder builder = new StoreContextBuilder(historyStores, storeContext);
        builder.buildStore();
    }

    public List<IHistoryStore> getHistoryStores() {
        return this.historyStores;
    }

    public void setAggregateContext(IAggregateContext aggregateContext) {
        this.aggregateContext=aggregateContext;
    }

    public void setAggregateEnabled(boolean enabledAggregate) {
        this.aggregateEnabled = enabledAggregate;
    }

    public IAggregateContext getAggregateContext() {
        return this.aggregateContext;
    }

    public AggregatorListener getAggregateListener() {
       return this.aggregatorListener;
    }

    public boolean isAggregateEnabled() {
        return aggregateEnabled;
    }

    public void sendDeciderContext(IDeciderContext context) {
        this.deciderContext = context;
        if (aggregateEnabled && detectAggregate(aggregateContext)) {
            this.producer.onDeciderContextReceive(context);
            this.knowledgeBase.onDeciderContextReceive(context);
        } else {
            this.producer.onDeciderContextReceive(context);
            this.knowledgeBase.onDeciderContextReceive(context);
        }
    }

    public void setAggregateListener(AggregatorListener aggregatorListener) {
        this.aggregatorListener = aggregatorListener;
    }

    public void setHistoryStores(List<IHistoryStore> historyStore) {
        this.historyStores = historyStore;
    }

    public void setProducer(IEPSProducer producer) {
        this.producer = producer;
    }

    public IEPSProducer getProducer() {
        if (producer == null) {
            this.producer = new EPSProducer();
        }
        return producer;
    }

    public void setContextPartition(C contextPartition) {
        if (deciderPair == null) {
            deciderPair = new DeciderPair();
        }
        this.deciderPair.setContextPartition(contextPartition);
        setDeciderPair(deciderPair);
    }

    public void setPatternChain(IPatternChain<IBasePattern> pattern) {
        if (deciderPair == null) {
            deciderPair = new DeciderPair();
        }
        this.deciderPair.setPatternDetector(pattern);
        setDeciderPair(deciderPair);
        this.patternChain = pattern;
    }

    public IPatternChain<IBasePattern> getPatternChain() {
        return patternChain;
    }

    public IDeciderPair<C> getDeciderPair() {
        return this.deciderPair;
    }

    public void setDeciderPair(IDeciderPair<C> deciderPair) {
        this.deciderPair = deciderPair;
    }

    /**
     * It provides an easy method to add a new history store to the list of
     * history stores.
     * @param historyStore An instance of history store to add to the list.
     */
    public void addHistoryStore(IHistoryStore historyStore) {
        historyStores.add(historyStore);
    }

    /**
     * It removes the history store from the list.
     * @param historyStore An instance of history store to remove from the list.
     */
    public void removeHistoryStore(IHistoryStore historyStore) {
        historyStores.remove(historyStore);
    }

    public void setKnowledgeBase(IKnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    public IKnowledgeBase getKnowledgeBase() {
        return this.knowledgeBase;
    }

    public boolean detectAggregate(IAggregateContext aggregateContext) {
        if (deciderContext == null) {
            return false;
        }
        this.aggregateContext.setIdentifier("Decider:="+IDUtil.getUniqueID(new Date().toString()));
        this.aggregateContext = aggregateContext;
        String attribute = aggregateContext.getAggregateProperty();
        EventAggregatorPE eape = new EventAggregatorPE(IDUtil.getUniqueID(new Date().toString()), attribute);
        IAggregation aggregator = aggregateContext.getAggregator();
        if (aggregatorListener != null) {
            eape.setAggregatorListener(aggregatorListener);
        }
        IAggregatePolicy aggregatePolicy= aggregateContext.getPolicy();
        if(aggregatePolicy!=null)eape.setAggregatePolicy(aggregatePolicy);
        eape.setAggregation(aggregator);
        for (Object event : deciderContext.getDeciderValue()) {
            eape.process(event);
        }
        eape.output();

        ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(aggregateContext.getAssertionType());
        double threshold = (Double) aggregateContext.getThresholdValue();
        double resultValue = (Double) aggregator.getValue();
        return assertion.assertEvent(new AssertionValuePair(threshold, resultValue));
    }
    
}
