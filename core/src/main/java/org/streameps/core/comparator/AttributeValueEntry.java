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
package org.streameps.core.comparator;

/**
 * It is a container for an event instance and its attribute value.
 * It is used to perform operations like sorting, searching, etc.
 *
 * @author Frank Appiah
 * @version 0.3.3
 */
public class AttributeValueEntry implements Comparable<AttributeValueEntry> {

    private Object event;
    private Double value;
    private String name;
    private CompareOrder co = CompareOrder.HIGHEST;

    public AttributeValueEntry(Object event, Double value) {
        this.event = event;
        this.value = value;
    }

    public AttributeValueEntry(Object event, Double value, String name) {
        this.event = event;
        this.value = value;
        this.name = name;
    }

      public AttributeValueEntry(Object event, Double value,CompareOrder order) {
        this.event = event;
        this.value = value;
        this.co=order;
    }

    /**
     * It returns the event instance.
     * @return An event instance.
     */
    public Object getEvent() {
        return event;
    }

    /**
     * It returns the value of an attribute from the event instance.
     * @return value of the property.
     */
    public Double getValue() {
        return value;
    }

    /**
     * It sets the event instance.
     * @param event event to set.
     */
    public void setEvent(Object event) {
        this.event = event;
    }

    /**
     * It sets the value of the property.
     * @param value value to set.
     */
    public void setValue(Double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * It performs the comparison operation.
     * @param o attribute value entry being compared to.
     * @return positive value for success and negative value for failure.
     */
    public int compareTo(AttributeValueEntry o) {
        switch (co) {
            case HIGHEST: {
                if (o.getValue() > this.value) {
                    return 1;
                } else if (o.getValue() < this.value) {
                    return -1;
                }
            }
            case LOWEST: {
                if (o.getValue() < this.value) {
                    return 1;
                } else if (o.getValue() > this.value) {
                    return -1;
                }
            }
        }
        return 0;
    }

    public enum CompareOrder {

        LOWEST,
        HIGHEST;
    }
}
