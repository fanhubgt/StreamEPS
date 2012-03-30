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
import java.util.TreeMap;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.streameps.msq.hornetq.PerformanceParams;

/**
 *
 * @author Frank Appiah
 */
public class EPSMSQConfig implements IEPSMSQConfig {

    private boolean persistenceEnabled = false;
    private boolean securityEnabled = false;
    private String queueName = "eps.queue";
    private String hostAddress = "localhost";
    private String port = "5555";
    private Map<String, String> optionMap;
    private long timeStamp;
    private String connectionFactoryName = "/ConnectionFactory";
    private String jndiLocationPath;
    private boolean trasactSession = false;
    private String messageListenerClass;
    private boolean disableMessageID;
    private boolean disableMessageTimestamp;
    private long timeToLive = 10;
    private String streamEPSPropertiesPath;
    private ClientSessionFactory factory;
    private ClientSession clientSession;
    private ClientConsumer clientConsumer;
    private ClientProducer clientProducer;
    private IEPSMSQConfig instance;
    private PerformanceParams pp;

    public EPSMSQConfig() {
        optionMap = new TreeMap<String, String>();
    }

    public EPSMSQConfig(Map<String, String> optionMap, long timeStamp, String connectionFactoryName) {
        this.optionMap = optionMap;
        this.timeStamp = timeStamp;
        this.connectionFactoryName = connectionFactoryName;
    }

    public IEPSMSQConfig getInstance() {
        if (instance == null) {
            instance = new EPSMSQConfig();
        }
        return instance;
    }

    public EPSMSQConfig(Map<String, String> optionMap) {
        this.optionMap = optionMap;
    }

    public void setPersistenceEnabled(boolean persist) {
        this.persistenceEnabled = persist;
    }

    public boolean getPersistenceEnabled() {
        return this.persistenceEnabled;
    }

    public void setSecurityEnabled(boolean securityEnabled) {
        this.securityEnabled = securityEnabled;
    }

    public boolean getSecurityEnabled() {
        return this.securityEnabled;
    }

    public void setQueueName(String queueName) {
        if (queueName.startsWith("/queue/")) {
            this.queueName = queueName;
        } else {
            this.queueName = "/queue/" + queueName;
        }
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setHostAddress(String host) {
        this.hostAddress = host;
    }

    public String getHostAddress() {
        return this.hostAddress;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return this.port;
    }

    public void setOptions(Map<String, String> options) {
        this.optionMap = options;
    }

    public Map<String, String> getOptions() {
        return this.optionMap;
    }

    public void setReceiverTimeOut(long timestamp) {
        this.timeStamp = timestamp;
    }

    public long getReceiverTimeOut() {
        return this.timeStamp;
    }

    public void setConnectionFactoryName(String factoryName) {
        this.connectionFactoryName = factoryName;
    }

    public String getConnectionFactoryName() {
        return this.connectionFactoryName;
    }

    public void setJNDILocationPath(String jndiPath) {
        this.jndiLocationPath = jndiPath;
    }

    public String getJNDILocationPath() {
        return this.jndiLocationPath;
    }

    public void setMessageListenerClass(String listenerClass) {
        this.messageListenerClass = listenerClass;
    }

    public String getMessageListenerClass() {
        return this.messageListenerClass;
    }

    public void setSessionTransacted(boolean transact) {
        this.trasactSession = transact;
    }

    public boolean getSessionTransacted() {
        return this.trasactSession;
    }

    public void setDisableMessageID(boolean messageID) {
        this.disableMessageID = messageID;
    }

    public boolean getDisableMessageID() {
        return this.disableMessageID;
    }

    public boolean getDisableMessageTimestamp() {
        return this.disableMessageTimestamp;
    }

    public void setDisableMessageTimestamp(boolean enableTimestamp) {
        this.disableMessageTimestamp = enableTimestamp;
    }

    public void setTimeToLive(long timestamp) {
        this.timeToLive = timestamp;
    }

    public long getTimeToLive() {
        return this.timeToLive;
    }

    public void setStreamPropertiesPath(String path) {
        this.streamEPSPropertiesPath = path;
    }

    public String getStreamPropertiesPath() {
        return this.streamEPSPropertiesPath;
    }

    public void setClientConsumer(ClientConsumer clientConsumer) {
        this.clientConsumer = clientConsumer;
    }

    public ClientConsumer getClientConsumer() {
        return this.clientConsumer;
    }

    public void setClientProducer(ClientProducer clientProducer) {
        this.clientProducer = clientProducer;
    }

    public ClientProducer getClientProducer() {
        return this.clientProducer;
    }

    public void setClientSession(ClientSession clientSession) {
        this.clientSession = clientSession;
    }

    public ClientSession getClientSession() {
        return this.clientSession;
    }

    public void setClientSessionFactory(ClientSessionFactory clientSessionFactory) {
        this.factory = clientSessionFactory;
    }

    public ClientSessionFactory getClientSessionFactory() {
        return this.factory;
    }

    public PerformanceParams getPerformanceParams() {
        return this.pp;
    }

    public void setPerformanceParams(PerformanceParams params) {
        this.pp=params;
    }
}
