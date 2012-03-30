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
package org.streameps.core;

/**
 * Interface for target reference specification.
 * 
 * @author  Frank Appiah
 */
public interface ITargetRefSpec {

    /**
     * It sets the identifier for the target spec.
     * @param identifier A unique identifier;
     */
    public void setIdentifier(String identifier);
    /**
     * It returns the identifier for the target spec.
     * @return A unique identifier;
     */
    public String getIdentifier();

    /**
     * It sets the class spec for the target reference.
     * @param target class name of the target.
     */
    public void setClazzSpec(IClassSpec target);

    /**
     * It returns the class spec of the target reference.
     * @return class name.
     */
    public IClassSpec getClazzSpec();

    /**
     * It returns the method to invoke on the target class.
     * @return method name
     */
    public IMethodSpec getMethodSpec();

    /**
     * It sets the method of the class.
     * 
     * @param method method of the class.
     */
    public void setMethodSpec(IMethodSpec methodSpec);

    /**
     * It sets the parameter values for the method.
     * @param values array of parameter values.
     */
    public void setParamValues(Object[] values);

    /**
     * It returns the parameter values for the method.
     *
     * @return array of parameter values.
     */
    public Object[] getParamValues();

    /**
     * The client could be a running GUI or console application.
     * @param clientTerminal Th client terminal.
     */
    public void setClientTerminal(Object clientTerminal);

    /**
     * It returns the client terminal.
     * @return Th client terminal.
     */
    public Object getClientTerminal();
}
