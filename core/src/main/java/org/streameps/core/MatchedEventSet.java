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

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import org.streameps.processor.pattern.policy.ConsumptionPolicy;
import org.streameps.processor.pattern.policy.ConsumptionType;

/**
 *
 * @author Frank Appiah
 */
public final class MatchedEventSet<E> extends AbstractSet<E> implements IMatchedEventSet<E> {

    private LinkedBlockingQueue<E> matchEvents = new LinkedBlockingQueue<E>();
    private volatile ParticipantEventSet<E> participantSet = null;
    private ConsumptionType consumptionType;
    private ConsumptionPolicy consumptionPolicy;

    public MatchedEventSet() {
        consumptionPolicy = new ConsumptionPolicy(ConsumptionType.CONSUME, this);
    }

    public MatchedEventSet(ConsumptionType consumptionType) {
        this.consumptionType = consumptionType;
        consumptionPolicy = new ConsumptionPolicy(consumptionType, this);
    }

    public Set<E> subset(int start, int end) {
        Set<E> subset = new HashSet<E>();
        for (int i = start; i <= end; i++) {
            E value = get(i);
            subset.add(value);
        }
        return subset;
    }

    @Override
    public boolean add(E e) {
        return matchEvents.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return matchEvents.addAll(c);
    }

    @Override
    public boolean isEmpty() {
        return matchEvents.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return matchEvents.contains((E) o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return matchEvents.containsAll(c);
    }

    @Override
    public boolean remove(Object o) {
        return matchEvents.remove((E) o);
    }

    public boolean removeRange(int start, int end) {
        boolean result = true;
        List<E> remEvents = new ArrayList<E>();
        // if the size of events is less than the end range value
        //return immedidately.
        if (size() < end) {
            return false;
        }
        for (int i = start; i <= end; i++) {
            E value = get(i);
            remEvents.add(value);
            result &= remove(value);
        }
        //if one fails then add the removed events back to the set.
        if (!result) {
            addAll(remEvents);
        }
        return result;
    }

    @Override
    public void clear() {
        matchEvents.clear();
    }

    public int size() {
        return matchEvents.size();
    }

    public Iterator<E> iterator() {
        return matchEvents.iterator();
    }

    public E get(int position) {
        int count = 0;
        Iterator<E> iterator = null;
        for (iterator = iterator(); iterator.hasNext();) {
            E result = (E) iterator.next();
            if (count == position) {
                return result;
            }
            count++;
        }
        return null;
    }

    public void setParticipantSet(ParticipantEventSet<E> participantSet) {
        this.participantSet = participantSet;
    }

    public void setConsumptionType(ConsumptionType consumptionType) {
        this.consumptionType = consumptionType;
    }
}
