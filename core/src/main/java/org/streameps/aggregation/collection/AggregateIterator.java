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
package org.streameps.aggregation.collection;

import java.util.Iterator;
import java.util.Set;
import org.streameps.aggregation.IAggregateValue;

/**
 * It iterates through an aggregated value passed to this class.
 * @see IAggregateValue
 * @author Frank Appiah
 */
public class AggregateIterator<T> implements IAggregateIterator<T> {

    private Set<T> aggregateSet;
    private Iterator<T> aggIterator = null;
    private IAggregateValue aggregateValue;

    public AggregateIterator(IAggregateValue aggregateValue) {
        this.aggregateValue = aggregateValue;
        this.aggregateSet = (Set<T>) aggregateValue.getValues();
    }

    /**
     * It returns the object at the position in the set of event objects.
     * @param position
     * @return T Object at position
     */
    public T getObject(int position) {
        int count = 0;
        this.aggregateSet = (Set<T>) aggregateValue.getValues();
        for (aggIterator = aggregateSet.iterator(); aggIterator.hasNext();) {
            T result = (T) aggIterator.next();
            if (count == position) {
                return result;
            }
            count++;
        }
        return null;
    }

    /**
     * Assumes that position starts at 0.
     *
     * @param position Position to remove value.
     * @return result of action : true /false
     */
    public boolean removeAt(int position) {
        int count = 0;
        this.aggregateSet = (Set<T>) aggregateValue.getValues();
        for (aggIterator = aggregateSet.iterator(); aggIterator.hasNext();) {
            T result = (T) next();
            if (count == position) {
                remove();
                return true;
            }
            count++;
        }
        return false;
    }

    public IAggregateValue getAggregateValue() {
        return aggregateValue;
    }

    public void setAggregateValue(IAggregateValue aggregateValue) {
        this.aggregateValue = aggregateValue;
    }

    public boolean hasNext() {
        return aggIterator.hasNext();
    }

    public T next() {
        return aggIterator.next();
    }

    public void remove() {
        aggIterator.remove();
    }
}
