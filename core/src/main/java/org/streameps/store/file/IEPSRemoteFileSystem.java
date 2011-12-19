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

import java.net.URI;
import java.util.List;

/**
 * Interface for a remote file system.
 * 
 * @author  Frank Appiah
 */
public interface IEPSRemoteFileSystem<F> {

    /**
     * It loads the file from the path specified.
     */
    public List<F> loadURLFiles(URI urlPath);

    /**
     * It load the file from the path specified.
     */
    public F loadURLFile(URI filePath);

    /**
     * It deletes the file component with the specified identifier.
     */
    public void deleteURLFile(String identifier);

    /**
     * It sets the URI file path for the EPS store.
     * @param filePath The file path for this store.
     */
    public void setURLFilePath(URI filePath);

    /**
     * It returns the URI file path for the EPS store.
     * @return The file path for this store.
     */
    public URI getURLFilePath();

    /**
     * It saves the file to a permanent store in the file system.
     * @param identifier A unique identifier.
     * @param ePSFile An EPS file component to store.
     */
    public void saveURLFile(String identifier, F fileComponent);

    /**
     * It deletes the file with the unique identifier.
     * @param identifier A unique identifier.
     */
    public void deleteURLFile(F fileComponent);

    /**
     * It searches to find a specific file with the same file name
     * as specified.
     * @param fileName The name of the file to find.
     */
    public F searchURLFile(String fileName);

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
    public void setDefaultName(String defaultComponentName);

    /**
     * It returns the default name used for all the saved components.
     * @return The default component name.
     */
    public String getDefaultName();

    /**
     * It sets the supported type for the file system.
     * @param supportedType The supported type
     */
    public void setSupportedType(SupportedType supportedType);

    /**
     * It returns the supported type for the file system.
     * @return The supported type
     */
    public SupportedType getSupportedType();
}
