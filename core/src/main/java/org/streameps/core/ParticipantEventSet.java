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
package org.streameps.core;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.streameps.processor.pattern.policy.OrderPolicy;
import org.streameps.processor.pattern.policy.OrderPolicyType;

/**
 *
 * @author Development Team
 */
public class ParticipantEventSet extends AbstractSet<Object> implements Set<Object>, Serializable {

    private OrderPolicyType orderPolicyType;
    private OrderPolicy orderPolicy;
    private Set<Object> streams = Collections.synchronizedSet(new HashSet<Object>());
    private MatchingEventSet matchingstreamset = null;

    public ParticipantEventSet() {
        orderPolicy = new OrderPolicy(OrderPolicyType.STREAM_POSITION, this);
    }

    public ParticipantEventSet(OrderPolicyType orderPolicyType) {
        this.orderPolicyType = orderPolicyType;
        orderPolicy = new OrderPolicy(orderPolicyType, this);
    }

    @Override
    public boolean add(Object e) {
        return streams.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends Object> c) {
        return streams.addAll(c);
    }

    @Override
    public boolean isEmpty() {
        return streams.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return streams.contains((Object) o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return streams.containsAll(c);
    }

    @Override
    public boolean remove(Object o) {
        return streams.remove((Object) o);
    }

    @Override
    public void clear() {
        streams.clear();
    }

    public int size() {
        return streams.size();
    }

    public Iterator<Object> iterator() {
        return streams.iterator();
    }

    public Object get(int position) {
        int count = 0;
        Iterator<Object> iterator = null;
        for (iterator = iterator(); iterator.hasNext();) {
            Object result = (Object) iterator.next();
            if (count == position) {
                return result;
            }
            count++;
        }
        return null;
    }

    public boolean removeRange(int start, int end) {
        boolean result = true;
        for (int i = start; i <= end; i++) {
            Object value = get(i);
            result &= remove(value);
        }
        return result;
    }

    public void order() {
        orderPolicy.checkPolicy();
    }

    public void setOrderPolicyType(OrderPolicyType orderPolicyType) {
        this.orderPolicyType = orderPolicyType;
        orderPolicy = new OrderPolicy(orderPolicyType, this);
    }

    public OrderPolicyType getOrderPolicy() {
        return orderPolicyType;
    }

    public void setMatchingStreamSet(MatchingEventSet matchingstreamset) {
        this.matchingstreamset = matchingstreamset;
    }
}
