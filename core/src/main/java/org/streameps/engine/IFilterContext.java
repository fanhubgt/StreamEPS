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
package org.streameps.engine;

import java.io.Serializable;
import org.streameps.filter.IEPSFilter;
import org.streameps.filter.IExprEvaluatorContext;

/**
 * Interface for the filter context for the engine after the filtering process.
 * 
 * @author  Frank Appiah
 */
public interface IFilterContext<T> extends Serializable{

    /**
     * It sets the identifier for the context.
     * @param identifier An identifier.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns the identifier for the context.
     * @return An identifier.
     */
    public String getIdentifier();

    /**
     * It sets the context value.
     * @param context The filtered value.
     */
    public void setFilteredValue(T context);

    /**
     * It returns the context value.
     * @return The filtered value.
     */
    public T getFilteredValue();

    /**
     * It returns the expression evaluator context.
     * @return The evaluator context.
     */
    public IExprEvaluatorContext getEvaluatorContext();

    /**
     * It sets the expression evaluator context.
     * @param sFilter The evaluator context.
     */
    public void setEvaluatorContext(IExprEvaluatorContext evaluatorContext);

    /**
     * It sets the EPS filter for the forwarder context.
     * @param filter The EPS filter.
     */
    public void setEPSFilter(IEPSFilter filter);

    /**
     * It return the EPS filter for the forwarder context.
     * @return The EPS filter.
     */
    public IEPSFilter getEPSFilter();

}
