/*
 * ====================================================================
 *  StreamEPS Platform
 *
 *  Distributed under the Modified BSD License.
 *  Copyright notice: The copyright for this software and a full listing
 *  of individual contributors are as shown in the packaged copyright.txt
 *  file.
 *  All rights reserved.
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 *  - Neither the name of the ORGANIZATION nor the names of its contributors may
 *  be used to endorse or promote products derived from this software without
 *  specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 *  FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 *  USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  =============================================================================
 */
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
    protected String name;
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
     * @param name
     *            the name to set
     */
    public void setName(String type) {
	this.name = type;
    }

    public abstract void processEvent(Object event);
}
