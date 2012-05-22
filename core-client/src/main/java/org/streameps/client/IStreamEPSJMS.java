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
package org.streameps.client;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import org.streameps.core.IStreamEvent;

/**
 * An interface specification for the configuration of a client access to a
 * JMS complaint server to send events to the JMS-enabled context service.
 * 
 * @author  Frank Appiah
 */
public interface IStreamEPSJMS {

    /**
     * It closes the session and connection to the JMS Server.
     */
    void close();

    /**
     * It connects to the JMS Server with the provided host and port.
     * @param host The host of the server.
     * @param port The port used to connect to the server.
     */
    void connect(String host, String port);

    /**
     * It establishes a connection to the JMS Server with the provided host and port.
     * This connection is authenticated for security reasons with the username and
     * password provided.
     * @param host The host of the server.
     * @param port The port used to connect to the server.
     * @param username The user name of the client detail on the server.
     * @param password The password of the client detail on the server.
     */
    void connect(String host, String port, String username, String password);

    /**
     * It returns the establish connection to the server.
     * @return An instance of the runtime connection to the server.
     */
    Connection getConnection();

    /**
     * It returns the connection factory used to create a connection to the JMS server.
     * @return An instance of the runtime connection factory to the server.
     */
    ConnectionFactory getConnectionFactory();

    /**
     * It returns the number of events that a slow server can support to prevent it
     * from clashing on the client because of differences in the physical machines
     * of the client and server.
     * @return The clients supported window size for the server.
     */
    int getProducerWindowSize();

    /**
     * It returns the queue name of the destination of the events.
     * @return The physical name of the destination.
     */
    String getQueueName();

    /**
     * It returns the session of the client created from the connection establishment
     * process.
     * @return An instance of a runtime session for the client to the server.
     */
    Session getSession();

    /**
     * It sends stream events to the JMS server with the connection established
     * earlier during the connection creation process.
     * @param streamEvent The instance of stream event to be sent to the server.
     */
    void sendEvent(IStreamEvent streamEvent);

    /**
     * It returns the established connection instantiated from the connection
     * factory.
     * @param connection The established connection used to send events to the
     * server.
     */
    void setConnection(Connection connection);

    /**
     * It sets the connection factory of the established server.
     * @param connectionFactory The connection factory of the connected server.
     */
    void setConnectionFactory(ConnectionFactory connectionFactory);

    /**
     * It sets the number of events allowed to prevent the server from clashing
     * on the client of overload of stream events.
     * @param producerWindowSize The producer window size of client to prevent
     * clashing.
     */
    void setProducerWindowSize(int producerWindowSize);

    /**
     * It is recommend you leave the queue name to its default: qstreameps.
     * For custom configuration, the destination/physical name of the context service
     * queue in the lite-activemq.xml needs to be changed as well.
     * @param queueName The queue name of the physical queue configured in the
     * JMS Server complaint.
     */
    void setQueueName(String queueName);

    /**
     * It sets the session created from the established connection to the server.
     * @param session An instance of a session for the client.
     */
    void setSession(Session session);
}
