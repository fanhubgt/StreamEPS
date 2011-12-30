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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.streameps.core.util.IDUtil;
import org.streameps.epn.channel.IEventEncryptor;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.store.file.component.EPSFileComponent;
import org.streameps.store.file.component.IEPSFileComponent;

/**
 *
 * @author Frank Appiah
 */
public class EPSFileSystemOp implements IEPSFileSystemOp {

    private ILogger logger = LoggerUtil.getLogger(EPSFileSystem.class);
    private AtomicInteger ai = new AtomicInteger();
    private IEPSDirectoryStatisitics directoryStatisitics;
    private IEventEncryptor encryptor;
    private List<IEPSFileComponent> components = new ArrayList<IEPSFileComponent>();
    private File file;
    private IEPSFileSystem fileSystem;
    private SupportedType supportedType = SupportedType.FSC;
    private int fileSize;
    private List<String> identifiers = new ArrayList<String>();

    public EPSFileSystemOp() {
        this.directoryStatisitics = new EPSDirectoryStatistics();
    }

    public EPSFileSystemOp(IEPSFileSystem fileSystem) {
        this.fileSystem = fileSystem;
        this.directoryStatisitics = new EPSDirectoryStatistics(fileSystem);
    }

    public List<IEPSFileComponent> loadFiles(String urlPath, File pathFile) {
        List<IEPSFileComponent> resultComponents = new ArrayList<IEPSFileComponent>();

        if (urlPath != null) {
            file = new File(urlPath + File.separator);
        } else if (file == null) {
            file = new File(fileSystem.getDirPath() + File.separator);
        } else {
            file = pathFile;
        }
        supportedType = SupportedType.FSC;
        if (file.exists()) {
            if (file.isDirectory() && file.canRead()) {
                File[] files = file.listFiles(new EPSFilenameFilter(supportedType));
                fileSize = files.length;
                for (File f : files) {
                    try {
                        readFile(f, f.length(), resultComponents, supportedType);
                    } catch (IOException ex) {
                        logger.error(ex.getMessage());
                    }
                }
            } else {
                try {
                    readFile(file, fileSize, resultComponents, supportedType);
                } catch (IOException ex) {
                    logger.error(ex.getMessage());
                }
            }
        }
        return resultComponents;
    }

    private void readFile(File file, long size, List<IEPSFileComponent> fileComps, SupportedType supportedType) throws IOException {
//        FileReader reader = new FileReader(file);
//        byte[] data = new byte[(int) size];
//        int counter = 0, d = 0;
//        while ((d = reader.read()) > 0) {
//            data[counter] = (byte) d;
//        }
        String id = IDUtil.getUniqueID(file.getName() + file.lastModified());

        switch (supportedType) {
            case FSC: {
                FileInputStream stream = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(stream);
                try {
                    Object obj = ois.readObject();
                    IEPSFileSystem system = (IEPSFileSystem) obj;
                    fileSystem = system;
                    for (Object fileComp : fileSystem.getFileComponents()) {
                        fileComps.add((IEPSFileComponent) fileComp);
                        components.add((IEPSFileComponent) fileComp);
                    }
                } catch (ClassNotFoundException ex) {
                    logger.error(ex.getMessage());
                }
            }
            break;
            case OMP: {
                FileInputStream stream = new FileInputStream(file);
                //ByteArrayInputStream bis = new ByteArrayInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(stream);
                try {
                    IEPSFileComponent component = (IEPSFileComponent) ois.readObject();
                    fileComps.add(component);
                } catch (ClassNotFoundException ex) {
                    logger.error(ex.getMessage());
                }
            }
            break;
        }
        identifiers.add(id);
    }

    public IEPSFileComponent loadFile(String filePath) {
        List<IEPSFileComponent> fs = loadFiles(filePath, null);
        return fs.get(0);
    }

    public IEPSFileComponent loadFile(File filePath) {
        List<IEPSFileComponent> comp = new ArrayList<IEPSFileComponent>();
        comp = loadFiles(null, file);
        return comp.get(0);
    }

