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

import org.streameps.store.file.component.EPSFileComponent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.streameps.core.util.IDUtil;
import org.streameps.epn.channel.IEventEncryptor;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.store.file.component.IEPSFileComponent;

/**
 *
 * @author Frank Appiah
 */
public class EPSFileSystem implements IEPSFileSystem {

    private String dirPath;
    private String defaultName;
    private transient List<String> identifiers = new ArrayList<String>();
    private String fileExtension;
    private transient File file;
    private SupportedType supportedType = SupportedType.FSC;
    private int fileSize = 0;
    private transient ILogger logger = LoggerUtil.getLogger(EPSFileSystem.class);
    private String identifier;
    private List<IEPSFileComponent> components = new ArrayList<IEPSFileComponent>();
    private transient AtomicInteger ai = new AtomicInteger();
    private IEPSDirectoryStatisitics directoryStatisitics;
    private IEventEncryptor encryptor;

    public EPSFileSystem() {
        directoryStatisitics = new EPSDirectoryStatistics(this);
    }

    public EPSFileSystem(String identifier) {
        this.identifier = identifier;
        directoryStatisitics = new EPSDirectoryStatistics(this);
    }

    public EPSFileSystem(String dirPath, String defaultName) {
        this.dirPath = dirPath;
        this.defaultName = defaultName;
        directoryStatisitics = new EPSDirectoryStatistics(this);
    }

    public EPSFileSystem(String dirPath, String defaultName, String fileExtension) {
        this.dirPath = dirPath;
        this.defaultName = defaultName;
        this.fileExtension = fileExtension;
        directoryStatisitics = new EPSDirectoryStatistics(this);
    }

    public List<IEPSFileComponent> loadFiles(String urlPath) {
        List<IEPSFileComponent> fs = new ArrayList<IEPSFileComponent>();

        if (urlPath != null) {
            file = new File(urlPath);
        } else {
            file = new File(getDirPath() + File.pathSeparator);
        }
        supportedType = SupportedType.FSC;
        if (file.exists()) {
            if (file.isDirectory() && file.canRead()) {
                File[] files = file.listFiles(new EPSFilenameFilter(supportedType));
                fileSize = files.length;
                for (File f : files) {
                    try {
                        readFile(f, f.length(), fs, supportedType);
                    } catch (IOException ex) {
                        logger.error(ex.getMessage());
                    }
                }
            }
        }
        components = fs;
        return fs;
    }

    private void readFile(File file, long size, List<IEPSFileComponent> files, SupportedType supportedType) throws IOException {
//        FileReader reader = new FileReader(file);
//        byte[] data = new byte[(int) size];
//        int counter = 0, d = 0;
//        while ((d = reader.read()) > 0) {
//            data[counter] = (byte) d;
//        }
        String id = IDUtil.getUniqueID(file.getName() + file.lastModified());

        switch (supportedType) {
//            case EPS: {
//                ByteArrayInputStream bis = new ByteArrayInputStream(data);
//                ObjectInputStream ois = new ObjectInputStream(bis);
//                try {
//                    IEPSFileComponent component = (IEPSFileComponent) ois.readObject();
//                    for (String iepsf : component.getEPSFiles().keySet()) {
//                        files.add((F) component.getEPSFiles().get(iepsf));
//                    }
//                } catch (ClassNotFoundException ex) {
//                    logger.error(ex.getMessage());
//                    files.add(null);
//                }
//            }
            case OMP: {
                FileInputStream stream = new FileInputStream(file);
                //ByteArrayInputStream bis = new ByteArrayInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(stream);
                try {
                    IEPSFileComponent component = (IEPSFileComponent) ois.readObject();
                    files.add(component);
                } catch (ClassNotFoundException ex) {
                    logger.error(ex.getMessage());
                }
            }
        }
        identifiers.add(id);
    }

    public IEPSFileComponent loadFile(String filePath) {
        List<IEPSFileComponent> fs = loadFiles(filePath);
        if (fs != null) {
            components.add(fs.get(0));
        }
        return fs.get(0);
    }

