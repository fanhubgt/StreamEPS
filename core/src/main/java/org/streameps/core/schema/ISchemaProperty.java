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
package org.streameps.core.schema;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 *
 * @author Frank Appiah
 */
public interface ISchemaProperty<E> {

    /**
     * It returns the name of the property field.
     * @return The property event field.
     */
    public String getName();

    /**
     * It sets the name of the property field.
     * @param name field of the property.
     */
    public void setName(String name);

    /**
     * It sets the event instance for the property.
     * @param object  An event instance.
     */
    public void setEventClazz(Class clazz);

    /**
     * It returns the event object for the property field.
     * @return An event instance.
     */
    public Class getEventClazz();

    /**
     * It returns the mutator method for the property method.
     * @return The mutator method.
     */
    public Method getMutatorMethod();

    /**
     * It returns the accessor method for the property method.
     * @return The accessor method.
     */
    public Method getAccessorMethod();

    /**
     * It sets the accessor method for the property field.
     * @param method Accessor method.
     */
    public void setAccessorMethod(Method method);

    /**
     * It sets the mutator method for the property field.
     * @param method mutator method.
     */
    public void setMutatorMethod(Method method);

    /**
     * It sets the object of the event.
     * @param object event instance.
     */
    public void setEvent(E object);

    /**
     * It returns the object of the event.
     * @return event instance.
     */
    public E getEvent();

    /**
     * It sets the parameter type of the event object.
     * @param type type of parameter.
     */
    public void setParameterType(Type type);

    /**
     * It returns the parameter type of the event object.
     * @return The return type of the parameter.
     */
    public Type getParameterType();
}
