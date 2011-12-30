package org.streameps.processor.pattern.policy;

/**
 * Interface for the pattern policy specification.
 *
 * @author Frank Appiah
 * @version 0.2.2
 */
public interface PatternPolicy{

    /**
     * It evaluates the pattern policy.
     * @param optional An optional parameter supported for some pattern
     * implementation.
     * @return It returns the success/failure after the matching process.
     */
    public boolean checkPolicy(Object... optional);

    /**
     * It returns the pattern policy type.
     * Supported types include cardinality, consumption, order, evaluation
     * and repeated type.
     * @return Type of pattern policy.
     */
    public PolicyType getPolicyType();
}
