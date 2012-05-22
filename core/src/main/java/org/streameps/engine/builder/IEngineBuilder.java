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
import java.util.concurrent.TimeUnit;
import org.streameps.agent.IAgentManager;
import org.streameps.core.IEventUpdateListener;
import org.streameps.context.IContextDetail;
import org.streameps.context.IContextPartition;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.engine.IClock;
import org.streameps.engine.IEPSDecider;
import org.streameps.engine.IEPSEngine;
import org.streameps.engine.IEPSForwarder;
import org.streameps.engine.IEPSProducer;
import org.streameps.engine.IEPSReceiver;
import org.streameps.engine.IHistoryStore;
import org.streameps.engine.IKnowledgeBase;
import org.streameps.engine.IPatternChain;
import org.streameps.epn.channel.IEventChannelManager;
import org.streameps.filter.IFilterManager;
import org.streameps.processor.IPatternManager;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;
import org.streameps.processor.pattern.policy.PatternPolicy;
import org.streameps.thread.IEPSExecutorManager;

/**
 * The specification of the engine builder.
 * 
 * @author  Frank Appiah
 */
public interface IEngineBuilder<T extends IContextDetail, E> {

     public IFilterManager getFilterManager();
     
    /**
     * It builds a pattern detector to be used for the EPS decider during the
     * pattern matching process.
     *
     * @param basePattern The pattern detector.
     * @param matchListener The pattern match listener.
     * @param unMatchListener The pattern un-match listener.
     * @param updateListener The update event listener.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder buildPattern(IBasePattern basePattern, IPatternMatchListener matchListener, IPatternUnMatchListener unMatchListener, IEventUpdateListener updateListener);

    public IEngineBuilder buildPattern(IBasePattern basePattern, IPatternMatchListener matchListener,
            IPatternUnMatchListener unMatchListener);

    public IDispatcherService getDispatcherService();

    public void setDispatcherService(IDispatcherService dispatcherService);

    /**    It builds the properties for the engine specifically the sequence size,
     * asynchronous flag and queue flag.
     * @param sequenceCount The size of the queue.
     * @param asynchronous An asynchronous flag.
     * @param queued A queue flag.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder buildProperties(int sequenceCount, boolean asynchronous, boolean queued);

    /**
     * It returns the engine after the properties of the engine is
     * properly set.
     * @return An instance of the Engine Builder.
     */
    public IEPSEngine<IContextPartition<T>, E> getEngine();

    /**
     * It indicates whether the aggregated enabled flag for the EPS decider is set.
     * @return The aggregated enabled flag.
     */
    public boolean isAggregatedEnabled();

    /**
     * It sets the channel manager and builds the EPS producer properties.
     * @param channelManager The event channel manager.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder setChannelManager(IEventChannelManager channelManager);

    /**
     * It sets the clock for the receiver and builds the receiver properties.
     * @param clock The clock for engine.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder setClock(IClock clock);

    /**
     * It sets the EPS decider and builds the decider properties.
     * @param decider The EPS decider for the engine.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder setDecider(IEPSDecider<IContextPartition<T>> decider);

    /**
     * It sets the filter manager and builds the producer properties.
     * @param filterManager The filter manager.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder setFilterManager(IFilterManager filterManager);

    /**
     * It sets the EPS forwarder and builds the producer.
     * @param forwarder The EPS forwarder for the engine.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder setForwarder(IEPSForwarder forwarder);

    /**
     * It sets the history store for the EPS decider.
     * @param historyStores The history store for the engine.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder setHistoryStores(List<IHistoryStore> historyStores);

    /**
     * It sets the knowledge base and builds the decider properties.
     * @param knowledgeBase The knowledge base for the decider.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder setKnowledgeBase(IKnowledgeBase knowledgeBase);

    /**
     * It sets the EPS producer and builds the decider properties.
     * @param producer The EPS producer for the engine.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder setProducer(IEPSProducer<IContextPartition<T>> producer);

    /**
     * It sets the receiver and builds the receiver properties.
     * @param receiver The EPS receiver for the engine.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder setReceiver(IEPSReceiver<IContextPartition<T>, E> receiver);

    public AggregateContextBuilder getAggregateContextBuilder();

    public FilterContextBuilder geFilterContextBuilder();

    public PatternBuilder getPatternBuilder();

    public ReceiverContextBuilder getReceiverContextBuilder();

    public StoreContextBuilder getStoreContextBuilder();

    public FilterContextBuilder getFilterContextBuilder();

    public IPatternChain<IBasePattern> getPatternChain();

    public void setEngine(IEPSEngine<IContextPartition<T>, E> engine);

    public void setPatternBuilder(PatternBuilder patternBuilder);

    public void setPatternChain(IPatternChain<IBasePattern> patternChain);

    public void setProducerAggregatedEnabled(boolean producerAggregatedEnabled);

    public void setReceiverContextBuilder(ReceiverContextBuilder receiverContextBuilder);

    public void setStoreContextBuilder(StoreContextBuilder storeContextBuilder);

    public void setFilterContextBuilder(FilterContextBuilder filterContextBuilder);

    public void setAggregateContextBuilder(AggregateContextBuilder aggregateContextBuilder);

    public void setAggregatedEnabled(boolean aggregatedEnabled);

    public void init();

    /**
     * It builds a dispatcher for the engine with the dispatcher size.
     * @param dispatcherSize The size of dispatch processes allowed.
     * @param dispatcherService The dispatcher service.
     * @return
     */
    public IEngineBuilder buildDispatcher(int dispatcherSize, IDispatcherService dispatcherService);

