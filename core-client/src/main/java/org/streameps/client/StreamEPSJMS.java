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
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;
import org.streameps.core.IStreamEvent;

/**
 * Implementation of client access to ActiveMQ Server.
 *
 * @author Frank Appiah
 */
public class StreamEPSJMS implements IStreamEPSJMS {

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private String queueName = "qstreameps";
    private Destination destination;
    private MessageProducer messageProducer;
    private Logger logger = Logger.getLogger(StreamEPSJMS.class);
    private int producerWindowSize = 102400;

    public StreamEPSJMS() {
    }

    public StreamEPSJMS(String queueName) {
        this.queueName = queueName;
    }

    public void connect(String host, String port) {
        connectToStreamEPS(host, port, null, null);
    }

    public void connect(String host, String port, String username, String password) {
        connectToStreamEPS(host, port, username, password);
    }

    private void connectToStreamEPS(String host, String port, String username, String password) {
        connectionFactory = new ActiveMQConnectionFactory(username, password, "tcp://" + host + ":" + port);
        ((ActiveMQConnectionFactory) connectionFactory).setProducerWindowSize(producerWindowSize);
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = new ActiveMQQueue(queueName);
            messageProducer = session.createProducer(destination);
        } catch (JMSException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendEvent(IStreamEvent streamEvent) {
        try {
            ObjectMessage message = session.createObjectMessage();
            message.setObject(streamEvent);
            messageProducer.send(message);
        } catch (JMSException ex) {
            logger.error(ex.getMessage());
        }
    }

    public int getProducerWindowSize() {
        return producerWindowSize;
    }

    public void setProducerWindowSize(int producerWindowSize) {
        this.producerWindowSize = producerWindowSize;
        if (connectionFactory != null) {
            ((ActiveMQConnectionFactory) connectionFactory).setProducerWindowSize(producerWindowSize);
        }
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void close() {
        try {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException ex) {
            logger.error(ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
