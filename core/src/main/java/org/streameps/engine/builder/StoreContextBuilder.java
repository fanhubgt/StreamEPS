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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.streameps.core.IMatchedEventSet;
import org.streameps.engine.IHistoryStore;
import org.streameps.engine.IStoreContext;
import org.streameps.store.IStoreProperty;
import org.streameps.thread.IEPSExecutorManager;

/**
 *
 * @author Frank Appiah
 */
public class StoreContextBuilder {

    private List<IHistoryStore> historyStores;
    private IStoreContext<IMatchedEventSet> storeContext = null;
    private IMatchedEventSet eventSet = null;
    private List<IStoreProperty> storeProperties;
    private Map<String, List<IStoreProperty>> storeMap;
    private IStoreProperty storeProperty;
    private IEPSExecutorManager executorManager;
    private IHistoryStore historyStore;

    public StoreContextBuilder() {
        storeProperties = new ArrayList<IStoreProperty>();
        storeMap = new HashMap<String, List<IStoreProperty>>();
        historyStores = new ArrayList<IHistoryStore>();
    }

    public StoreContextBuilder(List<IHistoryStore> historyStores) {
        this.historyStores = historyStores;
    }

    public StoreContextBuilder(List<IHistoryStore> historyStores, IMatchedEventSet eventSet) {
        this.historyStores = historyStores;
        this.eventSet = eventSet;
    }

    public StoreContextBuilder setHistoryStores(List<IHistoryStore> historyStores) {
        this.historyStores = historyStores;
        return this;
    }

    public List<IHistoryStore> getHistoryStores() {
        return this.historyStores;
    }

    /**
     * It adds a history store to the list of stores and expects that the store
     * property is set before adding the history store.
     * @param historyStore The store be added to the list.
     * @return Just itself.
     */
    public StoreContextBuilder addHistoryStore(IHistoryStore historyStore) {
        this.historyStores.add(historyStore);
        this.historyStore = historyStore;
        storeMap.put(historyStore.getIdentifier(), storeProperties);
        return this;
    }

    public StoreContextBuilder addStoreProperty(IStoreProperty storeProperty) {
        this.storeProperties.add(storeProperty);
        this.storeProperty = storeProperty;
        return this;
    }

    public IStoreProperty getStoreProperty() {
        return storeProperty;
    }

    public StoreContextBuilder removeStoreProperty(IStoreProperty storeProperty) {
        getStoreProperties().remove(storeProperty);
        return this;
    }

    public StoreContextBuilder setStoreProperties(List<IStoreProperty> storeProperties) {
        this.storeProperties = storeProperties;
        return this;
    }

    public List<IStoreProperty> getStoreProperties() {
        return this.storeProperties;
    }

    public Map<String, List<IStoreProperty>> getStoreMap() {
        return storeMap;
    }

    public StoreContextBuilder setExecutorManager(IEPSExecutorManager executorManager) {
        this.executorManager = executorManager;
        return this;
    }

    public IEPSExecutorManager getExecutorManager() {
        return executorManager;
    }

    /**
     * It returns the immediate history store in the list of history stores
     * with the immediate store property.
     * @return  The immediate history store.
     */
    public IHistoryStore getHistoryStore() {
        this.historyStore.setStoreProperty(storeProperty);
        return this.historyStore;
    }
}
