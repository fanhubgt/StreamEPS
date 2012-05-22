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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.IUnMatchedEventSet;
import org.streameps.core.MatchedEventSet;
import org.streameps.core.UnMatchedEventSet;
import org.streameps.core.util.IDUtil;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.store.file.IFileEPStore;
import org.streameps.store.IStoreProperty;
import org.streameps.store.file.EPSFile;
import org.streameps.store.file.FileEPStore;
import org.streameps.store.file.IEPSFile;
import org.streameps.store.file.IEPSFileSystem;
import org.streameps.store.file.component.IEPSFileComponent;
import org.streameps.thread.IEPSExecutorManager;
import org.streameps.thread.IWorkerCallable;

/**
 *
 * @author Frank Appiah
 */
public class AuditEventStore<T> implements IHistoryStore<T>, Runnable {

    private IFileEPStore epsStore;
    private String identifier;
    private StoreType storeType;
    private List<IStoreContext<IMatchedEventSet<T>>> matchContexts;
    private List<IStoreContext<IUnMatchedEventSet<T>>> unmatchContexts;
    private String group;
    private IStoreProperty storeProperty, tempProperty;
    private boolean match = false, changed = false;
    private ILogger logger = LoggerUtil.getLogger(AuditEventStore.class);
    private IEPSExecutorManager executorManager;
    private IStoreContext<Set<T>> storeContext;
    private long persistTimestamp = 5;
    private long lastEventCount = 0;
    private int eventSaveCount = 20;

    public AuditEventStore() {
        epsStore = new FileEPStore();
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
    }

    public AuditEventStore(IFileEPStore epsStore, String identifier, String group, StoreType storeType, IStoreProperty storeProperty) {
        this.epsStore = epsStore;
        this.identifier = identifier;
        this.storeType = storeType;
        this.group = group;
        this.storeProperty = storeProperty;
        this.epsStore.setStoreProperty(storeProperty);
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
    }

    public AuditEventStore(String identifier, IFileEPStore epsStore, String group, StoreType storeType, IStoreProperty storeProperty) {
        this.epsStore = epsStore;
        this.identifier = identifier;
        this.storeType = storeType;
        this.group = group;
        this.storeProperty = storeProperty;
        this.epsStore.setStoreProperty(storeProperty);
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
    }

    public AuditEventStore(String identifier, IFileEPStore epsStore, StoreType storeType, IStoreProperty storeProperty) {
        this.epsStore = epsStore;
        this.identifier = identifier;
        this.storeType = storeType;
        this.storeProperty = storeProperty;
        this.epsStore.setStoreProperty(storeProperty);
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
    }

    public AuditEventStore(String identifier, IStoreProperty storeProperty) {
        this.identifier = identifier;
        this.storeProperty = storeProperty;
        this.epsStore.setStoreProperty(storeProperty);
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
    }

    public AuditEventStore(String identifier, long persistTimeStamp, IStoreProperty storeProperty) {
        this.identifier = identifier;
        this.storeProperty = storeProperty;
        this.epsStore.setStoreProperty(storeProperty);
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
        this.persistTimestamp = persistTimeStamp;
    }

    public AuditEventStore(IStoreProperty storeProperty) {
        this.storeProperty = storeProperty;
        epsStore = new FileEPStore();
        this.epsStore.setStoreProperty(storeProperty);
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
    }

    public AuditEventStore(IStoreProperty storeProperty, IEPSExecutorManager executorManager) {
        this.storeProperty = storeProperty;
        epsStore = new FileEPStore();
        this.epsStore.setStoreProperty(storeProperty);
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
        this.executorManager = executorManager;
    }

    public AuditEventStore(String group) {
        this.group = group;
        epsStore = new FileEPStore();
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
    }

    public AuditEventStore(StoreType storeType) {
        this.storeType = storeType;
        epsStore = new FileEPStore();
        storeContext = new StoreContext<Set<T>>(new HashSet<T>());
    }

    public void addToStore(String group, T event) {
        this.group = group;
        storeContext.getEventSet().add(event);
        logger.info("The file context added to the component for permanent storage.");
        if (!changed) {
            tempProperty = storeProperty;
            String filePath = storeProperty.getPersistLocation();
            if (!filePath.endsWith(File.pathSeparator)) {
                filePath += File.pathSeparator;
                storeProperty.setPersistLocation(filePath + group);
            } else {
                storeProperty.setPersistLocation(filePath + group);
            }
            setStoreProperty(storeProperty);
            changed = true;
        }
        lastEventCount++;
    }

