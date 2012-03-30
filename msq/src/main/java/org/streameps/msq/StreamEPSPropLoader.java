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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.msq.hornetq.PerformanceParams;

/**
 *
 * @author Frank Appiah
 */
public class StreamEPSPropLoader implements IStreamEPSPropLoader {

    private ILogger logger = LoggerUtil.getLogger(StreamEPSPropLoader.class);
    private String streamLocation;
    private PerformanceParams perfParams;

    public StreamEPSPropLoader() {
        perfParams=new PerformanceParams();
    }

    public StreamEPSPropLoader(String streamLocation, PerformanceParams perfParams) {
        this.streamLocation = streamLocation;
        this.perfParams = perfParams;
    }

    public void configurePerformanceParams(String location) {
        Properties props = null;

        FileInputStream is = null;

        try {
            try {
                is = new FileInputStream(location);
            } catch (FileNotFoundException ex) {
                logger.warn(ex.getMessage());
            }
            props = new Properties();
            try {
                props.load(is);
            } catch (IOException ex) {
                logger.warn(ex.getMessage());
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    logger.warn(ex.getMessage());
                }
            }
        }
        populateParams(props);
        logger.info("[StreamEPS Loader] The properties of the streamEPS server is loaded");
    }

    public void configurePerformanceParams() {
        configurePerformanceParams(streamLocation);
    }

    public void populateParams(Properties props) {

        int noOfMessages = Integer.valueOf(props.getProperty("num-messages"));
        int noOfWarmupMessages = Integer.valueOf(props.getProperty("num-warmup-messages"));
        int messageSize = Integer.valueOf(props.getProperty("message-size"));
        boolean durable = Boolean.valueOf(props.getProperty("durable", "false"));
        boolean transacted = Boolean.valueOf(props.getProperty("transacted","true"));
        int batchSize = Integer.valueOf(props.getProperty("batch-size"));
        boolean drainQueue = Boolean.valueOf(props.getProperty("drain-queue"));
        String queueName = props.getProperty("queue-name", "/queue/streameps");
        String address = props.getProperty("address");
        int throttleRate = Integer.valueOf(props.getProperty("throttle-rate"));
        int port = Integer.valueOf(props.getProperty("port", "5555"));
        InetSocketAddress isa=new InetSocketAddress(port);
        String host = props.getProperty("host-address", isa.getHostName());
        int tcpBufferSize = Integer.valueOf(props.getProperty("tcp-buffer"));
        boolean tcpNoDelay = Boolean.valueOf(props.getProperty("tcp-no-delay", "true"));
        boolean preAck = Boolean.valueOf(props.getProperty("pre-acknowledge"));
        int confirmationWindowSize = Integer.valueOf(props.getProperty("confirmation-window"));
        int producerWindowSize = Integer.valueOf(props.getProperty("producer-window"));
        int consumerWindowSize = Integer.valueOf(props.getProperty("consumer-window"));
        boolean blockOnACK = Boolean.valueOf(props.getProperty("block-acknowledge", "false"));
        boolean blockOnPersistent = Boolean.valueOf(props.getProperty("block-persistence", "false"));
        boolean useSendAcks = Boolean.valueOf(props.getProperty("use-send-acknowledge", "false"));
        int rmiPort=Integer.parseInt(System.getProperty("rmi-port","1093"));
        String rmiAddress=System.getProperty("rmi-bind-address","localhost");
        
        if (useSendAcks && confirmationWindowSize < 1) {
            throw new ContextInitException("If you use send acks, then need to set confirmation-window-size to a positive integer");
        }

        perfParams.setNoOfMessagesToSend(noOfMessages);
        perfParams.setNoOfWarmupMessages(noOfWarmupMessages);
        perfParams.setMessageSize(messageSize);
        perfParams.setDurable(durable);
        perfParams.setSessionTransacted(transacted);
        perfParams.setBatchSize(batchSize);
        perfParams.setDrainQueue(drainQueue);
        perfParams.setQueueName(queueName);
        perfParams.setAddress(address);
        perfParams.setThrottleRate(throttleRate);
        perfParams.setHost(host);
        perfParams.setPort(port);
        perfParams.setTcpBufferSize(tcpBufferSize);
        perfParams.setTcpNoDelay(tcpNoDelay);
        perfParams.setPreAck(preAck);
        perfParams.setConfirmationWindow(confirmationWindowSize);
        perfParams.setProducerWindow(producerWindowSize);
        perfParams.setConsumerWindow(consumerWindowSize);
        perfParams.setBlockOnACK(blockOnACK);
        perfParams.setBlockOnPersistent(blockOnPersistent);
        perfParams.setUseSendAcks(useSendAcks);
        perfParams.setRmi_port(rmiPort);
        perfParams.setRmi_address(rmiAddress);

    }

    public void logParams(PerformanceParams params) {
        logger.info("num-messages: " + params.getNoOfMessagesToSend());
        logger.info("num-warmup-messages: " + params.getNoOfWarmupMessages());
        logger.info("message-size: " + params.getMessageSize());
        logger.info("durable: " + params.isDurable());
        logger.info("transacted: " + params.isSessionTransacted());
        logger.info("batch-size: " + params.getBatchSize());
        logger.info("drain-queue: " + params.isDrainQueue());
        logger.info("address: " + params.getAddress());
        logger.info("queue-name: " + params.getQueueName());
        logger.info("throttle-rate: " + params.getThrottleRate());
        logger.info("host Address:" + params.getHost());
        logger.info("port: " + params.getPort());
        logger.info("tcp-buffer: " + params.getTcpBufferSize());
        logger.info("tcp-no-delay: " + params.isTcpNoDelay());
        logger.info("pre-acknowledge: " + params.isPreAck());
        logger.info("confirmation-window: " + params.getConfirmationWindow());
        logger.info("producer-window: " + params.getProducerWindow());
        logger.info("consumer-window: " + params.getConsumerWindow());
        logger.info("block-acknowledge:" + params.isBlockOnACK());
        logger.info("block-persistent:" + params.isBlockOnPersistent());
        logger.info("use-send-acknowledge:" + params.isUseSendAcks());
    }

    public String getStreamLocation() {
        return streamLocation;
    }

    public void setStreamLocation(String streamLocation) {
        this.streamLocation = streamLocation;
    }

    public void setPerfParams(PerformanceParams perfParams) {
        this.perfParams = perfParams;
    }

    public PerformanceParams getPerfParams() {
        return perfParams;
    }
}
