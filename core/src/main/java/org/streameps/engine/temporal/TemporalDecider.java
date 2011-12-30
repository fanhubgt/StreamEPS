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
package org.streameps.engine.temporal;

import java.util.List;
import org.streameps.context.IContextDetail;
import org.streameps.context.IContextPartition;
import org.streameps.context.temporal.TemporalType;
import org.streameps.core.IMatchedEventSet;
import org.streameps.engine.AbstractEPSDecider;
import org.streameps.engine.IAggregateContext;
import org.streameps.engine.IDeciderContext;
import org.streameps.engine.IDeciderPair;
import org.streameps.engine.IEPSDecider;
import org.streameps.engine.IEPSProducer;
import org.streameps.engine.IHistoryStore;
import org.streameps.engine.IKnowledgeBase;
import org.streameps.engine.IPatternChain;
import org.streameps.engine.IStoreContext;
import org.streameps.processor.AggregatorListener;
import org.streameps.processor.pattern.IBasePattern;

/**
 *
 * @author Frank Appiah
 */
public class TemporalDecider<T extends IContextDetail>
        extends AbstractEPSDecider<IContextPartition<T>>
        implements ITemporalDecider<T>
{
    private AbstractEPSDecider<IContextPartition<T>> decider;
    private TemporalType temporalType;

    public TemporalDecider() {
        super();
    }

    public TemporalDecider(AbstractEPSDecider<IContextPartition<T>> decider) {
        super();
        this.decider = decider;
    }

    public TemporalDecider(AbstractEPSDecider<IContextPartition<T>> decider, TemporalType temporalType) {
        super();
        this.decider = decider;
        this.temporalType = temporalType;
    }

    public void decideOnContext(IDeciderPair<IContextPartition<T>> pair) {
        this.decider.decideOnContext(pair);
    }

    public void onContextPartitionReceive(List<IContextPartition<T>> partitions) {
        this.decider.onContextPartitionReceive(partitions);
    }

    public void setTemporalType(TemporalType temporalType) {
        this.temporalType = temporalType;
    }

    public TemporalType getTemporalType() {
        return temporalType;
    }

    @Override
    public void setAggregateEnabled(boolean enabledAggregate) {
        this.decider.setAggregateEnabled(enabledAggregate);
    }

    @Override
    public void setAggregateListener(AggregatorListener aggregatorListener) {
       this.decider.setAggregateListener(aggregatorListener);
    }

    @Override
    public void setContextPartition(List<IContextPartition<T>> contextPartition) {
        this.decider.setContextPartition(contextPartition);
    }

    public void setDecider(IEPSDecider<IContextPartition<T>> decider) {
        this.decider = (AbstractEPSDecider<IContextPartition<T>>) decider;
    }

    @Override
    public void setDeciderPair(IDeciderPair<IContextPartition<T>> deciderPair) {
        this.decider.setDeciderPair(deciderPair);
    }

    @Override
    public void setHistoryStores(List<IHistoryStore> historyStore) {
        this.decider.setHistoryStores(historyStore);
    }

    @Override
    public void setKnowledgeBase(IKnowledgeBase knowledgeBase) {
        this.decider.setKnowledgeBase(knowledgeBase);
    }

    @Override
    public void setPatternChain(IPatternChain<IBasePattern> pattern) {
        this.decider.setPatternChain(pattern);
    }

    @Override
    public void setProducer(IEPSProducer producer) {
        this.decider.setProducer(producer);
    }

    public AbstractEPSDecider<IContextPartition<T>> getDecider() {
        return this.decider;
    }

    @Override
    public IDeciderPair<IContextPartition<T>> getDeciderPair() {
        return this.decider.getDeciderPair();
    }

    @Override
    public List<IHistoryStore> getHistoryStores() {
        return this.decider.getHistoryStores();
    }

    @Override
    public IKnowledgeBase getKnowledgeBase() {
        return this.decider.getKnowledgeBase();
    }

    @Override
    public IPatternChain<IBasePattern> getPatternChain() {
        return this.decider.getPatternChain();
    }

    @Override
    public IEPSProducer getProducer() {
        return this.decider.getProducer();
    }

    @Override
    public void setSaveOnDecide(boolean saveOnDecide) {
        super.setSaveOnDecide(saveOnDecide);
        this.decider.setSaveOnDecide(saveOnDecide);
    }

    @Override
    public boolean isSaveOnDecide() {
        return super.isSaveOnDecide();
    }

    @Override
    public IHistoryStore getDeciderContextStore() {
        return super.getDeciderContextStore();
    }

    @Override
    public void setDeciderContextStore(IHistoryStore auditStore) {
        super.setDeciderContextStore(auditStore);
        this.decider.setDeciderContextStore(auditStore);
    }

    @Override
    public void persistStoreContext(IStoreContext<IMatchedEventSet> storeContext) {
        this.decider.persistStoreContext(storeContext);
    }

    @Override
    public boolean detectAggregate(IAggregateContext aggregateContext) {
        return this.decider.detectAggregate(aggregateContext);
    }

    @Override
    public boolean isAggregateEnabled() {
        return this.decider.isAggregateEnabled();
    }

    @Override
    public void removeHistoryStore(IHistoryStore historyStore) {
        this.decider.removeHistoryStore(historyStore);
    }

    @Override
    public void sendDeciderContext(IDeciderContext context) {
       this.decider.sendDeciderContext(context);
    }

    @Override
    public void addHistoryStore(IHistoryStore historyStore) {
        this.decider.addHistoryStore(historyStore);
    }

    @Override
    public void setAggregateContext(IAggregateContext aggregateContext) {
        this.decider.setAggregateContext(aggregateContext);
    }

    @Override
    public IAggregateContext getAggregateContext() {
        return this.decider.getAggregateContext();
    }

    @Override
    public AggregatorListener getAggregateListener() {
        return this.decider.getAggregateListener();
    }

    public void setDecider(AbstractEPSDecider<IContextPartition<T>> decider) {
        this.decider = decider;
    }

    
}
