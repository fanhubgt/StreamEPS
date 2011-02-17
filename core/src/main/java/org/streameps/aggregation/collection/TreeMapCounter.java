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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapCounter implements ITreeMapCounter {

    private Map<Object, Long> map;
    private AccumulationState accumulationMode;

    public TreeMapCounter() {
        this.accumulationMode = AccumulationState.Clean;
        this.map = Collections.synchronizedMap(new HashMap<Object, Long>());
    }

    public TreeMapCounter(AccumulationState accumulationMode) {
        this.accumulationMode = accumulationMode;
        this.map = new TreeMap<Object, Long>();
    }

    public TreeMapCounter(TreeMapCounter counter) {
        this.accumulationMode = counter.accumulationMode;
        this.map = Collections.synchronizedMap(new HashMap<Object, Long>());
    }

    public long incrementAt(Object key) {
        Long count = map.get(key);
        if (count == null) {
            map.put(key, 1L);
            return 1L;
        }
        map.put(key, ++count);
        this.accumulationMode = AccumulationState.Dirty;
        return count;
    }

    public long addAt(Object key, long delta) {
        Long count = map.get(key);
        if (count == null) {
            map.put(key, delta);
            return delta;
        }
        map.put(key, count + delta);
        this.accumulationMode = AccumulationState.Dirty;
        return count + delta;
    }

    public long totalCount() {
        long total = 0;
        for (long count : map.values()) {
            total += count;
        }
        return total;
    }

    public long totalCountByKey(Object key) {
        return map.get(key);
    }

    @Override
    public void clear() {
        if (accumulationMode == AccumulationState.Dirty) {
            map.clear();
        }
    }

    @Override
    public TreeMapCounter clone() {
        return new TreeMapCounter(this);
    }

    @Override
    public String toString() {
        return "TreeMapCounter [map=" + map + "]";
    }

    /**
     * @return the map
     */
    public Map<Object, Long> getMap() {
        return map;
    }
}
