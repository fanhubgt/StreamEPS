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

import java.io.Externalizable;
import java.io.Serializable;
import java.util.List;
import org.streameps.store.file.IEPSFileSystem;

/**
 * An interface for the file system component.
 * 
 * @author  Frank Appiah
 */
public interface IEPSFileSystemComponent extends Serializable, Externalizable, Comparable<IEPSFileSystemComponent> {

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
     * It adds an EPS file system from the component.
     * @param systemComponent  An EPS file system component.
     */
    public void addEPSFileSystem(IEPSFileSystem systemComponent);

    /**
     * It adds the file system to this component if it is absent.
     * @param systemComponent The file system to be added to the list.
     */
    public void addEPSFileSystemIfAbsent(IEPSFileSystem systemComponent);

    public void updateEPSFileSystem(IEPSFileSystem systemComponent);

    /**
     * It removes the file system from the system component.
     * @param systemComponent An EPS file system component.
     */
    public void removeEPSFileSystem(IEPSFileSystem systemComponent);

    /**
     * It returns a list of file systems in this component.
     * @return A list of file systems.
     */
    public List<IEPSFileSystem> getFileSystems();

    /**
     * It returns a list of file systems in this component.
     * @return A list of file systems.
     */
    public void setFileSystems(List<IEPSFileSystem> systems);

    /**
     * It sets the persist location of the file system component.
     * @param locationPath The path to the location of the saved file system.
     */
    public void setSaveLocation(String locationPath);

    /**
     * It returns the persist location of the file system component.
     * @return The path to the location of the saved file system.
     */
    public String getSaveLocation();

    /**
     * It sets the file system component name.
     * @param name The name of the file system component.
     */
    public void setFileSystemComponentName(String name);

    /**
     * It returns the file system component name.
     * @return The name of the file system component.
     */
    public String getFileSystemComponentName();

    public IEPSFileSystem getFileSystem(String identifier);
}
