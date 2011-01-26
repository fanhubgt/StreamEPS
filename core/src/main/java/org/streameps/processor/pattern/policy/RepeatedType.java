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
package org.streameps.processor.pattern.policy;

/**
 *
 * @author  Development Team
 */
public enum RepeatedType {

    /**
     * The participant event set keeps no more instances of any event type
     * than the number implied by the relevant event types list. If a new event instance
     * is encountered and the participant set already contains the required number of
     * instances of that type, the new instance replaces the oldest previous instance of
     * that type.
     */
    OVERRIDE("override"),
    /**
     * Every instance is kept in the participant event set, so that all possible
     * matching sets can be produced.
     */
    EVERY("every"),
    /**
     * Every instance is kept in the participant event set, but only the earliest
     * instances of each type are used for matching.
     */
    FIRST("first"),
    /**
     * Every instance is kept, but only the latest instances of each type are used
     * for matching.
     */
    LAST("last"),
    /**
     * Every instance is kept, but only the event or events with
     * the maximal value of the specified attribute are used for matching.
     */
    MAX_ATTRIBUTE("max_attribute"),
    /**
     * Every instance is kept, but only the event or events with the
     * minimal value of the specified attribute are used for matching.
     */
    MIN_ATTRIBUTE("min_attribute");
    private String name;

    private RepeatedType(String name) {
        this.name = name;
    }

    public static RepeatedType getType(String type) {
        for (RepeatedType t : RepeatedType.values()) {
            if (t.name.equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getName() {
        return name;
    }
    
}
