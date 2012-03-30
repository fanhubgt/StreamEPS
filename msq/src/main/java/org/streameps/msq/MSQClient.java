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
import javax.naming.InitialContext;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.streameps.msq.hornetq.EPSHortnetq;
import org.streameps.msq.hornetq.IEPSHornetq;

/**
 * The client to the runtime messaging queue server. It keeps a reference to the
 * connection factory, queue, message producer, initial context and the
 * configuration used to get these server resources.
 * 
 * @author Frank Appiah
 */
public class MSQClient implements IMSQClient {

    private String identifier;
    private Queue queue;
    private ConnectionFactory connectionFactory;
    private Session session;
    private MessageProducer messageProducer;
    private MessageConsumer messageConsumer;
    private Context initialContext;
    private IEPSMSQConfig config;
    private String username;
    private String password;
    private ClientSessionFactory clientSessionFactory;
    private static IMSQClient clientInstance;
    private IEPSHornetq hornetq;

    public MSQClient() {
        hornetq=new EPSHortnetq();
        config=new EPSMSQConfig();
    }

    public MSQClient(String identifier, Queue queue, ConnectionFactory connectionFactory, Session session, MessageProducer messageProducer, InitialContext initialContext) {
        this.identifier = identifier;
        this.queue = queue;
        this.connectionFactory = connectionFactory;
        this.session = session;
        this.messageProducer = messageProducer;
        this.initialContext = initialContext;
    }

    public static IMSQClient getInstance() {
        if (clientInstance == null) {
            clientInstance = new MSQClient();
        }
        return clientInstance;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Queue getQueue() {
        return this.queue;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return this.session;
    }

    public void setMessageProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public MessageProducer getMessageProducer() {
        return this.messageProducer;
    }

    public MessageConsumer getMessageConsumer() {
        return messageConsumer;
    }

    public void setMessageConsumer(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public void setInitiatorContext(Context initialContext) {
        this.initialContext = initialContext;
    }

    public Context getInitialContext() {
        return this.initialContext;
    }

    public void setMSQConfig(IEPSMSQConfig config) {
        this.config = config;
    }

    public IEPSMSQConfig getMSQConfig() {
        return this.config;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setClientSessionFactory(ClientSessionFactory sessionFactory) {
        this.clientSessionFactory = sessionFactory;
    }

    public ClientSessionFactory getClientSessionFactory() {
        return this.clientSessionFactory;
    }

    public void setHornetQRuntime(IEPSHornetq hornetqRuntime) {
        this.hornetq=hornetqRuntime;
    }

    public IEPSHornetq getHornetQRuntime() {
        return this.hornetq;
    }
    
}
