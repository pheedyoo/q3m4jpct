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
package q3m.q3.ani;

import java.io.IOException;
import java.io.InputStream;

/**
 * Quake III player legs animation configuration.
 * 
 * @author nlotz
 */
public class AniCfgQ3Lower extends AniCfgQ3Both {

    /**
     * Sequence index (walk crouched).
     */
    public static final int WALKCR = 6;

    /**
     * Sequence index (walk).
     */
    public static final int WALK = 7;

    /**
     * Sequence index (run).
     */
    public static final int RUN = 8;

    /**
     * Sequence index (back).
     */
    public static final int BACK = 9;

    /**
     * Sequence index (swim).
     */
    public static final int SWIM = 10;

    /**
     * Sequence index (jump).
     */
    public static final int JUMP = 11;

    /**
     * Sequence index (land).
     */
    public static final int LAND = 12;

    /**
     * Sequence index (jumpb).
     */
    public static final int JUMPB = 13;

    /**
     * Sequence index (landb).
     */
    public static final int LANDB = 14;

    /**
     * Sequence index (idle).
     */
    public static final int IDLE = 15;

    /**
     * Sequence index (idle crouched).
     */
    public static final int IDLECR = 16;

    /**
     * Sequence index (turn).
     */
    public static final int TURN = 17;

    /**
     * Reads a Quake III player legs animation configuration from a stream.
     * 
     * @param in the stream to read from
     * @throws IOException
     */
    public AniCfgQ3Lower(InputStream in) throws IOException {
        super(in, 25);
        int[][] v = new int[18][4];
        System.arraycopy(values, 0, v, 0, 6);
        System.arraycopy(values, 13, v, 6, 12);
        int offset = values[13][0] - values[6][0];
        for (int i = 6; i < v.length; i++) {
            v[i][0] -= offset;
        }
        values = v;
    }

}
