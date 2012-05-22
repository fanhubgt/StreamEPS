/*
 * ====================================================================
 *  StreamEPS Platform
 *  (C) Copyright 2011
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
import org.streameps.core.util.IDUtil;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.AggregatorListener;
import org.streameps.processor.EventAggregatorPE;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.store.file.IFileEPStore;

/**
 * Abstract implementation of the EPS decider interface. The specific functionality of
 * the <b>decide</b> method is left to be implemented by the developer.
 * 
 * @author Frank Appiah
 * @version 0.3.3
 */
public abstract class AbstractEPSDecider<C extends IContextPartition> implements IEPSDecider<C> {

    private List<IHistoryStore> historyStores;
    private IEPSProducer producer;
    private IDeciderPair<C> deciderPair;
    private IPatternChain<IBasePattern> patternChain;
    private IKnowledgeBase knowledgeBase;
    private IDeciderContext<IMatchedEventSet> deciderMatchContext = null;
    private IAggregateContext aggregateContext;
    private boolean aggregateDetectEnabled = false;
    private AggregatorListener aggregatorListener = null;
    private IStoreContext<IMatchedEventSet> storeMatchContext;
    private StoreContextBuilder builder;
    private boolean saveOnDecide = false;
    private IHistoryStore auditStore;
    private ILogger logger = LoggerUtil.getLogger(AbstractEPSDecider.class);
    private IHistoryStore externalMatchStore, externalUnMatchStore;
    private boolean patternDetectionEnabled = true;

    public AbstractEPSDecider() {
        historyStores = new ArrayList<IHistoryStore>();
        patternChain = new PatternChain();
        builder = new StoreContextBuilder(historyStores);
        auditStore = new AuditEventStore();
    }

    public void persistStoreContext(IStoreContext<IMatchedEventSet> storeContext) {
        this.storeMatchContext = storeContext;
        getDeciderContextStore().saveToStore(IFileEPStore.PATTERN_MATCH_GROUP, storeContext);
        logger.info("Persisiting the store context to the store....");
    }

    public List<IHistoryStore> getHistoryStores() {
        return this.historyStores;
    }

    public void setAggregateContext(IAggregateContext aggregateContext) {
        this.aggregateContext = aggregateContext;
    }

    public void setAggregateDetectEnabled(boolean enabledAggregate) {
        this.aggregateDetectEnabled = enabledAggregate;
    }

    public IAggregateContext getAggregateContext() {
        return this.aggregateContext;
    }

    public AggregatorListener getAggregateListener() {
        return this.aggregatorListener;
    }

    public boolean isAggregateDetectEnabled() {
        return aggregateDetectEnabled;
    }

    public void sendDeciderContext(IDeciderContext context) {
        this.deciderMatchContext = context;
        if (aggregateDetectEnabled && detectAggregate(aggregateContext)) {
            context.setAnnotation(ASSERTION_MATCH_EVENTS);
            this.producer.onDeciderContextReceive(context);
            this.knowledgeBase.onDeciderContextReceive(context);
        } else {
            this.producer.onDeciderContextReceive(context);
            this.knowledgeBase.onDeciderContextReceive(context);
        }
        logger.info("Sending the decider context to the producer.....");
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

    public void setSaveOnDecide(boolean saveOnDecide) {
        this.saveOnDecide = saveOnDecide;
    }

    public boolean isSaveOnDecide() {
        return saveOnDecide;
    }

    public IEPSProducer getProducer() {
        if (producer == null) {
            this.producer = new EPSProducer();
        }
        return producer;
    }

    public void setContextPartition(List<C> contextPartitions) {
        if (deciderPair == null) {
            deciderPair = new DeciderPair();
        }
        this.deciderPair.setContextPartitions(contextPartitions);
        setDeciderPair(deciderPair);
        logger.info("Setting the context partition of the decider pair......");
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
        logger.info("Adding a new history store to the list");
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
        logger.info("Detecting and forwarding of aggregate context for aggregation activity...");
        if (deciderMatchContext == null) {
            return false;
        }
        this.aggregateContext.setIdentifier("Decider:=" + IDUtil.getUniqueID(new Date().toString()));
        this.aggregateContext = aggregateContext;
        String attribute = aggregateContext.getAggregateProperty();
        EventAggregatorPE eape = new EventAggregatorPE(IDUtil.getUniqueID(new Date().toString()), attribute);
        List<IAggregation> aggregators = aggregateContext.getAggregatorList();
        for (IAggregation aggregator : aggregators) {
            if (aggregatorListener != null) {
                eape.setAggregatorListener(aggregatorListener);
            }
            IAggregatePolicy aggregatePolicy = aggregateContext.getPolicy();
            if (aggregatePolicy != null) {
                eape.setAggregatePolicy(aggregatePolicy);
            }
            eape.setAggregation(aggregator);
            for (Object event : deciderMatchContext.getDeciderValue()) {
                eape.process(event);
            }
            eape.output();

            ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(aggregateContext.getAssertionType());
            double threshold = (Double) aggregateContext.getThresholdValue();
            double resultValue = (Double) aggregator.getValue();
            return assertion.assertEvent(new AssertionValuePair(threshold, resultValue));
        }
        return false;
    }

    public void setDeciderContextStore(IHistoryStore auditStore) {
        this.auditStore = auditStore;
    }

    public IHistoryStore getDeciderContextStore() {
        return auditStore;
    }

    public ILogger getLogger() {
        return logger;
    }

    public IHistoryStore getExternalUnMatchStore() {
        return externalUnMatchStore;
    }

    public IHistoryStore getExternalMatchStore() {
        return externalMatchStore;
    }

    public boolean isPatternDetectionEnabled() {
        return patternDetectionEnabled;
    }

    public void setPatternDetectionEnabled(boolean patternDetectionEnabled) {
        this.patternDetectionEnabled = patternDetectionEnabled;
    }

    public void setExternalMatchStore(IHistoryStore externalMatchStore) {
        this.externalMatchStore = externalMatchStore;
    }

    public void setExternalUnMatchStore(IHistoryStore externalUnMatchStore) {
        this.externalUnMatchStore = externalUnMatchStore;
    }
}
