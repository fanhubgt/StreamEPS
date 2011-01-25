package org.streameps.aggregation;


public class CountAggregation implements EventAccumulator {

    private long count;

    public CountAggregation() {
    }

    public CountAggregation(CountAggregation counter) {
	this.count = counter.count;
    }

    public long increment() {
	return ++count;
    }

    public long add(long delta) {
	count += delta;
	return count;
    }

    public long totalCount() {
	return count;
    }

    @Override
    public void clear() {
	count = 0;
    }

    @Override
    public CountAggregation clone() {
	return new CountAggregation(this);
    }

    @Override
    public String toString() {
	return "Counter [count=" + count + "]";
    }

}
