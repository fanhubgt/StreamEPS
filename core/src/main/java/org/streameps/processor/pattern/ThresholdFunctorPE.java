package org.streameps.processor.pattern;

import org.streameps.aggregation.collection.AssertionValuePair;
import org.streameps.aggregation.collection.HashMapCounter;
import org.streameps.dispatch.Dispatchable;
import org.streameps.filter.Functor;
import org.streameps.filter.FunctorRegistry;

public class ThresholdFunctorPE<E> extends BasePattern<E> {

    private String outputStreamName;
    private FunctorRegistry functorRegistry;
    private String FUNCTOR_NAME = "eps:functor";
    private String id = "s4:threshold:functor";
    private HashMapCounter mapCounter = null;
    private boolean match = false;
    private Dispatchable dispatcher;
    private AssertionValuePair counter;
    private Functor functor;

    public ThresholdFunctorPE() {
    }

    @Override
    public void output() {
        matchingSet.clear();
    }

    public void processEvent(E event) {
        synchronized (this) {
            IPatternParameter<E> threshParam = parameters.get(0);
            String prop = threshParam.getPropertyName();
            if (prop.equalsIgnoreCase(FUNCTOR_NAME)) {
                long count = mapCounter.incrementAt(event);
                int threshold = (Integer) threshParam.getValue();
                //assertionType = (String) threshParam.getRelation();
                counter.threshold = threshold;
                counter.value = count;
            }
            execPolicy("process");
        }
    }

    public String getId() {
        return id;
    }

    /**
     * @param functorRegistry the functorRegistry to set
     */
    public void setFunctorRegistry(FunctorRegistry functorRegistry) {
        this.functorRegistry = functorRegistry;
    }

    /**
     * @param outputStreamName
     *            the outputStreamName to set
     */
    @Override
    public void setOutputStreamName(String outputStreamName) {
        this.outputStreamName = outputStreamName;
    }

    /**
     * @param dispatcher the dispatcher to set
     */
    public void setDispatcher(Dispatchable dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Functor getFunctor() {
        return functor;
    }
}
