package org.streameps.aggregation;

/**
 * Interface for the aggregation process.
 * 
 * @author Frank Appiah
 * @param <T> The accumulator for the aggregation process.
 * @param <S> The return type after the aggregation process.
 */
public interface IAggregation<T, E> {

    /**
     * This method computes aggregation function which are mainly: sum, average,
     * count, min, max etc.
     * 
     * @param cv The aggregate value buffer
     * @param value The value for the aggregate computation.
     */
    public void process(T cv, E value);

    /**
     * It returns the result of the aggregation computation.
     * 
     * @return double The result of the aggregation.
     */
    public E getValue();

    /**
     * It returns the aggregate value buffer.
     * @return Aggregate buffer value
     */
    public T getBuffer();

    /**
     * initialise all variable to zero.
     */
    public void reset();
}
