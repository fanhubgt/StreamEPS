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

/**
 *
 * @author Frank Appiah
 */
public class HistoryStore<T> implements IHistoryStore<T> {

    private StoreType storeType;
    private IHistoryStore<T> historyStore;
    private String identifier;
    //todo: Use the Java-SDO spec for the implementation.

    public HistoryStore() {
    }

    public HistoryStore(StoreType storeType) {
        this.storeType = storeType;
    }

    public HistoryStore(StoreType storeType, IHistoryStore<T> historyStore) {
        this.storeType = storeType;
        this.historyStore = historyStore;
    }

    public void addToStore(String group, T event) {
        historyStore.addToStore(group, event);
    }

    public void removeFromStore(String group, T event) {
        historyStore.removeFromStore(group, event);
    }

    public T getFromStore(String group, String uniqueIdentifier) {
        return historyStore.getFromStore(group, uniqueIdentifier);
    }

    public void loadStore(String url, String username, String password) {
        historyStore.loadStore(url, username, password);
    }

    public StoreType getStoreType() {
        return this.storeType;
    }

    public IHistoryStore<T> getHistoryStore() {
        return historyStore;
    }

    public void setHistoryStore(IHistoryStore<T> historyStore) {
        this.historyStore = historyStore;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }

    public void setIdentifier(String identifier) {
        this.identifier=identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }
    
}