    /**
     * It builds a dispatcher for the engine with the dispatcher size.
     * @param dispatcherSize The size of dispatch processes allowed.
     * @param initialDelay The initial delay for the a new dispatch process.
     * @param periodicDelay The delay for the next dispatch process.
     * @param dispatcherService The dispatcher service.
     * @return It builds a dispatcher for the dispatching process.
     */
    public IEngineBuilder buildDispatcher(int dispatcherSize, long initialDelay, long periodicDelay, IDispatcherService dispatcherService);

    /**
     * It builds a dispatcher for the engine with the dispatcher size.
     * @param dispatcherSize The size of dispatch processes allowed.
     * @param initialDelay The initial delay for the a new dispatch process.
     * @param periodicDelay The delay for the next dispatch process.
     * @param timeUnit The time unit for the initial delay and periodic delay.
     * @param dispatcherService The dispatcher service.
     * @return It builds a dispatcher for the dispatching process.
     */
    public IEngineBuilder buildDispatcher(int dispatcherSize, long initialDelay, long periodicDelay, TimeUnit timeUnit, IDispatcherService dispatcherService);

    public IEngineBuilder buildDomainManager(IPatternManager patternManager, IFilterManager filterManager, IAgentManager agentManager, IEPSExecutorManager executorManager);

    public IEngineBuilder buildExecutorManager(IEPSExecutorManager executorManager, int poolSize, String threadFactoryName);

    public IEngineBuilder buildExecutorManagerProperties(int poolSize, String threadFactoryName);

    /**
     * It builds the properties for the engine specifically the sequence size,
     * asynchronous flag and queue flag.
     * @param sequenceCount The size of the queue.
     * @param asynchronous An asynchronous flag.
     * @param queued A queue flag.
     * @param saveOnReceived An indicator to save events from the receiver.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder buildProperties(int sequenceCount, boolean asynchronous, boolean queued, boolean saveOnReceived);

    /**
     * It builds the properties for the engine specifically the sequence size,
     * asynchronous flag and queue flag.
     * @param sequenceCount The size of the queue.
     * @param asynchronous An asynchronous flag.
     * @param queued A queue flag.
     * @param saveOnReceived An indicator to save events from the receiver.
     * @param saveOnDecide An indicator to save events from the decider.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder buildProperties(int sequenceCount, boolean asynchronous, boolean queued, boolean saveOnReceived, boolean saveOnDecide);

    public IEngineBuilder buildPattern(IBasePattern basePattern);

    public IEngineBuilder buildAuditStore(IHistoryStore historyStore);

    public IEngineBuilder buildPattern(IBasePattern basePattern, PatternPolicy patternPolicy);

    /**
     * It builds the properties for the engine specifically the sequence size,
     * asynchronous flag and queue flag.
     * @param sequenceCount The size of the queue.
     * @param dispatcherSize The size of the dispatcher.
     * @param asynchronous An asynchronous flag.
     * @param queued A queue flag.
     * @return An instance of the Engine Builder.
     */
    public IEngineBuilder buildProperties(int sequenceCount, int dispatcherSize, boolean asynchronous, boolean queued);

    IClock getClock();

    IEPSDecider<IContextPartition<T>> getDecider();

    IEPSForwarder getForwarder();

    List<IHistoryStore> getHistoryStores();

    IKnowledgeBase getKnowledgeBase();

    IEPSProducer<IContextPartition<T>> getProducer();

    IEPSReceiver<IContextPartition<T>, E> getReceiver();

    IEventChannelManager getChannelManager();

    /**
     * It builds the history store specifically the event store for the
     * decider for both match and un-match events received from the receiver side
     * of the processing pipe line.
     * @param historyStore  An instance of a history store.
     * @return The specific history store being used to store the detected events.
     */
    EngineBuilder buildDeciderStore(IHistoryStore historyStore);

    /**
     * It builds the history store specifically the audit trail store for the
     * engine, receiver and decider for both match and un-match events
     * received from the receiver side of the processing pipe line.
     * @param historyStore
     * @return
     */
    EngineBuilder buildReceiverStore(IHistoryStore historyStore);
}
