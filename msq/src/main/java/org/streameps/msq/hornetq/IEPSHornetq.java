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

import javax.naming.InitialContext;
import org.hornetq.api.core.TransportConfiguration;
import org.streameps.msq.IEPSMSQConfig;
import org.hornetq.core.config.Configuration;
import org.hornetq.core.server.HornetQServer;
import org.hornetq.core.server.cluster.QueueConfiguration;
import org.hornetq.jms.server.JMSServerManager;
import org.jnp.server.Main;
import org.streameps.msq.IMSQClient;

/**
 * An interface for the event processing runtime system with the specific
 * binding to a Hornet Queue runtime server.
 * 
 * @author  Frank Appiah
 */
public interface IEPSHornetq {

    /**
     * It returns the Hornet Queue runtime server.
     * @param hornetQServer A runtime Hornet Queue server.
     */
    public void setHornetqServer(HornetQServer hornetQServer);

    /**
     * It returns the Hornet Queue runtime server.
     * @return A runtime instance of the Hornet runtime server.
     */
    public HornetQServer getHornetQServer();

    /**
     * It sets the configuration properties for the Hornet Queue server.
     * @param hornetqConfig An instance of the EPS configuration for the server.
     */
    public void setEPSHornetqConfigurator(IEPSMSQConfig hornetqConfig);

    /**
     * It returns the configuration for the Hornet Queue server.
     * @return An instance of the EPS configuration for the server.
     */
    public IEPSMSQConfig getEPSHornetqConfigurator();

    /**
     * It creates a Hornet Queue configuration instance for the runtime client.
     * @return The instance of a Hornet Queue configuration for the server.
     */
    public Configuration createConfigurator();

    /**
     * It creates the configuration instance for the Hornet Queue server.
     * @param hornetqConfig The instance of a Hornet Queue configuration for the server.
     * @return The instance of a Hornet Queue configuration for the server.
     */
    public Configuration createConfigurator(IEPSMSQConfig hornetqConfig);

    /**
     * It sets the instance of configuration of the HornetQ server.
     * @param configuration The instance of configuration of the HornetQ server.
     */
    public void setConfiguration(Configuration configuration);
    
    /**
     * It sets the JNDI server for the HornetQ server instance.
     * @param jnpServer An instance of the JNDI server.
     */
    public void setJNDIServer(Main jnpServer);

    /**
     * It returns the JNDI server for the HornetQ server instance.
     * @return An instance of the JNDI server.
     */
    public Main getJNDIServer();

    /**
     * It sets the naming initial context for the HornetQ server.
     * @param context The initial context instance of the server.
     */
    public void setInitialContext(InitialContext context);

    /**
     *It returns the naming initial context for the HornetQ server.
     * @return The initial context instance of the server.
     */
    public InitialContext getInitialContext();

    /**
     * It sets the transport configuration of the HornetQ server.
     * @param configuration The transport configuration of the HornetQ server.
     */
    public void setTransportConfiguration(TransportConfiguration configuration);

    /**
     * It returns the transport configuration of the HornetQ server.
     * @return The transport configuration of the HornetQ server.
     */
    public TransportConfiguration getTransportConfiguration();

    /**
     * It sets the queue configuration of the HornetQ server.
     * @param configuration The queue configuration of the HornetQ server.
     */
    public void setQueueConfiguration(QueueConfiguration configuration);

    /**
     * It returns the queue configuration of the HornetQ server.
     * @return The queue configuration of the HornetQ server.
     */
    public QueueConfiguration getQueueConfiguration();

    /**
     * It sets the JMS server manager for the HornetQ server.
     * @param serverManager the JMS server manager for the HornetQ server.
     */
    public void setJMSServerManager(JMSServerManager serverManager);

    /**
     * It returns the JMS server manager for the HornetQ server.
     * @return The JMS server manager for the HornetQ server.
     */
    public JMSServerManager getJMSServerManager();

    /**
     * It sets the MSQ client of the HornetQ Server.
     * @param client An instance of the runtime MSQ client of the HornetQ server.
     */
    public void setMSQClient(IMSQClient client);

    /**
     *  It returns the MSQ client of the HornetQ Server.
     * @return An instance of the runtime MSQ client of the HornetQ server.
     */
    public IMSQClient getMSQClient();

    /**
     *  It sets the queue manager for the HornetQ queue configuration
     * management.
     * @param manager The queue manager for the HornetQ queue configurations.
     */
    public void setQueueManager(EPSQueueManager manager);

    /**
     * It returns the queue manager for the HornetQ queue configurations.
     * management.
     * @return The queue manager for the HornetQ queue configurations.
     */
    public EPSQueueManager getQueueManager();
}
