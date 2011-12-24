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
import java.util.concurrent.TimeUnit;
import org.streameps.agent.IAgentManager;
import org.streameps.client.IEventUpdateListener;
import org.streameps.context.IContextDetail;
import org.streameps.context.IContextPartition;
import org.streameps.core.DomainManager;
import org.streameps.core.IDomainManager;
import org.streameps.decider.IDeciderContextListener;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.engine.AbstractEPSEngine;
import org.streameps.engine.AggregateContext;
import org.streameps.engine.EPSForwarder;
import org.streameps.engine.EPSProducer;
import org.streameps.engine.IAggregateContext;
import org.streameps.engine.IClock;
import org.streameps.engine.IEPSDecider;
import org.streameps.engine.IEPSEngine;
import org.streameps.engine.IEPSForwarder;
import org.streameps.engine.IEPSProducer;
import org.streameps.engine.IEPSReceiver;
import org.streameps.engine.IHistoryStore;
import org.streameps.engine.IKnowledgeBase;
import org.streameps.engine.IPatternChain;
import org.streameps.engine.KnowledgeBase;
import org.streameps.engine.PatternChain;
import org.streameps.engine.SystemClock;
import org.streameps.engine.segment.SegmentDecider;
import org.streameps.engine.segment.SegmentEngine;
import org.streameps.engine.segment.SegmentReceiver;
import org.streameps.epn.channel.EventChannelManager;
import org.streameps.epn.channel.IEventChannelManager;
import org.streameps.filter.FilterManager;
import org.streameps.filter.IFilterManager;
import org.streameps.processor.AggregatorListener;
import org.streameps.processor.IPatternManager;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;
import org.streameps.processor.pattern.policy.PatternPolicy;
import org.streameps.thread.IEPSExecutorManager;

/**
 *
 * @author Frank Appiah
 */
public final class EngineBuilder<T extends IContextDetail, E> {

    private IEPSDecider<IContextPartition<T>> decider = null;
    private IEPSEngine<IContextPartition<T>, E> engine = null;
    private IEPSReceiver<IContextPartition<T>, E> receiver = null;
    private IEPSProducer<IContextPartition<T>> producer = null;
    private IKnowledgeBase knowledgeBase = null;
    private IEPSForwarder forwarder = null;
    private IClock clock = null;
    private List<IHistoryStore> historyStore = null;
    private IFilterManager filterManager = null;
    private IEventChannelManager channelManager = null;
    private IPatternChain<IBasePattern> patternChain = null;
    private boolean aggregatedEnabled = false, producerAggregatedEnabled = false;
    private AggregateContextBuilder aggregateContextBuilder;
    private FilterContextBuilder filterContextBuilder;
    private StoreContextBuilder storeContextBuilder;
    private PatternBuilder patternBuilder;
    private ReceiverContextBuilder receiverContextBuilder;
    private IDispatcherService dispatcherService;

    public EngineBuilder() {
        patternChain = new PatternChain();
        initBuilders();
    }

    public EngineBuilder(IEPSDecider<IContextPartition<T>> decider,
            IEPSEngine<IContextPartition<T>, E> engine,
            IEPSReceiver<IContextPartition<T>, E> receiver) {
        this.decider = decider;
        this.engine = engine;
        this.receiver = receiver;
        initBuilders();
        buildEngine(decider, receiver, producer);
    }

    public void init() {
        clock = new SystemClock();
        decider = new SegmentDecider();
        receiver = new SegmentReceiver();
        this.receiver.setDecider(decider);
        engine = new SegmentEngine();
        this.receiver.setEPSEngine(engine);
    }

    /**
     * It returns the engine after the properties of the engine is
     * properly set.
     * @return An instance of the Engine Builder.
     */
    public IEPSEngine<IContextPartition<T>, E> getEngine() {
        //init();
        buildEngine(decider, receiver, producer);
        return engine;
    }

