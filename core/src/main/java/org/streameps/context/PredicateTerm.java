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
package org.streameps.context;

/**
 * Implementation of the predicate term for the predicate expression.
 * 
 * @author Frank Appiah
 */
public class PredicateTerm implements IPredicateTerm {

    private String propertyName;
    private String predicateOperator;
    private Object propertyValue;
    private String identifier;

    public PredicateTerm() {
    }

    public PredicateTerm(String propertyName, PredicateOperator po, Object propertyValue) {
        this.propertyName = propertyName;
        this.predicateOperator = po.getName();
        this.propertyValue = propertyValue;
    }

    public PredicateTerm(String propertyName, String predicateOperator, Object propertyValue) {
        this.propertyName = propertyName;
        this.predicateOperator = predicateOperator;
        this.propertyValue = propertyValue;
    }

    public PredicateTerm(String propertyName, String predicateOperator, Object propertyValue, String identifier) {
        this.propertyName = propertyName;
        this.predicateOperator = predicateOperator;
        this.propertyValue = propertyValue;
        this.identifier = identifier;
    }

     public PredicateTerm(String propertyName, Object propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPredicateOperator(String predicateOperator) {
        this.predicateOperator = predicateOperator;
    }

    public String getPredicateOperator() {
        return this.predicateOperator;
    }

    public void setPropertyValue(Object value) {
        this.propertyValue = value;
    }

    public Object getPropertyValue() {
        return this.propertyValue;
    }

    @Override
    public String toString() {
        return "PredicateTerm:="+
                ";property:"+getPropertyName()+
                ";operator:"+getPredicateOperator()+
                ";value:"+getPropertyValue();
    }

    public void setIdentifier(String identifier) {
        this.identifier=identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

}
