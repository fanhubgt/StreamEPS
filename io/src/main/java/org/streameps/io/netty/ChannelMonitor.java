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
 * @author Frank Appiah
 */
public class ChannelMonitor implements IChannelMonitor {

    private long channelCount;
    private long completeCount;
    private long successCount;
    private long failurecount;
    private List<IChannelFutureContext> channelFutureContexts;
    private IChannelFutureContext channelFutureContext;

    public ChannelMonitor() {
    }

    public ChannelMonitor(long channelCount, long completeCount, long successCount, long failurecount, List<IChannelFutureContext> channelFutureContexts) {
        this.channelCount = channelCount;
        this.completeCount = completeCount;
        this.successCount = successCount;
        this.failurecount = failurecount;
        this.channelFutureContexts = channelFutureContexts;
    }

    public long getNumberOfChannels() {
        return this.channelCount;
    }

    public void setNumberOfCompletes(long no_completes) {
        this.completeCount=no_completes;
    }

    public long getNumberOfCompletes() {
        return this.completeCount;
    }

    public long getSuccessCount() {
        return this.successCount;
    }

    public void setSuccessCount(long sucesses) {
        this.successCount=sucesses;
    }

    public void setFailureCount(long failed) {
        this.failurecount=failed;
    }

    public long getFailureCount() {
       return this.failurecount;
    }

    public void addChannelContext(IChannelFutureContext context) {
        channelFutureContexts.add(context);
        channelFutureContext=context;
    }

    public List<IChannelFutureContext> getChannelFutureContexts() {
        return this.channelFutureContexts;
    }

    public IChannelOperationMonitor createInstance() {
        return ChannelOperationMonitor.getInstance();
    }

    public IChannelOperationMonitor getOperationMonitor() {
        return createInstance();
    }

    public IChannelFutureContext getChannelFutureContext() {
        return channelFutureContext;
    }
    
}
