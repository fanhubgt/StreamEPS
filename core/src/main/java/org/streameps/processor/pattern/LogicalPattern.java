package org.streameps.processor.pattern;

import io.s4.persist.Persister;
import io.s4.schema.SchemaContainer;
import org.streameps.operator.assertion.logic.LogicAssertion;

public class LogicalPattern extends BasePattern {

    private Persister persister;
    private LogicAssertion logicAssertion;
    private String identifier = "s4:logicalpattern:";
    private String outputStreamName;
    private SchemaContainer container;
    private boolean match=false;
    
    /**
     * 
     */
    public LogicalPattern() {
	container=new SchemaContainer();
    }

    @Override
    public void output() {

    }
    
    public void processEvent(Object event) {
    
	
    }

    public String getOutputStreamName() {
	return outputStreamName;
    }

    public void setOutputStreamName(String outputStreamName) {
	this.outputStreamName = outputStreamName;
    }

    public Persister getPersister() {
	return persister;
    }

    public void setPersister(Persister persister) {
	this.persister = persister;
    }

    /**
     * @param logicAssertion the logicAssertion to set
     */
    public void setLogicAssertion(LogicAssertion logicAssertion) {
	this.logicAssertion = logicAssertion;
    }
    
    @Override
    public String getId() {
	return identifier + logicAssertion.getType().toString();
    }

}
