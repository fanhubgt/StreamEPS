package org.streameps.processor.pattern;

import io.s4.processor.AbstractPE;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.streameps.processor.pattern.policy.PatternPolicy;

/**
 * EPA Book Based: Abstraction of a pattern matching signature
 */
public abstract class BasePattern extends AbstractPE {

    /**
     * A label that determines the meaning and intention of the pattern and
     * specifies the particular kind of matching function to be used.
     */
    protected String type;
    protected static String RELATION = "relation";
    protected static String VALUE = "value";
    /**
     * Additional values used in the definition of the pattern function.
     */
    protected List<PatternParameter> parameters = new ArrayList<PatternParameter>();
    /**
     * The relevant event types list is a list of event types, and it forms part
     * of the pattern matching function. The order of these event types has
     * importance for some pattern functions.
     */
    protected List<Object> relevantEvents = new ArrayList<Object>();
    /**
     * A named parameter that disambiguates the semantics of the pattern and the
     * pattern matching process.
     */
    protected List<PatternPolicy> patternPolicies = new ArrayList<PatternPolicy>();

    protected Set<Object> matchingSet=new HashSet<Object>();
    
    protected Logger logger = Logger.getLogger(BasePattern.class);

    /**
     * @param parameter
     *            the parameter to set
     */
    public void setParameter(List<PatternParameter> parameter) {
	this.parameters = parameter;
    }

    /**
     * @param relevantEvents
     *            the relevantEvents to set
     */
    public void setRelevantEvents(List<Object> relevantEvents) {
	this.relevantEvents = relevantEvents;
    }

    public void setPatternPolicies(List<PatternPolicy> patternPolicies) {
	this.patternPolicies = patternPolicies;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
	this.type = type;
    }

}