    private void buildEngine(IEPSDecider<IContextPartition<T>> decider,
            IEPSReceiver<IContextPartition<T>, E> receiver,
            IEPSProducer<IContextPartition<T>> producer) {
        this.decider = decider;
        this.producer = producer;
        this.receiver = receiver;
        buildDecider();
        buildProducer();
        buildReceiver();
        //patternChain = new PatternChain();
        this.engine.setDomainManager(new DomainManager());
        this.engine.setEPSReceiver(this.receiver);
        this.engine.setDecider(decider);
    }

    /**
     * It builds the properties of the EPS receiver.
     * @return An instance of the Engine Builder.
     */
    private EngineBuilder buildReceiver() {
        if (receiver == null) {
            receiver = new SegmentReceiver();
        }
        if (decider == null) {
            decider = new SegmentDecider();
        }
        this.receiver.setDecider(decider);
        if (engine == null) {
            engine = new SegmentEngine();
        }
        this.receiver.setEPSEngine(engine);
        if (clock == null) {
            this.clock = new SystemClock();
        }
        this.receiver.setClock(clock);
        this.receiver.setChannelManager(channelManager);

        return this;
    }

    /**
     * It builds the properties of the decider producer.
     * @return An instance of the Engine Builder.
     */
    private EngineBuilder buildDecider() {
        if (channelManager == null) {
            channelManager = new EventChannelManager();
        }
        if (producer == null) {
            producer = new EPSProducer();
        }
        this.decider.setProducer(producer);
        if (knowledgeBase == null) {
            knowledgeBase = new KnowledgeBase();
        }
        this.decider.setKnowledgeBase(knowledgeBase);
        if (historyStore == null) {
            historyStore = new ArrayList<IHistoryStore>();
        }
        this.decider.setHistoryStores(historyStore);
        return this;
    }

    /**
     * It builds the properties of the EPS producer.
     * @return An instance of the Engine Builder.
     */
    private EngineBuilder buildProducer() {
        if (forwarder == null) {
            forwarder = new EPSForwarder();
        }
        this.producer.setForwarder(forwarder);
        if (filterManager == null) {
            filterManager = new FilterManager();
        }
        this.producer.setFilterManager(filterManager);
        if (channelManager == null) {
            channelManager = new EventChannelManager();
        }
        this.producer.setChannelManager(channelManager);
        return this;
    }

    /**
     * It sets the aggregated enabled flag for the EPS decider.
     * @param decider The EPS decider.
     * @param aggregatedEnabled The enabled flag.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setAggregatedDetectEnabled(IAggregateContext aggregateContext, AggregatorListener aggregateListener, boolean aggregatedEnabled) {
        this.aggregatedEnabled = aggregatedEnabled;
        this.decider.setAggregateEnabled(aggregatedEnabled);
        this.decider.setAggregateListener(aggregateListener);
        this.decider.setAggregateContext(aggregateContext);
        return this;
    }

    /**
     * It sets the aggregated enabled flag for the EPS decider.
     * @param decider The EPS decider.
     * @param The aggregate listener.
     * 
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setAggregatedDetectEnabled(IAggregateContext aggregateContext, AggregatorListener aggregateListener) {
        this.aggregatedEnabled = true;
        this.decider.setAggregateEnabled(aggregatedEnabled);
        this.decider.setAggregateListener(aggregateListener);
        this.decider.setAggregateContext(aggregateContext);

        return this;
    }

    /**
     * It sets the aggregated enabled flag for the EPS producer to produce an aggregate.
     * @param decider The EPS producer.
     * @param aggregatedEnabled The enabled flag.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setAggregatedEnabled(IAggregateContext aggregateContext, AggregatorListener aggregateListener, boolean aggregatedEnabled) {
        this.producerAggregatedEnabled = aggregatedEnabled;
        this.producer.setAggregateEnabled(aggregatedEnabled);
        this.producer.setAggregateContext(aggregateContext);
        this.producer.setAggregatorListener(aggregateListener);
        return this;
    }

    /**
     * It sets the decider context listener.
     * @param contextListener The decider context listener.
     * @return The decider context listener.
     */
    public EngineBuilder setDeciderListener(IDeciderContextListener contextListener) {
        this.producer.setDeciderContextListener(contextListener);
        return this;
    }

