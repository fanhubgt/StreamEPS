package org.streameps.processor.pattern;
/**
 * The always pattern is satisfied when the participant event set
 * is non-empty, and all the participant events satisfy the always pattern assertion.
 * If the pattern is satisfied, the matching set is the entire participant event set.
 */
public class ModalPatternPE extends BasePattern {

    @Override
    public void output() {

    }

    @Override
    public String getId() {
	return null;
    }
    
    public void processEvent(Object event) {
	
    }

}
