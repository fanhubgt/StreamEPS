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

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.streameps.aggregation.Accumulation;

public class SortedAccumulator<T> extends Accumulator implements ISortedAccumulator<T> {

    private TreeMap<Object, List<T>> map;
    private Accumulation processor;

    public long getSizeCount() {
        long total = 0;
        if(map!=null)
        {
        for (List<T> bucket : map.values()) {
            total += bucket.size();
        }}
        return total;
    }

    public class AddAccumulation<T> implements Accumulation<T> {

        @Override
        public void process(List<T> acc, T value) {
            acc.add(value);
        }
    }

    public class RemoveAccumulation<T> implements Accumulation<T> {

        @Override
        public void process(List<T> acc, T value) {
            acc.remove(value);
        }
    }

    public SortedAccumulator() {
        this.map = new TreeMap<Object, List<T>>();
        this.processor = new AddAccumulation();
    }

    public SortedAccumulator(AccumulationState accumulationMode) {
        this.map = new TreeMap<Object, List<T>>();
        this.processor = new AddAccumulation();
    }

    public SortedAccumulator(SortedAccumulator accumulator) {
        this.map = new TreeMap<Object, List<T>>(accumulator.map);
        this.processor = accumulator.processor;
    }

    public List<T> processAt(Object key, T value) {
        List<T> acc = map.get(key);
        if (acc == null) {
            acc = new LinkedList<T>();
            map.put(key, acc);
        }
        processor.process(acc, value);
        return acc;
    }

    public List<T> getAccumulatedByKey(Object key) {
        List<T> acc = map.get(key);
        return acc;
    }

    public List<T> lowest(int n) {
        List<T> results = new LinkedList<T>();
        int count = 0;
        for (Entry<Object, List<T>> entry : map.entrySet()) {
            List<T> objs = entry.getValue();
            for (T obj : objs) {
                results.add(obj);
                if (++count == n) {
                    return results;
                }
            }
        }
        return results;
    }

    public List<T> lowestByKey(Object key, int n) {
        List<T> results = new LinkedList<T>();
        int count = 0;
        List<T> objs = map.get(key);
        for (T obj : objs) {
            results.add(obj);
            if (++count == n) {
                return results;
            }
        }
        return results;
    }

    public List<T> highest(int n) {
        List<T> results = new LinkedList<T>();
        int count = 0;
        for (Object key : map.descendingKeySet()) {
            List<T> objs = map.get(key);
            for (T obj : objs) {
                results.add(obj);
                if (++count == n) {
                    return results;
                }
            }
        }
        return results;
    }

    public List<T> highestByKey(Object key, int n) {
        List<T> results = new LinkedList<T>();
        int count = 0;
        List<T> objs = map.get(key);
        for (T obj : objs) {
            results.add(obj);
            if (++count == n) {
                return results;
            }
        }
        return results;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public SortedAccumulator clone() {
        return new SortedAccumulator(this);
    }

    public TreeMap<Object, List<T>> getMap() {
        return map;
    }

    @Override
    public String toString() {
        return "SortedAccumulator [map=" + map + "]";
    }
    
}
