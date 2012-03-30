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


import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;

/**
 * An interface that keep references to the queue server resources for use at
 * the client side.
 * @author  Frank Appiah
 */
public interface IJMSClient {

    /**
     * It sets the identifier of the JMS client as provided by the id generator
     * utility.
     * @param identifier A unique identifier for the JMS client.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns the identifier of the JMS client as provided by the id generator
     * utility.
     * @return A unique identifier for the JMS client.
     */
    public String getIdentifier();

    /**
     * It sets the queue as the destination via which events are received from the
     * queue server.
     * @param queue The destination by which events are received.
     */
    public void setQueue(Queue queue);

    /**
     * It returns the queue/destination used to received events from the queue server.
     * @return The destination by which events are received.
     */
    public Queue getQueue();

    /**
     * It sets the connection factory instance created by the initial context lookup
     * instance.
     * @param connectionFactory An instance of the connection factory used to create
     * connections to the server.
     */
    public void setConnectionFactory(ConnectionFactory connectionFactory);

    /**
     * It returns the connection factory used in the JMS client.
     * @return An instance to the connection factory.
     */
    public ConnectionFactory getConnectionFactory();

    /**
     * It sets the session of the JMS client used for the interaction with the
     * queue server.
     * @param session An instance of the session at the client.
     */
    public void setSession(Session session);

    /**
     * It returns the session of the client connection.
     * @return  An instance of the session at the client.
     */
    public Session getSession();

    /**
     * It sets the message producer of the client.
     * @param messageProducer The message producer instance created by the client during the
     * session creation.
     */
    public void setMessageProducer(MessageProducer messageProducer);

    /**
     * It returns the message producer of the client.
     * @return The message producer instance created by the client during the
     * session creation.
     */
    public MessageProducer getMessageProducer();

    /**
     * It sets the initial context of the lookup context resource called the
     * InitialContext.
     * @param initialContext The initial context created at the client used for the lookup of
     * the connection.
     */
    public void setInitiatorContext(Context initialContext);

    /**
     * It returns the initial context of the client.
     * @return The initial context created at the client used for the lookup of
     * the connection.
     */
    public Context getInitialContext();

    /**
     * It returns the message consumer for the client.
     * @return The consumer of messages from the server.
     */
     public MessageConsumer getMessageConsumer();

     /**
      * It sets the message consumer for the client.
      * @param messageConsumer The consumer of messages from the server.
      */
     public void setMessageConsumer(MessageConsumer messageConsumer);
     
}
