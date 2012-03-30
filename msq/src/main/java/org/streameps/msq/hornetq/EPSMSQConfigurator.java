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

import org.streameps.msq.hornetq.listeners.EPSSessionFailureListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.ClientConsumer;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSessionFactory;
import org.hornetq.api.core.client.HornetQClient;
import org.hornetq.integration.transports.netty.TransportConstants;
import org.hornetq.integration.transports.netty.NettyConnectorFactory;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.msq.EPSMSQConfig;
import org.streameps.msq.IEPSMSQConfig;
import org.streameps.msq.IMSQClient;
import org.streameps.msq.IStreamEPSPropLoader;
import org.streameps.msq.MSQClient;
import org.streameps.msq.StreamEPSPropLoader;
import org.streameps.msq.hornetq.listeners.EPSSendAcknowledgeHandler;
import org.streameps.msq.hornetq.listeners.MessageHandlerComponent;

/**
 *
 * @author Frank Appiah
 */
public class EPSMSQConfigurator implements IEPSMSQConfigurator {

    private IMSQClient qClient;
    private IEPSMSQConfig qConfig;
    private ILogger logger = LoggerUtil.getLogger(EPSMSQConfigurator.class);
    private PerformanceParams perfParams;
    private String defaultStreamFile = "streameps.properties";
    private ClientSessionFactory factory;
    private ClientSession clientSession;
    private ClientConsumer clientConsumer;
    private ClientProducer clientProducer;
    private MessageHandlerComponent messageHandler;
    private IStreamEPSPropLoader streamEPSPropLoader;
    private static IEPSMSQConfigurator configurator;
    private boolean logParams = false;

    public EPSMSQConfigurator() {
        qClient = new MSQClient();
        qConfig = new EPSMSQConfig();
        perfParams = new PerformanceParams();
        messageHandler = new MessageHandlerComponent();
    }

    public EPSMSQConfigurator(boolean log) {
        qClient = new MSQClient();
        qConfig = new EPSMSQConfig();
        perfParams = new PerformanceParams();
        messageHandler = new MessageHandlerComponent();
        logParams = log;
    }

    public static IEPSMSQConfigurator getInstance() {
        if (configurator == null) {
            configurator = new EPSMSQConfigurator();
        }
        return configurator;
    }

    public void configureContext() {
        try {
            if (perfParams == null) {
                configurePerformanceParams();
            }
            Map<String, Object> params = new HashMap<String, Object>();

            params.put(TransportConstants.TCP_NODELAY_PROPNAME, perfParams.isTcpNoDelay());
            params.put(TransportConstants.TCP_SENDBUFFER_SIZE_PROPNAME, perfParams.getTcpBufferSize());
            params.put(TransportConstants.TCP_RECEIVEBUFFER_SIZE_PROPNAME, perfParams.getTcpBufferSize());

            params.put(TransportConstants.HOST_PROP_NAME, perfParams.getHost());
            params.put(TransportConstants.PORT_PROP_NAME, perfParams.getPort());

            TransportConfiguration configuration = new TransportConfiguration(NettyConnectorFactory.class.getName(), params);

            factory = HornetQClient.createClientSessionFactory(configuration);

            factory.setPreAcknowledge(perfParams.isPreAck());
            factory.setConfirmationWindowSize(perfParams.getConfirmationWindow());
            factory.setProducerWindowSize(perfParams.getProducerWindow());
            factory.setConsumerWindowSize(perfParams.getConsumerWindow());

            factory.setAckBatchSize(perfParams.getBatchSize());

            factory.setBlockOnAcknowledge(perfParams.isBlockOnACK());
            factory.setBlockOnDurableSend(perfParams.isBlockOnPersistent());
//            clientSession = factory.createSession(qClient.getUsername(), qClient.getPassword(),
//                    false, true, true, perfParams.isPreAck(),
//                    perfParams.getBatchSize());

            //clientSession = factory.createSession(perfParams.isSessionTransacted(), perfParams.isSessionTransacted());
            clientSession = factory.createSession(false,false,false);
            logger.info("[ClientSessionFactory]- The connection factory lookup is finished......");

            clientSession.addFailureListener(new EPSSessionFailureListener());
            logger.info("[Failure Listener]- The failure listener is added to the session....");

            clientSession.setSendAcknowledgementHandler(new EPSSendAcknowledgeHandler());
            logger.info("[Acknowledgement Handler]- The failure listener is added to the session....");

            clientSession.createQueue(perfParams.getAddress(), perfParams.getQueueName(), perfParams.isDurable());
            logger.info("[Queue]- The queue lookup is complete....");

            clientProducer = clientSession.createProducer(perfParams.getAddress());
            logger.info("[Message Producer]- The message producer is ready for sending events....");

            clientConsumer = clientSession.createConsumer(perfParams.getQueueName());
            clientConsumer.setMessageHandler(messageHandler);
            logger.info("[Message Consumer]- The message producer is ready for sending events....");

            qClient.setMSQConfig(qConfig);

            logger.info("[Started] ......StreamEPS Queue Client at " + new Date().toString());
            logger.info("StreamEPS Client version -" + clientSession.getVersion());

            qConfig.setClientSession(clientSession);
            qConfig.setClientConsumer(clientConsumer);
            qConfig.setClientProducer(clientProducer);
            qConfig.setClientSessionFactory(factory);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    public void createConfigurations() {
        configurePerformanceParams();
    }

    public void close() {
        try {
            clientSession.stop();
            factory.close();
        } catch (HornetQException ex) {
            logger.warn(ex.getMessage());
        }
        try {
            clientSession.close();
        } catch (HornetQException ex) {
            logger.warn(ex.getMessage());
        }
    }

    public void start() {
        try {
            clientSession.start();
        } catch (HornetQException ex) {
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

    public void configurePerformanceParams() {
        streamEPSPropLoader = new StreamEPSPropLoader(qConfig.getStreamPropertiesPath(), perfParams);
        streamEPSPropLoader.configurePerformanceParams();
        if (logParams) {
            streamEPSPropLoader.logParams(perfParams);
        }
    }

    public void setMessageHandler(MessageHandlerComponent messageHandler) {
        this.messageHandler = messageHandler;
        try {
            clientConsumer.setMessageHandler(messageHandler);
        } catch (HornetQException ex) {
            logger.warn(ex.getMessage());
        }
    }

    public MessageHandlerComponent getMessageHandler() {
        return this.messageHandler;
    }
}
