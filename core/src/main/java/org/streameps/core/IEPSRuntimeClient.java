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
package org.streameps.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import org.streameps.engine.IEPSEngine;
import org.streameps.engine.builder.AggregateContextBuilder;
import org.streameps.engine.builder.EngineBuilder;
import org.streameps.engine.builder.FilterContextBuilder;
import org.streameps.engine.builder.PatternBuilder;
import org.streameps.engine.builder.ReceiverContextBuilder;
import org.streameps.engine.builder.StoreContextBuilder;

/**
 * Interface to the stream event processing system.
 *
 * @author  Frank Appiah
 */
public interface IEPSRuntimeClient extends Serializable{

    /**
     * It returns the processing engine for the event processing platform.
     * @return an instance of the event processing engine.
     */
    public IEPSEngine getEngine();

    /**
     * It sets the engine of the runtime client.
     * @param engine an instance of the event processing engine.
     */
    public void setEngine(IEPSEngine engine);

    
    /**
     * It rebuilds the engine after the builders have change some properties
     * in the engine.
     */
    public void restartEngine();

    /**
     * It rebuilds the engine after the builders have change some properties
     * in the engine.
     */
    public IEPSEngine startEngine();
    /**
     * It loads a specify configurable developer implementation from the specified
     * location. The language implementation is defaulted to groovy. Dynamic loading
     * of event pattern structure, filter context, store context and aggregate context.
     *
     * Use the Java Beans Facility : java.beans
     * @param groovyConfigPath
     */
    public void loadBuilders(String groovyConfigPath);

    /**
     * It returns the property change support to this runtime client.
     * @return The instance of the property change support.
     */
    public PropertyChangeSupport getPropertyChangeSupport();

    /**
     * Its sets the property change support to this runtime client.
     * @param changeSupport The instance of the property change support.
     */
    public void setPropertyChangeSupport(PropertyChangeSupport changeSupport);

    public AggregateContextBuilder getAggregateContextBuilder();

    public EngineBuilder getEngineBuilder();

    public FilterContextBuilder getFilterContextBuilder();

    public PatternBuilder getPatternBuilder();

    public ReceiverContextBuilder getReceiverContextBuilder();

    public StoreContextBuilder getStoreContextBuilder();

    public void setAggregateContextBuilder(AggregateContextBuilder aggregateContextBuilder);

    public void setEngineBuilder(EngineBuilder engineBuilder);

    public void setFilterContextBuilder(FilterContextBuilder filterContextBuilder);

    public void setPatternBuilder(PatternBuilder patternBuilder);

    public void setReceiverContextBuilder(ReceiverContextBuilder receiverContextBuilder);

    public void setStoreContextBuilder(StoreContextBuilder storeContextBuilder);

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener changeListener);
}
