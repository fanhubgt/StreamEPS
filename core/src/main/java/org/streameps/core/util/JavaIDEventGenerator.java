/*
 * ====================================================================
 *  StreamEPS Platform
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
 *  =============================================================================
 */
package org.streameps.core.util;

import java.util.UUID;

/**
 * A unique event generator based on java supplied @see java.util.UUID.
 * The default generator used is the random java-based generator.
 * 
 * @author Frank Appiah
 * @version 0.2.3
 */
public class JavaIDEventGenerator implements UUIDEventGenerator {

    private IDType dType;
    private UUID uuid;
    private Long mostbits, leastbits;
    private byte[] byteValue;

    public JavaIDEventGenerator() {
        dType = IDType.RANDOM;
    }

    public JavaIDEventGenerator(IDType dType, Long mostbits, Long leastbits) {
        this.dType = dType;
        if (dType != IDType.USER_BASED) {
            throw new IllegalArgumentException("Identifier generator needs to be user based.");
        }
        this.mostbits = mostbits;
        this.leastbits = leastbits;
    }

    public JavaIDEventGenerator(IDType dType, byte[] value) {
        this.dType = dType;
        if (dType != IDType.STRING_BASED && dType != IDType.BTYE_BASED) {
            throw new IllegalArgumentException("Identifier generator needs to be string or btye based.");
        }
        this.byteValue = value;
    }

    public void setLeastbits(Long leastbits) {
        this.leastbits = leastbits;
    }

    public void setByteValue(byte[] byteValue) {
        this.byteValue = byteValue;
    }

    public void setMostbits(Long mostbits) {
        this.mostbits = mostbits;
    }

    public void setdType(IDType dType) {
        this.dType = dType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getMostbits() {
        return mostbits;
    }

    public Long getLeastbits() {
        return leastbits;
    }

    public byte[] getByteValue() {
        return byteValue;
    }

    public String UUID() {

        switch (dType) {
            case RANDOM:
                uuid = UUID.randomUUID();
                break;
            case BTYE_BASED:
                uuid = UUID.nameUUIDFromBytes(byteValue);
                break;
            case STRING_BASED:
                uuid = UUID.fromString(new String(byteValue));
                break;
            case USER_BASED:
                uuid = new UUID(mostbits, leastbits);
                break;
            default:
                throw new IllegalArgumentException("Type of unique identifier generator is not supported.");
        }
        return uuid.toString();
    }

}
