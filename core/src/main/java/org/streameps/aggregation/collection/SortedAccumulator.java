/*
 * ====================================================================
 * StreamEPS Platform
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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.streameps.aggregation.Accumulation;
import org.streameps.aggregation.EventAccumulator;

public class SortedAccumulator implements EventAccumulator {

    private TreeMap<Object, List<Object>> map;
    private Accumulation processor;
    private AccumulationState accumulationMode;

    public class AddAccumulation implements Accumulation {

        @Override
        public void process(List<Object> acc, Object value) {
            acc.add(value);
        }
    }

    public class RemoveAccumulation implements Accumulation {

        @Override
        public void process(List<Object> acc, Object value) {
            acc.remove(value);
        }
    }

    public SortedAccumulator() {
        this.accumulationMode = AccumulationState.Clean;
        this.map = new TreeMap<Object, List<Object>>();
        this.processor = new AddAccumulation();
    }

    public SortedAccumulator(AccumulationState accumulationMode) {
        this.accumulationMode = accumulationMode;
        this.map = new TreeMap<Object, List<Object>>();
        this.processor = new AddAccumulation();
    }

    public SortedAccumulator(SortedAccumulator accumulator) {
        this.accumulationMode = accumulator.accumulationMode;
        this.map = new TreeMap<Object, List<Object>>(accumulator.map);
        this.processor = accumulator.processor;
    }

    public List<Object> processAt(Object key, Object value) {
        List<Object> acc = map.get(key);
        if (acc == null) {
            acc = new ArrayList<Object>();
            map.put(key, acc);
            accumulationMode = AccumulationState.Dirty;
        }
        processor.process(acc, value);
        return acc;
    }

    public List<Object> getEventsByKey(Object key) {
        List<Object> acc = map.get(key);
        return acc;
    }

    public long totalCount() {
        long total = 0;
        for (List<Object> bucket : map.values()) {
            total += bucket.size();
        }
        return total;
    }

    public List<Object> lowest(int n) {
        List<Object> results = new ArrayList<Object>();
        int count = 0;
        for (Entry<Object, List<Object>> entry : map.entrySet()) {
            List<Object> objs = entry.getValue();
            for (Object obj : objs) {
                results.add(obj);
                if (++count == n) {
                    return results;
                }
            }
        }
        return results;
    }

    public List<Object> highest(int n) {
        List<Object> results = new ArrayList<Object>();
        int count = 0;
        for (Entry<Object, List<Object>> entry : map.descendingMap().entrySet()) {
            List<Object> objs = entry.getValue();
            for (Object obj : objs) {
                results.add(obj);
                if (++count == n) {
                    return results;
                }
            }
        }
        return results;
    }

    @Override
    public void clear() {
        if (accumulationMode == AccumulationState.Dirty) {
            map.clear();
        }
    }

    @Override
    public SortedAccumulator clone() {
        return new SortedAccumulator(this);
    }

    @Override
    public String toString() {
        return "SortedAccumulator [map=" + map + "]";
    }
}
