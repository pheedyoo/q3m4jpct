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

import q3m.q3.Q3;

/**
 * MD3 Constants.
 * 
 * @author nlotz
 */
public interface MD3 extends Q3 {

    /**
     * Magic number (little endian 'IDP3').
     */
    public static final int IDENT = 0x33504449;

    /**
     * Limit (max number of characters in frame names).
     */
    public static final int MAX_FRAME_NAME = 16;

    /**
     * Limit (max number of frames per model).
     */
    public static final int MAX_FRAMES = 1024;

    /**
     * Limit (max number of LOD models).
     */
    public static final int MAX_LODS = 3;

    /**
     * Limit (max number of shaders per surface).
     */
    public static final int MAX_SHADERS = 256;

    /**
     * Limit (max number of surfaces per model).
     */
    public static final int MAX_SURFACES = 32;

    /**
     * Limit (max number of tags per frame).
     */
    public static final int MAX_TAGS = 16;

    /**
     * Limit (max number of triangles per surface).
     */
    public static final int MAX_TRIANGLES = 8192;

    /**
     * Limit (max number of vertices per surface).
     */
    public static final int MAX_VERTS = 4096;

    /**
     * Magic number (15).
     */
    public static final int VERSION = 15;

    /**
     * Constant (vertex scale factor).
     */
    public static final float XYZ_SCALE = (1f / 64f);

}
