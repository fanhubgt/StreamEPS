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

import java.util.List;
import org.streameps.context.IContextPartition;
import org.streameps.core.IMatchedEventSet;
import org.streameps.processor.AggregatorListener;
import org.streameps.processor.pattern.IBasePattern;

/**
 * Interface for the event processing decider specification. It forms part of the
 * detection-production cycle. It's operation is to basically receive a pair of
 * context partition and pattern matcher, which is used to detect which of the events
 * in the context matches the pattern parameters. The pattern parameters are evaluated
 * with an operator assertion to produce the match result of true or false values and it
 * also support complex patterns.
 *
 * It then sends the sequence of matched events to the event processing system
 * producer.
 * <p>Supported Specific Context:</p>
 * <ol>
 * <li>Spatial</li>
 * <li>Temporal</li>
 * <li>Segment</li>
 * <li>Composite</li>
 * <li>State</li>
 * </ol>
 * @see IEPSProducer
 * 
 * @author  Frank Appiah
 * @version 0.3.3
 */
public interface IEPSDecider<C extends IContextPartition> {

    /**
     * It persists the decider context in the history store.
     * @param deciderContext The decider context to persist in a permanent history store.
     */
    public void persistStoreContext(IStoreContext<IMatchedEventSet> storeContext);

    /**
     * It decides on the pattern based on the context partition.
     * @param pair A decider pair that consists of a specific context partition
     * instance and pattern instance.
     * @see IDeciderPair
     */
    public void decideOnContext(IDeciderPair<C> pair);

    /**
     * It returns the aggregate enabled flag.
     * @return An indicator.
     */
    public boolean isAggregateEnabled();

    /**
     * This is part of the process used to decide on the context whether to send 
     * the context or not after the aggregate process value has exceeded
     * a certain threshold.
     * @param aggregateContext An aggregate context.
     */
    public boolean detectAggregate(IAggregateContext aggregateContext);

    /**
     * It sets the enable aggregate flag to indicate whether to detect aggregate threshold
     * on the decider context.
     * @param enabledAggregate An indicator flag.
     */
    public void setAggregateEnabled(boolean aggregateEnabled);

    /**
     * It sets the memory accumulator for the decider.
     * @param historyStore An instance of the history store.
     * @see IHistoryStore
     */
    public void setHistoryStores(List<IHistoryStore> historyStore);

    /**
     * It returns the memory store used to accumulate the items received from the
     * receiver.
     * @return A list of the history store.
     */
    public List<IHistoryStore> getHistoryStores();

    /**
     * It sets the partition for a particular context.
     * 
     * @param contextPartition partition of the context.
     */
    public void setContextPartition(List<C> contextPartition);

    /**
     * It pushes the list of context partitions to the EPS Decider.
     * @param partitions The list of context partitions.
     */
    public void onContextPartitionReceive(List<C> partitions);

    /**
     * It sets the pattern chain implementation used for the detection process.
     *
     * @param basePattern An instance of the pattern detector.
     */
    public void setPatternChain(IPatternChain<IBasePattern> pattern);

    /**
     * It returns the instance of the pattern chain.
     * @return An instance of the pattern chain.
     */
    public IPatternChain<IBasePattern> getPatternChain();

    /**
     * It sets the producer that takes a context information and executes
     * each triggered rule (i.e., its action part).
     *
     * @param producer An instance of event processing producer.
     */
    public void setProducer(IEPSProducer producer);

    /**
     * It returns the producer that takes a context information and executes
     * each triggered rule.
     * 
     * @return An instance of the event system producer.
     */
    public IEPSProducer getProducer();

    /**
     * It sends the decider context to the EPS producer and the Knowledge base.
     * @param context The decider context.
     */
    public void sendDeciderContext(IDeciderContext context);

    /**
     * It returns the container for the context partition instance and the base
     * pattern.
     * @return An instance of the decider pair.
     */
    public IDeciderPair<C> getDeciderPair();

    /**
     * It sets the knowledge base for decider.
     * @param knowledgeBase The knowledge base.
     */
    public void setKnowledgeBase(IKnowledgeBase knowledgeBase);

    /**
     * It returns the knowledge base for decider.
     * @return  The knowledge base.
     */
    public IKnowledgeBase getKnowledgeBase();

    /**
     * It sets the aggregate listener.
     * @param aggregatorListener The aggregate listener.
     */
    public void setAggregateListener(AggregatorListener aggregatorListener);

    /**
     * It returns the aggregation listener.
     * @return The aggregate listener.
     */
    public AggregatorListener getAggregateListener();

    /**
     * It sets the aggregate context for the aggregation detection process.
     * @param aggregateContext The aggregate context.
     */
    public void setAggregateContext(IAggregateContext aggregateContext);

    /**
     * It returns the aggregate context.
     * @return The aggregate context used to produce the aggregate.
     */
    public IAggregateContext getAggregateContext();

    /**
     * It sets the indicator to determine whether to save events on received
     * or not.
     * @param saveOnReceive An indicator whether to save or not.
     */
    public void setSaveOnDecide(boolean saveOnDecide);

    /**
     * It returns the indicator to determine whether to save events on received
     * or not.
     * @return An indicator whether to save or not.
     */
    public boolean isSaveOnDecide();

    /**
     * It sets the audit store for the decider match context.
     * @param auditStore The audit store for the decider match context.
     */
    public void setDeciderContextStore(IHistoryStore auditStore);

    /**
     * It returns the audit store for the decider match context.
     * @return The audit store for the decider match context.
     */
    public IHistoryStore getDeciderContextStore();
}
