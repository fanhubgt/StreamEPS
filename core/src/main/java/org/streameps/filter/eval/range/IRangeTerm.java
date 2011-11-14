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
package org.streameps.filter.eval.range;

import org.streameps.filter.IRangeValueSet;

/**
 * Interface for the range term containing the property name for the even
 * instance and the range end point value set.
 * 
 * @author  Frank Appiah
 */
public interface IRangeTerm {

    /**
     * It sets the property name for the event instance.
     * @param propertyName Property name of event.
     */
    public void setPropertyName(String propertyName);

    /**
     * It returns the property name of event instance.
     * @return property name.
     */
    public String getPropertyName();

    /**
     * It sets the range end point.
     * @param endPoint The range end point.
     */
    public void setRangeEndPoint(IRangeEndPoint endPoint);

    /**
     * It returns the range end point.
     * @return The range end point.
     */
    public IRangeEndPoint getRangeEndPoint();

    /**
     *  It sets the range value set.
     * @param rangeValueSet The range value set container.
     */
    public void setRangeValueSet(IRangeValueSet rangeValueSet);

    /**
     * It returns the range value set.
     * @return The range value set container.
     */
    public IRangeValueSet getRangeValueSet();
}
