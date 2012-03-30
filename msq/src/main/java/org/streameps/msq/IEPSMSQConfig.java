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

import java.util.Map;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.streameps.msq.hornetq.PerformanceParams;

/**
 * The interface for configuring the HornetQ Server for the implementation
 * of the queueing system in the event processing system.
 * 
 * @author  Frank Appiah
 */
public interface IEPSMSQConfig {

    /**
     * It sets the flag of the persistence property of the queueing.
     * @param persist The persistence enabled indicator.
     */
    public void setPersistenceEnabled(boolean persist);

    /***
     * It returns the indicator whether to persist the events/messages on received
     * from the an event stream source.
     * @return The indicator to determine whether the persistence is enabled or not.
     */
    public boolean getPersistenceEnabled();

    /**
     * It sets the security enabled indicator of the queueing server.
     * @param securityEnabled The security enabled flag of the server.
     */
    public void setSecurityEnabled(boolean securityEnabled);

    /**
     * It returns the security enabled indicator of the queueing server.
     * @return The security enabled flag of the server.
     */
    public boolean getSecurityEnabled();

    /**
     * It sets the queue name of the server.
     * @param queueName The queue name for the queueing server.
     */
    public void setQueueName(String queueName);

    /**
     * It returns the queue name of the server.
     * @return The queue name for the queueing server.
     */
    public String getQueueName();

    /**
     * It sets the host address of the queueing server.
     * @param host The host address of the queueing server.
     */
    public void setHostAddress(String host);

    /**
     * It returns the host address of the queueing server.
     * @return The host address of the queueing server.
     */
    public String getHostAddress();

    /**
     * It sets the stream properties location for the configuration of the 
     * queue server performance parameters.
     * @param path The path to the stream properties configuration file.
     */
    public void setStreamPropertiesPath(String path);

    /**
     * It returns the stream properties location for the configuration of the
     * queue server performance parameters.
     * @return The path to the stream properties configuration file.
     */
    public String getStreamPropertiesPath();

    /**
     * It sets the port used to listen for stream events from the queueing server.
     * @param port The port of the queueing server.
     */
    public void setPort(String port);

    /**
     * It returns the port used to listen for stream events from the queueing server.
     *
     * @return The port of the queueing server.
     */
    public String getPort();

    /**
     * It sets the additional parameters that will be needed for the queueing server.
     * 
     * @param options An optional parameter map.
     */
    public void setOptions(Map<String, String> options);

    /**
     * It returns the additional parameters that will be needed for the queueing server.
     * 
     * @return An optional parameter map.
     */
    public Map<String, String> getOptions();

    /**
     * It sets the receiver timeout of the queueing server.
     * @param timestamp The receiver timeout of the queueing server.
     */
    public void setReceiverTimeOut(long timestamp);

    /**
     * It returns the receiver timeout of the queueing server.
     * @return The receiver timeout of the queueing server.
     */
    public long getReceiverTimeOut();

    /**
     * It sets the connection factory name for the messaging queue.
     * @param factoryName The connection factory name for the messaging queue.
     */
    public void setConnectionFactoryName(String factoryName);

    /**
     * It returns the connection factory name for the messaging queue.
     * @return The connection factory name for the messaging queue.
     */
    public String getConnectionFactoryName();

    /**
     * It sets the path to the JNDI configuration file.
     * @param jndiPath The path to the configuration file.
     */
    public void setJNDILocationPath(String jndiPath);

    /**
     * It returns the path to the JNDI configuration file.
     * @return The path to the configuration file.
     */
    public String getJNDILocationPath();

    /**
     * It sets the message listener class for the configuration instance.
     * @param listenerClass The custom message listener class developed by the
     * developer.
     */
    public void setMessageListenerClass(String listenerClass);

    /**
     * It returns the message listener class for the configuration instance.
     * @return The custom message listener class developed by the
     * developer.
     */
    public String getMessageListenerClass();

    /***
     * It sets the session transacted indicator for this configuration.
     * @param transact An indicator whether to transact a session created or not.
     */
    public void setSessionTransacted(boolean transact);

    /**
     * It returns the session transacted indicator.
     * @return An indicator whether to transact a session created or not.
     */
    public boolean getSessionTransacted();

    /**
     * It sets the disable message indicator.
     * @param messageID An indicator whether to enable message timestamp or
     * not.
     */
    public void setDisableMessageID(boolean messageID);

    /**
     * It returns the disable message identifier indicator set on the
     * @return  An indicator whether to enable message timestamp or
     * not.
     */
    public boolean getDisableMessageID();

    /**
     * It returns the disable message timestamp of the configuration instance.
     * @return An indicator whether to enable message timestamp or
     * not.
     */
    public boolean getDisableMessageTimestamp();

    /**
     * It sets the disable message timestamp of the configuration instance.
     * @param enableTimestamp An indicator whether to enable message timestamp or
     * not.
     */
    public void setDisableMessageTimestamp(boolean enableTimestamp);

    /**
     * It sets the time to live for the producer of events to the queue server.
     * @param timestamp The time to live for events sent.
     */
    public void setTimeToLive(long timestamp);

    /**
     * It returns the time to live for the producer of events to the queue server.
     * @return The time to live for events sent.
     */
    public long getTimeToLive();

    /**
     * It sets the client consumer of the HornetQ server.
     * @param clientConsumer The client consumer of the HornetQ server.
     */
    public void setClientConsumer(ClientConsumer clientConsumer);

    /**
     * It returns the client consumer of the HornetQ server.
     * @return The client consumer of the HornetQ server.
     */
    public ClientConsumer getClientConsumer();

    /**
     * It sets the client producer for the HornetQ server.
     * @param clientProducer The client producer of the HornetQ server.
     */
    public void setClientProducer(ClientProducer clientProducer);

    /**
     * It returns the client producer for the HornetQ server.
     * @return The client producer of the HornetQ server.
     */
    public ClientProducer getClientProducer();

    /**
     * It sets the client session for the HornetQ server.
     * @param clientSession  The client session for the HornetQ server.
     */
    public void setClientSession(ClientSession clientSession);

    /**
     * It returns the client session for the HornetQ server.
     * @return The client session for the HornetQ server.
     */
    public ClientSession getClientSession();

    /**
     * It sets the client session factory created from the HornetQ server.
     * @param clientSessionFactory The created client session factory.
     */
    public void setClientSessionFactory(ClientSessionFactory clientSessionFactory);

    /**
     * It returns the client session factory created from the HornetQ server.
     * @return The created client session factory.
     */
    public ClientSessionFactory getClientSessionFactory();

    /**
     * It sets the performance parameters for the client.
     * @return The performance parameters for the client.
     */
    public PerformanceParams getPerformanceParams();

    /**
     * It returns the performance parameters for the client.
     * @param params  The performance parameters for the client.
     */
    public void setPerformanceParams(PerformanceParams params);
}