    public void deleteFile(String identifier) {
        IEPSFileComponent result = searchFileComponentByIdentifier(identifier);
        identifiers.remove(identifier);
        fileSystem.getFileComponents().remove(result);
        deleteFromStore(null, result);
    }

    public void saveFile(String identifier, IEPSFileComponent fileComponent) {
        try {
            identifiers.add(identifier);
            components.add(fileComponent);
            ((EPSFileComponent) fileComponent).setSaveLocation(fileSystem.getDirPath());

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public void addFileComponent(String identifier, IEPSFileComponent fileComponent) {
        fileSystem.getFileComponents().add(fileComponent);
        identifiers.add(identifier);
    }

    public void deleteFile(IEPSFileComponent fileComponent) {
        deleteFile(fileComponent.getIdentifier());
        deleteFromStore(null, fileComponent);
    }

    public void deleteFromStore(String fileName, IEPSFileComponent component) {
        file = new File(fileSystem.getDirPath() + File.pathSeparator + fileName + "." + supportedType.getType());
        if (!file.exists()) {
            file = new File(fileSystem.getDirPath() + File.pathSeparator + component.getComponentName() + "." + supportedType.getType());
        }
        file.delete();
    }

    public IEPSFileComponent searchFile(String fileName) {
        File dir = new File(fileSystem.getDirPath());
        List<IEPSFileComponent> result = new ArrayList<IEPSFileComponent>();
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles(new EPSFilenameFilter(supportedType));
            if (files != null) {
                for (File fc : files) {
                    if (fc.getName().equalsIgnoreCase(fileName)) {
                        try {
                            readFile(fc, fc.length(), result, supportedType);
                        } catch (IOException ex) {
                            logger.error(ex.getMessage());
                        }
                    }
                }
            }
        }
        return result.get(0);
    }

    public IEPSFileComponent searchFileComponentByIdentifier(String identifier) {
        for (Object c : fileSystem.getFileComponents()) {
            IEPSFileComponent comp = (IEPSFileComponent) c;
            if (comp.getIdentifier().equalsIgnoreCase(identifier)) {
                return comp;
            }
        }
        return null;
    }

    public IEPSFile searchFileByIdentifier(String identifier) {
        for (Object c : fileSystem.getFileComponents()) {
            IEPSFileComponent comp = (IEPSFileComponent) c;
            for (String key : comp.getEPSFiles().keySet()) {
                IEPSFile epsFile = comp.getEPSFiles().get(key);
                if (epsFile.getIdentifier().equalsIgnoreCase(identifier)) {
                    return epsFile;
                }
            }
        }
        return null;
    }

    public void setEPSDirectoryStatistics(IEPSDirectoryStatisitics statisitics) {
        this.directoryStatisitics = statisitics;
    }

    public IEPSDirectoryStatisitics getEPSDirectoryStatistics() {
        this.directoryStatisitics.setFileSystem(fileSystem);
        return this.directoryStatisitics;
    }

    public void setEncrpytor(IEventEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public IEventEncryptor getEncryptor() {
        return this.encryptor;
    }

    public void setFileSystem(IEPSFileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public IEPSFileSystem getFileSystem() {
        return fileSystem;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    public void saveFileSystem(IEPSFileSystem fileSystem, ObjectOutputStream outputStream) {
        File fscFile = null;
        FileOutputStream fos = null;
        ObjectOutputStream objectStream = null;
        try {
            if (outputStream == null) {
                fscFile = new File(fileSystem.getDirPath()
                        + File.separator
                        + fileSystem.getDefaultName()
                        + "-" + ai.incrementAndGet()
                        + "."
                        + SupportedType.FSC.getType());
                fscFile.setExecutable(true);
                fscFile.setReadOnly();
                fos = new FileOutputStream(fscFile);
                objectStream = new ObjectOutputStream(fos);
            } else {
                objectStream = (ObjectOutputStream) outputStream;
            }
            objectStream.writeObject(fileSystem);
            objectStream.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    logger.error(ex.getMessage());
                }
            }
        }
    }
    
}
