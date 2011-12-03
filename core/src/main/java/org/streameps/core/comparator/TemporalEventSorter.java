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
package org.streameps.core.comparator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.streameps.context.TemporalOrder;

/**
 * It sorts the participant/matched event set by a temporal attribute using
 * a user_define_attribute, detection time and occurrence time.
 * 
 * @author Frank Appiah
 */
public class TemporalEventSorter<E> implements ITemporalEventSorter<E> {

    private Set<E> eventSet = null;
    private List<E> eventList = null;
    private boolean isEventList = false;
    private String sortAttribute = null;
    private TemporalOrder temporalOrder = TemporalOrder.TEMPORAL_ATT;

    public TemporalEventSorter() {
    }

    public TemporalEventSorter(String sortAttribute, List<E> eventList, TemporalOrder order) {
        this.sortAttribute = sortAttribute;
        this.eventList = eventList;
        this.temporalOrder = order;
        this.isEventList = true;
    }

    public TemporalEventSorter(String sortAttribute, Set<E> eventSet, TemporalOrder order) {
        this.sortAttribute = sortAttribute;
        this.eventSet = eventSet;
        this.temporalOrder = order;
        this.isEventList = false;
    }

    public void setEventList(List<E> eventList) {
        this.eventList = eventList;
        isEventList = true;
    }

    public void setEventSet(Set<E> eventSet) {
        this.eventSet = eventSet;
    }

    public void setSortAttribute(String sortAttribute) {
        this.sortAttribute = sortAttribute;
    }

    public void setIsEventList(boolean isEventList) {
        this.isEventList = isEventList;
    }

    public Set<E> sortSet(TemporalOrder o) {
       Set<E> sorted=new HashSet<E>();
       switch(temporalOrder)
       {
           case DETECTION_TIME:
               break;
           case OCCURENCE_TIME:
               break;
           case TEMPORAL_ATT:
               break;
       }
       return sorted;
    }

    public List<E> sortList(TemporalOrder o)
    {
       List<E> sorted=new ArrayList<E>();
       switch(temporalOrder)
       {
           case DETECTION_TIME:
               break;
           case OCCURENCE_TIME:
               break;
           case TEMPORAL_ATT:
               break;
       }
       return sorted;
    }

    private Set<Object> sortByDT()
    {
     Set<Object> sorted=new HashSet<Object>();
     return sorted;
    }

    private Set<Object> sortByOT()
    {
         Set<Object> sorted=new HashSet<Object>();
     return sorted;
    }

    private Set<Object> sortByTA()
    {
        Set<Object> sorted=new HashSet<Object>();
     return sorted;
    }

    private List<Object> sortByDTList()
    {
      List<Object> dt=new ArrayList<Object>();
      return dt;

    }

    private List<Object> sortByOTList()
    {
      List<Object> dt=new ArrayList<Object>();
      return dt;
    }

    private List<Object> sortByTAList()
    {
     List<Object> dt=new ArrayList<Object>();
     return dt;
    }

}
