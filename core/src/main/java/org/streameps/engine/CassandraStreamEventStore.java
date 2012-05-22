/*
 * ====================================================================
 *  SoftGene Technologies
 * 
 *  (C) Copyright 2012.
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
package org.streameps.engine;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;
import org.streameps.core.IHeader;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.IPayload;
import org.streameps.core.IStreamEvent;
import org.streameps.core.IUnMatchedEventSet;
import org.streameps.core.util.IDUtil;
import org.streameps.core.util.SchemaUtil;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.store.IStoreProperty;
import org.streameps.store.bigdata.ICassandraBDStore;
import org.streameps.store.file.IFileEPStore;
import org.streameps.thread.IEPSExecutorManager;

/**
 *
 * @author Frank Appiah
 */
public class CassandraStreamEventStore<T> implements IHistoryStore<T>, Runnable {

    private String identifier;
    private IEPSExecutorManager executorManager;
    private IStoreContext<Set<T>> storeContext;
    private ICassandraBDStore cassandraBDStore;
    private Map<String, List<String>> groupColumNames;
    private StoreType storeType = StoreType.NOSQL;
    private boolean saveStreamEvent = true;
    public static final String IDENTIFIER = "identifier";
    public static final String EVENT_SOURCE = "eventSource";
    public static final String EVENT_IDENTITY = "eventCertainty";
    public static final String EVENT_ANNOTATION = "eventAnnotation";
    public static final String DETECTION_TIME = "detectionTime";
    public static final String ISCOMPOSABLE = "composable";
    public static final String EVENT_CERTAINTY = "eventCertainty";
    public static final String EVENT_OCCURENCE = "eventOccurence";
    public static final String PAYLOAD_ID = "payloadID";
    public static final String PATTERN_TYPE = "patternType";
    private ILogger logger = LoggerUtil.getLogger(CassandraStreamEventStore.class);

    public CassandraStreamEventStore() {
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
        groupColumNames = new TreeMap<String, List<String>>();
    }

