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

import java.util.List;
import org.streameps.context.IContextEntry;
import org.streameps.context.IPredicateExpr;
import org.streameps.context.IPredicateTerm;
import org.streameps.context.temporal.IInitiatorEventList;

/**
 *
 * @author Frank Appiah
 */
public class InitiatorEventValidator<T> implements IInitiatorEventValidator<T> {

    public boolean validate(IInitiatorEventList eventList, IInitiatorContext<T> context) {
        return validateEventTypeAndTerms(eventList, context);
    }

    private boolean validateEventTypeAndTerms(IInitiatorEventList eventList, IInitiatorContext<T> context) {
        boolean valid = false;
        for (IContextEntry entry : eventList.getInitiatorEntry()) {
            valid |= (validateEventType(context.getEventType(), entry.getEventType())
                    || validateEventClass(entry.getEventType(), context.getEventClass()))
                    && validateTerms(context.getEvent(), entry.getPredicateExpr(), entry.getPredicateTerms());
        }
        return valid;
    }

    private boolean validateEventClass(String eventType, Class clazz) {
        if (clazz == null) {
            return false;
        }
        return (eventType.equalsIgnoreCase(clazz.getName()) ||
                eventType.equalsIgnoreCase(clazz.getSimpleName()));
    }

    private boolean validateEventType(String eventType, String contextEventType) {
        if (contextEventType == null) {
            return false;
        }
        return (eventType.equalsIgnoreCase(contextEventType));
    }

    private boolean validateTerms(T instance, IPredicateExpr expr, List<IPredicateTerm> terms) {
        if (expr != null) {
            return expr.evalExpr(instance, terms);
        }
        return false;
    }
}
