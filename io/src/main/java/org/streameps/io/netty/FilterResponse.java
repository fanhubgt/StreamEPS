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

package org.streameps.io.netty;

import java.util.Set;
import org.streameps.context.IPartitionWindow;

/**
 *
 * @author Frank Appiah
 */
public class FilterResponse<T> extends EPSResponse implements IFilterResponse<T>{

    private IPartitionWindow<Set<T>> window;
    private IPartitionWindow<Set<T>> unMatchWindow;
    private String identifier;

    public FilterResponse() {
    }

    public FilterResponse(String identifier) {
        this.identifier = identifier;
    }

    public FilterResponse(IPartitionWindow<Set<T>> window, IPartitionWindow<Set<T>> unMatchwindow) {
        this.window = window;
        this.unMatchWindow = unMatchwindow;
    }

    public void setMatchWindow(IPartitionWindow<Set<T>> window) {
        this.window=window;
    }

    public IPartitionWindow<Set<T>> getMatchWindow() {
       return this.window;
    }

    public void setUnMatchWindow(IPartitionWindow<Set<T>> unMatchwindow) {
        this.unMatchWindow=unMatchwindow;
    }

    public IPartitionWindow<Set<T>> getUnMatchWindow() {
        return this.unMatchWindow;
    }

    public String getIdentifier() {
       return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier=identifier;
    }

}
