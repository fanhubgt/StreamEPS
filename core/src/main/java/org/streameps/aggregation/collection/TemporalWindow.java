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
 * @author Development Team
 */
public class TemporalWindow {

    private ArrayDeque<IWindowMapAccumulator<Object>> window = new ArrayDeque<IWindowMapAccumulator<Object>>();
    private Long currentTimestamp;
    private Map<Object, ArrayDeque<Object>> backup = Collections.synchronizedMap(new TreeMap<Object, ArrayDeque<Object>>());

    public TemporalWindow() {
    }

    public void adjustWindow(long delta) {
        for (IWindowMapAccumulator accumulator : window) {
            accumulator.updateWindowTime(delta);
        }
        if (currentTimestamp != null) {
            currentTimestamp += delta;
        }
    }

    public void putOrUpdate(long timestamp, Object event) {
        if (currentTimestamp == null) {
            currentTimestamp = timestamp;
        }
        if (window.isEmpty()) {
            putNew(timestamp, event);
            return;
        }
        IWindowMapAccumulator lastAcc = window.getLast();
        if (lastAcc.getTimestamp() == timestamp) {
            lastAcc.getAccumulate(timestamp).add(event);
            backup.put(event, lastAcc.getAccumulate(timestamp));
            return;
        }
        putNew(timestamp, event);

    }

    private void putNew(long timestamp, Object event) {
        ArrayDeque<Object> winEvent = new ArrayDeque<Object>();
        winEvent.add(event);
        IWindowMapAccumulator<Object> newAcc = new WindowMapAccumulator<Object>();
        newAcc.accumulate(timestamp, winEvent);
        backup.put(event, winEvent);
        window.add(newAcc);
    }

    public void remove(Object event) {
        ArrayDeque<Object> listEvents = backup.get(event);
        if (listEvents != null) {
            listEvents.remove(event);
        }
        backup.remove(event);
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
    public ArrayDeque<Object> getWindowEvents(long expireTimestamp) {
        if (window.isEmpty()) {
            return null;
        }
        IWindowMapAccumulator<Object> windowEvent = window.getFirst();
        if (windowEvent.getTimestamp() >= expireTimestamp) {
            return null;
        }
        ArrayDeque<Object> windowEvents = new ArrayDeque<Object>();
        do {
            long lastTimestamp = windowEvent.getTimestamp();
            windowEvents.addAll(windowEvent.getAccumulate(lastTimestamp));
            window.removeFirst();
            if (window.isEmpty()) {
                break;
            }
            windowEvent = window.getFirst();
        } while (windowEvent.getTimestamp() > expireTimestamp);
        if (window.isEmpty()) {
            currentTimestamp = null;
        } else {
            currentTimestamp = windowEvent.getTimestamp();
        }
        for (Object event : windowEvents) {
            backup.remove(event);
        }
        return windowEvents;
    }

    public boolean isEmpty() {
        return window.isEmpty();
    }
}
