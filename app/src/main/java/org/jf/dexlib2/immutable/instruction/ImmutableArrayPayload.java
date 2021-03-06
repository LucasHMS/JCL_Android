/*
 * Copyright 2012, Google Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jf.dexlib2.immutable.instruction;

import com.google.common.collect.ImmutableList;
import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
import org.jf.util.ImmutableUtils;



import java.util.List;

public class ImmutableArrayPayload extends ImmutableInstruction implements ArrayPayload {
    public static final Opcode OPCODE = Opcode.ARRAY_PAYLOAD;
    private static final long serialVersionUID = -7491469279415331616L;

    protected final int elementWidth;
     protected final ImmutableList<Number> arrayElements;

    public ImmutableArrayPayload(int elementWidth,
                                  List<Number> arrayElements) {
        super(OPCODE);
        this.elementWidth = elementWidth;
        this.arrayElements = arrayElements==null ? ImmutableList.<Number>of() : ImmutableList.copyOf(arrayElements);
    }

    public ImmutableArrayPayload(int elementWidth,
                                  ImmutableList<Number> arrayElements) {
        super(OPCODE);
        //TODO: need to ensure this is a valid width (1, 2, 4, 8)
        this.elementWidth = elementWidth;
        //TODO: need to validate the elements fit within the width
        this.arrayElements = ImmutableUtils.nullToEmptyList(arrayElements);
    }


    public static ImmutableArrayPayload of(ArrayPayload instruction) {
        if (instruction instanceof ImmutableArrayPayload) {
            return (ImmutableArrayPayload)instruction;
        }
        return new ImmutableArrayPayload(
                instruction.getElementWidth(),
                instruction.getArrayElements());
    }

    @Override public int getElementWidth() { return elementWidth; }
     @Override public List<Number> getArrayElements() { return arrayElements; }

    @Override public int getCodeUnits() { return 4 + (elementWidth * arrayElements.size() + 1) / 2; }
    @Override public Format getFormat() { return OPCODE.format; }
}
