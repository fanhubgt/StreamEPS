package org.streameps.processor.pattern.policy;

public class EvaluationPolicy implements PatternPolicy{

    private EvaluationPolicyType type;
    
 
    public EvaluationPolicy() { }
    /**
     * @param type the type to set
     */
    public void setType(EvaluationPolicyType type) {
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
