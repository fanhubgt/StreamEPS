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

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.core.config.Configuration;
import org.hornetq.core.config.impl.ConfigurationImpl;
import org.hornetq.core.server.HornetQServer;
import org.hornetq.core.server.HornetQServers;
import org.hornetq.integration.transports.netty.NettyAcceptorFactory;
import org.hornetq.integration.transports.netty.NettyConnectorFactory;
import org.hornetq.integration.transports.netty.TransportConstants;
import org.hornetq.jms.server.JMSServerManager;
import org.hornetq.jms.server.config.ConnectionFactoryConfiguration;
import org.hornetq.jms.server.config.JMSConfiguration;
import org.hornetq.jms.server.config.impl.ConnectionFactoryConfigurationImpl;
import org.hornetq.jms.server.config.impl.JMSConfigurationImpl;
import org.hornetq.jms.server.impl.JMSServerManagerImpl;
import org.jnp.server.Main;
import org.jnp.server.NamingBeanImpl;
import org.streameps.core.util.IDUtil;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.msq.ContextInitException;
import org.streameps.msq.EPSJndiPropLoader;
import org.streameps.msq.EPSMSQConfig;
import org.streameps.msq.IEPSJndiPropLoader;
import org.streameps.msq.IEPSMSQConfig;
import org.streameps.msq.IMSQClient;
import org.streameps.msq.IStreamEPSPropLoader;
import org.streameps.msq.MSQClient;
import org.streameps.msq.StreamEPSPropLoader;
import org.streameps.msq.hornetq.listeners.MessageHandlerComponent;
import org.streameps.msq.jms.EPSJMSConfigurator;
import org.streameps.msq.jms.IEPSJMSConfigurator;

/**
 *
 * @author Frank Appiah
 */
public class EPSHornetQEmbedded implements IEPSHornetQEmbedded {

    private IMSQClient qClient;
    private IEPSMSQConfig qConfig;
    private static ILogger logger = LoggerUtil.getLogger(EPSHornetQEmbedded.class);
    private PerformanceParams perfParams;
    private String defaultStreamFile = "streameps.properties";
    private MessageHandlerComponent messageHandler;
    private IStreamEPSPropLoader streamEPSPropLoader;
    private static EPSHornetQEmbedded hornetEmbedded;
    private Configuration configuration;
    public String RESULT_QUEUE_NAME = "resultQueue";
    private EPSQueueManager queueManager;
    private IEPSJMSConfigurator configurator;
    private boolean logParams = false;

    public EPSHornetQEmbedded() {
        perfParams = new PerformanceParams();
        queueManager = new EPSQueueManager();
        qClient = new MSQClient();
        qConfig = new EPSMSQConfig();
    }

    public EPSHornetQEmbedded(boolean log) {
        perfParams = new PerformanceParams();
        queueManager = new EPSQueueManager();
        qClient = new MSQClient();
        qConfig = new EPSMSQConfig();
        logParams = log;
    }

    public void configurePerformanceParams() {
        streamEPSPropLoader = new StreamEPSPropLoader(qConfig.getStreamPropertiesPath(), perfParams);
        streamEPSPropLoader.configurePerformanceParams();
        qConfig.setPerformanceParams(perfParams);
        if (logParams) {
            streamEPSPropLoader.logParams(perfParams);
        }
    }

