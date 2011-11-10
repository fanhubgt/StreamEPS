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
package org.streameps.core.util;

import org.streameps.core.util.JavaIDEventGenerator.IDType;

/**
 * A utility for unique identifier generator.
 * 
 * @author Frank Appiah
 */
public class IDUtil {

    /**
     * It generates a unique identifier from a template string value
     * from the UUIDEventGenerator implemented from Java.
     * @param template template for the generator.
     * @return A unique identifier.
     */
    public static String getUniqueID(String template) {
        UUIDEventGenerator generator = new JavaIDEventGenerator();
        generator.setByteValue(template.getBytes());
        return generator.UUID();
    }

    /**
     * It generates a unique identifier from a most bits and least bits long value.
     *
     * @param mostbits Most bits
     * @param leastbits Least bits
     * @return A unique identifier.
     */
    public static String getUniqueID(Long mostbits, Long leastbits) {
        UUIDEventGenerator generator = new JavaIDEventGenerator(IDType.USER_BASED, mostbits, leastbits);
        return generator.UUID();
    }

    /**
     * It generates a unique random identifier from a template.
     * 
     * @return A unique random identifier.
     */
    public static String getUniqueIDRand() {
        UUIDEventGenerator generator = new JavaIDEventGenerator(IDType.RANDOM, null);
        return generator.UUID();
    }

    /**
     * It generates an immutable universally unique identifier unique from a template.
     *
     * @param value An array of byte
     * @return A unique random identifier.
     */
    public static String getUniqueUUID(byte[] value){
        UUIDEventGenerator generator = new JavaIDEventGenerator(IDType.BTYE_BASED, null);
        return generator.UUID();
    }
    
}
