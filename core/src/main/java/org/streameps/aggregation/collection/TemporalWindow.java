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
package org.streameps.aggregation.collection;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Frank Appiah
 */
public class TemporalWindow<T> extends Accumulator implements ITemporalWindow<T> {

    private ArrayDeque<IWindowMapAccumulator<T>> window;
    private Long currentTimestamp;
    private Map<Long, ArrayDeque<T>> backupEvents = Collections.synchronizedMap(new TreeMap<Long, ArrayDeque<T>>());

    public TemporalWindow() {
        window = new ArrayDeque<IWindowMapAccumulator<T>>();
    }

    public void adjustWindow(long delta) {
        for (IWindowMapAccumulator<T> accumulator : window) {
            accumulator.updateWindowTime(delta);
        }
        if (currentTimestamp != null) {
            currentTimestamp += delta;
        }
    }

    public void putOrUpdate(long timestamp, T event) {
        if (currentTimestamp == null) {
            currentTimestamp = timestamp;
        }
        if (window.isEmpty()) {
            putNew(timestamp, event);
            return;
        }
        IWindowMapAccumulator<T> lastAcc = window.getLast();
        if (lastAcc.getTimestamp() == timestamp) {
            window.getLast().getAccumulate(timestamp).add(event);
            backupEvents.put(timestamp, lastAcc.getAccumulate(timestamp));
            return;
        }
        putNew(timestamp, event);

    }

    private void putNew(long timestamp, T event) {
        ArrayDeque<T> winEvent = new ArrayDeque<T>();
        winEvent.add(event);
        IWindowMapAccumulator<T> newAcc = new WindowMapAccumulator<T>();
        newAcc.accumulate(timestamp, winEvent);
        backupEvents.put(timestamp, winEvent);
        window.add(newAcc);
    }

    public void remove(T event) {
        //ArrayDeque<T> listEvents = backupEvents.get(event);
        //if (listEvents != null) {
        //    listEvents.remove(event);
        //}
        //backupEvents.remove(event);
    }

    public Long getCurrentTimestamp() {
        return currentTimestamp;
    }

    /**
     * Retrieves a list of events in a specific time window.
     * 
     * @param expireTimestamp Timestamp expire
     * @return Queue of events
     */
    public ArrayDeque<T> getWindowEvents(long expireTimestamp) {
        if (window.isEmpty()) {
            return null;
        }
        IWindowMapAccumulator<T> windowEvent = window.removeFirst();
        if (windowEvent.getTimestamp() >= expireTimestamp) {
            return null;
        }
        ArrayDeque<T> windowEvents = new ArrayDeque<T>();
        do {
            long lastTimestamp = windowEvent.getTimestamp();
            windowEvents.addAll(windowEvent.getAccumulate(lastTimestamp));
            if (window.isEmpty()) {
                break;
            }
            windowEvent = window.removeFirst();
        } while (windowEvent.getTimestamp() < expireTimestamp);
        if (window.isEmpty()) {
            currentTimestamp = null;
        } else {
            currentTimestamp = windowEvent.getTimestamp();
        }
      //  for (T event : windowEvents) {
        //    backupEvents.remove(event.getClass().getName());
       // }
        return windowEvents;
    }

    public boolean isEmpty() {
        return window.isEmpty();
    }

    public long getSizeCount() {
        long total = 0;
        for (Object bucket : window.toArray()) {
            IWindowMapAccumulator<T> accumulator = (IWindowMapAccumulator<T>) bucket;
            total += accumulator.getSizeCount();
        }
        return total;
    }

    public Map<Long, ArrayDeque<T>> getBackupEvents() {
        return backupEvents;
    }
}
