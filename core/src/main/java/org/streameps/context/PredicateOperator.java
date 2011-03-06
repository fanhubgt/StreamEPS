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
 *
 * @author  Frank Appiah
 */
public enum PredicateOperator {

       /**
     * LESS THAN : <
     */
    LESS_THAN("lt", "<"),
    /**
     * GREATER THAN OR EQUAL: >=
     */
    GREATER_THAN_OR_EQUAL("geq", ">="),
    /**
     * EQUAL: =
     */
    EQUAL("eq", "="),
    /**
     *  GREATER: >
     */
    GREATER("gt", ">"),
    /**
     * LESS_THAN_OR_EQUAL : <=
     */
    LESS_THAN_OR_EQUAL("leq", "<="),
    /**
     * NOT EQUAL: !=
     */
    NOT_EQUAL("neq", "!="),
    /**
     * Range contains neither endpoint, i.e. (a,b)
     */
    RANGE_OPEN("ro","(a,b)"),

    /**
     * Range contains low and high endpoint, i.e. [a,b]
     */
    RANGE_CLOSED("rc","[a,b]"),

    /**
     * Range includes low endpoint but not high endpoint, i.e. [a,b)
     */
    RANGE_HALF_OPEN("rho","[a,b)"),

    /**
     * Range includes high endpoint but not low endpoint, i.e. (a,b]
     */
    RANGE_HALF_CLOSED("rhc","(a,b]"),

    /**
     * Inverted-Range contains neither endpoint, i.e. (a,b)
     */
    NOT_RANGE_OPEN("not ro","!(a,b)"),

    /**
     * Inverted-Range contains low and high endpoint, i.e. [a,b]
     */
    NOT_RANGE_CLOSED("not rc","![a,b]"),

    /**
     * Inverted-Range includes low endpoint but not high endpoint, i.e. [a,b)
     */
    NOT_RANGE_HALF_OPEN("not rho","![a,b)"),

    /**
     * Inverted-Range includes high endpoint but not low endpoint, i.e. (a,b]
     */
    NOT_RANGE_HALF_CLOSED("not rhc","!(a,b]"),

    /**
     * List of values using the 'in' operator, [...]
     */
    IN_LIST_OF_VALUES("in","[...]"),

    /**
     * Not-in list of values using the 'not in' operator, ![...]
     */
    NOT_IN_LIST_OF_VALUES("not in","![...]");

    private String name;
    private String symbol;

    private PredicateOperator(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public static PredicateOperator getOperator(String name) {
        for (PredicateOperator po : PredicateOperator.values()) {
            if (po.name.equalsIgnoreCase(name) || po.symbol.equalsIgnoreCase(name)) {
                return po;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
    
}
