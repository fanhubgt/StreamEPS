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

import java.io.File;
import java.util.Date;
import java.util.List;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.IUnMatchedEventSet;
import org.streameps.core.util.IDUtil;
import org.streameps.store.IEPStore;
import org.streameps.store.IStoreProperty;
import org.streameps.store.file.EPSFile;
import org.streameps.store.file.FileEPStore;
import org.streameps.store.file.IEPSFile;
import org.streameps.store.file.IEPSFileSystem;
import org.streameps.store.file.component.IEPSFileComponent;

/**
 *
 * @author Frank Appiah
 */
public class AuditEventStore<T> implements IHistoryStore<T> {

    private IEPStore epsStore;
    private String identifier;
    private StoreType storeType;
    private List<IStoreContext<IMatchedEventSet<T>>> matchContexts;
    private List<IStoreContext<IUnMatchedEventSet<T>>> unmatchContexts;
    private String group;
    private IStoreProperty storeProperty;
    private boolean match = false;

    public AuditEventStore() {
        epsStore = new FileEPStore();
    }

    public AuditEventStore(IEPStore epsStore, String identifier, String group, StoreType storeType, IStoreProperty storeProperty) {
        this.epsStore = epsStore;
        this.identifier = identifier;
        this.storeType = storeType;
        this.group = group;
        this.storeProperty = storeProperty;
    }

    public AuditEventStore(String identifier, IEPStore epsStore, String group, StoreType storeType, IStoreProperty storeProperty) {
        this.epsStore = epsStore;
        this.identifier = identifier;
        this.storeType = storeType;
        this.group = group;
        this.storeProperty = storeProperty;
    }

    public AuditEventStore(String identifier, IEPStore epsStore, StoreType storeType, IStoreProperty storeProperty) {
        this.epsStore = epsStore;
        this.identifier = identifier;
        this.storeType = storeType;
        this.storeProperty = storeProperty;
    }

    public AuditEventStore(String identifier, IStoreProperty storeProperty) {
        this.identifier = identifier;
        this.storeProperty = storeProperty;
    }

    public AuditEventStore(IStoreProperty storeProperty) {
        this.storeProperty = storeProperty;
    }

    public AuditEventStore(String group) {
        this.group = group;
    }

    public AuditEventStore(StoreType storeType) {
        this.storeType = storeType;
    }

    public void addToStore(String group, T context) {
        this.group = group;
        IEPSFile<T> file = new EPSFile<T>();

        file.setData(context);
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        epsStore.getFileManager().saveEPSFile(storeProperty.getComponentIdentifier(), storeProperty.getSystemIdentifier(), file);
    }

    public void removeFromStore(String group, T event) {
    }

    public T getFromStore(String group, String uniqueIdentifier) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadStore(String url, String username, String password) {
        IEPSFileSystem system = epsStore.getFileSystemComponent().getFileSystem(storeProperty.getSystemIdentifier());
        List<IEPSFileComponent> components = system.getFileSystemOptor().loadFiles(storeProperty.getPersistLocation(), new File(url));
        for (IEPSFileComponent component : components) {
            buildStoreContez(component);
        }
    }

    private void buildStoreContez(IEPSFileComponent component) {
        for (IEPSFile iepsf : component.getEPSFiles().values()) {
            Object obj = iepsf.getData();
            try {
                match = true;
                IStoreContext<IMatchedEventSet<T>> context = (IStoreContext<IMatchedEventSet<T>>) obj;
                matchContexts.add(context);
            } catch (Exception e) {
                match = false;
                IStoreContext<IUnMatchedEventSet<T>> context = (IStoreContext<IUnMatchedEventSet<T>>) obj;
                unmatchContexts.add(context);
            }
        }
    }

    public StoreType getStoreType() {
        return this.storeType;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setStoreContexts(List<IStoreContext<IMatchedEventSet<T>>> contexts) {
        this.matchContexts = contexts;
    }

    public List<IStoreContext<IMatchedEventSet<T>>> getStoreContexts() {
        return this.matchContexts;
    }

    public void saveToStore(String group, IMatchedEventSet<T> eventSet) {
        IStoreContext<IMatchedEventSet<T>> storeContext = new StoreContext<IMatchedEventSet<T>>(
                IDUtil.getUniqueID(new Date().toString()),
                eventSet, group, null);
        IEPSFile<IStoreContext<IMatchedEventSet<T>>> file = new EPSFile<IStoreContext<IMatchedEventSet<T>>>();
        file.setData(storeContext);
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        file.setFilePath(storeProperty.getPersistLocation());
        file.setStoreProperty(storeProperty);

        epsStore.getFileManager().saveEPSFile(file);
    }

    public void saveToStore(String group, IUnMatchedEventSet<T> eventSet) {
        if (group == null) {
            group = "unmatched-" + new Date().toString();
        }
        IStoreContext<IUnMatchedEventSet<T>> storeContext = new StoreContext<IUnMatchedEventSet<T>>(
                IDUtil.getUniqueID(new Date().toString()),
                eventSet, group, null);
        IEPSFile<IStoreContext<IUnMatchedEventSet<T>>> file = new EPSFile<IStoreContext<IUnMatchedEventSet<T>>>();
        file.setData(storeContext);
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        file.setFilePath(storeProperty.getPersistLocation());
        file.setStoreProperty(storeProperty);

        epsStore.getFileManager().saveEPSFile(file);
    }

    public void saveToStore(String group, IStoreContext<IMatchedEventSet<T>> storeContext) {
        if (group == null) {
            group = "match-" + new Date().toString();
            storeContext.setGroup(group);
        }
        IEPSFile<IStoreContext<IMatchedEventSet<T>>> file = new EPSFile<IStoreContext<IMatchedEventSet<T>>>();
        file.setData(storeContext);
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        file.setFilePath(storeProperty.getPersistLocation());
        file.setStoreProperty(storeProperty);

        epsStore.getFileManager().saveEPSFile(file);
    }

    public void saveToStore(IStoreContext<IUnMatchedEventSet<T>> storeContext) {
        if (group == null) {
            group = "unmatch-" + new Date().toString();
            storeContext.setGroup(group);
        }
        IEPSFile<IStoreContext<IUnMatchedEventSet<T>>> file = new EPSFile<IStoreContext<IUnMatchedEventSet<T>>>();
        file.setData(storeContext);
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        file.setFilePath(storeProperty.getPersistLocation());
        file.setStoreProperty(storeProperty);

        epsStore.getFileManager().saveEPSFile(file);
    }

    public void configureStore() {
        epsStore.configureManagers();
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }

    public List<IStoreContext<IUnMatchedEventSet<T>>> getStoreUnMatchContexts() {
        return unmatchContexts;
    }
    
}
