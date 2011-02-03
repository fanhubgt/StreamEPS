package org.streameps.aggregation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapCounter implements EventAccumulator {

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
