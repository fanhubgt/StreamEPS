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

import java.util.List;

/**
 *
 * @author  Frank Appiah
 */
public interface IServerConnectGroupParam {

    /**
     * It sets the group name of the server.
     * @param group The name of the group.
     */
    public void setServerGroupName(String group);

    /**
     * It returns the group name of this server.
     * @return The name of the group.
     */
    public String getServerGroupName();

    /**
     *  It returns the master node parameters.
     * @return The parameter of the master node server.
     */
    public IServerConnectParam getGlobalServerParam();

    /**
     * It sets the master node parameters.
     * @param serverConnectParam  The parameter of the master node server.
     */
    public void setGlobalServerParam(IServerConnectParam serverConnectParam);

    /**
     * It returns the server connection parameters for each server runtime instance.
     * @return The list of server connection parameters.
     */
    public List<IServerConnectParam> getServerConnectParams();

    /**
     * It sets the server connection parameters for each server runtime instance.
     * @param connectParams The list of server connection parameters.
     */
    public void setServerConnectParams(List<IServerConnectParam> connectParams);
}
