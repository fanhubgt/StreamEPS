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

import me.prettyprint.cassandra.dao.SimpleCassandraDao;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import static me.prettyprint.hector.api.factory.HFactory.createColumnQuery;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMultigetSliceQuery;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;

/**
 *
 * @author Frank Appiah
 */
public class CassandraBDStore implements ICassandraBDStore {

    private SimpleCassandraDao cassandraDao;
    private Keyspace keyspace;
    private String columnFamilyName, patternColumnFamily, aggregateColumnFamily;
    private String knowledgeColumnFamily, filterColumnFamily, assertionColumnFamily;
    private String streamColumnFamily;
    private String keySpaceName;
    private HFactory factory;
    private ThriftCluster thriftCluster;
    private CassandraHostConfigurator cassandraHostConfigurator;
    private String clusterName;

    public CassandraBDStore() {
    }

    public CassandraBDStore(SimpleCassandraDao cassandraDao, Keyspace keyspace, String columnFamilyName, String keySpaceName) {
        this.cassandraDao = cassandraDao;
        this.keyspace = keyspace;
        this.columnFamilyName = columnFamilyName;
        this.keySpaceName = keySpaceName;
    }

    public CassandraBDStore(SimpleCassandraDao cassandraDao, Keyspace keyspace, String columnFamilyName, String patternColumnFamily, String aggregateColumnFamily, String knowledgeColumnFamily, String filterColumnFamily, String assertionColumnFamily, String streamColumnFamily, String keySpaceName, HFactory factory, ThriftCluster thriftCluster, CassandraHostConfigurator cassandraHostConfigurator, String clusterName) {
        this.cassandraDao = cassandraDao;
        this.keyspace = keyspace;
        this.columnFamilyName = columnFamilyName;
        this.patternColumnFamily = patternColumnFamily;
        this.aggregateColumnFamily = aggregateColumnFamily;
        this.knowledgeColumnFamily = knowledgeColumnFamily;
        this.filterColumnFamily = filterColumnFamily;
        this.assertionColumnFamily = assertionColumnFamily;
        this.streamColumnFamily = streamColumnFamily;
        this.keySpaceName = keySpaceName;
        this.factory = factory;
        this.thriftCluster = thriftCluster;
        this.cassandraHostConfigurator = cassandraHostConfigurator;
        this.clusterName = clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setStreamColumnFamily(String streamColumnFamily) {
        this.streamColumnFamily = streamColumnFamily;
    }

    public void setKnowledgeColumnFamily(String knowledgeColumnFamily) {
        this.knowledgeColumnFamily = knowledgeColumnFamily;
    }

    public String getStreamColumnFamily() {
        return streamColumnFamily;
    }

    public String getKnowledgeColumnFamily() {
        return knowledgeColumnFamily;
    }

    public void setPatternColumnFamily(String patternColumnFamily) {
        this.patternColumnFamily = patternColumnFamily;
    }

    public void setFilterColumnFamily(String filterColumnFamily) {
        this.filterColumnFamily = filterColumnFamily;
    }

    public void setAssertionColumnFamily(String assertionColumnFamily) {
        this.assertionColumnFamily = assertionColumnFamily;
    }

    public void setAggregateColumnFamily(String aggregateColumnFamily) {
        this.aggregateColumnFamily = aggregateColumnFamily;
    }

    public String getPatternColumnFamily() {
        return patternColumnFamily;
    }

    public String getFilterColumnFamily() {
        return filterColumnFamily;
    }

    public String getAssertionColumnFamily() {
        return assertionColumnFamily;
    }

    public String getAggregateColumnFamily() {
        return aggregateColumnFamily;
    }

    public void setCassandraDao(SimpleCassandraDao cassandraDao) {
        this.cassandraDao = cassandraDao;
    }

    public SimpleCassandraDao getCassandraDao() {
        return this.cassandraDao;
    }

    public void setKeyspace(Keyspace keyspace) {
        this.keyspace = keyspace;
    }

    public Keyspace getKeyspace() {
        return this.keyspace;
    }

    public void setColumnFamilyName(String colmnName) {
        this.columnFamilyName = colmnName;
    }

    public String getColumnFamilyName() {
        return this.columnFamilyName;
    }

    public String getKeySpaceName() {
        return this.keySpaceName;
    }

    public void setKeySpaceName(String keySpace) {
        this.keySpaceName = keySpace;
    }

    public void setFactory(HFactory factory) {
        this.factory = factory;
    }

    public HFactory getFactory() {
        return this.factory;
    }

    public void setThriftCluster(ThriftCluster thriftCluster) {
        this.thriftCluster = thriftCluster;
    }

    public ThriftCluster getThriftCluster() {
        return this.thriftCluster;
    }

    public void setCassandraHostConfigurator(CassandraHostConfigurator cassandraHostConfigurator) {
        this.cassandraHostConfigurator = cassandraHostConfigurator;
    }

    public CassandraHostConfigurator getCassandraHostConfigurator() {
        return cassandraHostConfigurator;
    }

    public void createStoreProperties() {
        keyspace = createKeyspace(keySpaceName, thriftCluster);
        cassandraDao.setKeyspace(keyspace);
    }
}