    public IEPSFileComponent loadFile(File filePath) {
        List<IEPSFileComponent> comp = new ArrayList<IEPSFileComponent>();
        try {
            readFile(file, file.length(), comp, supportedType);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        return comp.get(0);
    }

    public void deleteFile(String identifier) {
        IEPSFileComponent result = searchFileComponentByIdentifier(identifier);
        identifiers.remove(identifier);
        getFileComponents().remove(result);
        deleteFromStore(null, result);
    }

    public void setDirPath(String filePath) {
        this.dirPath = filePath;
    }

    public String getDirPath() {
        return this.dirPath;
    }

    public List getIdentifiers() {
        return this.identifiers;
    }

    public void saveFile(String identifier, IEPSFileComponent fileComponent) {
        try {
            identifiers.add(identifier);
            components.add(fileComponent);
            ((EPSFileComponent) fileComponent).setSaveLocation(dirPath);
            ((EPSFileComponent) fileComponent).writeExternal(null);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    public void addFileComponent(String identifier, IEPSFileComponent fileComponent) {
        getFileComponents().add(fileComponent);
        identifiers.add(identifier);
    }

    public void deleteFile(IEPSFileComponent fileComponent) {
        deleteFile(fileComponent.getIdentifier());
        deleteFromStore(null, fileComponent);
    }

    public void deleteFromStore(String fileName, IEPSFileComponent component) {
        file = new File(getDirPath() + File.pathSeparator + fileName + "." + supportedType.getType());
        if (!file.exists()) {
            file = new File(getDirPath() + File.pathSeparator + component.getComponentName() + "." + supportedType.getType());
        }
        file.delete();
    }

    public IEPSFileComponent searchFile(String fileName) {
        File dir = new File(dirPath);
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
        for (IEPSFileComponent comp : getFileComponents()) {
            if (comp.getIdentifier().equalsIgnoreCase(identifier)) {
                return comp;
            }
        }
        return null;
    }

    public IEPSFile searchFileByIdentifier(String identifier) {
        for (IEPSFileComponent comp : getFileComponents()) {
            for (String key : comp.getEPSFiles().keySet()) {
                IEPSFile epsFile = comp.getEPSFiles().get(key);
                if (epsFile.getIdentifier().equalsIgnoreCase(identifier)) {
                    return epsFile;
                }
            }
        }
        return null;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public void setFileExtension(String fileExt) {
        this.fileExtension = fileExt;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String getDefaultName() {
        return this.defaultName;
    }

    public void setSupportedType(SupportedType supportedType) {
        this.supportedType = supportedType;
    }

    public SupportedType getSupportedType() {
        return this.supportedType;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        File fscFile = null;
        FileOutputStream fos = null;
        ObjectOutputStream objectStream = null;
        if (out == null) {
            fscFile = new File(getDirPath() + getDefaultName() + ai.incrementAndGet()
                    + "." + SupportedType.FSC.getType());
            fscFile.setExecutable(true);
            fos = new FileOutputStream(fscFile);
            objectStream = new ObjectOutputStream(fos);
        } else {
            objectStream = new ObjectOutputStream((OutputStream) out);
        }
        objectStream.writeObject(this);
        objectStream.close();
        fos.close();
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        Object fsc = in.readObject();
        IEPSFileSystem fileSystem = (IEPSFileSystem) fsc;
        identifier = fileSystem.getIdentifier();
        supportedType = fileSystem.getSupportedType();
        defaultName = fileSystem.getDefaultName();
        dirPath = fileSystem.getDirPath();
        fileSize = fileSystem.getFileSize();

        components = new ArrayList<IEPSFileComponent>();

        for (Object input : fileSystem.getFileComponents()) {
            components.add((IEPSFileComponent) input);
        }
    }

    public List<IEPSFileComponent> getFileComponents() {
        if (components == null) {
            return new ArrayList<IEPSFileComponent>();
        }
        if (components.size() < 0) {
            loadFiles(dirPath);
        }
        return components;
    }

    public void setEPSDirectoryStatistics(IEPSDirectoryStatisitics statisitics) {
        this.directoryStatisitics = statisitics;
    }

    public IEPSDirectoryStatisitics getEPSDirectoryStatistics() {
        return this.directoryStatisitics;
    }

    public void setEncrpytor(IEventEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public IEventEncryptor getEncryptor() {
        return this.encryptor;
    }
}
