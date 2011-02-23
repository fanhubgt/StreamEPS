package org.streameps.processor.pattern.policy;

/**
 * A cardinality policy is a semantic abstraction that controls how many matching
 * sets are created. The possible policies are single, unrestricted, and bounded.
 * The default cardinality is a single matching set.
 * 
 * @author Frank Appiah
 * @version 0.2.2
 */
public final class CardinalityPolicy implements PatternPolicy {

    private long count_bound = 0;
    private long upper_bound = 0;
    private CardinalityType cardinalityType = CardinalityType.SINGLE;

    public CardinalityPolicy() {
    }

    /**
     * It sets the upper bound for the number of matching set generated
     * during the matching process.
     * @param upper_bound upper bound for number of matched set.
     */
    public CardinalityPolicy(long upper_bound) {
        this.upper_bound = upper_bound-1;
    }

    /**
     * Constructor of cardinality policy with the upper bound and cardinality
     * type.
     * @param upper_bound upper bound for the number of matched set.
     * @param cardinalityType type of cardinality
     */
    public CardinalityPolicy(long upper_bound, CardinalityType cardinalityType) {
        this.cardinalityType = cardinalityType;
        this.upper_bound = upper_bound;
        if (cardinalityType != CardinalityType.BOUNDED) {
            throw new IllegalArgumentException("Upper bound is set with a bounded type cardinality");
        }
    }

    /**
     * It sets the type of cardinality.
     * @param cardinalityType type of cardinality
     */
    public void setCardinalityType(CardinalityType cardinalityType) {
        this.cardinalityType = cardinalityType;
        reset();
    }

    @Override
    public boolean checkPolicy(Object... optional) {
        boolean result = false;
        switch (cardinalityType) {
            case BOUNDED:
                result = (count_bound <= upper_bound);
                count_bound++;
                break;
            case SINGLE:
                result = (count_bound == 1);
                count_bound++;
                break;
            case UNRESTRICTED:
                result = true;
                break;
        }
        return result;
    }

    public PolicyType getPolicyType() {
        return PolicyType.CARDINALITY;
    }

    public void setUpper_bound(long upper_bound) {
        this.upper_bound = upper_bound;
    }

    /**
     * It resets the values of the control variables.
     */
    private void reset() {
        count_bound = 0;
        upper_bound = 0L;
    }
    
}
