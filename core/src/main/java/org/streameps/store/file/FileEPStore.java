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

import java.util.Date;
import org.streameps.core.util.IDUtil;
import org.streameps.store.IStoreProperty;
import org.streameps.store.file.component.IEPSFileSystemComponent;
import org.streameps.store.file.component.IEPSFileManagerComponent;
import org.streameps.store.file.manager.IEPSFileManager;
import org.streameps.store.file.manager.IEPSComponentManager;
import org.streameps.store.file.manager.EPSComponentManager;
import org.streameps.store.file.manager.EPSFileManager;

/**
 *
 * @author Frank Appiah
 */
public class FileEPStore implements IFileEPStore {

    private IEPSFileSystemComponent fileSystemComponent;
    private IEPSFileManagerComponent fileManagerComponent;
    private IEPSFileManager fileManager;
    private IEPSComponentManager componentManager;
    private IStoreProperty storeProperty;
    private String componentName;

    public FileEPStore() {
        fileManager = new EPSFileManager();
        componentManager = new EPSComponentManager();
    }

    public FileEPStore(IEPSComponentManager componentManager, IEPSFileManager fileManager) {
        this.componentManager = componentManager;
        this.fileManager = fileManager;
    }

    public void setComponentManager(IEPSComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    public IEPSComponentManager getComponentManager() {
        return this.componentManager;
    }

    public void setFileManager(IEPSFileManager fileManager) {
        this.fileManager = fileManager;
        this.fileManagerComponent.addFileManager(fileManager);
    }

    public IEPSFileManager getFileManager() {
        return this.fileManager;
    }

    public void setFileManagerComponent(IEPSFileManagerComponent fileManagerComponent) {
        this.fileManagerComponent = fileManagerComponent;
    }

    public IEPSFileManagerComponent getFileManagerComponent() {
        return fileManagerComponent;
    }

    public void setFileSystemComponent(IEPSFileSystemComponent fileSystemComponent) {
        this.fileSystemComponent = fileSystemComponent;
    }

    public IEPSFileSystemComponent getFileSystemComponent() {
        return this.fileSystemComponent;
    }

    public void setStoreProperty(IStoreProperty storeProperty) {
        this.storeProperty = storeProperty;
        configureManagers();
    }

    public IStoreProperty getStoreProperty() {
        return this.storeProperty;
    }

    public void configureManagers() {
        componentManager.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        fileManagerComponent = componentManager.getEPSFileManagerComponent(IEPSComponentManager.DEFAULT_FILE_MANAGER_COMPONENT, null);
        fileManager = fileManagerComponent.getEPSFileManager(IEPSFileManager.DEFAULT_FILE_MANAGER);
        fileManager.setComponentManager(componentManager);

        fileSystemComponent = componentManager.getEPSFileSystemComponent(IEPSComponentManager.DEFAULT_FILE_SYSTEM_COMPONENT, null);

        configureProperty(storeProperty);
    }

    public void configureProperty(IStoreProperty storeProperty) {
        //fileManagerComponent.setComponentName(storeProperty.getComponentIdentifier());
        //fileSystemComponent.setFileSystemComponentName(storeProperty.getSystemIdentifier());

        if (fileManager.getIdentifier().equalsIgnoreCase(IEPSFileManager.DEFAULT_FILE_MANAGER)) {
            fileManager.setStoreProperty(storeProperty);
            fileManagerComponent.updateFileManager(fileManager);
        }
        IEPSFileSystem fileSystem = fileSystemComponent.getFileSystem(IEPSFileSystem.DEFAULT_SYSTEM_ID);
        fileSystem.setDirPath(storeProperty.getPersistLocation());
        fileSystemComponent.updateEPSFileSystem(fileSystem);
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
        componentManager.setComponentName(componentName);
    }
    
}