    public void close() throws ContextInitException {
        try {
            qClient.getHornetQRuntime().getHornetQServer().stop();
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        qClient.getHornetQRuntime().getJNDIServer().stop();
        try {
            qClient.getHornetQRuntime().getJMSServerManager().stop();
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
    }

    public void start() {
        try {
            qClient.getHornetQRuntime().getHornetQServer().start();
            logger.info("[StreamEPS Server]- Started Embedded Queue Server....");
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        try {
            qClient.getHornetQRuntime().getJNDIServer().start();
            logger.info("[JNDI Server]- Started Embedded JNDI Server....");
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        try {
            qClient.getHornetQRuntime().getJMSServerManager().start();
            logger.info("[JMS Server]- Started Embedded JMS Server....");
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
    }

    public void setLogOn(boolean log) {
        this.logParams = log;
    }

    public void configureContext() throws ContextInitException {
        configurePerformanceParams();
        createHornetQ();
        createJNDIServer();
        createJMSManager();
    }

    public void createHornetQ() {
        configuration = new ConfigurationImpl();
        configuration.setPersistenceEnabled(false);
        configuration.setSecurityEnabled(false);

        Map<String, Object> params = new HashMap<String, Object>();

        params.put(TransportConstants.TCP_NODELAY_PROPNAME, perfParams.isTcpNoDelay());
        params.put(TransportConstants.TCP_SENDBUFFER_SIZE_PROPNAME, perfParams.getTcpBufferSize());
        params.put(TransportConstants.TCP_RECEIVEBUFFER_SIZE_PROPNAME, perfParams.getTcpBufferSize());

        params.put(TransportConstants.HOST_PROP_NAME, perfParams.getHost());
        params.put(TransportConstants.PORT_PROP_NAME, perfParams.getPort());

        TransportConfiguration transport = new TransportConfiguration(NettyAcceptorFactory.class.getName(), params);
        configuration.getAcceptorConfigurations().add(transport);

        HornetQServer hornetqServer = HornetQServers.newHornetQServer(configuration);
        qClient.getHornetQRuntime().setHornetqServer(hornetqServer);
        qClient.getHornetQRuntime().setConfiguration(configuration);

    }

    public void createJNDIServer() {
        try {
            IEPSJndiPropLoader propLoader = new EPSJndiPropLoader();
            Properties properties = propLoader.loadProperties(qClient);
            System.setProperty("java.naming.factory.initial", properties.getProperty("java.naming.factory.initial"));
            System.setProperty("java.naming.provider.url", properties.getProperty("java.naming.provider.url"));
            System.setProperty("java.naming.factory.url.pkgs", properties.getProperty("java.naming.factory.url.pkgs"));
            if(logParams)
            propLoader.logJndiProperties();

            NamingBeanImpl naming = new NamingBeanImpl();
            try {
                naming.start();
            } catch (Exception ex) {
                logger.warn(ex.getMessage());
            }
            Main jndiServer = new Main();
            jndiServer.setNamingInfo(naming);
            //jndiServer.setPort(perfParams.getPort());

            //jndiServer.setBindAddress(perfParams.getHost());
            jndiServer.setLookupExector(Executors.newSingleThreadExecutor());
            try {
                jndiServer.setJNPServerSocketFactory(EPSServerSocketFactory.class.getName());
            } catch (ClassNotFoundException ex) {
                logger.warn(ex.getMessage());
            } catch (InstantiationException ex) {
                logger.warn(ex.getMessage());
            } catch (IllegalAccessException ex) {
                logger.warn(ex.getMessage());
            }

            jndiServer.setRmiPort(perfParams.getRmi_port());
            logger.info("[RMI Port]- This binded to the set port value (+1) in the streameps.properties.");
            jndiServer.setRmiBindAddress(perfParams.getAddress());
            qClient.getHornetQRuntime().setJNDIServer(jndiServer);
        } catch (UnknownHostException ex) {
            logger.warn(ex.getMessage());
        }

    }

    public void createJMSManager() {
        JMSConfiguration jmsConfig = new JMSConfigurationImpl();

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put("java.naming.factory.initial", System.getProperty("java.naming.factory.initial"));
        env.put("java.naming.provider.url", System.getProperty("java.naming.provider.url"));
        env.put("java.naming.factory.url.pkgs", System.getProperty("java.naming.factory.url.pkgs"));

        Context context = null;
        try {
            context = new InitialContext(env);
        } catch (NamingException ex) {
            Logger.getLogger(EPSHornetQEmbedded.class.getName()).log(Level.SEVERE, null, ex);
        }
        jmsConfig.setContext(context);

        TransportConfiguration connectorConfig = new TransportConfiguration(NettyConnectorFactory.class.getName());
        String connectionFactory = qClient.getMSQConfig().getConnectionFactoryName();
        ConnectionFactoryConfiguration cfConfig = new ConnectionFactoryConfigurationImpl(connectionFactory, connectorConfig, "/" + connectionFactory);
        jmsConfig.getConnectionFactoryConfigurations().add(cfConfig);

        getQueueManager().setClient(qClient);
        getQueueManager().configureQueue(jmsConfig);
        JMSServerManager jmsServer = null;
        try {
            jmsServer = new JMSServerManagerImpl(qClient.getHornetQRuntime().getHornetQServer(), jmsConfig);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        qClient.getHornetQRuntime().setJMSServerManager(jmsServer);

    }

    public void createJMSResources() {
        configurator = new EPSJMSConfigurator();
        configurator.setqClient(qClient);
        configurator.setqConfig(qClient.getMSQConfig());
        configurator.configureContext();
    }

    public static void main(String[] args) {

        IEPSHornetQEmbedded embedded = new EPSHornetQEmbedded();
        embedded.setLogOn(Boolean.parseBoolean(System.getProperty("DEBUG")));

        IEPSMSQConfig config = new EPSMSQConfig();
        config.setStreamPropertiesPath(System.getProperty("PROPERTIES_LOC") + "/streameps.properties");
        config.setJNDILocationPath(System.getProperty("PROPERTIES_LOC") + "/jndi.properties");

        embedded.setqConfig(config);

        IMSQClient client = new MSQClient();
        client.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        client.setMSQConfig(config);
        embedded.setqClient(client);

        embedded.createConfigurations();
        embedded.configureContext();

        embedded.start();

        {
//        Context context = embedded.getqClient().getInitialContext();
//        try {
//            ConnectionFactory cf = (ConnectionFactory) context.lookup("/" + client.getMSQConfig().getConnectionFactoryName());
//            client.setConnectionFactory(cf);
//            Queue queue = (Queue) context.lookup(StreamEPSConstants.EPS_QUEUE_NAME);
//            client.setQueue(queue);
//
//            Connection connection = null;
//            connection = cf.createConnection();
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            client.setSession(session);
//
//            MessageProducer producer = session.createProducer(queue);
//            client.setMessageProducer(producer);
//
//            MessageConsumer messageConsumer = session.createConsumer(queue);
//            client.setMessageConsumer(messageConsumer);
//
//            connection.start();
//        } catch (Exception e) {
//            logger.warn(e.getMessage());
//            System.exit(-1);
//        }
        }
    }

    public void createConfigurations() {
        configurePerformanceParams();
    }

    public IMSQClient getqClient() {
        return this.qClient;
    }

    public IEPSMSQConfig getqConfig() {
        return this.qConfig;
    }

    public void setqClient(IMSQClient qClient) {
        this.qClient = qClient;
    }

    public void setqConfig(IEPSMSQConfig qConfig) {
        this.qConfig = qConfig;
    }

    public void setMessageHandler(MessageHandlerComponent messageHandler) {
    }

    public MessageHandlerComponent getMessageHandler() {
        return null;
    }

    public void setQueueManager(QueueManager sQueueManager) {
        this.queueManager = (EPSQueueManager) sQueueManager;
    }

    public QueueManager getQueueManager() {
        return this.queueManager;
    }

    public IEPSJMSConfigurator getJMSConfigurator() {
        return configurator;
    }

    @Override
    protected void finalize() throws Throwable {
        close();
    }


}
