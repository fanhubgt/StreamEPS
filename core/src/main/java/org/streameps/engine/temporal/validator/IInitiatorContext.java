/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2011.
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
package org.streameps.engine.temporal.validator;

/**
 * An interface for the initiator context specification.
 * 
 * @author  Frank Appiah
 */
public interface IInitiatorContext<T> {

    /**
     * It sets the event type of the initiator context used in the initiator
     * event validation process.
     * @param eventType A specific event type.
     */
    public void setEventType(String eventType);

    /**
     * It returns the event type of the initiator context used in the initiator
     * event validation process.
     * @return A specific event type.
     */
    public String getEventType();

    /**
     * It sets the event type of the initiator context used in the initiator
     * event validation process.
     * @param classEventType The class event type.
     */
    public void setEventClass(Class classEventType);

    /**
     * It returns the event type of the initiator context used in the initiator
     * event validation process.
     * @return The class event type.
     */
    public Class getEventClass();

    /**
     * It sets the event for the initiator context  validation.
     * @param event The event used in the initiator context validation.
     */
    public void setEvent(T event);

    /**
     * It returns the event for the initiator context validation.
     * @return The event for the initiator context.
     */
    public T getEvent();
    
}
