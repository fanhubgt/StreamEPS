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

import org.streameps.msq.jms.IEPSJMSConfigurator;

/**
 * An interface for the embedded Hortneq Server implementation.
 * 
 * @author  Frank Appiah
 */
public interface IEPSHornetQEmbedded extends IEPSMSQConfigurator {

    /**
     * It sets the queue manager for the queue configurations.
     * @param sQueueManager  The queue manager for the queue configurations.
     */
    public void setQueueManager(QueueManager sQueueManager);

    /**
     * It returns the queue manager for the queue configurations.
     * @return The queue manager for the queue configurations.
     */
    public QueueManager getQueueManager();

    /**
     * This is the client instance type of the configuration.
     * @return An instance of the JMS configuration.
     */
    public IEPSJMSConfigurator getJMSConfigurator();

    /**
     * It sets the logging indicator for the StreamEPS parameters.
     * @param log The logging indicator.
     */
    public void setLogOn(boolean log);

    /**
     * It creates the JMS resources for the embedded server.
     */
    public void createJMSResources();

    /**
     * It creates the HornetQ embedded server.
     */
    public void createHornetQ();

    /**
     * It creates the JNDI server.
     */
    public void createJNDIServer();

    /**
     * It creates the JMS manager for the embedded server.
     */
    public void createJMSManager();
}
