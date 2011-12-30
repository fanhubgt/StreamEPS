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
package org.streameps.test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.streameps.core.util.IDUtil;
import org.streameps.store.IStoreProperty;
import org.streameps.store.StoreProperty;
import org.streameps.store.file.EPSFile;
import org.streameps.store.file.FileEPStore;
import org.streameps.store.file.IEPSFile;
import org.streameps.store.file.IEPSFileSystem;
import org.streameps.store.file.SupportedType;
import org.streameps.store.file.component.IEPSFileComponent;
import org.streameps.store.file.component.IEPSFileManagerComponent;
import org.streameps.store.file.component.IEPSFileSystemComponent;
import org.streameps.store.file.manager.EPSComponentManager;
import org.streameps.store.file.manager.IEPSComponentManager;
import org.streameps.store.file.manager.IEPSFileManager;

/**
 *
 * @author Development Team
 */
public class FileStoreTest extends TestCase {

    private IEPSComponentManager comp;
    private IStoreProperty property;
    private String location = "C:/store";

    public FileStoreTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        comp = new EPSComponentManager(IDUtil.getUniqueID(new Date().toString()), "comp");
        property = new StoreProperty(null, "comp", IEPSFileSystem.DEFAULT_SYSTEM_ID, location);
        property.setSupportType(SupportedType.FSC);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testStoreSave() {

        IEPSFileManagerComponent managerComponent = comp.getEPSFileManagerComponent(IEPSComponentManager.DEFAULT_FILE_MANAGER_COMPONENT, null);

        IEPSFileManager manager = managerComponent.getEPSFileManager(IEPSFileManager.DEFAULT_FILE_MANAGER);
        manager.setComponentManager(comp);

        manager.setStoreProperty(property);
        FileEPStore feps = new FileEPStore(comp, manager);

        IEPSFile<String> file = new EPSFile<String>(manager, property);
        file.setData("oewpwwwwweeiiiiiiiiiiiiiiiiiii934202932klao;439ai200902191kkkls");
        file.setIdentifier(IDUtil.getUniqueID(new Date().toString()));

        //un-comment to save file.
        //make sure you change the dir location.
         //manager.saveEPSFile(property.getComponentIdentifier(), property.getSystemIdentifier(), file);

    }

    public void testloadFile() {
        try {
            IEPSFileSystemComponent systemComponent = comp.getEPSFileSystemComponent(IEPSComponentManager.DEFAULT_FILE_SYSTEM_COMPONENT, null);
            IEPSFileSystem fileSystem = systemComponent.getFileSystem(IEPSFileSystem.DEFAULT_SYSTEM_ID);
            fileSystem.setDirPath(property.getPersistLocation());

            IEPSFileComponent component = fileSystem.getFileSystemOptor().loadFile(property.getPersistLocation());

            assertNotNull(component);
            
            Map<String, IEPSFile> map = component.getEPSFiles();
            
           // assertEquals(1, map.size());
            
        } catch (Exception ex) {
            Logger.getLogger(FileStoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void testloadFiles() {
        try {
            IEPSFileSystemComponent systemComponent = comp.getEPSFileSystemComponent(IEPSComponentManager.DEFAULT_FILE_SYSTEM_COMPONENT, null);
            IEPSFileSystem fileSystem = systemComponent.getFileSystem(IEPSFileSystem.DEFAULT_SYSTEM_ID);
            fileSystem.setDirPath(property.getPersistLocation());

            List<IEPSFileComponent> components = fileSystem.getFileSystemOptor().loadFiles(property.getPersistLocation(), null);
            
            //assertEquals(2, components.size());
        } catch (Exception ex) {
            Logger.getLogger(FileStoreTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
