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
import org.streameps.client.IEventUpdateListener;
import org.streameps.context.IContextDetail;
import org.streameps.context.IContextPartition;
import org.streameps.engine.IClock;
import org.streameps.engine.IEPSDecider;
import org.streameps.engine.IEPSEngine;
import org.streameps.engine.IEPSForwarder;
import org.streameps.engine.IEPSProducer;
import org.streameps.engine.IEPSReceiver;
import org.streameps.engine.IHistoryStore;
import org.streameps.engine.IKnowledgeBase;
import org.streameps.epn.channel.IEventChannelManager;
import org.streameps.filter.IFilterManager;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;

/**
 *
 * @author  Frank Appiah
 */
public interface IEngineBuilder<T extends IContextDetail, E> {

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
    EngineBuilder buildPattern(IBasePattern basePattern, IPatternMatchListener matchListener, IPatternUnMatchListener unMatchListener, IEventUpdateListener updateListener);

    /**    It builds the properties for the engine specifically the sequence size,
     * asynchronous flag and queue flag.
     * @param sequenceCount The size of the queue.
     * @param asynchronous An asynchronous flag.
     * @param queued A queue flag.
     * @return An instance of the Engine Builder.
     */
    EngineBuilder buildProperties(int sequenceCount, boolean asynchronous, boolean queued);

    /**
     * It returns the engine after the properties of the engine is
     * properly set.
     * @return An instance of the Engine Builder.
     */
    IEPSEngine<IContextPartition<T>, E> getEngine();

    /**
     * It indicates whether the aggregated enabled flag for the EPS decider is set.
     * @return The aggregated enabled flag.
     */
    boolean isAggregatedEnabled();

    /**
     * It sets the aggregated enabled flag for the EPS decider.
     * @param decider The EPS decider.
     * @param aggregatedEnabled The enabled flag.
     * @return An instance of the Engine Builder.
     */
    EngineBuilder setAggregatedEnabled(IEPSDecider decider, boolean aggregatedEnabled);

    /**
     * It sets the channel manager and builds the EPS producer properties.
     * @param channelManager The event channel manager.
     * @return An instance of the Engine Builder.
     */
    EngineBuilder setChannelManager(IEventChannelManager channelManager);

    /**
     * It sets the clock for the receiver and builds the receiver properties.
     * @param clock The clock for engine.
     * @return An instance of the Engine Builder.
     */
    EngineBuilder setClock(IClock clock);

    /**
     * It sets the EPS decider and builds the decider properties.
     * @param decider The EPS decider for the engine.
     * @return An instance of the Engine Builder.
     */
    EngineBuilder setDecider(IEPSDecider<IContextPartition<T>> decider);

    /**
     * It sets the filter manager and builds the producer properties.
     * @param filterManager The filter manager.
     * @return An instance of the Engine Builder.
     */
    EngineBuilder setFilterManager(IFilterManager filterManager);

    /**
     * It sets the EPS forwarder and builds the producer.
     * @param forwarder The EPS forwarder for the engine.
     * @return An instance of the Engine Builder.
     */
    EngineBuilder setForwarder(IEPSForwarder forwarder);

    /**
     * It sets the history store for the EPS decider.
     * @param historyStores The history store for the engine.
     * @return An instance of the Engine Builder.
     */
    EngineBuilder setHistoryStore(List<IHistoryStore> historyStores);

    /**
     * It sets the knowledge base and builds the decider properties.
     * @param knowledgeBase The knowledge base for the decider.
     * @return An instance of the Engine Builder.
     */
    EngineBuilder setKnowledgeBase(IKnowledgeBase knowledgeBase);

    /**
     * It sets the EPS producer and builds the decider properties.
     * @param producer The EPS producer for the engine.
     * @return An instance of the Engine Builder.
     */
    EngineBuilder setProducer(IEPSProducer<IContextPartition<T>> producer);

    /**
     * It sets the receiver and builds the receiver properties.
     * @param receiver The EPS receiver for the engine.
     * @return An instance of the Engine Builder.
     */
    EngineBuilder setReceiver(IEPSReceiver<IContextPartition<T>, E> receiver);

    AggregateContextBuilder getAggregateContextBuilder();

    FilterContextBuilder geFilterContextBuilder();

    PatternBuilder getPatternBuilder();

    ReceiverContextBuilder getReceiverContextBuilder();

    StoreContextBuilder getStoreContextBuilder();
}
