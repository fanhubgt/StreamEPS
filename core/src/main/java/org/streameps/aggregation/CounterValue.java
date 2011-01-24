package org.streameps.aggregation;

public class CounterValue {

    public long count;

    public long value;

    public CounterValue(long count, long value) {
	this.count = count;
	this.value = value;
    }
    
    public CounterValue(int count, int value) {
	this.count = count;
	this.value = value;
    }
    
    public CounterValue(double count, double value) {
	this.count = (long) count;
	this.value = (long) value;
    }
    
    public CounterValue(float count, float value) {
	this.count = (long) count;
	this.value = (long) value;
    }    

    @Override
    public String toString() {
	return "CountValue [count=" + count + ", value=" + value + "]";
    }
}