    /**
     * It indicates whether the aggregated enabled flag for the EPS decider is set.
     * @return The aggregated enabled flag.
     */
    public boolean isAggregatedEnabled() {
        return aggregatedEnabled;
    }

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
    public EngineBuilder buildPattern(IBasePattern basePattern, IPatternMatchListener matchListener,
            IPatternUnMatchListener unMatchListener, IEventUpdateListener updateListener) {
        if (patternChain == null) {
            patternChain = new PatternChain();
        }
        patternChain.addPattern(basePattern);
        patternChain.addPatternMatchedListener(matchListener);
        patternChain.addPatternUnMatchedListener(unMatchListener);
        patternChain.addEventUpdateListener(updateListener);
        getEngine().getDecider().setPatternChain(patternChain);
        return this;
    }

    public EngineBuilder buildPattern(IBasePattern basePattern, IPatternMatchListener matchListener,
            IPatternUnMatchListener unMatchListener) {
        if (patternChain == null) {
            patternChain = new PatternChain();
        }
        patternChain.addPattern(basePattern);
        patternChain.addPatternMatchedListener(matchListener);
        patternChain.addPatternUnMatchedListener(unMatchListener);
        getEngine().getDecider().setPatternChain(patternChain);
        return this;
    }

    public EngineBuilder buildPattern(IBasePattern basePattern, IPatternUnMatchListener unMatchListener) {
        if (patternChain == null) {
            patternChain = new PatternChain();
        }
        patternChain.addPattern(basePattern);
        patternChain.addPatternUnMatchedListener(unMatchListener);
        getEngine().getDecider().setPatternChain(patternChain);
        return this;
    }

    public EngineBuilder buildPattern(IBasePattern basePattern, IPatternMatchListener matchListener) {
        if (patternChain == null) {
            patternChain = new PatternChain();
        }
        patternChain.addPattern(basePattern);
        patternChain.addPatternMatchedListener(matchListener);
        getEngine().getDecider().setPatternChain(patternChain);
        return this;
    }

    public EngineBuilder buildPattern(IBasePattern basePattern) {
        if (patternChain == null) {
            patternChain = new PatternChain();
        }
        patternChain.addPattern(basePattern);
        getEngine().getDecider().setPatternChain(patternChain);
        return this;
    }

    public EngineBuilder buildPattern(IBasePattern basePattern, PatternPolicy patternPolicy) {
        if (patternChain == null) {
            patternChain = new PatternChain();
        }
        basePattern.getPatternPolicies().add(patternPolicy);
        patternChain.addPattern(basePattern);
        getEngine().getDecider().setPatternChain(patternChain);
        return this;
    }

    public EngineBuilder buildDomainManager(IPatternManager patternManager, IFilterManager filterManager,
            IAgentManager agentManager,
            IEPSExecutorManager executorManager) {
        IDomainManager domainManager = new DomainManager(patternManager, filterManager, agentManager, executorManager);
        ((AbstractEPSEngine) getEngine()).setDomainManager(domainManager);
        return this;
    }

    public EngineBuilder buildExecutorManager(IEPSExecutorManager executorManager, int poolSize, String threadFactoryName) {
        if (executorManager == null) {
            ((AbstractEPSEngine) getEngine()).getDomainManager().getExecutorManager().setPoolSize(poolSize);
            ((AbstractEPSEngine) getEngine()).getDomainManager().getExecutorManager().setThreadFactoryName(threadFactoryName);
        } else {
            executorManager.setPoolSize(poolSize);
            executorManager.setThreadFactoryName(threadFactoryName);
            ((AbstractEPSEngine) getEngine()).getDomainManager().setExecutorManager(executorManager);
        }
        return this;
    }

