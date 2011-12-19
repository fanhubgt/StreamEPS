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
package org.streameps.client;

import org.streameps.engine.IEPSEngine;
import org.streameps.engine.builder.AggregateContextBuilder;
import org.streameps.engine.builder.EngineBuilder;
import org.streameps.engine.builder.FilterContextBuilder;
import org.streameps.engine.builder.PatternBuilder;
import org.streameps.engine.builder.ReceiverContextBuilder;
import org.streameps.engine.builder.StoreContextBuilder;

/**
 *
 * @author Frank Appiah
 */
public final class EPSRuntimeClient implements IEPSRuntimeClient {

    private static IEPSEngine engine = null;
    private static IEPSRuntimeClient instance = null;
    private EngineBuilder engineBuilder;
    private AggregateContextBuilder aggregateContextBuilder;
    private FilterContextBuilder filterContextBuilder;
    private PatternBuilder patternBuilder;
    private StoreContextBuilder storeContextBuilder;
    private ReceiverContextBuilder receiverContextBuilder;

    public EPSRuntimeClient() {
    }

    public EPSRuntimeClient(EngineBuilder engineBuilder, AggregateContextBuilder aggregateContextBuilder, FilterContextBuilder filterContextBuilder, PatternBuilder patternBuilder, StoreContextBuilder storeContextBuilder, ReceiverContextBuilder receiverContextBuilder) {
        this.engineBuilder = engineBuilder;
        this.aggregateContextBuilder = aggregateContextBuilder;
        this.filterContextBuilder = filterContextBuilder;
        this.patternBuilder = patternBuilder;
        this.storeContextBuilder = storeContextBuilder;
        this.receiverContextBuilder = receiverContextBuilder;
    }

    public IEPSEngine getEngine() {
        restartEngine();
        return engine;
    }

    public static IEPSRuntimeClient getInstance() {
        if (instance == null) {
            instance = new EPSRuntimeClient();
        }
        return instance;
    }

    public void restartEngine() {
        engine = engineBuilder.getEngine();
    }

    public void setAggregateContextBuilder(AggregateContextBuilder aggregateContextBuilder) {
        this.aggregateContextBuilder = aggregateContextBuilder;
    }

    public void setEngineBuilder(EngineBuilder engineBuilder) {
        this.engineBuilder = engineBuilder;
    }

    public void setFilterContextBuilder(FilterContextBuilder filterContextBuilder) {
        this.filterContextBuilder = filterContextBuilder;
    }

    public void setPatternBuilder(PatternBuilder patternBuilder) {
        this.patternBuilder = patternBuilder;
    }

    public void setReceiverContextBuilder(ReceiverContextBuilder receiverContextBuilder) {
        this.receiverContextBuilder = receiverContextBuilder;
    }

    public void setStoreContextBuilder(StoreContextBuilder storeContextBuilder) {
        this.storeContextBuilder = storeContextBuilder;
    }

    public AggregateContextBuilder getAggregateContextBuilder() {
        return aggregateContextBuilder;
    }

    public EngineBuilder getEngineBuilder() {
        return engineBuilder;
    }

    public FilterContextBuilder getFilterContextBuilder() {
        return filterContextBuilder;
    }

    public PatternBuilder getPatternBuilder() {
        return patternBuilder;
    }

    public ReceiverContextBuilder getReceiverContextBuilder() {
        return receiverContextBuilder;
    }

    public StoreContextBuilder getStoreContextBuilder() {
        return storeContextBuilder;
    }
}
