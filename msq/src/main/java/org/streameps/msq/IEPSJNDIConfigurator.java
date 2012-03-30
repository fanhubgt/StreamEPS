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

import javax.naming.Context;

/**
 * It creates the initial context from the JNDI properties and configures the
 * connection with the connection factory with a lookup from the initial context
 * loaded from the file system.
 * 
 * @author  Frank Appiah
 */
public interface IEPSJNDIConfigurator {

    /**
     * It creates an instance of the JNDI configuration.
     * @return An instance of the JNDI configuration.
     */
    public IEPSJNDIConfigurator createInstance();

    /**
     * It creates an instance of the initial context from the JNDI properties.
     * @return An instance of the initial context.
     */
    public Context createInitialContext() throws ContextInitException;

    /**
     * It sets the initial context created from the external process.
     * @param context An instance of the initial context.
     */
    public void setInitialContext(Context context);

    /**
     * It returns the initial context instance created by the configuration instance.
     * @return An instance of the initial context.
     */
    public Context getInitialContext();

    /**
     * It closes the initial context and the connection created.
     */
    public void close() throws ContextInitException;

    /**
     * It starts the connection created from the connection factory.
     */
    public void start();

    /**
     * It configures the context and creates a connection to the server for
     * sending messages by the message producer.
     */
    public void configureContext() throws ContextInitException;

    /**
     * It creates a configuration from the property file.
     */
    public void createConfigurations();

    /**
     * It returns the client instance created by the EPS configuration instance.
     * @return An instance of the client reference.
     */
    public IMSQClient getqClient();

    /**
     * It returns the configuration instance created as strong reference.
     * @return The configuration instance created from the JVM options.
     */
    public IEPSMSQConfig getqConfig();

    /**
     * It sets the properties of the client as reference usage in the further
     * implementations.
     * @param qClient An instance of the client reference.
     */
    public void setqClient(IMSQClient qClient);

    /**
     * It sets the configurations from the system properties set by the JVM options.
     * @param qConfig The configuration instance created from the JVM options.
     */
    public void setqConfig(IEPSMSQConfig qConfig);

    /**
     * It sets the message listener for the queue created by this configuration
     * instance.
     * @param messageListener The message listener used by the connection to listen
     * to messages from the server.
     */
    public void setMessageListener(EPSMessageListener messageListener);

    /**
     * It returns the message listener for the queue created by this configuration
     * instance.
     * @return The message listener used by the connection to listen
     * to messages from the server.
     */
    public EPSMessageListener getMessageListener();
}
