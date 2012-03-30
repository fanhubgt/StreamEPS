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
package org.streameps.client;

import org.streameps.core.EPSRuntimeClient;
import org.streameps.IStreamEPS;
import org.streameps.core.IEPSRuntimeClient;
import org.streameps.core.IEventProducer;
import org.streameps.core.IOutputTerminal;
import org.streameps.io.netty.client.AbstractClientChannelHandler;
import org.streameps.io.netty.client.ClientConfigurator;
import org.streameps.io.netty.client.ClientConnectParam;
import org.streameps.io.netty.client.ClientProxy;
import org.streameps.io.netty.client.IClientConfigurator;
import org.streameps.io.netty.client.IClientConnectParam;
import org.streameps.io.netty.client.IClientProxy;

/**
 *
 * @author Frank Appiah
 */
public class StreamEPSNetty<E> extends AbstractEventSender<E> implements IStreamEPS<E> {

    private IEPSRuntimeClient runtimeClient;
    private String groovyPath;
    private int port;
    private String address;
    private IEPSTransport transport;
    private Class<? extends IEPSTransport> transportClazz;
    private IEventProducer eventProducer;
    private IClientConfigurator clientConfigurator;
    private IClientProxy clientProxy;
    private StreamEPSNetty streamEPS;
    private IClientConnectParam clientConnectParam;
    private int corePoolSize = 5;

    public StreamEPSNetty() {
        super();
        runtimeClient = new EPSRuntimeClient();
        clientConfigurator = new ClientConfigurator();
        clientProxy = new ClientProxy();
        eventProducer = new EPSNettyEventSender();
        clientConnectParam = new ClientConnectParam();
    }

    public StreamEPSNetty(StreamEPSNetty streamEPS) {
        this.streamEPS = streamEPS;
        runtimeClient = new EPSRuntimeClient();
        clientConfigurator = new ClientConfigurator();
        clientProxy = new ClientProxy();
        eventProducer = new EPSNettyEventSender();
        clientConnectParam = new ClientConnectParam();
    }

    public StreamEPSNetty(IEPSTransport transport, IClientConnectParam clientConnectParam) {
        this.transport = transport;
        this.clientProxy = new ClientProxy();
        this.clientConnectParam = clientConnectParam;
        eventProducer = new EPSNettyEventSender();
    }

    public StreamEPSNetty(IClientConnectParam clientConnectParam) {
        this.clientProxy = new ClientProxy();
        this.clientConnectParam = clientConnectParam;
        this.clientConfigurator = new ClientConfigurator();
        this.eventProducer = new EPSNettyEventSender();
    }

    public StreamEPSNetty(IEPSTransport transport, IClientProxy clientProxy, IClientConnectParam clientConnectParam) {
        this.transport = transport;
        this.clientProxy = clientProxy;
        this.clientConnectParam = clientConnectParam;
    }

    public StreamEPSNetty(int port, String address, Class<? extends IEPSTransport> transportClazz, IEventProducer eventSender, IClientConfigurator clientConfigurator, IClientProxy clientProxy) {
        this.port = port;
        this.address = address;
        this.transportClazz = transportClazz;
        this.eventProducer = eventSender;
        this.clientConfigurator = clientConfigurator;
        this.clientProxy = clientProxy;
        this.clientConnectParam = new ClientConnectParam();
        setServerAddress(address);
        setServerPort(port);
    }

    public StreamEPSNetty(IEPSRuntimeClient runtimeClient, String groovyPath, int port, String address) {
        super();
        this.runtimeClient = runtimeClient;
        this.groovyPath = groovyPath;
        this.port = port;
        this.address = address;
        this.clientConfigurator = new ClientConfigurator();
        this.clientProxy = new ClientProxy();
        this.clientConnectParam = new ClientConnectParam();
    }

    public StreamEPSNetty setServerPort(int port) {
        clientConnectParam.setServerPort(port);
        return this;
    }

    public StreamEPSNetty setServerAddress(String address) {
        clientConnectParam.setServerAddress(address);
        return this;
    }

