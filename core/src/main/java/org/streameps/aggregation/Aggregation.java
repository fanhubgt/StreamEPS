package org.streameps.aggregation;

public interface Aggregation<T, S> {

    /**
     * This method computes aggregation function which are mainly: sum, average,
     * count, min, max etc.
     * 
     * @param cv The aggregate value buffer
     * @param value The value for the aggregate computation.
     */
    public void process(T cv, S value);

    /**
     * It returns the result of the aggregation computation.
     * 
     * @return double The result of the aggregation.
     */
    public S getValue();

    /**
     * initialise all variable to zero.
     */
    public void reset();

    
}
