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
package org.streameps.store.bigdata;

import me.prettyprint.hector.api.Cluster;
import java.util.HashMap;
import java.util.Map;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createColumnQuery;
import static me.prettyprint.hector.api.factory.HFactory.createMultigetSliceQuery;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;

/**
 *
 * @author Frank Appiah
 */
public class EPSCassandraConnector {

    private String keySpaceName = "StreamEPSLite";
    private String clusterName = "StreamEPSCluster";
    private String hostPort = "localhost:9160";
    private String columnFamily = "ParticipationContext";
    /** Column name where values are stored */
    private String columnName = "init";
    private final StringSerializer serializer = StringSerializer.get();
    private Keyspace keyspace = null;

    public EPSCassandraConnector() {
    }

    public void connect() {
        Cluster cluster = getOrCreateCluster(clusterName, hostPort);
        keyspace = createKeyspace(keySpaceName, cluster);
    }

    public void connect(String clusterName, String keySpaceName, String hostPort) {
        setClusterName(clusterName);
        setHostPort(hostPort);
        setKeySpaceName(keySpaceName);
        Cluster cluster = getOrCreateCluster(clusterName, hostPort);
        keyspace = createKeyspace(keySpaceName, cluster);
    }
    /* Insert a new value keyed by key
     *
     * @param key   Key for the value
     * @param value the String value to insert
     */

    public <K> void insert(final K key, final String value, Serializer<K> keySerializer) {
        createMutator(keyspace, keySerializer).insert(
                key, columnFamily, createColumn(columnName, value, serializer, serializer));
    }

    /**
     * Get a string value.
     *
     * @return The string value; null if no value exists for the given key.
     */
    public <K> String get(final K key, Serializer<K> keySerializer) throws HectorException {
        ColumnQuery<K, String, String> q = createColumnQuery(keyspace, keySerializer, serializer, serializer);
        QueryResult<HColumn<String, String>> r = q.setKey(key).
                setName(columnName).
                setColumnFamily(columnFamily).
                execute();
        HColumn<String, String> c = r.get();
        return c == null ? null : c.getValue();
    }

    /**
     * Get multiple values
     * @param keys
     * @return
     */
    public <K> Map<K, String> getMulti(Serializer<K> keySerializer, K... keys) {
        MultigetSliceQuery<K, String, String> q = createMultigetSliceQuery(keyspace, keySerializer, serializer, serializer);
        q.setColumnFamily(columnFamily);
        q.setKeys(keys);
        q.setColumnNames(columnName);

        QueryResult<Rows<K, String, String>> r = q.execute();
        Rows<K, String, String> rows = r.get();
        Map<K, String> ret = new HashMap<K, String>(keys.length);
        for (K k : keys) {
            HColumn<String, String> c = rows.getByKey(k).getColumnSlice().getColumnByName(columnName);
            if (c != null && c.getValue() != null) {
                ret.put(k, c.getValue());
            }
        }
        return ret;
    }

    /**
     * Insert multiple values
     */
    public <K> void insertMulti(Map<K, String> keyValues, Serializer<K> keySerializer) {
        Mutator<K> m = createMutator(keyspace, keySerializer);
        for (Map.Entry<K, String> keyValue : keyValues.entrySet()) {
            m.addInsertion(keyValue.getKey(), columnFamily,
                    createColumn(columnName, keyValue.getValue(), keyspace.createClock(), serializer, serializer));
        }
        m.execute();
    }

    /**
     * Delete multiple values
     */
    public <K> void delete(Serializer<K> keySerializer, K... keys) {
        Mutator<K> m = createMutator(keyspace, keySerializer);
        for (K key : keys) {
            m.addDeletion(key, columnFamily, columnName, serializer);
        }
        m.execute();
    }

    public void setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public String getKeySpaceName() {
        return keySpaceName;
    }

    public void setKeySpaceName(String keySpaceName) {
        this.keySpaceName = keySpaceName;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

    public Keyspace getKeyspace() {
        return keyspace;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterName() {
        return clusterName;
    }
}
