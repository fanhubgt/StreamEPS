package org.streameps.processor.pattern;

public class SpatialPattern extends BasePattern {

    private String NAME = "s4:spatial";
    private boolean match=false;
    //private 

    /*
     * @see io.s4.processor.AbstractPE#output()
     */
    @Override
    public void output() {

    }
    
    public void process(Object event)
    {
	
    }

    /*
     * @see io.s4.processor.ProcessingElement#getId()
     */
    @Override
    public String getId() {
	return NAME;
    }

}
