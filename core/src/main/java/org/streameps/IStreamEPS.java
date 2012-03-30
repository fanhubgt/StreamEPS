/*
 * ====================================================================
 *  IStreamEPS Platform
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
 *  =============================================================================
 */
package org.streameps;

import org.streameps.core.IEPSRuntimeClient;
import org.streameps.core.IEventProducer;

/**
 * Interface to the stream event processing system.
 *
 * @author  Frank Appiah
 */
public interface IStreamEPS<E> {

    /**
     * It sets the runtime client to the core of the stream event processing system.
     * @param runtimeClient An instance to the runtime client.
     */
    public void setRuntimeClient(IEPSRuntimeClient runtimeClient);

    /**
     * It returns the runtime client to the core of the stream event processing system.
     * @return An instance to the runtime client.
     */
    public IEPSRuntimeClient getRuntimeClient();

    /**
     * It builds the runtime client for the main processing system.
     */
    public void buildRuntimeClient();

    /**
     * It builds the runtime client for the main processing system.
     * @param client An instance of the runtime client.
     * @return The instance of the runtime client passed to the builder with some
     * modifications to the structure.
     */
    public IEPSRuntimeClient buildRuntimeClient(IEPSRuntimeClient client);

    /**
     * It returns the groovy path used to build the runtime client to the core of the
     * EPS engine.
     * @param groovyPath The file path to the groovy configuration file.
     */
    public void setGroovvyPath(String groovyPath);

    /**
     * It sets the groovy path used to build the runtime client to the core of the
     * EPS engine.
     * @return The file path to the groovy configuration file.
     */
    public String getGroovyPath();

    /**
     * It builds the runtime client for the main processing system.
     */
    public void buildRuntimeClientRemotely(String address, String port);

    /**
     * It closes the client connection to the server.
     */
    public void close();

    /**
     * It sets the transport class type.
     * 
     * @param transport An instance of the transport class.
     */
  //  public void setTransport(T transport, Class... transportClass);

    /**
     * It configures the StreamEPS client instance for the server.
     */
    public void configure();

    /**
     *  It returns the transport class type set for the EPS.
     * @return The transport class type set for the EPS.
     */
   // public T getTransport();

    /**
     * It returns the instance of the event producer for the StreamEPS.
     * @return The instance of the event producer for the StreamEPS.
     */
    public IEventProducer getEventSender();

    /**
     * It sets the instance of the event producer for the StreamEPS.
     * @param eventSender An event producer instance.
     */
    public void setEventSender(IEventProducer eventSender);
}
