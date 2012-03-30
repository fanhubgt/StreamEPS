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
import org.streameps.msq.IMSQClient;

/**
 * A manager for the queue configuration management operations.
 * 
 * @author  Frank Appiah
 */
public interface QueueManager {

    /**
     * 
     * @param configuration
     */
    public void configureQueue(JMSConfiguration configuration);

    /**
     * It adds the queue configuration to the queue configuration lists.
     * @param queueConfiguration A queue instance to add to the JMS configuration.
     */
    public void addQueueConfiguration(QueueConfiguration queueConfiguration);

    /**
     * It removes the queue configuration from the queue configurations.
     * @param queueConfiguration A queue instance to remove to the JMS configuration.
     */
    public void removeQueueConfiguration(QueueConfiguration queueConfiguration);

    /**
     * It returns the JMS configuration after the queue configuration list is
     * updated.
     * @return The JMS configuration for HornetQ runtime.
     */
    public JMSConfiguration getJMSConfiguration();

    /**
     * It creates a queue configuration for the JMS configuration queue list.
     * @param name The name of the queue.
     * @param selector The queue name selector or filter.
     * @param durable Whether to create a durable session or not.
     * @param bindings The specified bindings which starts with the prefix <b>/queue/<b>.
     * @return An instance of the queue configuration.
     */
    public void createQueueConfiguration(String name, String selector, boolean durable, String... bindings);

    /**
     * It sets the messaging client for the queue manager.
     * @param client An instance of the messaging client.
     */
    public void setClient(IMSQClient client);
}
