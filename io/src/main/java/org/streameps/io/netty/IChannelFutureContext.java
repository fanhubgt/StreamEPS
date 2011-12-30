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
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;

/**
 * An interface for the channel context to hold properties of a future channel
 * activity.
 * @author  Frank Appiah
 */
public interface IChannelFutureContext {

    /**
     * It returns the channel for this context.
     * @return The channel for this future context.
     */
    public Channel getChannel();

    /**
     * It sets the success flag of this context.
     * @return The success flag indicator.
     */
    public void setSuccessFlag(boolean success);

    /**
     * It returns the success flag of this context.
     * @return The success flag indicator.
     */
    public boolean getSuccessFlag();

    /**
     * It sets the complete flag to indicate whether or not the channel operation
     * is complete.
     * @return The complete status flag;
     */
    public void setCompleteFlag(boolean complete);

    /**
     * It returns the complete flag to indicate whether or not the channel operation
     * is complete.
     * @return A complete channel operation indicator.
     */
    public boolean getCompleteFlag();

    /**
     * The context for wait operation on the channel.
     * @param waitContext The wait context.
     */
    public void setWaitContext(IWaitContext waitContext);

    /**
     * It returns the wait context for the channel context.
     * @return The wait context.
     */
    public IWaitContext getWaitContext();

    /**
     *  It returns the list of channel future listeners for this context.
     * @param futureListeners the list of channel future listeners for this context.
     */
    public void setChannelFutureListeners(List<ChannelFutureListener> futureListeners);

    /**
     * It returns the list of channel future listeners for this context.
     * @return The list of channel future listeners for this context.
     */
    public List<ChannelFutureListener> getChannelFutureListeners();

    /**
     * It sets the progress status of the channel context.
     * @param progressStatus The progress status instance of the channel context.
     */
    public void setProgressStatus(IProgressStatus progressStatus);

    /**
     * It returns the progress status of the channel context.
     * @return The progress status instance of the channel context.
     */
    public IProgressStatus getProgressStatus();
}
