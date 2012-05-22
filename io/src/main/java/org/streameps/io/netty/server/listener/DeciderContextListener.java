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
package org.streameps.io.netty.server.listener;

import java.util.Date;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.util.IDUtil;
import org.streameps.decider.IDeciderContextListener;
import org.streameps.engine.IDeciderContext;
import org.streameps.io.netty.DeciderResponse;
import org.streameps.io.netty.IDeciderResponse;
import org.streameps.io.netty.server.IServiceCallback;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;


/**
 *
 * @author Frank Appiah
 */
public class DeciderContextListener implements IDeciderContextListener {

    private IServiceCallback<IDeciderResponse> serviceCallback;
    private IDeciderResponse deciderResponse;
    private IDeciderContext<IMatchedEventSet> deciderContext;
    private ILogger logger=LoggerUtil.getLogger(DeciderContextListener.class);
    private String identifier;

    public DeciderContextListener() {
    }

    public DeciderContextListener(IServiceCallback<IDeciderResponse> serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    public void onDeciderReceive(IDeciderContext<IMatchedEventSet> context) {
        deciderContext = context;
        fireCallback();
        logger.debug("Decider match received from the listener.");
    }

    public void setServiceCallback(IServiceCallback<IDeciderResponse> serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    private void fireCallback() {
        deciderResponse = new DeciderResponse();
        deciderResponse.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        for (Object event : deciderContext.getDeciderValue()) {
            deciderResponse.geDeciderContext().getDeciderValue().add(event);
        }
        serviceCallback.onServiceCall(deciderResponse, true);
        logger.debug("Firing the decider response to the client.");
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier=identifier;
    }
    
}
