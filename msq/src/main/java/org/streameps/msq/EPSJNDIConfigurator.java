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

import java.util.Date;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.NamingException;
import org.streameps.core.util.IDUtil;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;

/**
 * The main messaging runtime server implementation.
 * 
 * @author Frank Appiah
 */
public class EPSJNDIConfigurator implements IEPSJNDIConfigurator {

    private IMSQClient qClient;
    private IEPSMSQConfig qConfig;
    private Context initialContext = null;
    private Connection connection = null;
    private EPSMessageListener messageListener;
    private ILogger logger = LoggerUtil.getLogger(EPSJNDIConfigurator.class);
    private static IEPSJNDIConfigurator configurator;

    public EPSJNDIConfigurator() {
        qClient = new MSQClient();
        qConfig = new EPSMSQConfig();
    }

    public IEPSJNDIConfigurator createInstance() {
        if (configurator == null) {
            configurator = new EPSJNDIConfigurator();
        }
        return configurator;
    }

    public static IEPSJNDIConfigurator getInstance() {
        if (configurator == null) {
            configurator = new EPSJNDIConfigurator();
        }
        return configurator;
    }

    public void configureContext() {
        try {
            initialContext = createInitialContext();
            qClient.setInitiatorContext(initialContext);
            logger.info("[InitialContext]- The initial context to the Queue Server is created.....");

            ConnectionFactory cf = (ConnectionFactory) initialContext.lookup(qConfig.getConnectionFactoryName());
            
            qClient.setConnectionFactory(cf);
            logger.info("[ConnectionFactory]- The connection factory lookup is finished......");

            connection = cf.createConnection(qClient.getUsername(), qClient.getPassword());

            Session session = connection.createSession(qConfig.getSessionTransacted(), Session.AUTO_ACKNOWLEDGE
                    | Session.CLIENT_ACKNOWLEDGE);
            qClient.setSession(session);

            Queue queue = (Queue) initialContext.lookup(qConfig.getQueueName());
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

            connection.setClientID(IDUtil.getUniqueID(new Date().toString()));//todo: save each generated id to a file.
            connection.setExceptionListener(new EPSQueueExceptionListener());

            qClient.setMSQConfig(qConfig);

            logger.info("[Started] ......StreamEPS Queue Server at " + new Date().toString());
        } catch (Exception e) {
            logger.warn(e.getMessage());
            close();
        }
    }

    public void createConfigurations() {
        String systemVar = System.getProperty(MSQConstants.INITIAL_CONTEXT_FILE);
        if (systemVar != null) {
            qConfig.setJNDILocationPath(systemVar);
        }
        systemVar = System.getProperty(MSQConstants.QUEUE_NAME);
        if (systemVar != null) {
            qConfig.setQueueName(systemVar);
        }
        systemVar = System.getProperty(MSQConstants.HOST_ADDRESS);
        if (systemVar != null) {
            qConfig.setHostAddress(systemVar);
        }
        systemVar = System.getProperty(MSQConstants.CONNECTION_FACTORY);
        if (systemVar != null) {
            qConfig.setConnectionFactoryName(systemVar);
        }

        boolean persistenceEnable = Boolean.parseBoolean(System.getProperty(MSQConstants.PERSISTENCE_ENABLED));
        if (persistenceEnable) {
            qConfig.setPersistenceEnabled(persistenceEnable);
        }
        systemVar = System.getProperty(MSQConstants.PORT);
        if (systemVar != null) {
            qConfig.setPort(systemVar);
        }

        qConfig.setDisableMessageID(Boolean.getBoolean(System.getProperty(MSQConstants.DISABLE_MESSAGE_ID)));

        qConfig.setDisableMessageTimestamp(Boolean.parseBoolean(System.getProperty(MSQConstants.DISABLE_MESSAGE_TIMESTAMP)));

        systemVar = System.getProperty(MSQConstants.TIME_TO_LIVE);
        if (systemVar != null) {
            long timestamp = Long.parseLong(systemVar);
            qConfig.setTimeToLive(timestamp);
        }
    }

    public void close() {
        if (initialContext != null) {
            try {
                initialContext.close();
            } catch (NamingException ex) {
                logger.warn("Explanation: " + ex.getExplanation());
            }
        } else {
            throw new ContextInitException("Initial context failed to initialised properly.");
        }
        if (connection != null) {
            try {
                connection.close();
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

    public Context createInitialContext() {
        IEPSJndiPropLoader propLoader=new EPSJndiPropLoader();
        propLoader.loadProperties(qClient);
        return qClient.getInitialContext();
    }

    public void setInitialContext(Context context) {
        this.initialContext = context;
    }

    public Context getInitialContext() {
        return this.initialContext;
    }

    public void setMessageListener(EPSMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public EPSMessageListener getMessageListener() {
        return this.messageListener;
    }
}
