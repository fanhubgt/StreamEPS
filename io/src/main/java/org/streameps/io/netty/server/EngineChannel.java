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

package org.streameps.io.netty.server;

import org.jboss.netty.channel.Channel;
import org.streameps.engine.IEPSEngine;

/**
 *
 * @author Frank Appiah
 */
public class EngineChannel implements IEngineChannel{

    private Channel channel;
    private IEPSEngine engine;
    private String identifier;

    public EngineChannel() {
    }

    public EngineChannel(Channel channel, IEPSEngine engine) {
        this.channel = channel;
        this.engine = engine;
    }

    public EngineChannel(Channel channel, IEPSEngine engine, String identifier) {
        this.channel = channel;
        this.engine = engine;
        this.identifier = identifier;
    }
    
    public void setChannel(Channel channel) {
        this.channel=channel;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void setEngine(IEPSEngine engine) {
        this.engine=engine;
    }

    public IEPSEngine getEngine() {
       return this.engine;
    }

    public void setIdentifier(String identifier) {
        this.identifier=identifier;
    }

    public String getIdentifier() {
       return this.identifier;
    }

}
