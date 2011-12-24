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

import org.streameps.core.util.IDUtil;
import org.streameps.store.IStoreProperty;
import org.streameps.store.file.IEPSFile;
import org.streameps.store.file.component.IEPSFileComponent;

/**
 *
 * @author  Frank Appiah
 */
public interface IEPSFileManager {

    public String DEFAULT_FILE_MANAGER = IDUtil.getUniqueID("Default_File_Manager");

    /**
     * It sets the auto-generated identifier for the manager.
     * @param identifier A unique identifier.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns the auto-generated identifier for the manager.
     * @return A unique identifier.
     */
    public String getIdentifier();

    /**
     * It sets the file path for the EPS store.
     * @param filePath The file path for this store.
     */
    public void setFilePath(String filePath);

    /**
     * It returns the file path for the EPS store.
     * @return The file path for this store.
     */
    public String getFilePath();

    /**
     * It saves the file to a permanent store in the file system.
     * @param identifier A unique identifier of the specific component.
     * @param componentIdentifier  The identifier of the system component.
     * @param ePSFile An EPS file to store.
     */
    public void saveEPSFile(String componentIdentifier, String systemIdentifier, IEPSFile ePSFile);

     public void saveEPSFile(IEPSFile ePSFile);
     
    /**
     * It adds the file to a permanent store in the file system.
     * @param identifier A unique identifier of the specific component.
     * @param componentIdentifier  The identifier of the system component.
     * @param ePSFile An EPS file to store.
     */
    public void addEPSFile(String componentIdentifier, String systemIdentifier, IEPSFile ePSFile);

    /**
     * It adds the file component to the list of file components.
     * @param systemIdentifier A system identifier.
     * @param component An EPS file component to be added to the list.
     */
    public void addEPSFileComponent(String systemIdentifier);

    /**
     * It loads the file from the path specified.
     * @param urlPath The URL path to the EPSfile.
     * @param identifier A unique identifier of the specific component.
     * @param componentIdentifier  The identifier of the system component.
     */
    public IEPSFile loadFile(String urlPath, String componentIdentifier, String systemIdentifier);

    /**
     * It deletes the file with the unique identifier.
     * @param componentIdentifier A unique identifier of the specific component.
     * @param systemIdentifier  The identifier of the system component.
     */
    public void deleteEPSFile(String componentIdentifier, String systemIdentifier);

    /**
     * It deletes the file component with the specified identifier.
     */
    public void deleteFileComponent(String identifier);

    /**
     * It searches for a file with the specified file name and/or identifier.
     *
     * @param fileIdentifier The file identifier.
     * @param identifier A unique identifier of the specific component.
     * @param componentIdentifier  The identifier of the system component.
     */
    public IEPSFile searchFile(String fileIdentifier, String componentIdentifier, String systemIdentifier);

    /**
     * It searches the file component from the file system with the specified system identifier.
     * @param fileName The name of the component file.
     * @param componentIdentifier The identifier for the component.
     * @param systemIdentifier The identifier for the system.
     * @return An instance of a file component.
     */
    public IEPSFileComponent searchFileComponent(String fileName, String componentIdentifier, String systemIdentifier);

    /**
     * It sets the file extension for the saved file component.
     * @param fileExt The name for the file extension.
     */
    public void setFileExtension(String fileExt);

    /**
     * It returns the file extension for the saved file component.
     * @return The name for the file extension.
     */
    public String getFileExtension();

    /**
     * It returns the number of files in the file store.
     * @return The number of files.
     */
    public int getFileSize();

    /**
     * It sets the default name for this file manager.
     * @param defaultComponentName The default manager name.
     */
    public void setFileManagerName(String defaultFileName);

    /**
     * It returns the default name for this file manager.
     * @return The default manager name.
     */
    public String getFileManagerName();

    /**
     * It sets the file system for the manager.
     * @param fileSystem The file system for the manager.
     */
    public void setComponentManager(IEPSComponentManager componentManager);

    /**
     * It returns the file system for the manager.
     * @return The file system for the manager.
     */
    public IEPSComponentManager getComponentManager();

    /**
     * It sets the component auto0-generated identifier to be used throughout
     * the file management.
     * @param componentID The auto-generated component identifier.
     */
    public void setComponentIdentifier(String componentID);

    /**
     * It returns the component auto-generated identifier.
     * @return The auto-generated component identifier.
     */
    public String getComponentIdentifier();

    /**
     * It sets the auto-generated system identifier for the file management.
     * @param systemIdentifier The auto-generated system identifier
     */
    public void setSystemIdentifier(String systemIdentifier);

    /**
     * It returns the auto-generated system identifier for the file management.
     * @return The auto-generated system identifier
     */
    public String getSystemIdentifier();

    /**
     * It sets the property of the file store for the file management operations.
     * 
     * @param storeProperty The properties of the file store.
     */
    public void setStoreProperty(IStoreProperty storeProperty);

    /**
     * It returns the property of the file store for the file management operations.
     * @return The properties of the file store.
     */
    public IStoreProperty getStoreProperty();

    /**
     * It persists the file components to the store.
     */
    public void save();

     public void reset();
}