    public CassandraStreamEventStore(String identifier, IEPSExecutorManager executorManager) {
        this.identifier = identifier;
        this.executorManager = executorManager;
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setStoreContexts(List<IStoreContext<IMatchedEventSet<T>>> contexts) {
    }

    public List<IStoreContext<IMatchedEventSet<T>>> getStoreContexts() {
        return null;
    }

    public void addToStore(String group, T event) {
        List<String> columns = groupColumNames.get(group);
        if (event.getClass().isAssignableFrom(IStreamEvent.class) || event.getClass().isAssignableFrom(IStreamEvent.class)) {
            IStreamEvent streamEvent = (IStreamEvent) event;
            IPayload payload = streamEvent.getPayload();
            IHeader header = streamEvent.getHeader();
            String headerID = streamEvent.getHeader().getIdentifier();
            if (group.equalsIgnoreCase(IFileEPStore.STREAMS)) {
                cassandraBDStore.getCassandraDao().setColumnFamilyName(cassandraBDStore.getStreamColumnFamily());
                createStreamColumns(group, headerID, header, streamEvent);
                logger.info("Creating stream event..." + headerID);
            }
        } else {
            Object eventData = event;
            if (group.equalsIgnoreCase(IFileEPStore.PARTICIPANT_GROUP)) {
                cassandraBDStore.getCassandraDao().setColumnFamilyName(cassandraBDStore.getColumnFamilyName());
                cassandraBDStore.getCassandraDao().insert(group, PAYLOAD_ID, IDUtil.getUniqueID(new Date().toString()));
                for (String column : columns) {
                    Object value = SchemaUtil.getPropertyValue(eventData, column);
                    cassandraBDStore.getCassandraDao().insert(group, column, value.toString());
                    logger.info("Group: Participant===Column:" + column + " Value:" + value);
                }
            } else if (group.equalsIgnoreCase(IFileEPStore.FILTER_EVENT_GROUP)) {
                cassandraBDStore.getCassandraDao().setColumnFamilyName(cassandraBDStore.getFilterColumnFamily());
                cassandraBDStore.getCassandraDao().insert(group, PAYLOAD_ID, IDUtil.getUniqueID(new Date().toString()));
                for (String column : columns) {
                    Object value = SchemaUtil.getPropertyValue(eventData, column);
                    cassandraBDStore.getCassandraDao().insert(group, column, value.toString());
                    logger.info("Group: Filter===Column:" + column + " Value:" + value);
                }
            } else if (group.equalsIgnoreCase(IFileEPStore.UNFILTER_EVENT_GROUP)) {
                cassandraBDStore.getCassandraDao().setColumnFamilyName(cassandraBDStore.getFilterColumnFamily());
                cassandraBDStore.getCassandraDao().insert(group, PAYLOAD_ID, IDUtil.getUniqueID(new Date().toString()));
                for (String column : columns) {
                    Object value = SchemaUtil.getPropertyValue(eventData, column);
                    cassandraBDStore.getCassandraDao().insert(group, column, value.toString());
                    logger.info("Group: Filter===Column:" + column + " Value:" + value);
                }
            }
            else if (group.equalsIgnoreCase(IFileEPStore.AGGREGATE_GROUP)) {
                cassandraBDStore.getCassandraDao().setColumnFamilyName(cassandraBDStore.getAggregateColumnFamily());
                cassandraBDStore.getCassandraDao().insert(group, PAYLOAD_ID, IDUtil.getUniqueID(new Date().toString()));
                for (String column : columns) {
                    Object value = SchemaUtil.getPropertyValue(eventData, column);
                    cassandraBDStore.getCassandraDao().insert(group, column, value.toString());
                    logger.info("Group: Aggregate===Column:" + column + " Value:" + value);
                }
            }
        }
    }

    private void createStreamColumns(String group, String headerID, IHeader header, IStreamEvent event) {
        cassandraBDStore.getCassandraDao().insert(group, PAYLOAD_ID, headerID);
        cassandraBDStore.getCassandraDao().insert(group, DETECTION_TIME, header.getDetectionTime().toString());
        cassandraBDStore.getCassandraDao().insert(group, EVENT_ANNOTATION, header.getEventAnnotation());
        cassandraBDStore.getCassandraDao().insert(group, EVENT_CERTAINTY, header.getEventCertainty().toString());
        cassandraBDStore.getCassandraDao().insert(group, EVENT_IDENTITY, header.getEventIdentity());
        cassandraBDStore.getCassandraDao().insert(group, EVENT_OCCURENCE, header.getOccurrenceTime().toString());
        cassandraBDStore.getCassandraDao().insert(group, EVENT_SOURCE, header.getEventSource());
        cassandraBDStore.getCassandraDao().insert(group, ISCOMPOSABLE, Boolean.toString(header.isIsComposable()));

    }

    public boolean isSaveStreamEvent() {
        return saveStreamEvent;
    }

    public void setSaveStreamEvent(boolean saveStreamEvent) {
        this.saveStreamEvent = saveStreamEvent;
    }

    public void removeFromStore(String group, T context) {
    }

    public Set<T> getFromStore(String group, String uniqueIdentifier) {
        SliceQuery<String, String, String> slice = cassandraBDStore.getFactory().createSliceQuery(cassandraBDStore.getKeyspace(),
                new StringSerializer(), new StringSerializer(), new StringSerializer());
        slice.setColumnFamily(group).setKey(uniqueIdentifier);
        List<String> columns = groupColumNames.get(group);
        slice.setColumnNames(columns.toArray(new String[0]));
        QueryResult<ColumnSlice<String, String>> t = slice.execute();
        Set set = new HashSet();
        List<HColumn<String, String>> hColumns = t.get().getColumns();
        if (hColumns != null) {
            for (HColumn<String, String> column : hColumns) {
                set.add(column);
            }
        }
        return set;
    }

    public void loadStore(String url, String username, String password) {
    }

    public void saveToStore(String group, IStoreContext<IMatchedEventSet<T>> storeContext) {
        addToStore(group, storeContext);
    }

    public void saveToStore(String group, IMatchedEventSet<T> eventSet) {
        addToStore(group, eventSet);
    }

    public void saveToStore(String group, IUnMatchedEventSet<T> eventSet) {
        addToStore(group, eventSet);
    }

    public void saveToStore(IStoreContext<IUnMatchedEventSet<T>> storeContext) {
        addToStore(storeContext);
    }

    public void addToStore(IStoreContext<IUnMatchedEventSet<T>> storeContext) {
        addToStore(storeContext.getGroup(), storeContext.getEventSet());
    }

    public void setStoreProperty(IStoreProperty storeProperty) {
    }

    public IStoreProperty getStoreProperty() {
        return null;
    }

    public void save() {
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }

    public void configureStore() {
    }

    public void run() {
    }

    public void addToStore(String group, IStoreContext<IMatchedEventSet<T>> storeContext) {
        addToStore(group, storeContext.getEventSet());
    }

    public void addToStore(String group, IMatchedEventSet<T> eventSet) {
        List<String> columns = groupColumNames.get(group);
        String id = IDUtil.getUniqueID(new Date().toString());
        for (T event : eventSet) {
            if (group.equalsIgnoreCase(IFileEPStore.PATTERN_MATCH_GROUP)) {
                cassandraBDStore.getCassandraDao().setColumnFamilyName(cassandraBDStore.getPatternColumnFamily());
                cassandraBDStore.getCassandraDao().insert(group, PATTERN_TYPE, group);
                for (String column : columns) {
                    Object value = SchemaUtil.getPropertyValue(event, column);
                    cassandraBDStore.getCassandraDao().insert(group, column, value.toString());
                }
            }
        }
    }

    public void addToStore(String group, IUnMatchedEventSet<T> eventSet) {
        List<String> columns = groupColumNames.get(group);
        String id = IDUtil.getUniqueID(new Date().toString());
        for (T event : eventSet) {
            if (group.equalsIgnoreCase(IFileEPStore.PATTERN_UNMATCH_GROUP)) {
                cassandraBDStore.getCassandraDao().setColumnFamilyName(cassandraBDStore.getPatternColumnFamily());
                cassandraBDStore.getCassandraDao().insert(id, PATTERN_TYPE, group);
                for (String column : columns) {
                    Object value = SchemaUtil.getPropertyValue(event, column);
                    cassandraBDStore.getCassandraDao().insert(id, column, value.toString());
                }
            }
        }
    }

    public void setExecutorManager(IEPSExecutorManager executorManager) {
        this.executorManager = executorManager;
    }

    public IEPSExecutorManager getExecutorManager() {
        return executorManager;
    }

    public IHistoryStore<T> getHistoryStore() {
        return this;
    }

    public void setHistoryStore(IHistoryStore<T> historyStore) {
    }

    public void setCassandraBDStore(ICassandraBDStore cassandraBDStore) {
        this.cassandraBDStore = cassandraBDStore;
    }

    public ICassandraBDStore getCassandraBDStore() {
        return cassandraBDStore;
    }

    public void setGroupColumNames(Map<String, List<String>> groupColumNames) {
        this.groupColumNames = groupColumNames;
    }

    public Map<String, List<String>> getGroupColumNames() {
        return groupColumNames;
    }
}
