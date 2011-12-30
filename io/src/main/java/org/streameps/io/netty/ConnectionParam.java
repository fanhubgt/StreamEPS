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
 * @author Frank Appiah
 */
public class ConnectionParam implements IConnectionParam {

    private boolean tcpNoDelay = true;
    private boolean keepAlive = false;
    private int port = 8080;
    private String serverName = "Default";
    private String address = "localhost";
    private boolean reuseAddress = true;

    public ConnectionParam() {
    }

    public ConnectionParam(boolean tcpNoDelay, boolean keepAlive, int port, String serverName, String address, boolean reuseAddress) {
        this.tcpNoDelay = tcpNoDelay;
        this.keepAlive = keepAlive;
        this.port = port;
        this.serverName = serverName;
        this.address = address;
        this.reuseAddress = reuseAddress;
    }

    public void setTcpNoDelay(boolean noDelay) {
        this.tcpNoDelay = noDelay;
    }

    public boolean getTcpNoDelay() {
        return this.tcpNoDelay;
    }

    public void setKeepAliveFlag(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean getKeepAliveFlag() {
        return this.keepAlive;
    }

    public void setServerPort(int port) {
        this.port = port;
    }

    public int getServerPort() {
        return this.port;
    }

    public void setServerName(String name) {
        this.serverName = name;
    }

    public String getServerName() {
        return this.serverName;
    }

    public void setServerAddress(String address) {
        this.address = address;
    }

    public String getServerAddress() {
        return address;
    }

    public void setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    public boolean getReuseAddress() {
        return this.reuseAddress;
    }
}
