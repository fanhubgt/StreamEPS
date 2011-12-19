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
package org.streameps.core.util;

import java.util.List;
import java.util.Set;

/**
 * A utility for checking the event type from a set of event either in a filter context
 * or decider context.
 * 
 * @author Frank Appiah
 */
public class EventTypeUtil {

    public static boolean validateEvent(List<String> eventTypes, Object event) {
        boolean validate = true;
        for(String eventType:eventTypes)
        {
            String eventName=event.getClass().getName();
            String eventSimpleName=event.getClass().getSimpleName();
            validate &=(checkSimpleName(eventType, eventSimpleName)||
                    checkFullPackageName(eventType, eventName));
        }
        return validate;
    }

    public static boolean validateEvents(List<String> eventTypes, Set<Object> events) {
        boolean validate = true;

        for (Object event : events) {
            validate &= validateEvent(eventTypes, event);
        }
        return validate;
    }

    private static boolean checkSimpleName(String name, String eventName)
    {
       return (name.equalsIgnoreCase(eventName));
    }

    private static boolean checkFullPackageName(String name, String eventName)
    {
        return (name.equalsIgnoreCase(eventName));
    }
    
}
