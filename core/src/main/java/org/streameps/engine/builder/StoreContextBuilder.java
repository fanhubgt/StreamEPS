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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.prettyprint.cassandra.dao.SimpleCassandraDao;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.util.IDUtil;
import org.streameps.engine.CassandraStreamEventStore;
import org.streameps.engine.IHistoryStore;
import org.streameps.engine.IStoreContext;
import org.streameps.store.IStoreProperty;
import org.streameps.store.bigdata.CassandraBDStore;
import org.streameps.store.bigdata.ICassandraBDStore;
import org.streameps.thread.IEPSExecutorManager;

/**
 *
 * @author Frank Appiah
 */
public class StoreContextBuilder implements IStoreContextBuilder {

    private List<IHistoryStore> historyStores;
    private IStoreContext<IMatchedEventSet> storeContext = null;
    private IMatchedEventSet eventSet = null;
    private List<IStoreProperty> storeProperties;
    private Map<String, List<IStoreProperty>> storeMap;
    private IStoreProperty storeProperty;
    private IEPSExecutorManager executorManager;
    private IHistoryStore historyStore;
    private ICassandraBDStore cassandraBDStore;

    public StoreContextBuilder() {
        storeProperties = new ArrayList<IStoreProperty>();
        storeMap = new HashMap<String, List<IStoreProperty>>();
        historyStores = new ArrayList<IHistoryStore>();
        cassandraBDStore = new CassandraBDStore();
    }

    public StoreContextBuilder(List<IHistoryStore> historyStores) {
        this.historyStores = historyStores;
        cassandraBDStore = new CassandraBDStore();
    }

    public StoreContextBuilder(List<IHistoryStore> historyStores, IMatchedEventSet eventSet) {
        this.historyStores = historyStores;
        this.eventSet = eventSet;
        cassandraBDStore = new CassandraBDStore();
    }

    public void setHistoryStores(List<IHistoryStore> historyStores) {
        this.historyStores = historyStores;
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

    public StoreContextBuilder buildCassandraStore(String host, String port, String clusterName, String columnFamilyName, String keySpaceName) {
        CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator(host + ":" + port);
        ThriftCluster cluster = new ThriftCluster(clusterName, cassandraHostConfigurator);
        Keyspace keySpace = HFactory.createKeyspace(keySpaceName, cluster);
        SimpleCassandraDao cassandraDao = new SimpleCassandraDao();
        cassandraDao.setKeyspace(keySpace);
        cassandraDao.setColumnFamilyName(columnFamilyName);

        cassandraBDStore.setKeySpaceName(keySpaceName);
        cassandraBDStore.setCassandraDao(cassandraDao);
        cassandraBDStore.setKeyspace(keySpace);
        cassandraBDStore.setThriftCluster(cluster);
        cassandraBDStore.setClusterName(clusterName);

        return this;
    }

    public StoreContextBuilder buildCassandraColumnFamilies(String patternFamily, String aggregateFamily, String knowledgeFamily, String filterFamily, String assertionFamily, String streamFamily) {

        cassandraBDStore.setAggregateColumnFamily(aggregateFamily);
        cassandraBDStore.setAssertionColumnFamily(assertionFamily);
        cassandraBDStore.setFilterColumnFamily(filterFamily);
        cassandraBDStore.setStreamColumnFamily(streamFamily);
        cassandraBDStore.setPatternColumnFamily(patternFamily);
        cassandraBDStore.setKnowledgeColumnFamily(knowledgeFamily);
        return this;
    }

    public CassandraStreamEventStore getCassandraEventStore(Map<String, List<String>> groupColunNames, boolean saveStreamEvents) {
        CassandraStreamEventStore eventStore = new CassandraStreamEventStore(IDUtil.getUniqueID(new Date().toString()), executorManager);
        eventStore.setCassandraBDStore(cassandraBDStore);
        eventStore.setGroupColumNames(groupColunNames);
        eventStore.setExecutorManager(executorManager);
        eventStore.setSaveStreamEvent(saveStreamEvents);
        return eventStore;
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

    public void setEventSet(IMatchedEventSet eventSet) {
        this.eventSet = eventSet;
    }

    public void setStoreContext(IStoreContext<IMatchedEventSet> storeContext) {
        this.storeContext = storeContext;
    }

    public void setHistoryStore(IHistoryStore historyStore) {
        this.historyStore = historyStore;
    }

    public void setStoreProperty(IStoreProperty storeProperty) {
        this.storeProperty = storeProperty;
    }

    public IMatchedEventSet getEventSet() {
        return eventSet;
    }

    public IStoreContext<IMatchedEventSet> getStoreContext() {
        return storeContext;
    }
}
