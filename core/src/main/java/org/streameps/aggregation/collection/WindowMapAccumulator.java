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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Frank Appiah
 */
public class WindowMapAccumulator<T> extends Accumulator implements IWindowMapAccumulator<T> {

    private Map<Long, ArrayDeque<T>> windowMap;
    private long lastTimestamp;

    public WindowMapAccumulator() {
        windowMap = Collections.synchronizedMap(new ConcurrentHashMap<Long, ArrayDeque<T>>());
    }

    public WindowMapAccumulator(Map<Long, ArrayDeque<T>> map) {
        windowMap = map;
    }

    public Map<Long, ArrayDeque<T>> getWindowMap() {
        return windowMap;
    }

    public void updateWindowTime(long delta) {
        Map<Long, ArrayDeque<T>> win = new HashMap<Long, ArrayDeque<T>>();
        for (Long time : windowMap.keySet()) {
            win.put(time + delta, windowMap.get(time));
        }
        windowMap.clear();
        windowMap.putAll(win);
    }

    public ArrayDeque<T> getAccumulate(long windowTime) {
        return windowMap.get(windowTime);
    }

    public void accumulate(long win, ArrayDeque<T> event) {
        lastTimestamp = win;
        windowMap.put(win, event);
    }

    public void remove(Long timestamp) {
        windowMap.remove(timestamp);
    }

    public Long getTimestamp() {
        return lastTimestamp;
    }

    @Override
    public IEventAccumulator clone() {
        return this;
    }

    public void clear() {
        this.clear();
    }

    public long getSizeCount() {
        long total = 0;
        for (ArrayDeque<T> bucket : windowMap.values()) {
            total += bucket.size();
        }
        return total;
    }
    
}
