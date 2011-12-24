/**
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
package org.streameps.store.file.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.streameps.core.util.IDUtil;
import org.streameps.store.IStoreProperty;
import org.streameps.store.file.IEPSFile;
import org.streameps.store.file.component.EPSFileComponent;
import org.streameps.store.file.component.IEPSFileComponent;

/**
 * 
 * @author Frank Appiah
 */
public class EPSFileManager implements IEPSFileManager {

    private String identifier;
    private String filePath;
    private String componentIdentifier;
    private String systemIdentifier;
    private IEPSFile file;
    private int size;
    private String fileExtension;
    private String defaultName;
    private IEPSComponentManager componentManager;
    private IStoreProperty storeProperty;
    private EPSFileComponent fileComponent;
    private List<IEPSFileComponent> components;

    public EPSFileManager() {
        components = new ArrayList<IEPSFileComponent>();
    }

    public EPSFileManager(String identifier) {
        this.identifier = identifier;
        components = new ArrayList<IEPSFileComponent>();
    }

    public EPSFileManager(String identifier, IStoreProperty storeProperty) {
        this.storeProperty = storeProperty;
        this.identifier = identifier;
        components = new ArrayList<IEPSFileComponent>();
    }

    public EPSFileManager(String identifier, String filePath, String componentIdentifier, String systemIdentifier) {
        this.identifier = identifier;
        this.filePath = filePath;
        this.componentIdentifier = componentIdentifier;
        this.systemIdentifier = systemIdentifier;
        components = new ArrayList<IEPSFileComponent>();
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void saveEPSFile(String componentIdentifier, String systemIdentifier, IEPSFile ePSFile) {
        addEPSFile(componentIdentifier, systemIdentifier, ePSFile);
        addEPSFileComponent(systemIdentifier);
        save();
    }

    public void saveEPSFile(IEPSFile ePSFile) {
        this.storeProperty=ePSFile.getStoreProperty();
        componentIdentifier=storeProperty.getComponentIdentifier();
        systemIdentifier=storeProperty.getSystemIdentifier();
        
        addEPSFile(componentIdentifier, systemIdentifier, ePSFile);
        addEPSFileComponent(systemIdentifier);
        save();
    }

    public void addEPSFile(String componentIdentifier, String systemIdentifier, IEPSFile ePSFile) {
        fileComponent = new EPSFileComponent(IDUtil.getUniqueID(new Date().toString()));
        fileComponent.setSaveLocation(storeProperty.getPersistLocation());
        fileComponent.addEPSFile(ePSFile);
        this.componentIdentifier = componentIdentifier;
        this.systemIdentifier = systemIdentifier;
    }

    public void addEPSFileComponent(String systemIdentifier) {
        components.add(fileComponent);
    }

    public void save() {
        this.systemIdentifier = storeProperty.getSystemIdentifier();
        this.filePath = storeProperty.getPersistLocation();
        this.componentIdentifier = storeProperty.getComponentIdentifier();

        getComponentManager().saveEPSFileComponents(systemIdentifier, components);
    }

    public void reset() {
        fileComponent = new EPSFileComponent(IDUtil.getUniqueID(new Date().toString()));
        components = new ArrayList<IEPSFileComponent>();
    }

    public IEPSFile loadFile(String urlPath, String componentIdentifier, String systemIdentifier) {
        this.componentIdentifier = componentIdentifier;
        this.systemIdentifier = systemIdentifier;
        return null;
    }

    public void deleteEPSFile(String componentIdentifier, String systemIdentifier) {
        this.componentIdentifier = componentIdentifier;
        this.systemIdentifier = systemIdentifier;

    }

    public void deleteFileComponent(String identifier) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IEPSFile searchFile(String fileIdentifier, String componentIdentifier, String systemIdentifier) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IEPSFileComponent searchFileComponent(String fileName, String componentIdentifier, String systemIdentifier) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFileExtension(String fileExt) {
        this.fileExtension = fileExt;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public int getFileSize() {
        return this.size;
    }

    public void setFileManagerName(String defaultFileName) {
        this.defaultName = defaultFileName;
    }

    public String getFileManagerName() {
        return this.defaultName;
    }

    public void setComponentManager(IEPSComponentManager componentManager) {
        this.componentManager = componentManager;
        this.componentManager.setFilePath(filePath);
        this.componentManager.setStoreProperty(storeProperty);
        this.componentManager.setFileManager(this);
    }

    public IEPSComponentManager getComponentManager() {
        return this.componentManager;
    }

    public void setComponentIdentifier(String componentID) {
        this.componentIdentifier = componentID;
    }

    public String getComponentIdentifier() {
        return this.componentIdentifier;
    }

    public void setSystemIdentifier(String systemIdentifier) {
        this.systemIdentifier = systemIdentifier;
    }

    public String getSystemIdentifier() {
        return this.systemIdentifier;
    }

    public void setStoreProperty(IStoreProperty storeProperty) {
        this.storeProperty = storeProperty;
    }

    public IStoreProperty getStoreProperty() {
        return this.storeProperty;
    }
}
