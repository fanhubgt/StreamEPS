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
package org.streameps.msq;

import org.hornetq.api.core.client.ClientSessionFactory;
import org.streameps.msq.hornetq.IEPSHornetq;

/**
 * An interface for a messaging queue client.
 * 
 * @author  Frank Appiah
 */
public interface IMSQClient extends IJMSClient {

    /**
     * It sets the event processing system messaging queue configuration.
     * @param config The queue configuration instance.
     */
    public void setMSQConfig(IEPSMSQConfig config);

    /**
     * It returns the event processing system messaging queue configuration.
     * @return The event processing system messaging queue configuration instance.
     */
    public IEPSMSQConfig getMSQConfig();

    /**
     * It sets the username used for the connection to the queue server.
     * @param username
     */
    public void setUsername(String username);

    /**
     * It returns the username used for the connection to the queue server.
     * @return The username used for the connection.
     */
    public String getUsername();

    /**
     * It sets the password for establishing connection to the queue server.
     * @param password The password used to connect to the queue server.
     */
    public void setPassword(String password);

    /**
     * It returns the password for establishing connection to the queue server.
     * @return The password used to connect to the queue server.
     */
    public String getPassword();

    /**
     * It sets the client session factory for the client.
     * @param sessionFactory The client session factory for the client.
     */
    public void setClientSessionFactory(ClientSessionFactory sessionFactory);

    /**
     * It returns the client session factory for the client.
     * @return The client session factory for the client.
     */
    public ClientSessionFactory getClientSessionFactory();

    /**
     * It sets the HornetQ runtime server.
     * @param hornetqRuntime
     */
    public void setHornetQRuntime(IEPSHornetq hornetqRuntime);

    /**
     * It returns the HornetQ runtime server.
     * @return The runtime properties of the HornetQ server.
     */
    public IEPSHornetq getHornetQRuntime();
    
}
