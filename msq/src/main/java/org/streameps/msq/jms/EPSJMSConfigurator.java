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
package org.streameps.msq.jms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.integration.transports.netty.NettyConnectorFactory;
import org.hornetq.integration.transports.netty.TransportConstants;
import org.streameps.core.util.IDUtil;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.msq.ContextInitException;
import org.streameps.msq.EPSMSQConfig;
import org.streameps.msq.EPSMessageListener;
import org.streameps.msq.EPSQueueExceptionListener;
import org.streameps.msq.IEPSMSQConfig;
import org.streameps.msq.IMSQClient;
import org.streameps.msq.IStreamEPSPropLoader;
import org.streameps.msq.MSQClient;
import org.streameps.msq.StreamEPSPropLoader;
import org.streameps.msq.hornetq.PerformanceParams;

/**
 *
 * @author Frank Appiah
 */
public class EPSJMSConfigurator implements IEPSJMSConfigurator {

    private IMSQClient qClient;
    private IEPSMSQConfig qConfig;
    private Connection connection = null;
    private EPSMessageListener messageListener;
    private PerformanceParams perfParams;
    private ILogger logger = LoggerUtil.getLogger(EPSJMSConfigurator.class);
    private static IEPSJMSConfigurator configurator;
    private IStreamEPSPropLoader streamEPSPropLoader;
    private boolean logParam = false;

    public EPSJMSConfigurator() {
        qClient = new MSQClient();
        qConfig = new EPSMSQConfig();
        streamEPSPropLoader = new StreamEPSPropLoader();
        perfParams = new PerformanceParams();
    }

    public EPSJMSConfigurator(boolean log) {
        qClient = new MSQClient();
        qConfig = new EPSMSQConfig();
        streamEPSPropLoader = new StreamEPSPropLoader();
        perfParams = new PerformanceParams();
        this.logParam = log;
    }

    public IEPSJMSConfigurator createInstance() {
        if (configurator == null) {
            configurator = new EPSJMSConfigurator();
        }
        return configurator;
    }

    public IEPSJMSConfigurator getInstance() {
        if (configurator == null) {
            configurator = new EPSJMSConfigurator();
        }
        return configurator;
    }

    public void configureContext() throws ContextInitException {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(TransportConstants.HOST_PROP_NAME, perfParams.getHost());
            params.put(TransportConstants.PORT_PROP_NAME, perfParams.getPort());

            TransportConfiguration configuration = new TransportConfiguration(NettyConnectorFactory.class.getName(), params);

            ConnectionFactory cf = HornetQJMSClient.createConnectionFactory(configuration);
            qClient.setConnectionFactory(cf);
            logger.info("[ConnectionFactory]- The connection factory lookup is finished......");

            try {
                connection = cf.createConnection(qClient.getUsername(), qClient.getPassword());
            } catch (JMSException ex) {
                logger.error(ex.getMessage());
            }

            Session session = connection.createSession(qConfig.getSessionTransacted(),
                    Session.AUTO_ACKNOWLEDGE);
            qClient.setSession(session);
            Queue queue = (Queue) session.createQueue(qConfig.getQueueName());
            logger.info("[Queue]- The queue lookup is complete....");
            if (queue == null) {
                queue = session.createQueue(qConfig.getQueueName());
                logger.info("[Queue]- The queue is created....");
            }
            session.setMessageListener(getMessageListener());
            logger.info("[Session]- The session is successfully created....");

            MessageConsumer messageConsumer = session.createConsumer(queue);
            qClient.setMessageConsumer(messageConsumer);

            MessageProducer producer = session.createProducer(queue);
            qClient.setMessageProducer(producer);
            producer.setDisableMessageID(qConfig.getDisableMessageID());
            producer.setDisableMessageTimestamp(qConfig.getDisableMessageTimestamp());
            producer.setTimeToLive(qConfig.getTimeToLive());

            logger.info("[Message Producer]- The message producer is ready for sending events....");

            connection.setClientID(IDUtil.getUniqueID(new Date().toString())); //todo: save each generated id to a file.

            connection.setExceptionListener(new EPSQueueExceptionListener());
            qClient.setMSQConfig(qConfig);
        } catch (JMSException ex) {
            logger.error(ex.getMessage());
        }
    }

    public void close() throws ContextInitException {
        if (connection != null) {
            try {
                connection.close();
                logger.info("[Connection]-- The connection is close.");
            } catch (JMSException ex) {
                logger.warn("Error Code: " + ex.getErrorCode() + "====Message: " + ex.getMessage());
            }
        }
    }

    public void start() {
        try {
            connection.start();
            logger.info("[Connection]- The connection to the queue server is established.......");
        } catch (JMSException ex) {
            logger.warn(ex.getMessage());
        }
    }

    public IMSQClient getqClient() {
        return qClient;
    }

    public void setqClient(IMSQClient qClient) {
        this.qClient = qClient;
    }

    public void setqConfig(IEPSMSQConfig qConfig) {
        this.qConfig = qConfig;
    }

    public IEPSMSQConfig getqConfig() {
        return qConfig;
    }

    public void setMessageListener(EPSMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public EPSMessageListener getMessageListener() {
        return this.messageListener;
    }

    public void createConfigurations() {
        streamEPSPropLoader = new StreamEPSPropLoader(qConfig.getStreamPropertiesPath(), perfParams);
        streamEPSPropLoader.configurePerformanceParams();
        qConfig.setQueueName(perfParams.getQueueName());
        qConfig.setPort(String.valueOf(perfParams.getPort()));
        qConfig.setSessionTransacted(perfParams.isSessionTransacted());
        qConfig.setReceiverTimeOut(perfParams.getConsumerWindow());
        qConfig.setPersistenceEnabled(perfParams.isBlockOnPersistent());
        if (logParam) {
            streamEPSPropLoader.logParams(perfParams);
        }
    }
}
