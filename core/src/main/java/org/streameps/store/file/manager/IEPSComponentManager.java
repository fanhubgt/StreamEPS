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

import java.util.List;
import org.streameps.store.IStoreProperty;
import org.streameps.store.file.EPSFilenameFilter;
import org.streameps.store.file.IEPSFileSystem;
import org.streameps.store.file.component.IEPSFileComponent;
import org.streameps.store.file.component.IEPSFileManagerComponent;
import org.streameps.store.file.component.IEPSFileSystemComponent;

/**
 * An interface for the EPS component manager for the normal file system
 * operation that includes saving, deleting, searching.
 * 
 * @author  Frank Appiah
 */
public interface IEPSComponentManager {

    /**
     * It loads the file from the path specified.
     */
    public List<IEPSFileComponent> loadFileComponents();

    /**
     * It saves the file to a permanent store in the file system.
     * @param identifier A unique identifier.
     * @param ePSFile An EPS file component to store.
     */
    public void saveEPSFileComponents(String systemIdentifier, List<IEPSFileComponent> fileComponents);

    /**
     * It adds the file component to the list of file components.
     * @param systemIdentifier A system identifier.
     * @param component An EPS file component to be added to the list.
     */
    public void addEPSFileComponent(String systemIdentifier, IEPSFileComponent component);

    /**
     * It removes the file component from the list of file components.
     * @param systemIdentifier A system identifier.
     * @param component An EPS file component to be remove from the list.
     */
    public void removeEPSFileComponent(String systemIdentifier, IEPSFileComponent component);

    /**
     * It saves the file to a permanent store in the file system.
     * @param identifier A unique identifier.
     * @param ePSFile An EPS file component to store.
     */
    public void saveEPSFileSystemComponents(String identifier, List<IEPSFileSystemComponent> fileComponents);

    /**
     * It adds a file system component to the list of file system components.
     * 
     * @param systemComponent An EPS file system component to be added to the list.
     */
    public void addEPSFileSystemComponent(IEPSFileSystemComponent systemComponent);

    /**
     * It removes the file system component from the list of components.
     * @param systemComponent The file system component to be removed.
     */
    public void removeEPSFileSystemComponent(IEPSFileSystemComponent systemComponent);

    /**
     * It returns the file system component from the list of file system components.
     * @param systemIdentifier The identifier of the system component.
     * @return The file system component as specified by the system identifier.
     */
    public IEPSFileSystemComponent getEPSFileSystemComponent(String systemIdentifier, String componentName);

    /**
     * It deletes the file with the unique identifier.
     * @param identifier A unique identifier.
     */
    public void deleteEPSFile(String identifier);

    /**
     * It returns the file system from the file system component.
     * 
     * @param identifier A unique identifier.
     * @return The file system from the file system component.
     */
    public IEPSFileSystem getFileSystem(String identifier, String systemName);

    /**
     * It returns the file manager component with the specified identifier or
     * the component name defined by the developer.
     * @param identifier An auto-generated unique identifier.
     * @param componentName The name of the file manager component.
     * @return The file manager component.
     */
    public IEPSFileManagerComponent getEPSFileManagerComponent(String identifier, String componentName);

    /**
     * It adds a file manager component to the list of file manager components.
     * @param component A file manager component being added to the list.
     */
    public void addFileManagerComponent(IEPSFileManagerComponent component);

    /**
     * It removes a file manager component to the list of file manager components.
     * @param component A file manager component being removed to the list.
     */
    public void removeFileManagerComponent(IEPSFileManagerComponent component);

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
     * It sets the store property for the file management. It provides the
     * identifiers, store location and the default file manager for the file operation.
     * @param storeProperty The store property for the file management.
     */
    public void setStoreProperty(IStoreProperty storeProperty);

    /**
     * It returns the store property for the file management.
     * @return The store property for the file management.
     */
    public IStoreProperty getStoreProperty();

    /**
     * It searches to find a specific file with the same file name
     * as specified.
     * @param fileName The name of the file to find.
     */
    public IEPSFileComponent searchFileComponent(String fileName, String systemIdentifier);

    /**
     * It loads the file from the path specified.
     * @param urlPath The URL path to the file system
     * @param filenameFilter The filter used to reduce files by the extension of supported types.
     */
    public List<IEPSFileSystemComponent> loadFileSystemComponents(String urlPath, EPSFilenameFilter filenameFilter);

    /**
     * It returns the component size from the store component location.
     * @return The number of components.
     */
    public int getManagerComponentSize();

    /**
     * It returns the component size from the store component location.
     * @return The number of components.
     */
    public int getFileComponentSize();

    /**
     * It returns the component size from the store component location.
     * @return The number of components.
     */
    public int getSystemComponentSize();

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
     * It sets the default component name for all the saved components.
     * @param defaultComponentName The default component name.
     */
    public void setComponentName(String componentName);

    /**
     * It returns the default component name for all the saved components.
     * @return The default component name.
     */
    public String getComponentName();

    /**
     * It returns the component for this file manager.
     * @return The component for this file manager.
     */
    public List<IEPSFileComponent> getFileComponents();

    /**
     * It returns the file component.
     * @param fileComponent The file component.
     */
    public void setFileComponents(List<IEPSFileComponent> fileComponent);

    /**
     * It returns the component for this file manager.
     * @return The component for this file manager.
     */
    public List<IEPSFileSystemComponent> getFileSystemComponents();

    /**
     * It returns the file component.
     * @param fileComponent The file component.
     */
    public void setFileSystemComponents(List<IEPSFileSystemComponent> systemComponents);

    /**
     * It returns the component for this file manager.
     * @return The component for this file manager.
     */
    public List<IEPSFileManagerComponent> getFileManagerComponents();

    /**
     * It returns the file component.
     * @param fileComponent The file component.
     */
    public void setFileManagerComponents(List<IEPSFileManagerComponent> fileComponents);

    /**
     * It sets the identifiers of the components loaded from the store location.
     * @param identifiers A list of component identifiers.
     */
    public void setComponentIdentifiers(List<String> identifiers);

    /**
     *  It returns the identifiers of the components loaded from the store location.
     * @return A list of component identifiers.
     */
    public List<String> getComponentIdentifiers();

    /**
     * It sets the generated identifier for the manager.
     * @param identifier A unique identifier.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns the generated identifier for the manager.
     * @return A unique identifier.
     */
    public String getIdentifier();
}
