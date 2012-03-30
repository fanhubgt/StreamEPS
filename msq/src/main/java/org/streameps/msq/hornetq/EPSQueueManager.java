/*
 * ====================================================================
 *  StreamEPS Platform
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
package org.streameps.msq.hornetq;

import org.hornetq.jms.server.config.JMSConfiguration;
import org.hornetq.jms.server.config.QueueConfiguration;
import org.hornetq.jms.server.config.impl.QueueConfigurationImpl;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.msq.IMSQClient;

/**
 *
 * @author Frank Appiah
 */
public class EPSQueueManager implements QueueManager {

    private JMSConfiguration configuration;
    private QueueConfiguration queueConfiguration;
    private IMSQClient qClient;
    private ILogger logger = LoggerUtil.getLogger(EPSQueueManager.class);

    public EPSQueueManager() {
    }

    public EPSQueueManager(JMSConfiguration configuration, IMSQClient client) {
        this.configuration = configuration;
        this.qClient = client;
    }

    public void configureQueue(JMSConfiguration configuration) {
        this.configuration = configuration;
        boolean durable = qClient.getMSQConfig().getPerformanceParams().isDurable();
        createQueueConfiguration(StreamEPSConstants.EPS_QUEUE, null, durable, "/queue/" + qClient.getMSQConfig().getQueueName());
        createQueueConfiguration(StreamEPSConstants.COMPUTE_QUEUE, null, durable, StreamEPSConstants.COMPUTE_QUEUE_NAME);
        createQueueConfiguration(StreamEPSConstants.COMPUTE_EXCEPTION_QUEUE, null, durable, StreamEPSConstants.COMPUTE_EXCEPTION_QUEUE_NAME);
        logger.info("[QueueManager] The queues are properly configured.");
    }

    public void addQueueConfiguration(QueueConfiguration queueConfiguration) {
        configuration.getQueueConfigurations().add(queueConfiguration);
    }

    public void removeQueueConfiguration(QueueConfiguration queueConfiguration) {
        configuration.getQueueConfigurations().remove(queueConfiguration);
    }

    public JMSConfiguration getJMSConfiguration() {
        return this.configuration;
    }

    public void createQueueConfiguration(String name, String selector, boolean durable, String... bindings) {
        queueConfiguration = new QueueConfigurationImpl(name, selector, durable, bindings);
        addQueueConfiguration(queueConfiguration);
        logger.info("[QueueManager] Creating the queue:"+ name +" for the client connection.");
    }

    public void setClient(IMSQClient client) {
        this.qClient=client;
    }
    
}
