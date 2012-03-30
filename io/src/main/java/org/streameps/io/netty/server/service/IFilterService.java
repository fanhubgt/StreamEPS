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
package org.streameps.io.netty.server.service;

import org.streameps.filter.IEPSFilter;
import org.streameps.filter.IFilterValueSet;
import org.streameps.io.netty.server.IServerChannelHandler;
import org.streameps.io.netty.server.listener.IFilterServiceListener;
import org.streameps.io.netty.server.listener.IUnFilterServiceListener;

/**
 * Interface for the filter service.
 * 
 * @author  Frank Appiah
 */
public interface IFilterService extends IServerChannelHandler{

    /**
     * It sets the filter service listener for the service.
     * @param filterServiceListener An instance of the filter service listener.
     */
    public void setFilterServiceListener(IFilterServiceListener filterServiceListener);

    /**
     * It sets the un-filter service listener for the service.
     * @param unFilterServiceListener An instance of the filter service listener.
     */
    public void setUnFilterServiceListener(IUnFilterServiceListener unFilterServiceListener);

    /**
     * It returns the filter service listener for the service.
     * @return An instance of the filter service listener.
     */
    public IFilterServiceListener getFilterServiceListener();

    /**
     * It returns the un-filter service listener for the service.
     * @return  An instance of the filter service listener.
     */
    public IUnFilterServiceListener getUnFilterServiceListener();

    /**
     * It returns the matched filter value set from the filter listener.
     * @return  The matched filter value set from the filter listener.
     */
    public IFilterValueSet getMatchFilterSet();

    /**
     * It returns the  un-matched filter value set from the filter listener.
     * @return The un-matched filter value set from the filter listener.
     */
    public IFilterValueSet getUnMatchFilterSet();

    /**
     * It sets the matched filter value set from the filter listener.
     * @param matchFilterSet The matched filter value set from the filter listener.
     */
    public void setMatchFilterSet(IFilterValueSet matchFilterSet);

    /**
     * It sets the un-matched filter value set from the filter listener.
     * @param unMatchFilterSet The un-matched filter value set from the filter listener.
     */
    public void setUnMatchFilterSet(IFilterValueSet unMatchFilterSet);

    /**
     * It sets the EPS filter for the filter service.
     * @param filter An instance of the EPS filter.
     */
    public void setEPSFilter(IEPSFilter filter);

    /**
     * It returns the EPS filter for the filter service.
     * @return An instance of the EPS filter.
     */
    public IEPSFilter getEPSFilter();

     public void setCallBack();
}
