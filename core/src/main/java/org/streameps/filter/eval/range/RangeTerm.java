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
import org.streameps.filter.RangeValueSet;

/**
 *
 * @author Frank Appiah
 */
public class RangeTerm implements IRangeTerm {

    private String propertyName;
    private IRangeEndPoint endPoint;
    private IRangeValueSet rangeValueSet;

    public RangeTerm() {
        endPoint = new RangeEndPoint();
        rangeValueSet = new RangeValueSet();
    }

    public RangeTerm(String propertyName) {
        this.propertyName = propertyName;
    }

    public RangeTerm(String propertyName, IRangeEndPoint endPoint, IRangeValueSet rangeValueSet) {
        this.propertyName = propertyName;
        this.endPoint = endPoint;
        this.rangeValueSet = rangeValueSet;
    }

    public RangeTerm(String propertyName, IRangeValueSet rangeValueSet) {
        this.propertyName = propertyName;
        this.rangeValueSet = rangeValueSet;
    }

    public RangeTerm(String propertyName, IRangeEndPoint endPoint) {
        this.propertyName = propertyName;
        this.endPoint = endPoint;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setRangeEndPoint(IRangeEndPoint endPoint) {
        this.endPoint = endPoint;
    }

    public IRangeEndPoint getRangeEndPoint() {
        return this.endPoint;
    }

    public void setRangeValueSet(IRangeValueSet rangeValueSet) {
        this.rangeValueSet = rangeValueSet;
    }

    public IRangeValueSet getRangeValueSet() {
        return this.rangeValueSet;
    }
}
