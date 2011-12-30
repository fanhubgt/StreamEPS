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
package org.streameps.io.netty.server;

import org.streameps.io.netty.IServerConnectParam;
import org.streameps.io.netty.server.IEPSNettyGroupServer;
import org.streameps.io.netty.server.IEPSNettyServer;

/**
 * An interface to create a configurable server with the server parameters provided
 * in its instance.
 * @author  Frank Appiah
 */
public interface IServerConfigurator {

    /**
     * It creates an instance of a complete functional server from the instance
     * parameters.
     * @param nettyServer An instance of an EPS server.
     */
    public IEPSNettyServer createServer(IEPSNettyServer nettyServer);

    /**
     * It returns the instance of the EPS server created.
     * @return An instance of an EPS server.
     */
    public IEPSNettyServer getEPSServer();

    /**
     * It sets the EPS server for the server configurator.
     * @param nettyServer The EPS server for the server configurator.
     */
    public void setEPSServer(IEPSNettyServer nettyServer);

    /**
     * It creates an instance of a complete functional server from the instance
     * parameters.
     * @param nettyServer An instance of an EPS server.
     */
    public IEPSNettyGroupServer createGroupServer(IEPSNettyGroupServer nettyServer);

    /**
     * It returns the instance of the EPS server created.
     * @return An instance of an EPS server.
     */
    public IEPSNettyGroupServer getEPSGroupServer();

    /**
     * It configures the EPS server.
     */
    public void configure();

    /**
     * It configure group server.
     */
    public void configureGroupServer();

    /**
     * It sets whether the group server.
     * @param groupServer
     */
    public void setGroupServer(boolean groupServer);

    /**
     * It returns the indicator whether it is a group server or not.
     * @return Is it a group server.
     */
    public boolean getGroupServer();

    /**
     * It sets the server connection parameters.
     * @param connectParam An instance of the server connection parameter.
     */
    public void setServerConnectionParameter(IServerConnectParam connectParam);

    /**
     * It returns the server connection parameter.
     * @return An instance of the server connection parameter.
     */
    public IServerConnectParam getServerConnectionParameter();

    /**
     * It sets the core pool size for the executor service.
     * @param corePoolSize The number of core pool size.
     */
    public void setCorePoolSize(int corePoolSize);

    /**
     *  It returns the core pool size for the executor service.
     * @return The number of core pool size.
     */
    public int getCorePoolSize();
}
