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

import java.io.Serializable;
import java.util.List;
import org.streameps.core.ISchedulableEvent;
import org.streameps.dispatch.IDispatcherService;

/**
 * Interface for a schedulable event queue.
 * 
 * @author  Frank Appiah
 */
public interface ISchedulableQueue<E> extends Serializable {

    /**
     * It polls for a list of events at a scheduled rate.
     * @return A list of event objects.
     */
    public void schedulePollAtRate();

    /**
     * It polls for a list of events at a scheduled rate.
     *
     * @return A list of event objects.
     */
    public void schedulePollAtRateByCount();

    /**
     * It renew a list of events.
     * @param timestamp A timestamp for the renewal process.
     */
    public void renewScheduledEvent(long timestamp);

    /**
     * It adds a new event to the queue in FIFO manner.
     *
     * @param event event being added to the queue.
     * @param ID A unique identifier.
     */
    public void addToQueue(E event);

    /**
     * It returns the events in the queue.
     * @return list of events.
     */
    public List<ISchedulableEvent<E>> getQueueEvents(long timestamp);

    /**
     * It polls a number of events from the event queue.
     * @param n Number of events to poll.
     * @return A list of events.
     */
    public List<ISchedulableEvent<E>> poll(int n);

    /**
     * It sets the dispatcher service for dispatching the queued events.
     * @param dispatcherService An instance of the dispatcher service.
     */
    public void setDispatcherService(IDispatcherService dispatcherService);

    /**
     * It returns the dispatcher service for dispatching the queued events.
     * @return An instance of the dispatcher service.
     */
    public IDispatcherService getDispatcherService();

    /**
     * It sets the schedule process to be executed when a new schedule events
     * are received on time elapsed.
     * @param callable A schedule process call on the queue.
     */
    public void setScheduleCallable(IScheduleCallable callable);

    /**
     * It returns the schedule process to be executed on a queue.
     * @return A schedule process call on the queue.
     */
    public IScheduleCallable getScheduleCallable();

    /**
     * It sets the number of event to poll during the scheduled process.
     * @param count The number of event to poll.
     */
    public void setCount(int count);

    /**
     * It returns the number of event to poll during the scheduled process.
     * @return The number of event to poll.
     */
    public int getCount();

    /**
     * It sets the periodic time stamp for the polling.
     * @param period The time stamp for the polling.
     */
    public void setPeriod(long period);

    /**
     * It returns the periodic time stamp for the polling.
     * @return The time stamp for the polling.
     */
    public long getPeriod();
    
}
