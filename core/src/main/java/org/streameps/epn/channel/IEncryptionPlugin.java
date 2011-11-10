/*
 * ====================================================================
 *  StreamEPS Platform
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
package org.streameps.epn.channel;

import java.util.Map;

/**
 * It specifies on which connection which encryption algorithm is used. Thereby,
 * it is possible to use strong encryption when communicating to brokers in
 * insecure networks, but switch encryption completely off if clients are
 * connected locally.
 * 
 * @author Frank Appiah
 * @version 0.3.3
 */
public interface IEncryptionPlugin {

    /**
     * It adds a new encryption implementation to the plug-in manager.
     * @param channelIdentifier An identifier for the event channel.
     * @param encryptor An implementation of the channel.
     */
    public void addChannelEncryptor(String channelIdentifier, IEventEncryptor encryptor);

    /**
     * It returns a map of channel identifiers with its associated event encryption implementation.
     * @return A map of identifiers and encryption implementation.
     */
    public Map<String, IEventEncryptor> getEncryptionPlugs();

    /**
     * It returns an instance of an event encryption plug by channel identifier.
     * @return An instance of an encryption plug.
     */
    public IEventEncryptor getEncryptorByChannel(String channelID);

    /**
     * It returns the number of plug-in.
     * @return size of encryption plug-ins.
     */
    public int getPluginSize();
}
