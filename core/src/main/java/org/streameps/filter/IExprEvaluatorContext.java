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
package org.streameps.filter;

import java.io.Serializable;
import org.streameps.context.IContextEntry;

/**
 * Interface for the expression evaluator context for a partition window.
 * 
 * @author  Frank Appiah
 */
public interface IExprEvaluatorContext<T extends IValueSet> extends Serializable{

    /**
     * It returns the event container.
     * @return
     */
    public T getEventContainer();

    /**
     * The filter type
     * @return The filter type.
     */
    public FilterType getFilterType();

    /**
     * The filter operator
     * @return The filter operator.
     */
    public FilterOperator getOperator();

    /**
     * The result event container after evaluator context.
     * 
     * @param eventContainer The result event container.
     */
    public void setEventContainer(T eventContainer);

    /**
     * The type of filter for the evaluator.
     * @param filterType The filter type instance.
     */
    public void setFilterType(FilterType filterType);

    /**
     * It sets the filter operator for the context evaluator.
     * @param operator The instance of filter operator.
     */
    public void setOperator(FilterOperator operator);

    /**
     * It sets the context entry for the evaluator.
     * @param contextEntry
     */
    public void setContextEntry(IContextEntry contextEntry);

    /**
     * It returns the context entry.
     * @return The context entry.
     */
    public IContextEntry getContextEntry();

    public void setIdentifier(String identifier);

    public String getIdentifier();
}
