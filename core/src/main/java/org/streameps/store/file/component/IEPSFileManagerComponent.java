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
package org.streameps.store.file.component;

import java.util.List;
import org.streameps.store.file.manager.IEPSFileManager;

/**
 * An EPS file manager for the file component.
 * 
 * @author  Frank Appiah
 */
public interface IEPSFileManagerComponent {

    /**
     * It sets the identifier of the file system component.
     * @param identifier A unique identifier.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns the identifier of the file system component.
     * @return A unique identifier.
     */
    public String getIdentifier();

    /**
     * It returns a list of file manager identifiers.
     * @return A list of file manager identifiers.
     */
    public List<String> getManagerIdentifiers();

    /**
     * It adds the file manager from the component.
     * @param fileManager The file manager to be added.
     */
    public void addFileManager(IEPSFileManager fileManager);

    /**
     * It adds the file manager from the component.
     * @param fileManager The file manager to be added.
     */
    public void addFileManagerIfAbsent(IEPSFileManager fileManager);

    /**
     * It removes a file manager from the component.
     * @param fileManager  The file manager to be removed.
     */
    public void removeFileManager(IEPSFileManager fileManager);

    /**
     * It returns a list of file manager.
     * @return A list of file manager.
     */
    public List<IEPSFileManager> getFileManagers();

    /**
     * It returns the component size from the store component location.
     * @return The number of components.
     */
    public int getComponentSize();

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
}
