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

/**
 * MD3 Tag.
 * 
 * @author nlotz
 */
public class MD3Tag implements MD3 {

    /**
     * Name.
     */
    public String name = null;

    /**
     * NUM_FRAMES * translation vector.
     */
    public float[][] origin = null;

    /**
     * NUM_FRAMES * 3x3 rotation matrix.
     */
    public float[][] axis = null;

    /**
     * Constructs an MD3 tag.
     * 
     * @param numFrames the number of animation frames
     */
    public MD3Tag(int numFrames) {
        name = null;
        origin = new float[numFrames][3];
        axis = new float[numFrames][9];
    }

}
