/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2011.
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
package org.streameps.io.netty;

/**
 *
 * @author  Frank Appiah
 */
public interface EPSCommandLineProp {

    public String GROUP_SERVER = "groupServer";
    public String CHILD_KEEP_ALIVE = "keepAlive";
    public String CHILD_TCP_NO_DELAY = "tcpNoDelay";
    public String REUSE_ADDRESS = "reuseAddress";
    public String RECEIVE_BUFFER_SIZE = "receiverBufferSize";
    public String REMOTE_ADDRESS = "remoteAddress";
    public String CORE_POOL_SIZE = "corePoolSize";
    public String PORT = "port";
    public String LOCAL_ADDRESS = "localAddress";
    public String DEBUG = "debug";
    public String PROPERTY_FILE = "propertyFile";
    public String ENGINE_PER_CLIENT = "enginePerClient";
    public String STORE_LOCATION = "storeLocation";
    public String QUEUE = "queued";
    public String DISPATCHER_SIZE = "dispatcherSize";
    public String INITIAL_DELAY = "initialDelay";
    public String PERIODIC_DELAY = "periodicDelay";
    public String SEQUENCE_SIZE = "sequenceSize";
    public String ASYNCHRONOUS = "asynchronous";
    public String THREAD_FACTORY_NAME = "threadFactoryName";
    public String SAVE_ON_DECIDE = "saveOnDecide";
    public String SAVE_ON_RECEIVE="saveOnReceive";
}
