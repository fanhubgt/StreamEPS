/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2012.
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

import java.util.List;
import java.util.Map;
import org.streameps.aggregation.collection.Accumulator;
import org.streameps.filter.FilterType;
import org.streameps.filter.IFilterValueSet;

/**
 * A interface for executing a series of filter expressions defined in the filter
 * context.
 * 
 * @author  Frank Appiah
 */
public interface IFilterChain<T extends Accumulator> {

    /**
     * It adds the filter context for the chain of filter context instances.
     * @param context An instance of the filter context.
     */
    public void addFilterContext(IFilterContext<T> context);

    /**
     * It adds the filter visitor with its filter type to the map of filterVisitor-filterType
     * pair.
     * @param filterType The specific filter type.
     * @param filterVisitor The instance of the filter value set.
     */
    public void addFilterVisitor(FilterType filterType, IFilterVisitor<T> filterVisitor);

    /**
     *  It adds the filter value set with its filter type to the map of filterValue-filterType
     * pair.
     * @param filterType The specific filter type.
     * @param filterValueSet The instance of the filter value set.
     */
    public void addFilterValueSet(FilterType filterType, IFilterValueSet<T> filterValueSet);

    /**
     * It returns the list of filter contexts to be used for the filter evaluation
     * defined by the filter visitor.
     * @return  A list of filter context instances.
     */
    public List<IFilterContext<T>> getFilterContexts();

    /**
     * It sets the list of filter contexts to be used for the filter evaluation
     * defined by the filter visitor.
     * 
     * @param filterContexts A list of filter context instances.
     */
    public void setFilterContexts(List<IFilterContext<T>> filterContexts);

    /**
     * It visits the filter visitor context passed to it.
     * @param filterVisitor An instance of the filter visitor.
     */
    public void executeVisitor();

    /**
     * It returns the filter value map after execution of the filter chain.
     * @return A map of filter value set.
     */
    public Map<FilterType, IFilterValueSet<T>> getFilterValueMap();

    /**
     * It sets the filter value map after execution of the filter chain.
     * @param valueMap A map of filter value set.
     */
    public void setFilterValueMap(Map<FilterType, IFilterValueSet<T>> valueMap);

    /**
     *  It sets a map containing the filter type and the filter visitor for
     * each filter context.
     *
     * @param visitorMap  A map instance containing the filterType-filterVisitor pair.
     */
    public void setFilterVisitorMap(Map<FilterType, IFilterVisitor<T>> visitorMap);

    /**
     * It returns a map containing the filter type and the filter visitor for
     * each filter context.
     * 
     * @return A map instance containing the filterType-filterVisitor pair.
     */
    public Map<FilterType, IFilterVisitor<T>> getFilterVisitorMap();
}