    public void removeFromStore(String group, T event) {
        if (group.contentEquals(new StringBuffer(IFileEPStore.PATTERN_MATCH_GROUP))) {
            if (matchContexts != null) {
                for (IStoreContext<IMatchedEventSet<T>> context : matchContexts) {
                    context.getEventSet().remove(event);
                }
            }
        } else if (group.contentEquals(new StringBuffer(IFileEPStore.PATTERN_UNMATCH_GROUP))) {
            if (unmatchContexts != null) {
                for (IStoreContext<IUnMatchedEventSet<T>> context : unmatchContexts) {
                    context.getEventSet().remove(event);
                }
            }
        }
    }

    public Set<T> getFromStore(String group, String uniqueIdentifier) {
        Set<T> content = new HashSet<T>();
        if (matchContexts == null || unmatchContexts == null) {
            loadStore(null, null, null);
        }
        if (group.contentEquals(new StringBuffer(IFileEPStore.PATTERN_MATCH_GROUP))) {
            if (matchContexts != null) {
                for (IStoreContext<IMatchedEventSet<T>> context : matchContexts) {
                    for (T event : context.getEventSet()) {
                        content.add(event);
                    }
                }
            }
        } else if (group.contentEquals(new StringBuffer(IFileEPStore.PATTERN_UNMATCH_GROUP))) {
            if (unmatchContexts != null) {
                for (IStoreContext<IUnMatchedEventSet<T>> context : unmatchContexts) {
                    for (T event : context.getEventSet()) {
                        content.add(event);
                    }
                }
            }
        }
        return content;
    }

    public void loadStore(String url, String username, String password) {
        IEPSFileSystem system = epsStore.getFileSystemComponent().getFileSystem(storeProperty.getSystemIdentifier());
        List<IEPSFileComponent> components = system.getFileSystemOptor().loadFiles(storeProperty.getPersistLocation(), new File(url));
        for (IEPSFileComponent component : components) {
            buildStoreContez(component);
        }
        logger.debug("The file system is loaded from the store specified.");
    }

