/*
 * The MIT License
 *
 * Copyright (c) 2010 Nikolas Lotz <nikolas.lotz@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package q3m.md3;

import java.io.IOException;

import q3m.q3.Q3Stream;

/**
 * MD3 Frame.
 * 
 * @author nlotz
 */
public class MD3Frame implements MD3 {

    /**
     * Bounding box coordinates.
     */
    public float[][] bounds = null;

    /**
     * Local origin coordinates.
     */
    public float[] localOrigin = null;

    /**
     * Bounding sphere radius.
     */
    public float radius = 0f;

    /**
     * Reads an MD3 frame from a stream.
     * 
     * @param stream the stream to read from
     * @throws IOException
     */
    public MD3Frame(Q3Stream stream) throws IOException {
        bounds = stream.readFloats(2, 3);
        localOrigin = stream.readFloats(3);
        radius = stream.readFloat();
        /* String name = */stream.readString(MAX_FRAME_NAME);
    }

}
