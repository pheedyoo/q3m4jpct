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
package q3m.ani;

/**
 * Single-sequence animation configuration.
 * 
 * @author nlotz
 */
public class AniCfgSingle extends AniCfgAbstr {

    /**
     * The default number of frames per second. 
     */
    public static final int DEFAULT_FPS = 15;

    /**
     * Creates a single-sequence animation configuration.
     * 
     * @param numFrames the number of frames
     */
    public AniCfgSingle(int numFrames) {
        this(numFrames, DEFAULT_FPS);
    }

    /**
     * Creates a single-sequence animation configuration.
     * 
     * @param numFrames the number of frames
     * @param fps the number of frames per second
     */
    public AniCfgSingle(int numFrames, int fps) {
        super(new int[][] {}, new String[] { ZERO_SEQUENCE_NAME });
        zeroSequence = new int[] { 0, fps, numFrames, numFrames };
    }

    /* (non-Javadoc)
     * @see q3m.ani.AniCfg#getFirstIndex()
     */
    public int getFirstIndex() {
        return 0;
    }

    /* (non-Javadoc)
     * @see q3m.ani.AniCfg#getFpsIndex()
     */
    public int getFpsIndex() {
        return 1;
    }

    /* (non-Javadoc)
     * @see q3m.ani.AniCfg#getLengthIndex()
     */
    public int getLengthIndex() {
        return 2;
    }

    /* (non-Javadoc)
     * @see q3m.ani.AniCfg#getLoopingIndex()
     */
    public int getLoopingIndex() {
        return 3;
    }

}
