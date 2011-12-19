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
package org.streameps.thread;

import java.util.PriorityQueue;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.aggregation.collection.SortedAccumulator;

/**
 *
 * @author Frank Appiah
 */
public class FutureResultQueue implements IFutureResultQueue {

    private PriorityQueue<IResultUnit<?>> resultUnits;
    private int initialCapacity = 5;
    private ISortedAccumulator<IResultUnit<?>> accumulator;
    private IWorkerRegistry workerRegistry;

    public FutureResultQueue() {
        resultUnits = new PriorityQueue<IResultUnit<?>>(initialCapacity);
        accumulator = new SortedAccumulator<IResultUnit<?>>();
    }

    public FutureResultQueue(IWorkerRegistry workerRegistry) {
        resultUnits = new PriorityQueue<IResultUnit<?>>(initialCapacity);
        accumulator = new SortedAccumulator<IResultUnit<?>>();
        this.workerRegistry = workerRegistry;
    }

    public int getSize() {
        return this.resultUnits.size();
    }

    public void addResultUnit(IResultUnit<?> resultFuture) {
        this.resultUnits.add(resultFuture);
        this.accumulator.processAt(resultFuture.getIdentifier(), resultFuture);
    }

    public PriorityQueue<IResultUnit<?>> getResultQueue() {
        return this.resultUnits;
    }

    public IResultUnit<?> getResultUnit(String identifier) {
        return this.accumulator.getAccumulatedByKey(identifier).get(0);
    }

    public void setInitialQueueCapacity(int capacity) {
        this.initialCapacity = capacity;
    }

    public IResultUnit<?> getNextResultUnit() {
        return this.resultUnits.poll();
    }

    public void setWorkerRegistry(IWorkerRegistry workerRegistry) {
        this.workerRegistry = workerRegistry;
    }

    public IWorkerRegistry getWorkerRegistry() {
        return workerRegistry;
    }

    
}