    public EngineBuilder buildExecutorManagerProperties(int poolSize, String threadFactoryName) {
        ((AbstractEPSEngine) getEngine()).getDomainManager().getExecutorManager().setPoolSize(poolSize);
        ((AbstractEPSEngine) getEngine()).getDomainManager().getExecutorManager().setThreadFactoryName(threadFactoryName);
        return this;
    }

    /**
     * It builds a dispatcher for the engine with the dispatcher size.
     * @param dispatcherSize The size of dispatch processes allowed.
     * @param dispatcherService The dispatcher service.
     * @return
     */
    public EngineBuilder buildDispatcher(int dispatcherSize, IDispatcherService dispatcherService) {
        dispatcherService.setExecutionManager(((AbstractEPSEngine) getEngine()).getDomainManager().getExecutorManager());
        ((AbstractEPSEngine) getEngine()).setDispatcherService(dispatcherService);
        ((AbstractEPSEngine) getEngine()).setDispatcherSize(dispatcherSize);
        return this;
    }

    /**
     * It builds a dispatcher for the engine with the dispatcher size.
     * @param dispatcherSize The size of dispatch processes allowed.
     * @param initialDelay The initial delay for the a new dispatch process.
     * @param periodicDelay The delay for the next dispatch process.
     * @param timeUnit The time unit for the initial delay and periodic delay.
     * @param dispatcherService The dispatcher service.
     * @return It builds a dispatcher for the dispatching process.
     */
    public EngineBuilder buildDispatcher(int dispatcherSize, long initialDelay, long periodicDelay, TimeUnit timeUnit, IDispatcherService dispatcherService) {
        dispatcherService.setExecutionManager(((AbstractEPSEngine) getEngine()).getDomainManager().getExecutorManager());
        ((AbstractEPSEngine) getEngine()).setDispatcherService(dispatcherService);
        ((AbstractEPSEngine) getEngine()).setDispatcherSize(dispatcherSize);
        ((AbstractEPSEngine) getEngine()).setPeriodicDelay(periodicDelay);
        ((AbstractEPSEngine) getEngine()).setInitialDelay(initialDelay);
        return this;
    }

    /**
     * It builds a dispatcher for the engine with the dispatcher size.
     * @param dispatcherSize The size of dispatch processes allowed.
     * @param initialDelay The initial delay for the a new dispatch process.
     * @param periodicDelay The delay for the next dispatch process.
     * @param dispatcherService The dispatcher service.
     * @return It builds a dispatcher for the dispatching process.
     */
    public EngineBuilder buildDispatcher(int dispatcherSize, long initialDelay, long periodicDelay, IDispatcherService dispatcherService) {
        dispatcherService.setExecutionManager(((AbstractEPSEngine) getEngine()).getDomainManager().getExecutorManager());
        ((AbstractEPSEngine) getEngine()).setDispatcherService(dispatcherService);
        ((AbstractEPSEngine) getEngine()).setDispatcherSize(dispatcherSize);
        ((AbstractEPSEngine) getEngine()).setPeriodicDelay(periodicDelay);
        ((AbstractEPSEngine) getEngine()).setInitialDelay(initialDelay);
        return this;
    }

