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
package org.streameps.store.file.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.store.IStoreProperty;
import org.streameps.store.file.EPSFilenameFilter;
import org.streameps.store.file.IEPSFile;
import org.streameps.store.file.IEPSFileSystem;
import org.streameps.store.file.SupportedType;
import org.streameps.store.file.component.EPSFileComponent;
import org.streameps.store.file.component.EPSFileManagerComponent;
import org.streameps.store.file.component.EPSFileSystemComponent;
import org.streameps.store.file.component.IEPSFileComponent;
import org.streameps.store.file.component.IEPSFileManagerComponent;
import org.streameps.store.file.component.IEPSFileSystemComponent;
import org.streameps.util.IDUtil;

/**
 *
 * @author Frank Appiah
 */
public class EPSComponentManager implements IEPSComponentManager {

    private List<IEPSFileComponent> fileComponents;
    private List<IEPSFileManagerComponent> fileManagerComponents;
    private List<IEPSFileSystemComponent> fileSystemComponents;
    private String identifier;
    private String filePath;
    private List<String> componentIdentifiers = new ArrayList<String>();
    private List<String> systemIdentifiers = new ArrayList<String>();
    private String fileExt;
    private IEPSFileComponent fileComponent;
    private IEPSFileManagerComponent fileManagerComponent;
    private IEPSFileSystemComponent fileSystemComponent;
    private IEPSFileManager fileManager;
    private String componentName;
    private ILogger logger = LoggerUtil.getLogger(EPSComponentManager.class);
    private IStoreProperty storeProperty;

    public EPSComponentManager() {
        setDefaultComponents();
    }

    public EPSComponentManager(String identifier, String componentName) {
        this.identifier = identifier;
        this.componentName = componentName;
        setDefaultComponents();
    }

    public EPSComponentManager(String identifier, String componentName, IEPSFileManager fileManager) {
        this.identifier = identifier;
        this.fileManager = fileManager;
        this.componentName = componentName;
    }

    private void setDefaultComponents() {
        fileComponents = new ArrayList<IEPSFileComponent>();
        fileManagerComponents = new ArrayList<IEPSFileManagerComponent>();
        fileSystemComponents = new ArrayList<IEPSFileSystemComponent>();

        fileComponent = new EPSFileComponent(IDUtil.getUniqueID(new Date().toString()));
        fileManagerComponent = new EPSFileManagerComponent(IDUtil.getUniqueID(new Date().toString()));
        fileSystemComponent = new EPSFileSystemComponent(IDUtil.getUniqueID(new Date().toString()));

        getFileComponents().add(fileComponent);
        getFileManagerComponents().add(fileManagerComponent);
        getFileSystemComponents().add(fileSystemComponent);
    }

