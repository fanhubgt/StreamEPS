package org.streameps.aggregation;


public interface Aggregation<T> {

    /**
     * This method computes aggregation function which are mainly: sum, average,
     * count, min, max etc.
     * 
     * @param cv The aggregate value
     * @param value value for the computation.
     */
    public void process(T cv, double value );

    /**
     * It returns the result of the aggregation computation.
     * 
     * @return double The result of the aggregation.
     */
    public Double getValue();
}
