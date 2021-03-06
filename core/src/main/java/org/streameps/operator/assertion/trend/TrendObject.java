/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  Copyright 2011.
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
package org.streameps.operator.assertion.trend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.streameps.core.schema.ISchemaProperty;
import org.streameps.core.util.JavaIDEventGenerator;
import org.streameps.core.util.UUIDEventGenerator;

/**
 *
 * @author Frank Appiah
 */
public class TrendObject<E> implements ITrendObject<E> {

    private String timestamp = null;
    private String attribute = null;
    private List<ISchemaProperty<E>> trendList;

    public TrendObject() {
        trendList=new ArrayList<ISchemaProperty<E>>();
    }

    public TrendObject(String timestamp, String attribute, List<ISchemaProperty<E>> trendList) {
        this.timestamp = timestamp;
        this.attribute = attribute;
        this.trendList = trendList;
    }

    public void setObjectId(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getObjectId() {
        UUIDEventGenerator generator = new JavaIDEventGenerator();
        String stamp;
        if (timestamp == null) {
            stamp = new Date().toString();
        } else {
            stamp = timestamp;
        }
        generator.setByteValue(stamp.getBytes());
        return generator.UUID();
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public List<ISchemaProperty<E>> getTrendList() {
        return this.trendList;
    }

    public void setTrendList(List<ISchemaProperty<E>> trendList) {
        this.trendList = trendList;
    }
}
