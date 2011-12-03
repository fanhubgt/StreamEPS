/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  Copyright 2011.
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
 * 
 *  =============================================================================
 */
package org.streameps.core;


import java.util.Iterator;
import java.util.List;
import org.streameps.core.schema.IPropertyDescriptor;

/**
 *
 * @author Frank Appiah
 */
public class EventType<T> implements IEventType<T> {

    private String name;
    private String clazz;
    private IEventType[] superTypes;
    private Iterator<IEventType<T>> deepSuperTypes;
    private List<IPropertyDescriptor<T>> descriptors;

    public EventType() {
    }

    public EventType(String name, String clazz, IEventType<T>[] superTypes, Iterator<IEventType<T>> deepSuperTypes, List<IPropertyDescriptor<T>> descriptors) {
        this.name = name;
        this.clazz = clazz;
        this.superTypes = superTypes;
        this.deepSuperTypes = deepSuperTypes;
        this.descriptors = descriptors;
    }

    public EventType(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public void setEventName(String name) {
        this.name = name;
    }

    public String getEventName() {
        return this.name;
    }

    public void setClazzName(String clazz) {
        this.clazz = clazz;
    }

    public String getClazzName() {
        return this.clazz;
    }

    public IEventType<T>[] getSuperTypes() {
       return this.superTypes;
    }

    public Iterator<IEventType<T>> getDeepSuperTypes() {
        return this.deepSuperTypes;
    }

    public void setProperties(List<IPropertyDescriptor<T>> propSchema) {
        this.descriptors=propSchema;
    }

    public List<IPropertyDescriptor<T>> getProperties() {
        return this.descriptors;
    }
    
}
