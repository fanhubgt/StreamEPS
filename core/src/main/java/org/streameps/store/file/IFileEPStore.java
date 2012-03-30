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
package org.streameps.store.file;

import org.streameps.store.IStoreProperty;
import org.streameps.store.file.manager.IEPSComponentManager;
import org.streameps.store.file.manager.IEPSFileManager;
import org.streameps.store.file.component.IEPSFileManagerComponent;
import org.streameps.store.file.component.IEPSFileSystemComponent;

/**
 * Interface for the EPS store.
 * 
 * @author  Frank Appiah
 */
public interface IFileEPStore {

    public String MATCH_GROUP="match";

    public String UNMATCH_GROUP="unmatch";

    public String PARTICIPANT_GROUP="participant";

    public String AGGREGATE_GROUP="aggregate";

    public String FILTER_GROUP="filter";

    public String ANY_GROUP="any";

    public String STREAMS="stream";
    /**
     * It sets the store property for the configuration of the store.
     * @param storeProperty The configuration property set.
     */
    public void setStoreProperty(IStoreProperty storeProperty);

    /**
     * It returns the store property for the configuration of the store.
     * @return The configuration property set.
     */
    public IStoreProperty getStoreProperty();

    /**
     * It configure the component and file manager for the file store activities.
     */
    public void configureManagers();

    /**
     * It sets the component manager for the store.
     * @param componentManager The component manager.
     */
    public void setComponentManager(IEPSComponentManager componentManager);

    /**
     * It returns the component manager.
     * @return The component manager to manage the components.
     */
    public IEPSComponentManager getComponentManager();

    /**
     * It sets the file manager for the store.
     * @param fileManager The file manager to manage the files.
     */
    public void setFileManager(IEPSFileManager fileManager);

    /**
     * It returns the file manager.
     * @return The file manager to manage the files.
     */
    public IEPSFileManager getFileManager();

    /**
     * It sets the file system component for the store.
     * @param fileSystemComponent The file system component.
     */
    public void setFileSystemComponent(IEPSFileSystemComponent fileSystemComponent);

    /**
     * It returns the file system component.
     * @return The file system component for store.
     */
    public IEPSFileSystemComponent getFileSystemComponent();

    /**
     * It sets the file manager component.
     * @param fileManagerComponent The file manager component.
     */
    public void setFileManagerComponent(IEPSFileManagerComponent fileManagerComponent);

    /**
     * It returns the file manager component.
     * @return The file manager component.
     */
    public IEPSFileManagerComponent getFileManagerComponent();
}
