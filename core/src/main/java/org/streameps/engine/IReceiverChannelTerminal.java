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

package org.streameps.engine;

import org.streameps.dispatch.DispatcherService;
import org.streameps.epn.channel.ChannelInputTerminal;
import org.streameps.filter.IFilterManager;
import org.streameps.processor.IEventAggregator;
import org.streameps.processor.IPatternManager;

/**
 *
 * @author  Frank Appiah
 */
public interface IReceiverChannelTerminal<T> extends ChannelInputTerminal<T>, IScheduleCallable<T> {

    DispatcherService getDispatcherService();

    IEventAggregator getEventAggregator();

    IFilterManager getFilterManager();

    IEPSForwarder<T> getForwarder();

    IPatternManager<T> getPatternManager();

    IEPSProducer getProducer();

    ISchedulableQueue<T> getSchedulableQueue();

    int getSquenceSize();

    boolean isStarted();

    boolean isStop();

    void setDispatcherService(DispatcherService dispatcherService);

    void setEventAggregator(IEventAggregator eventAggregator);

    void setFilterManager(IFilterManager filterManager);

    void setForwarder(IEPSForwarder<T> forwarder);

    void setPatternManager(IPatternManager<T> patternManager);

    void setProducer(IEPSProducer producer);

    void setSchedulableQueue(ISchedulableQueue<T> schedulableQueue);

    void setSquenceSize(int squenceSize);

    void startTerminal();

    void stopTerminal();

}
