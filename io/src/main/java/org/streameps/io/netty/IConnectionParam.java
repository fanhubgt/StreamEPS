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
public interface IConnectionParam {
/**
     * It sets the TCP no delay property.
     * @param noDelay Whether to delay on request or not.
     */
    public void setTcpNoDelay(boolean noDelay);

    /**
     * It returns the TCP no delay property.
     * @return Whether to delay on request or not.
     */
    public boolean getTcpNoDelay();

    /**
     * It sets the TCP keep alive flag property.
     * @param keepAlive An indicator to keep TCP connection alive.
     */
    public void setKeepAliveFlag(boolean keepAlive);

    /**
     * It returns the TCP keep alive flag property.
     * @return An indicator to keep TCP connection alive.
     */
    public boolean getKeepAliveFlag();

    /**
     * It sets the port of the server.
     * @param port
     */
    public void setServerPort(int port);

    /**
     * It returns the port of the server.
     * @return The port of the server.
     */
    public int getServerPort();

    /**
     * It sets the address of the server.
     * @param address An IP address used by clients to connect to the server
     * runtime.
     */
    public void setServerAddress(String address);

    /**
     * It returns the address of the server used to serve requests from the client.
     * @return The address of the server.
     */
    public String getServerAddress();

    /**
     * It sets the name of this server.
     * @param name  The name of this server.
     */
    public void setServerName(String name);

    /**
     * It returns the name of the server.
     * @return The name of this server.
     */
    public String getServerName();

    /**
     * It sets the reuse address indicator for the server.
     * @param reuseAddress The reuse address indicator for the server.
     */
    public void setReuseAddress(boolean reuseAddress);

    /**
     * It returns the reuse address indicator for the server.
     * @return The reuse address indicator for the server.
     */
    public boolean getReuseAddress();
    
}
