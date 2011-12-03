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

import java.util.List;
import org.streameps.aggregation.collection.IAccumulator;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.PartitionWindow;
import org.streameps.core.util.SchemaUtil;

/**
 *
 * @author Frank Appiah
 */
public class InNotValueSet<T extends IAccumulator> extends ValueSet implements IInNotValueSet<T> {

    private IPartitionWindow<T> valueSet;
    private String valueIdentifier;
     private String propertyName;

    public InNotValueSet() {
        valueSet=new PartitionWindow<T>();
    }

    public InNotValueSet(IPartitionWindow<T> valueSet, String valueIdentifier, String propertyName) {
        this.valueSet = valueSet;
        this.valueIdentifier = valueIdentifier;
        setValueIdentifier(valueIdentifier);
        this.propertyName = propertyName;
    }

    public InNotValueSet(IPartitionWindow<T> valueSet, String valueIdentifier) {
        this.valueSet = valueSet;
        this.valueIdentifier = valueIdentifier;
        setValueIdentifier(valueIdentifier);
    }

    public IPartitionWindow<T> getValueSet() {
        return this.valueSet;
    }

    public void setValueSet(IPartitionWindow<T> valueSet) {
        this.valueSet = valueSet;
    }

     @Override
    public boolean equals(Object o) {
        IInNotValueSet<T> rangevalueSet = (IInNotValueSet<T>) o;
        T accumulator = rangevalueSet.getValueSet().getWindow();
        int result = 0, size = 0;
        if (accumulator instanceof ISortedAccumulator) {
            ISortedAccumulator ac = (ISortedAccumulator) accumulator;
            List<Object> valueList = (List<Object>) ac.getMap().firstEntry().getValue();
            size = valueList.size();
            int i = 0;
            for (Object value : valueList) {
                Double dValue = (Double) SchemaUtil.getPropertyValue(value, getPropertyName());
                if (getDataValue(i) == dValue) {
                    result += 1;
                    i += 1;
                }
            }
        }
        if (result == size) {
            return true;
        }
        return false;
    }

    private Double getDataValue(int i) {
        T accumulator = this.valueSet.getWindow();
        Double dValue = 0.0;
        if (accumulator instanceof ISortedAccumulator) {
            ISortedAccumulator ac = (ISortedAccumulator) accumulator;
            List<Object> valueList = (List<Object>) ac.getMap().firstEntry().getValue();
            dValue = (Double) SchemaUtil.getPropertyValue(valueList.get(i), getPropertyName());
        }
        return dValue;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + valueIdentifier.length() + propertyName.length();
    }
    
}
