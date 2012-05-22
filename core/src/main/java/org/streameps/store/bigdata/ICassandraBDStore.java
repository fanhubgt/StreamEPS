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

/**
 *
 * @author  Frank Appiah
 */
public interface ICassandraBDStore {

    public void setCassandraDao(SimpleCassandraDao cassandraDao);

    public SimpleCassandraDao getCassandraDao();

    public void setKeyspace(Keyspace keyspace);

    public Keyspace getKeyspace();

    public void setColumnFamilyName(String colmnName);

    public String getColumnFamilyName();

    public void setKeySpaceName(String keySpace);

    public String getKeySpaceName();

    public void setFactory(HFactory factory);

    public HFactory getFactory();

    public void setThriftCluster(ThriftCluster thriftCluster);

    public ThriftCluster getThriftCluster();

    CassandraHostConfigurator getCassandraHostConfigurator();

    void setCassandraHostConfigurator(CassandraHostConfigurator cassandraHostConfigurator);

    String getClusterName();

    void setClusterName(String clusterName);

    String getAggregateColumnFamily();

    String getAssertionColumnFamily();

    String getFilterColumnFamily();

    String getKnowledgeColumnFamily();

    String getPatternColumnFamily();

    String getStreamColumnFamily();

    void setAggregateColumnFamily(String aggregateColumnFamily);

    void setAssertionColumnFamily(String assertionColumnFamily);

    void setFilterColumnFamily(String filterColumnFamily);

    void setKnowledgeColumnFamily(String knowledgeColumnFamily);

    void setPatternColumnFamily(String patternColumnFamily);

    void setStreamColumnFamily(String streamColumnFamily);
}
