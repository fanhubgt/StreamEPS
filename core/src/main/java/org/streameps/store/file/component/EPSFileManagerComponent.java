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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.streameps.store.file.manager.IEPSFileManager;

/**
 *
 * @author Frank Appiah
 */
public class EPSFileManagerComponent implements IEPSFileManagerComponent {

    private String componentName = "eps-comp";
    private int componentSize = 0;
    private List<String> identifiers = new ArrayList<String>();
    private Map<String, IEPSFileManager> fileManagerMap;
    private List<IEPSFileManager> fileManagerList;
    private String identifier;

    public EPSFileManagerComponent(String identifier) {
        this.identifier = identifier;
        fileManagerList = new ArrayList<IEPSFileManager>();
        fileManagerMap = new TreeMap<String, IEPSFileManager>();
    }

    public EPSFileManagerComponent() {
        fileManagerList = new ArrayList<IEPSFileManager>();
        fileManagerMap = new TreeMap<String, IEPSFileManager>();
    }

    public int getComponentSize() {
        return this.componentSize;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void addFileManager(IEPSFileManager fileManager) {
        this.fileManagerList.add(fileManager);
    }

    public void removeFileManager(IEPSFileManager fileManager) {
        fileManagerList.remove(fileManager);
        fileManagerMap.remove(fileManager.getIdentifier());
    }

    public List<IEPSFileManager> getFileManagers() {
        return this.fileManagerList;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentName() {
        return this.componentName;
    }

    public List<String> getManagerIdentifiers() {
        return this.identifiers;
    }

    public void addFileManagerIfAbsent(IEPSFileManager fileManager) {
        if (fileManagerMap.containsKey(fileManager.getIdentifier())) {
            removeFileManager(fileManager);
        }
        addFileManager(fileManager); 
    }
    
}
