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
import java.util.Map;

/**
 * An interface specification for a channel monitor for the
 * 
 * @author Frank Appiah
 */
public interface IChannelMonitor {

    /**
     * It returns the number of channels in the monitor.
     * @return The number of channels in the monitor.
     */
    public long getNumberOfChannels();

    /**
     * The sets the number of complete processes in the monitor.
     * @param no_completes The number of complete processes.
     */
    public void setNumberOfCompletes(long no_completes);

    /**
     * It returns the number of complete processes in the monitor.
     * @return The number of complete processes.
     */
    public long getNumberOfCompletes();

    /**
     * It returns the number of success processes in the monitor.
     * @return The number of success processes.
     */
    public long getSuccessCount();

    /**
     * It sets the number of success processes in the monitor.
     * @param sucesses  The number of success processes.
     */
    public void setSuccessCount(long sucesses);

    /**
     * It sets the number of failures in the monitor.
     * @param failed The number of failures in the monitor.
     */
    public void setFailureCount(long failed);

    /**
     * It returns the number of failures in the monitor.
     * @return The number of failures in the monitor.
     */
    public long getFailureCount();

    /**
     * It adds a channel future context to be monitored. It sets the number of
     * channels in the monitor.
     * @param context A channel future context to be added.
     */
    public void addChannelContext(IChannelFutureContext context);

    /**
     * It returns the list of open/close channel future context.
     * @return A list of channel future context.
     */
    public List<IChannelFutureContext> getChannelFutureContexts();

    /**
     * It create a new singleton of the channel operation monitor.
     * @return The instance of the channel operation monitor.
     */
    public IChannelOperationMonitor createInstance();

    /**
     * It returns the singleton channel operation monitor created by the
     * create instance method call.
     * @return The instance of the channel operation monitor.
     */
    public IChannelOperationMonitor getOperationMonitor();

    Map<String, IChannelOperationMonitor> getOperationMonitorMap();

    void setOperationMonitorMap(Map<String, IChannelOperationMonitor> operationMonitorMap);
}
