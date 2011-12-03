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
package org.streameps.engine;

import java.util.List;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.dispatch.IDispatcherService;

/**
 * A buffering mechanism that is used to cope with out-of-order arrivals. 
 * It depicts a sequence accumulator/bucket of events from a channel input stream.
 *
 * @author  Frank Appiah
 * @version 0.3.3
 */
public interface IWorkerEventQueue<T> {

    /**
     * It returns the last event in the queue.
     * @return The last event in the queue.
     */
    public T getLastEvent();

    /**
     * It returns the last event unique identifier.
     * @return The last event identifier.
     */
    public String getLastEventID();

    /**
     * It sets the size of the queue.
     * @param size size of the queue.
     */
    public void setQueueSize(int size);

    /**
     * It adds a new event to the queue in FIFO manner.
     * 
     * @param event event being added to the queue.
     * @param ID A unique identifier.
     */
    public void addToQueue(T event, String ID);

    /**
     * It returns the events in the queue.
     * @return list of events.
     */
    public List<T> getQueueEvents(String ID);

    /**
     * It polls a number of events from the event queue.
     * @param n Number of events to poll.
     * @return A list of events.
     */
    public List<T> poll(int n, String ID);

    /**
     * It builds the context partition.
     * @param contextDetail A context detail built from the queue.
     */
    public void buildContextPartition(IReceiverContext receiverContext);

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
     * It returns the sorted accumulator for the worker event queue.
     * @return A sorted accumulator.
     */
    public ISortedAccumulator getAccumulator();
}
