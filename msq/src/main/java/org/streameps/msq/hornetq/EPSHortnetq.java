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

import javax.naming.InitialContext;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.core.server.cluster.QueueConfiguration;
import org.hornetq.jms.server.JMSServerManager;
import org.jnp.server.Main;
import org.streameps.msq.IEPSMSQConfig;
import org.hornetq.core.config.Configuration;
import org.hornetq.core.server.HornetQServer;
import org.streameps.msq.IMSQClient;

/**
 *
 * @author Frank Appiah
 */
public class EPSHortnetq implements IEPSHornetq{

    private HornetQServer hornetQServer;
    private IEPSMSQConfig hornetqConfig;
    private Configuration configuration;
    private Main jniServer;
    private InitialContext initialContext;
    private TransportConfiguration transportConfig;
    private IMSQClient sQClient;
    private JMSServerManager serverManager;
    private QueueConfiguration  queueConfig;
    private EPSQueueManager queueManager;

    public EPSHortnetq() {
    }

    public EPSHortnetq(HornetQServer hornetQServer, IEPSMSQConfig hornetqConfig, Configuration configuration) {
        this.hornetQServer = hornetQServer;
        this.hornetqConfig = hornetqConfig;
        this.configuration = configuration;
    }

    public void setHornetqServer(HornetQServer hornetQServer) {
        this.hornetQServer=hornetQServer;
    }

    public HornetQServer getHornetQServer() {
        return this.hornetQServer;
    }

    public void setEPSHornetqConfigurator(IEPSMSQConfig hornetqConfig) {
        this.hornetqConfig=hornetqConfig;
    }

    public IEPSMSQConfig getEPSHornetqConfigurator() {
        return this.hornetqConfig;
    }

    public Configuration createConfigurator() {
        return this.configuration;
    }

    public Configuration createConfigurator(IEPSMSQConfig hornetqConfig) {
        return this.configuration;
    }

    public void setJNDIServer(Main jnpServer) {
        this.jniServer=jnpServer;
    }

    public Main getJNDIServer() {
       return this.jniServer;
    }

    public void setInitialContext(InitialContext context) {
        this.initialContext=context;
    }

    public InitialContext getInitialContext() {
        return this.initialContext;
    }

    public void setTransportConfiguration(TransportConfiguration configuration) {
        this.transportConfig=configuration;
    }

    public TransportConfiguration getTransportConfiguration() {
        return this.transportConfig;
    }

    public void setQueueConfiguration(QueueConfiguration configuration) {
        this.queueConfig=configuration;
    }

    public QueueConfiguration getQueueConfiguration() {
       return this.queueConfig;
    }

    public void setJMSServerManager(JMSServerManager serverManager) {
        this.serverManager=serverManager;
    }

    public JMSServerManager getJMSServerManager() {
        return this.serverManager;
    }

    public void setMSQClient(IMSQClient client) {
        this.sQClient=client;
    }

    public IMSQClient getMSQClient() {
       return this.sQClient;
    }

    public void setQueueManager(EPSQueueManager manager) {
        this.queueManager=manager;
    }

    public EPSQueueManager getQueueManager() {
      return this.queueManager;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration=configuration;
    }
    
}
