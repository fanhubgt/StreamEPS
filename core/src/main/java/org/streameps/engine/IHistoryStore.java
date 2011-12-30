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
package org.streameps.engine;

import java.io.Serializable;
import java.util.Set;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.IUnMatchedEventSet;
import org.streameps.store.IStoreProperty;

/**
 * Interface for history store specification.
 * 
 * @author  Frank Appiah
 * @version 0.3.3
 */
public interface IHistoryStore<T> extends Serializable {

    /**
     * It sets the identifier for the history store .
     * @param identifier An unique identifier.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns the identifier for the history store.
     * @return An unique identifier.
     */
    public String getIdentifier();

    /**
     * It adds an event to the store.
     * @param context an instance of event.
     */
    public void addToStore(String group, T event);

    /**
     * It removes the event from the store.
     * @param context an event instance.
     */
    public void removeFromStore(String group, T context);

    /**
     * It returns the event instance from the store by a certain unique identifier.
     * @param uniqueIdentifier some unique identifier.
     * @return An event instance.
     */
    public Set<T> getFromStore(String group, String uniqueIdentifier);

    /**
     * It loads the history of events from the store at a certain Url location.
     * @param url connection to the store.
     * @param username name of the user.
     * @param password password of user of the store.
     */
    public void loadStore(String url, String username, String password);

    public void saveToStore(String group, IStoreContext<IMatchedEventSet<T>> storeContext);

    public void addToStore(String group, IStoreContext<IMatchedEventSet<T>> storeContext);

    public void saveToStore(String group, IMatchedEventSet<T> eventSet);

    public void addToStore(String group, IMatchedEventSet<T> eventSet);

    public void saveToStore(String group, IUnMatchedEventSet<T> eventSet);

    public void addToStore(String group, IUnMatchedEventSet<T> eventSet);

    public void saveToStore(IStoreContext<IUnMatchedEventSet<T>> storeContext);

    public void addToStore(IStoreContext<IUnMatchedEventSet<T>> storeContext);

    public void setStoreProperty(IStoreProperty storeProperty);

    public IStoreProperty getStoreProperty();

    public void save();

    /**
     * It returns the type of store: memory, database, file: NoSQl
     * @return The type of store.
     */
    public StoreType getStoreType();

    /**
     * It sets the store type of the history store.
     * @param storeType The type of store.
     */
    public void setStoreType(StoreType storeType);

    /**
     * It configures the store of the history.
     */
    public void configureStore();
}
