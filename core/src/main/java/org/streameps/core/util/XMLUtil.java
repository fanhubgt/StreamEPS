/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2012.
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
package org.streameps.core.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;

/**
 *
 * @author Frank Appiah
 */
public class XMLUtil {

    private static ILogger logger = LoggerUtil.getLogger(XMLUtil.class);

    public static byte[] encode(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);

        XMLEncoder encoder = new XMLEncoder(bos);
        encoder.writeObject(object);
        encoder.flush();
        return baos.toByteArray();
    }

    public static XMLEncoder encode(Object value, String filename) {

        FileOutputStream fos = null;
        XMLEncoder encoder = null;
        try {
            try {
                fos = new FileOutputStream(filename);
            } catch (FileNotFoundException ex) {
                logger.error(ex.getMessage());
            }
            BufferedOutputStream stream = new BufferedOutputStream(fos);
            encoder = new XMLEncoder(stream);
            encoder.writeObject(value);
        } finally {
            try {
                encoder.flush();
                fos.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }
        return encoder;
    }

    public static Object decode(byte[] array) {
        ByteArrayInputStream baos = null;
        XMLDecoder decoder = null;

        try {
            baos = new ByteArrayInputStream(array);
            BufferedInputStream stream = new BufferedInputStream(baos);
            decoder = new XMLDecoder(stream);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return decoder.readObject();
    }

    public static Object decode(String filename) {
        FileInputStream fos = null;
        XMLDecoder decoder = null;
        try {
            try {
                fos = new FileInputStream(filename);
            } catch (FileNotFoundException ex) {
                logger.error(ex.getMessage());
            }
            BufferedInputStream stream = new BufferedInputStream(fos);
            decoder = new XMLDecoder(stream);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }
        return decoder.readObject();
    }

    public static String encode(Object object, boolean... op) {
        XMLEncoder encoder = encode(object, "eps.txt");
        FileReader reader;
        File fis;
        String value = null;
        try {
            fis = new File("eps.txt");
            reader = new FileReader(fis);
            char[] data = new char[(int) fis.length()];
            try {
                reader.read(data);
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
            value = new String(data);
            fis.deleteOnExit();
        } catch (FileNotFoundException ex) {
            logger.error(ex.getMessage());
        }
        return value;
    }

    
}
