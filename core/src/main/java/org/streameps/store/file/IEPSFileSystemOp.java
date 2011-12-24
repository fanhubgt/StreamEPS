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

import java.io.File;
import java.io.ObjectOutputStream;
import java.util.List;
import org.streameps.epn.channel.IEventEncryptor;
import org.streameps.store.file.component.IEPSFileComponent;

/**
 * The interface for the file system type operation management.
 * 
 * @author  Frank Appiah
 */
public interface IEPSFileSystemOp {

    /**
     * It sets the EPS directory statistics.
     * @param statisitics The statistics of the file system directory.
     */
    public void setEPSDirectoryStatistics(IEPSDirectoryStatisitics statisitics);

    /**
     * It returns the EPS directory statistics.
     * @return The statistics of the file system directory.
     */
    public IEPSDirectoryStatisitics getEPSDirectoryStatistics();

    /**
     * It searches the file component by the identifier specified.
     * @param identifier The identifier for the component.
     * @return The file component to search from the file system.
     */
    public IEPSFileComponent searchFileComponentByIdentifier(String identifier);

    /**
     * It saves the file to a permanent store in the file system.
     * @param identifier A unique identifier.
     * @param ePSFile An EPS file component to store.
     */
    public void saveFile(String identifier, IEPSFileComponent fileComponent);

    /**
     * It saves the file to a permanent store in the file system.
     * @param identifier A unique identifier.
     * @param ePSFile An EPS file component to store.
     */
    public void addFileComponent(String identifier, IEPSFileComponent fileComponent);

    /**
     * It deletes the file with the unique identifier.
     * @param identifier A unique identifier.
     */
    public void deleteFile(IEPSFileComponent fileComponent);

    /**
     * It deletes the file component from the default store location.
     * @param fileName The file name of the component to delete.
     * @param component The file component to delete from store.
     */
    public void deleteFromStore(String fileName, IEPSFileComponent component);

    /**
     * It searches to find a specific file with the same file name
     * as specified.
     * @param fileName The name of the file to find.
     */
    public IEPSFileComponent searchFile(String fileName);

    /**
     * It searches to find a specific file with the same file identifier
     * as specified.
     * @param fileName The name of the file to find.
     */
    public IEPSFile searchFileByIdentifier(String identifier);

    /**
     * It load the file from the path specified.
     */
    public IEPSFileComponent loadFile(String filePath);

    /**
     * It loads the file from the path specified.
     */
    public List<IEPSFileComponent> loadFiles(String filePath, File file);

    /**
     * It loads the file from the path specified.
     * @param filePath
     * @return The file component from the location
     */
    public IEPSFileComponent loadFile(File filePath);

    /**
     * It deletes the file component with the specified identifier.
     */
    public void deleteFile(String identifier);

    /**
     * It sets the encryption scheme for the file system.
     * @param encryptor  An encryption for the file system.
     */
    public void setEncrpytor(IEventEncryptor encryptor);

    /**
     * It returns the encryption scheme for the file system
     * which is left to be implemented by developer.
     *
     * @return An encryption for the file system.
     */
    public IEventEncryptor getEncryptor();

    /**
     * The list of file component identifiers.
     * @return The component identifiers.
     */
    public List<String> getIdentifiers();

    /**
     * It sets the file system for the file management.
     * @param fileSystem The file system for the file management.
     */
    public void setFileSystem(IEPSFileSystem fileSystem);

    /**
     * It returns the file system for the file management.
     * @return The file system for the file management.
     */
    public IEPSFileSystem getFileSystem();

    /**
     * It saves to a permanent store.
     * @param fileSystem The file system for the file management.
     */
    public void saveFileSystem(IEPSFileSystem fileSystem, ObjectOutputStream outputStream);

}
