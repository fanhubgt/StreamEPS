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
import org.streameps.aggregation.IAggregation;
import org.streameps.context.IContextPartition;
import org.streameps.decider.IDeciderContextListener;
import org.streameps.epn.channel.IEventChannelManager;
import org.streameps.filter.IFilterManager;
import org.streameps.filter.IFilterValueSet;
import org.streameps.processor.AggregatorListener;

/**
 * Interface for the system producer.
 * 
 * @author  Frank Appiah
 */
public interface IEPSProducer<C extends IContextPartition> {

    public void addFilterContext(IFilterContext context);

    /**
     * It indicates whether to execute a filtering process with the same filter value set
     * or to use the filter value of each filter process for the next in the chain.
     * 
     * @param filterChainEnabled Whether to chain the execution of a filter process or not.
     */
    public void setFilterChainEnabled(boolean filterChainEnabled);

    /**
     * whether to execute a filtering process with the same filter value set or not.
     * 
     * @return Whether to chain the execution of a filter process or not.
     */
    public boolean isFilterChainEnabled();

    /**
     * It produces the decider context received from the EPSDecider.
     * @param deciderContext The decider context produced.
     */
    public void produceDeciderContext(IDeciderContext deciderContext);

    /**
     * It returns the filter context for the filtering process.
     * 
     * @param filterContext The filter context.
     */
    public void setFilterContexts(List<IFilterContext<IFilterValueSet>> filterContext);

    /**
     * It sets the filter context for the filtering process.
     * @return The filter context.
     */
    public List<IFilterContext<IFilterValueSet>> getFilterContexts();

    public void setFilterChain(IFilterChain filterChain);

    public IFilterChain getFilterChain();

    /**
     * If the assertion flag is enabled in the producer, then the matched events
     * in the threshold assertion will produce a decider context received by the
     * decider context listener.
     *
     * @param assertionEnabled A flag to produce the assertion events.
     */
    public void setAssertionEnabled(boolean assertionEnabled);

    /**
     * It indicates whether to produce the assertion matched events or not.
     * @return Whether assertion events will be produced or not.
     */
    public boolean isAssertionEnabled();

    /**
     * It sets the aggregate contexts for the EPS producer.
     * @param aggregateContext The aggregate context.
     */
    public void setAggregateContexts(List<IAggregateContext> aggregateContext);

    /**
     * It returns the aggregate context used to compute the aggregate result in
     * the EPS producer.
     * @return The aggregate context.
     */
    public List<IAggregateContext> getAggregateContexts();

    /**
     * It sets the aggregated enabled flag.
     * @param aggregateEnabled A flag indicator.
     */
    public void setAggregateEnabled(boolean aggregateEnabled);

    /**
     * It returns the aggregated enabled flag.
     * @return A flag indicator.
     */
    public boolean isAggregateEnabled();

    public void setFilterEnabled(boolean filterEnabled);

    public boolean isFilterEnabled();

    /**
     * It sets the aggregator listener to observe the aggregation process.
     * 
     * @param aggregatorListener An aggregate listener.
     */
    public void setAggregatorListener(AggregatorListener<IAggregation> aggregatorListener);

    /**
     * It sets the decider context listener.
     * @param contextListener The decider context listener.
     */
    public void setDeciderContextListener(IDeciderContextListener contextListener);

    /**
     * It returns the decider context listener.
     * @return The decider context listener.
     */
    public IDeciderContextListener getDeciderContextListener();

    /**
     * It the event processing forwarder.
     * @param forwarder The event forwarder.
     */
    public void setForwarder(IEPSForwarder forwarder);

    /**
     * It returns the event processing forwarder.
     * @return The event forwarder.
     */
    public IEPSForwarder getForwarder();

    /**
     * It sets event channel for managing the input and output channels.
     * @param channel The event channel manager being set to the receiver.
     */
    public void setChannelManager(IEventChannelManager channel);

    /**
     * It returns the event channel for managing the input and output channels.
     * @return event channel An instance of the channel manager.
     */
    public IEventChannelManager getChannelManager();

    /**
     * It sets the filter manager for the EPS producer.
     * @param filterManager A filter manager.
     */
    public void setFilterManager(IFilterManager filterManager);

    /**
     *  It returns the filter manager for the EPS producer.
     * @return A filter manager.
     */
    public IFilterManager getFilterManager();

    /**
     * It sends the filter context to the EPS Forwarder.
     */
    public void sendFilterContext();

    /**
     * It receives the decider context from the engine decider and then
     * performs the aggregation process.
     * 
     * @see IEPSProducer#produceAggregate(org.streameps.engine.IAggregateContext).
     * @param deciderContext A decider context from the engine decider.
     */
    public void onDeciderContextReceive(IDeciderContext deciderContext);

    /**
     * It sends the aggregate result computed to the aggregate listener connected to
     * this producer and then sends the aggregate context to EPS forwarder
     * for the output listeners.
     * 
     * @param aggregateContext An aggregate context.
     */
    public void produceAggregate(IAggregateContext aggregateContext);

    /**
     * It produces the forwarder context to be forwarded to the EPS forwarder.
     * @param context The forwarder context.
     */
    public void produceFilterContext(IFilterContext context);

    IKnowledgeBase getKnowledgeBase();

    void setKnowledgeBase(IKnowledgeBase knowledgeBase);
}
