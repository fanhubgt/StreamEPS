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

import java.util.LinkedList;
import java.util.List;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.dispatch.IDispatcherService;

/**
 * It queue events received from the receiver and will dispatch the accumulated events after
 * the sequence size threshold is met.
 * 
 * @author Frank Appiah
 * @version 0.3.3
 */
public class WorkerEventQueue implements IWorkerEventQueue {

    private int sequenceSize = 1, tempSize;
    private List<Object> events = new LinkedList<Object>();
    private SortedAccumulator accumulator;
    private Object lastEvent;
    private IDispatcherService dispatcherService;

    public WorkerEventQueue(int tempSize, IDispatcherService dispatcherService) {
        this.sequenceSize = tempSize;
        this.dispatcherService = dispatcherService;
    }

    public WorkerEventQueue(int squenceSize) {
        this.sequenceSize = squenceSize;
        accumulator = new SortedAccumulator();
    }

    public void setQueueSize(int size) {
        this.sequenceSize = size;
        this.tempSize = size;
    }

    public void addToQueue(Object event) {
        events = this.accumulator.processAt(event.getClass().getName(), event);
        tempSize -= 1;
        if (tempSize < 0) {
            //TODO: implements dispatcher for the decider
            tempSize = sequenceSize;
            this.accumulator.getMap().remove(event.getClass().getName());
        }
        lastEvent = event;
    }

    public List<Object> getQueueEvents() {
        //events = accumulator.highest(sequenceSize);
        return events;
    }

    public Object getLastEvent() {
        return lastEvent;
    }

    public void setDispatcherService(IDispatcherService dispatcherService) {
        this.dispatcherService=dispatcherService;
    }
}
