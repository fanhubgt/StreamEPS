package org.streameps.processor.pattern.policy;

public class EvaluationPolicy implements PatternPolicy{

    private EvaluationType type;
    
 
    public EvaluationPolicy() { }
    /**
     * @param type the type to set
     */
    public void setType(EvaluationType type) {
	this.type = type;
    }
    /* (non-Javadoc)
     * @see io.s4.processor.pattern.policy.PatternPolicy#checkPolicy()
     */
    @Override
    public boolean checkPolicy() {
	// TODO Auto-generated method stub
	return false;
    }
    
}
