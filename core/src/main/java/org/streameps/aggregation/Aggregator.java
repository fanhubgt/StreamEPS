package org.streameps.aggregation;


public class Aggregator implements EventAccumulator {

    private CounterValue agg;

    private Aggregation processor;

    public class SumAggregation implements Aggregation {

	@Override
	public void process(CounterValue cv, double value) {
	    cv.count++;
	    cv.value += value;
	}
    }

    public Aggregator() {
	agg = new CounterValue(0, 0);
	this.processor = new SumAggregation();
    }

    public Aggregator(Aggregator aggregator) {
	this.agg = aggregator.agg;
	this.processor = aggregator.processor;
    }

    public CounterValue process(double value) {
	processor.process(agg, value);
	return agg;
    }

    @Override
    public void clear() {
	agg.count = 0;
	agg.value = 0;
    }

    @Override
    public Aggregator clone() {
	return new Aggregator(this);
    }

    @Override
    public String toString() {
	return "Aggregator [agg=" + agg + "]";
    }
}