    public void setComponentIdentifiers(List<String> identifiers) {
        this.componentIdentifiers = identifiers;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<String> getComponentIdentifiers() {
        return this.componentIdentifiers;
    }

    public List<IEPSFileComponent> loadFileComponents() {
        return fileComponents;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void deleteEPSFile(String identifier) {
        for (IEPSFileComponent component : getFileComponents()) {
            for (String key : component.getEPSFiles().keySet()) {
                IEPSFile file = component.getEPSFiles().get(key);
                if (file != null) {
                    component.getEPSFiles().remove(key);
                }
            }
        }
    }

    public IEPSFileComponent searchFileComponent(String fileName, String systemIdentifier) {
        String path = getFilePath() + File.pathSeparator + fileName;
        File file = new File(path);
        if (file.exists()) {
            IEPSFileSystem fileSystem = getFileSystem(identifier, fileName);
            fileSystem.loadFile(file);
        }
        return null;
    }

    public int getFileComponentSize() {
        return fileComponents.size();
    }

    public void setFileExtension(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFileExtension() {
        return this.fileExt;
    }

    public List<IEPSFileComponent> getFileComponents() {
        return this.fileComponents;
    }

    public void setFileComponents(List<IEPSFileComponent> fileComponents) {
        this.fileComponents = fileComponents;
    }

    public List<IEPSFileSystemComponent> getFileSystemComponents() {
        return this.fileSystemComponents;
    }

    public void setFileSystemComponents(List<IEPSFileSystemComponent> systemComponent) {
        this.fileSystemComponents = systemComponent;
    }

    public List<IEPSFileManagerComponent> getFileManagerComponents() {
        return this.fileManagerComponents;
    }

    public void setFileManagerComponents(List<IEPSFileManagerComponent> fileManagerComponent) {
        this.fileManagerComponents = fileManagerComponent;
    }

    public void saveEPSFileComponents(String systemIdentifier, List<IEPSFileComponent> fileComponents) {
        ObjectOutputStream outputStream = null;
        try {
            IEPSFileSystem fileSystem = getFileSystem(systemIdentifier, "");
            for (IEPSFileComponent component : fileComponents) {
                fileSystem.addFileComponent(component.getIdentifier(), component);
            }
            File file = new File(filePath);
            file.setExecutable(true);
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
            fileSystem.writeExternal(outputStream);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
                logger.warn(ex.getMessage());
            }
        }
    }

    public void saveEPSFileSystemComponents(String identifier, List<IEPSFileSystemComponent> fileComponents) {
        ObjectOutputStream inputStream = null;
        try {
            File file = new File(filePath);
            file.setExecutable(true);
            inputStream = new ObjectOutputStream(new FileOutputStream(file));
            for (IEPSFileSystemComponent systemComponent : fileComponents) {
                systemComponent.writeExternal(inputStream);
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    public List<IEPSFileSystemComponent> loadFileSystemComponents(String urlPath, EPSFilenameFilter filenameFilter) {
        File file = new File(urlPath);
        List<IEPSFileSystemComponent> components = new ArrayList<IEPSFileSystemComponent>();
        FileInputStream stream = null;
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles(new EPSFilenameFilter(SupportedType.FSC));
            for (File f : files) {
                try {
                    stream = new FileInputStream(f);
                    ObjectInputStream ois = new ObjectInputStream(stream);
                    try {
                        IEPSFileSystemComponent component = (IEPSFileSystemComponent) ois.readObject();
                        components.add(component);
                    } catch (ClassNotFoundException ex) {
                        logger.error(ex.getMessage());
                    }
                } catch (IOException ex) {
                    logger.error(ex.getMessage());
                }
            }
            try {
                stream.close();
            } catch (IOException ex) {
                logger.warn(ex.getMessage());
            }
        }
        fileSystemComponents = components;
        return components;
    }

    public int getManagerComponentSize() {
        return fileManagerComponents.size();
    }

    public int getSystemComponentSize() {
        return this.fileSystemComponents.size();
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentName() {
        return this.componentName;
    }

    public IEPSFileSystem getFileSystem(String identifier, String systemName) {
        IEPSFileSystem fileSystem = null;
        for (IEPSFileSystemComponent component : getFileSystemComponents()) {
            for (IEPSFileSystem system : component.getFileSystems()) {
                if (system.getIdentifier().equalsIgnoreCase(identifier)
                        || system.getDefaultName().equalsIgnoreCase(systemName)) {
                    fileSystem = system;
                    fileSystemComponent = component;
                }
            }
        }
        return fileSystem;
    }

    public void setStoreProperty(IStoreProperty storeProperty) {
        this.storeProperty = storeProperty;
    }

    public IStoreProperty getStoreProperty() {
        return this.storeProperty;
    }

    public void addEPSFileComponent(String systemIdentifier, IEPSFileComponent component) {
        this.fileComponents.add(component);
        IEPSFileSystem fileSystem = getFileSystem(systemIdentifier, "");
        fileSystem.addFileComponent(component.getIdentifier(), fileComponent);

        fileSystemComponent.addEPSFileSystemIfAbsent(fileSystem);

        getFileSystemComponents().remove(fileSystemComponent);
        getFileSystemComponents().add(fileSystemComponent);
    }

    public IEPSFileSystemComponent getEPSFileSystemComponent(String systemIdentifier, String componentName) {
        for (IEPSFileSystemComponent component : fileSystemComponents) {
            if (component.getIdentifier().equalsIgnoreCase(systemIdentifier)
                    || component.getFileSystemComponentName().equalsIgnoreCase(componentName)) {
                return component;
            }
        }
        return null;
    }

    public void addEPSFileSystemComponent(IEPSFileSystemComponent systemComponent) {
        this.fileSystemComponents.add(systemComponent);
    }

    public void addFileManagerComponent(IEPSFileManagerComponent component) {
        this.fileManagerComponents.add(component);
    }

    public IEPSFileManagerComponent getEPSFileManagerComponent(String identifier, String componentName) {
        for (IEPSFileManagerComponent component : fileManagerComponents) {
            if (component.getIdentifier().equalsIgnoreCase(identifier)
                    || component.getComponentName().equalsIgnoreCase(componentName)) {
                return component;
            }
        }
        return null;
    }

    public void removeEPSFileComponent(String systemIdentifier, IEPSFileComponent component) {
        this.fileComponents.remove(component);
    }

    public void removeEPSFileSystemComponent(IEPSFileSystemComponent systemComponent) {
        this.fileSystemComponents.remove(systemComponent);
    }

    public void removeFileManagerComponent(IEPSFileManagerComponent component) {
        this.fileManagerComponents.remove(component);
    }
    
}
