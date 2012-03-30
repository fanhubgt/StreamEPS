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
package org.streameps.engine;

import java.util.List;
import java.util.Set;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.IUnMatchedEventSet;
import org.streameps.store.IStoreProperty;

/**
 *
 * @author Frank Appiah
 */
public class InMemoryEventStore<T> implements IHistoryStore<T> {

    public void setIdentifier(String identifier) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getIdentifier() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addToStore(String group, T event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeFromStore(String group, T event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadStore(String url, String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public StoreType getStoreType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveToStore(String group, IStoreContext<IMatchedEventSet<T>> storeContext) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveToStore(String group, IMatchedEventSet<T> eventSet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveToStore(String group, IUnMatchedEventSet<T> eventSet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveToStore(IStoreContext<IUnMatchedEventSet<T>> storeContext) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setStoreType(StoreType storeType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void configureStore() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addToStore(String group, IMatchedEventSet<T> eventSet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Set<T> getFromStore(String group, String uniqueIdentifier) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addToStore(String group, IStoreContext<IMatchedEventSet<T>> storeContext) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addToStore(String group, IUnMatchedEventSet<T> eventSet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addToStore(IStoreContext<IUnMatchedEventSet<T>> storeContext) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setStoreProperty(IStoreProperty storeProperty) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IStoreProperty getStoreProperty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setStoreContexts(List<IStoreContext<IMatchedEventSet<T>>> contexts) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<IStoreContext<IMatchedEventSet<T>>> getStoreContexts() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IHistoryStore<T> getHistoryStore() {
       return this;
    }

    public void setHistoryStore(IHistoryStore<T> historyStore) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