    public StreamEPSNetty setReuseAddress(boolean reuse) {
        clientConnectParam.setReuseAddress(reuse);
        return this;
    }

    public StreamEPSNetty setServerName(String serverName) {
        clientConnectParam.setServerName(serverName);
        return this;
    }

    public StreamEPSNetty setTcpNoDelay(boolean tcpNoDelay) {
        clientConnectParam.setTcpNoDelay(tcpNoDelay);
        return this;
    }

    public StreamEPSNetty setKeepAliveFlag(boolean keepAlive) {
        clientConnectParam.setKeepAliveFlag(keepAlive);
        return this;
    }

    public void setRuntimeClient(IEPSRuntimeClient runtimeClient) {
        this.runtimeClient = runtimeClient;
    }

    public IEPSRuntimeClient getRuntimeClient() {
        return this.runtimeClient;
    }

    public void buildRuntimeClient() {
    }

    public IEPSRuntimeClient buildRuntimeClient(IEPSRuntimeClient client) {
        this.runtimeClient = client;
        return this.runtimeClient;
    }

    public void setGroovvyPath(String groovyPath) {
        this.groovyPath = groovyPath;
    }

    public String getGroovyPath() {
        return this.groovyPath;
    }

    public void buildRuntimeClientRemotely(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void setTransport(IEPSTransport transport, Class... transportClass) {
        this.transport = transport;
        this.transportClazz = transportClass[0];
    }

    public IEPSTransport getTransport() {
        return this.transport;
    }

    public void sendEvent(E event) {
        for (IOutputTerminal terminal : getOutputTerminals()) {
            terminal.sendEvent(event);
        }
        if (eventProducer != null) {
            eventProducer.sendEvent(event);
        }
    }

    public void sendEventAsync(E event) {
        for (IOutputTerminal terminal : getOutputTerminals()) {
            terminal.sendEvent(event);
        }
        if (eventProducer != null) {
            eventProducer.sendEvent(event);
        }
    }

    public void routeEvent(E event) {
    }

    public void routeEventAsync(E event) {
    }

    public IClientConfigurator getClientConfigurator() {
        return clientConfigurator;
    }

    public void setClientConfigurator(IClientConfigurator clientConfigurator) {
        this.clientConfigurator = clientConfigurator;
    }

    public void close() {
        ((EPSNettyEventSender) clientProxy.getEventProducer()).getNettyTransport().close();
    }

    public IEventProducer getEventSender() {
        return this.eventProducer;
    }

    public void setEventSender(IEventProducer eventSender) {
        this.eventProducer = eventSender;
    }

    public void setClientProxy(IClientProxy clientProxy) {
        this.clientProxy = clientProxy;
    }

    public IClientProxy getClientProxy() {
        return clientProxy;
    }

    public void configure() {
        clientConfigurator = new ClientConfigurator();
        clientConfigurator.setClientConnectionParameter(clientConnectParam);
        clientConfigurator.setCorePoolSize(corePoolSize);
        clientProxy.setClientConfigurator(clientConfigurator);
        clientProxy.setEventProducer(eventProducer);

        ((EPSNettyEventSender) clientProxy.getEventProducer()).setClientProxy(clientProxy);
        ((EPSNettyEventSender) clientProxy.getEventProducer()).getNettyTransport().setClientProxy(clientProxy);
        clientProxy.configure();

        transport = (IEPSTransport) new EPSNettyTransport(clientProxy, clientConfigurator.getChannel());
        eventProducer = new EPSNettyEventSender(transport, clientProxy);
        eventProducer.setOutputTerminals(getOutputTerminals());
        ((EPSNettyEventSender) clientProxy.getEventProducer()).getNettyTransport().open();
        clientProxy.setEventProducer(eventProducer);
    }

    public void setClientConnectParam(IClientConnectParam clientConnectParam) {
        this.clientConnectParam = clientConnectParam;
    }

    public void addClientEventAdapter(AbstractClientChannelHandler handler){
        getClientConfigurator().addClientListener(handler);
    }
    public IClientConnectParam getClientConnectParam() {
        return clientConnectParam;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void buildRuntimeClientRemotely(String address, String port) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
