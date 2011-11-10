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

package org.streameps.core;


import java.util.Iterator;
import java.util.List;
import org.streameps.core.schema.IPropertyDescriptor;

/**
 * Interface for the event type specification.
 *
 * @author Frank Appiah
 * @version 0.2.2
 */
public interface IEventType {

    /**
     * It sets the event name for an event instance.
     * @param name A simple name for the event instance.
     */
    public void setEventName(String name);

    /**
     * It returns the event name for an event instance.
     * @return A simple name for the event instance.
     */
    public String getEventName();

    /**
     * It sets the class name for the event type.
     * @param clazz The full class name for the event instance.
     */
    public void setClazzName(String clazz);

    /**
     * It returns the class name for the event type.
     * @return The full class name for the event instance.
     */
    public String getClazzName();

    /**
     * It returns the super types for a specific event.
     * @return array of the super types.
     */
    public EventType[] getSuperTypes();

    /**
     * It returns the deep super types.
     * @return an iteration of the deep super types.
     */
    public Iterator<EventType> getDeepSuperTypes();

    /**
     * It sets the properties for the event type.
     * @param propSchema minimum schema for a property.
     */
    public void setProperties(List<IPropertyDescriptor> propSchema);

    /**
     * It returns the properties for the event type.
     * @param propSchema minimum schema for a property.
     */
    public List<IPropertyDescriptor> getProperties();
}