    /**
     * It builds the properties for the engine specifically the sequence size,
     * asynchronous flag and queue flag.
     * @param sequenceCount The size of the queue.
     * @param asynchronous An asynchronous flag.
     * @param queued A queue flag.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder buildProperties(int sequenceCount, boolean asynchronous, boolean queued) {
        ((AbstractEPSEngine) getEngine()).setAsynchronous(asynchronous);
        ((AbstractEPSEngine) getEngine()).setSequenceCount(sequenceCount - 1);
        ((AbstractEPSEngine) getEngine()).setEventQueued(queued);
        return this;
    }

    /**
     * It builds the properties for the engine specifically the sequence size,
     * asynchronous flag and queue flag.
     * @param sequenceCount The size of the queue.
     * @param dispatcherSize The size of the dispatcher.
     * @param asynchronous An asynchronous flag.
     * @param queued A queue flag.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder buildProperties(int sequenceCount, int dispatcherSize, boolean asynchronous, boolean queued) {
        ((AbstractEPSEngine) getEngine()).setAsynchronous(asynchronous);
        ((AbstractEPSEngine) getEngine()).setSequenceCount(sequenceCount - 1);
        ((AbstractEPSEngine) getEngine()).setEventQueued(queued);
        ((AbstractEPSEngine) getEngine()).setDispatcherSize(dispatcherSize);
        return this;
    }

    /**
     * It sets the filter manager and builds the producer properties.
     * @param filterManager The filter manager.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setFilterManager(IFilterManager filterManager) {
        this.filterManager = filterManager;
        buildProducer();
        return this;
    }

    /**
     * It sets the knowledge base and builds the decider properties.
     * @param knowledgeBase The knowledge base for the decider.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setKnowledgeBase(IKnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
        buildDecider();
        return this;
    }

    /**
     * It sets the EPS decider and builds the decider properties.
     * @param decider The EPS decider for the engine.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setDecider(IEPSDecider<IContextPartition<T>> decider) {
        this.decider = decider;
        buildDecider();
        return this;
    }

    /**
     * It sets the clock for the receiver and builds the receiver properties.
     * @param clock The clock for engine.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setClock(IClock clock) {
        this.clock = clock;
        buildReceiver();
        return this;
    }

    /**
     * It sets the channel manager and builds the EPS producer properties.
     * @param channelManager The event channel manager.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setChannelManager(IEventChannelManager channelManager) {
        this.channelManager = channelManager;
        buildProducer();
        return this;
    }

    /**
     * It sets the EPS producer and builds the decider properties.
     * @param producer The EPS producer for the engine.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setProducer(IEPSProducer<IContextPartition<T>> producer) {
        this.producer = producer;
        buildDecider();
        return this;
    }

    public IDispatcherService getDispatcherService() {
        return dispatcherService;
    }

    public void setDispatcherService(IDispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;

    }

    /**
     * It sets the receiver and builds the receiver properties.
     * @param receiver The EPS receiver for the engine.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setReceiver(IEPSReceiver<IContextPartition<T>, E> receiver) {
        this.receiver = receiver;
        buildReceiver();
        return this;
    }

    /**
     * It sets the EPS forwarder and builds the producer.
     * @param forwarder The EPS forwarder for the engine.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setForwarder(IEPSForwarder forwarder) {
        this.forwarder = forwarder;
        buildProducer();
        return this;
    }

    /**
     * It sets the history store for the EPS decider.
     * @param historyStores The history store for the engine.
     * @return An instance of the Engine Builder.
     */
    public EngineBuilder setHistoryStore(List<IHistoryStore> historyStores) {
        this.historyStore = historyStores;
        buildDecider();
        return this;
    }

    private void initBuilders() {
        aggregateContextBuilder = new AggregateContextBuilder(new AggregateContext());
        filterContextBuilder = new FilterContextBuilder();
        storeContextBuilder = new StoreContextBuilder();
        patternBuilder = new PatternBuilder(null);
        receiverContextBuilder = new ReceiverContextBuilder();
    }

    public AggregateContextBuilder getAggregateContextBuilder() {
        return this.aggregateContextBuilder;
    }

    public FilterContextBuilder geFilterContextBuilder() {
        return this.filterContextBuilder;
    }

    public PatternBuilder getPatternBuilder() {
        return this.patternBuilder;
    }

    public ReceiverContextBuilder getReceiverContextBuilder() {
        return this.receiverContextBuilder;
    }

    public StoreContextBuilder getStoreContextBuilder() {
        return this.storeContextBuilder;
    }
}
