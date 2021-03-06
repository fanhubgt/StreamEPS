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
package org.streameps.context.temporal;

/**
 * This specifies how frequently the interval is to repeat, if at all.
 * 
 * @author  Frank Appiah
 */
public enum FrequencyRepeatType {

    SECONDLY("second", 1 * 1000),
    MINUTELY("minute", 60 * 1000),
    HOURLY("hourly", 60 * 60 * 1000),
    DAILY("daily", 60 * 60 * 24 * 1000),
    WEEKLY("weekly", 60 * 60 * 24 * 7 * 1000),
    MONTHLY("monthly", 60 * 60 * 24 * 7 * 4 * 1000),
    YEARLY("yearly", 60 * 60 * 24 * 7 * 4 * 12 * 1000);
    private String name;
    private long timestamp;

    private FrequencyRepeatType(String name, long timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    public static FrequencyRepeatType getRepeatType(String type) {
        for (FrequencyRepeatType repeatType : FrequencyRepeatType.values()) {
            if (repeatType.name.equalsIgnoreCase(type)) {
                return repeatType;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getName() {
        return name;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
