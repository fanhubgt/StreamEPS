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
public enum OrderPolicyType {
    /**
     * The order of events in the participant event set is determined
     * by comparing their occurrence time attributes, so that the order reflects
     * the order in which the events happened in reality (as accurately as the temporal
     * granularity allows)
     */
    OCCURENCE_TIME("occurence_time"),
    /**
     * The order of events in the participant event set is determined
     * by comparing their detection time attributes, that is the order in which events
     * are detected by the event processing system.
     */
    DETECTION_TIME("detection_time"),
    /**
     * Some event payloads contain a timestamp, sequence number, or
     * another attribute that increases over time, and this can be used to
     * determine the order.
     */
    USER_DEFINED_ATTRIBUTE("user_defined_attribute"),
    /**
     * The order to be used is the order in which the events are
     * delivered to the event processing agent from the channel that feeds it.
     */
    STREAM_POSITION("stream_position");
    private String name;

    private OrderPolicyType(String name) {
        this.name = name;
    }

    public static OrderPolicyType getType(String type) {
        for (OrderPolicyType t : OrderPolicyType.values()) {
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