    private void buildStoreContez(IEPSFileComponent component) {
        for (IEPSFile iepsf : component.getEPSFiles().values()) {
            Object data = iepsf.getData();
            match = true;
            IStoreContext<Set<T>> filecontext = (IStoreContext<Set<T>>) data;
            if (filecontext.getGroup().contentEquals(new StringBuffer(IFileEPStore.PATTERN_MATCH_GROUP))) {
                IStoreContext<IMatchedEventSet<T>> context = new StoreContext<IMatchedEventSet<T>>(new MatchedEventSet<T>());
                context.setIdentifier(filecontext.getIdentifier());
                context.setGroup(filecontext.getGroup());
                context.setStoreIdentity(filecontext.getStoreIdentity());
                for (T event : filecontext.getEventSet()) {
                    context.getEventSet().add(event);
                }
                matchContexts.add(context);
                logger.debug("The store context is rather a match event set.");
                match = true;
            } else if (filecontext.getGroup().contentEquals(new StringBuffer(IFileEPStore.PATTERN_UNMATCH_GROUP))) {
                IStoreContext<IUnMatchedEventSet<T>> context = new StoreContext<IUnMatchedEventSet<T>>(new UnMatchedEventSet<T>());
                context.setIdentifier(filecontext.getIdentifier());
                context.setGroup(filecontext.getGroup());
                context.setStoreIdentity(filecontext.getStoreIdentity());
                for (T event : filecontext.getEventSet()) {
                    context.getEventSet().add(event);
                }
                unmatchContexts.add(context);
                match = false;
                logger.debug("The store context is rather an un-match event set.");
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

    /**
     * It returns a list of store contexts either in the memory or from the file
     * loaded the EPSStore implementer.
     * @return  A list of store contexts.
     */
    public List<IStoreContext<IMatchedEventSet<T>>> getStoreContexts() {
        return this.matchContexts;
    }

    public void saveToStore(String group, IMatchedEventSet<T> eventSet) {
        if (group == null) {
            group = IFileEPStore.PATTERN_MATCH_GROUP + new Date().toString();
            this.storeContext.setGroup(group);
        }
        this.storeContext.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        this.storeContext.setStoreIdentity(storeProperty.getStoreIdentity());

        IEPSFile<IStoreContext<Set<T>>> file = new EPSFile<IStoreContext<Set<T>>>();
        file.setData(storeContext);
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        file.setFilePath(storeProperty.getPersistLocation());
        file.setStoreProperty(storeProperty);

        doPersist(file);
        logger.debug("The group " + group + " is being persisted permanently to a store.");
    }

    public void saveToStore(String group, IUnMatchedEventSet<T> eventSet) {
        if (group == null) {
            group = IFileEPStore.PATTERN_UNMATCH_GROUP + new Date().toString();
            this.storeContext.setGroup(group);
        }
        this.storeContext.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        this.storeContext.setStoreIdentity(storeProperty.getStoreIdentity());
        for (T event : eventSet) {
            this.storeContext.getEventSet().add(event);
        }

        IEPSFile<IStoreContext<Set<T>>> file = new EPSFile<IStoreContext<Set<T>>>();
        file.setData(this.storeContext);
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        file.setFilePath(storeProperty.getPersistLocation());
        file.setStoreProperty(storeProperty);

        doPersist(file);
        logger.debug("The group " + group + " is being persisted permanently to a store.");
    }

    public void addToStore(String group, IStoreContext<IMatchedEventSet<T>> storeContext) {
        for (T event : storeContext.getEventSet()) {
            this.storeContext.getEventSet().add(event);
        }
        this.group = group;
    }

    public void addToStore(String group, IMatchedEventSet<T> eventSet) {
        for (T event : eventSet) {
            this.storeContext.getEventSet().add(event);
        }
        this.group = group;
    }

    public void addToStore(String group, IUnMatchedEventSet<T> eventSet) {
        for (T event : eventSet) {
            this.storeContext.getEventSet().add(event);
        }
        this.group = group;
    }

    public void addToStore(IStoreContext<IUnMatchedEventSet<T>> storeContext) {
        for (T event : storeContext.getEventSet()) {
            this.storeContext.getEventSet().add(event);
        }
    }

    private void doPersist(final IEPSFile sFile) {
        if (executorManager == null) {
            epsStore.getFileManager().saveEPSFile(sFile);
        } else {
            executorManager.execute(new IWorkerCallable<String>() {

                public String getIdentifier() {
                    return "audit-" + IDUtil.getUniqueID(new Date().toString());
                }

                public void setIdentifier(String name) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public String call() throws Exception {
                    epsStore.getFileManager().saveEPSFile(sFile);
                    return sFile.getIdentifier();
                }
            });
        }
    }

    public void saveToStore(String group, IStoreContext<IMatchedEventSet<T>> storeContext) {
        IStoreContext<Set<T>> context = new StoreContext<Set<T>>(new HashSet<T>());
        if (group == null) {
            group = IFileEPStore.PATTERN_MATCH_GROUP + new Date().toString();
            context.setGroup(group);
        }
        String persistLoc = tempProperty.getPersistLocation();
        persistLoc += "/match/" + group;
        storeProperty.setPersistLocation(persistLoc);
        context.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        context.setStoreIdentity(storeProperty.getStoreIdentity());
        for (T event : storeContext.getEventSet()) {
            context.getEventSet().add(event);
        }

        IEPSFile<IStoreContext<Set<T>>> file = new EPSFile<IStoreContext<Set<T>>>();
        file.setData(context);
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        file.setFilePath(storeProperty.getPersistLocation());
        file.setStoreProperty(storeProperty);

        doPersist(file);
        logger.debug("The group: " + group + " matched event store context is successfully persisted.");
    }

    public void saveToStore(IStoreContext<IUnMatchedEventSet<T>> storeContext) {
        if (group == null) {
            group = IFileEPStore.PATTERN_UNMATCH_GROUP + new Date().toString();
            this.storeContext.setGroup(group);
        }
        String persistLoc = tempProperty.getPersistLocation();
        persistLoc += "/unmatch/" + group;
        storeProperty.setPersistLocation(persistLoc);
        this.storeContext.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        this.storeContext.setStoreIdentity(storeProperty.getStoreIdentity());
        for (T event : storeContext.getEventSet()) {
            this.storeContext.getEventSet().add(event);
        }
        IEPSFile<IStoreContext<Set<T>>> file = new EPSFile<IStoreContext<Set<T>>>();
        file.setData(this.storeContext);
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        file.setFilePath(storeProperty.getPersistLocation());
        file.setStoreProperty(storeProperty);

        doPersist(file);
        logger.debug("The unmatched event store context is successfully persisted.");
    }

    public void saveStream(String group, IStoreContext<Set<T>> storeContext) {
        if (group == null) {
            group = IFileEPStore.STREAMS + new Date().toString();
            this.storeContext.setGroup(group);
        }
        String persistLoc = tempProperty.getPersistLocation();
        persistLoc += "/" + group;
        storeProperty.setPersistLocation(persistLoc);
        this.storeContext.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        this.storeContext.setStoreIdentity(storeProperty.getStoreIdentity());
        for (T event : storeContext.getEventSet()) {
            this.storeContext.getEventSet().add(event);
        }
        IEPSFile<IStoreContext<Set<T>>> file = new EPSFile<IStoreContext<Set<T>>>();
        file.setData(this.storeContext);
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        file.setFilePath(storeProperty.getPersistLocation());
        file.setStoreProperty(storeProperty);

        doPersist(file);
        logger.debug("The unmatched event store context is successfully persisted.");
    }

    public void configureStore() {
        epsStore.configureManagers();
        logger.debug("The store is properly configured.");
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }

    /**
     * It returns a list of store contexts either in the memory or from the file
     * loaded the EPSStore implementer.
     *
     * @param context  A list of store contexts.
     */
    public List<IStoreContext<IUnMatchedEventSet<T>>> getStoreUnMatchContexts() {
        return unmatchContexts;
    }

    public void setExecutorManager(IEPSExecutorManager executorManager) {
        this.executorManager = executorManager;
    }

    public IEPSExecutorManager getExecutorManager() {
        return executorManager;
    }

    public void setEpsStore(IFileEPStore epsStore) {
        this.epsStore = epsStore;
    }

    public IFileEPStore getEpsStore() {
        return epsStore;
    }

    public void setStoreProperty(IStoreProperty storeProperty) {
        this.storeProperty = storeProperty;
        this.tempProperty = storeProperty;
        this.epsStore.setStoreProperty(storeProperty);
    }

    public IStoreProperty getStoreProperty() {
        return storeProperty;
    }

    public void save() {
        this.storeContext.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        this.storeContext.setGroup(group);

        IEPSFile<IStoreContext<Set<T>>> file = new EPSFile<IStoreContext<Set<T>>>();
        file.setData(this.storeContext);
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        file.setFilePath(storeProperty.getPersistLocation());
        file.setStoreProperty(storeProperty);

        epsStore.setStoreProperty(storeProperty);
        epsStore.getFileManager().saveEPSFile(file);
    }

    public IHistoryStore<T> getHistoryStore() {
        return this;
    }

    public void setHistoryStore(IHistoryStore<T> historyStore) {
    }

    public void run() {
        long systime = Long.parseLong(System.getProperty("persistTimestamp","100"));
        String timeunit = System.getProperty("timeUnit", "MILLISECONDS");
        if (systime > 0) {
            persistTimestamp = systime;
        }
        TimeUnit timeUnit;
        if (TimeUnit.valueOf(timeunit) != null) {
            timeUnit = TimeUnit.valueOf(timeunit);
        } else {
            timeUnit = TimeUnit.SECONDS;
        }
        if (System.getProperty("eventSaveCount") != null) {
            eventSaveCount = Integer.parseInt(System.getProperty("eventSaveCount","20") );
        }
        executorManager.executeAtFixedRate(new IWorkerCallable<Boolean>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String name) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Boolean call() throws Exception {
                synchronized (storeContext) {
                    if (lastEventCount >= eventSaveCount) {
                        save();
                        storeContext.setEventSet(new HashSet<T>());
                        lastEventCount = 0;
                    }
                }
                return Boolean.TRUE;
            }
        }, 1, persistTimestamp, timeUnit);
    }

    public int getEventSaveCount() {
        return eventSaveCount;
    }

    public void setEventSaveCount(int eventSaveCount) {
        this.eventSaveCount = eventSaveCount;
    }

    public void setPersistTimestamp(long persistTimestamp) {
        this.persistTimestamp = persistTimestamp;
    }

    public long getPersistTimestamp() {
        return persistTimestamp;
    }
}
