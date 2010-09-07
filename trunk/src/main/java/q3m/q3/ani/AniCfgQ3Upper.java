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
 * Quake III player torso animation configuration.
 * 
 * @author nlotz
 */
public class AniCfgQ3Upper extends AniCfgQ3Both {

    /**
     * Sequence index (gesture).
     */
    public static final int GESTURE = 6;

    /**
     * Sequence index (attack).
     */
    public static final int ATTACK = 7;

    /**
     * Sequence index (attack2).
     */
    public static final int ATTACK2 = 8;

    /**
     * Sequence index (drop).
     */
    public static final int DROP = 9;

    /**
     * Sequence index (raise).
     */
    public static final int RAISE = 10;

    /**
     * Sequence index (stand).
     */
    public static final int STAND = 11;

    /**
     * Sequence index (stand2).
     */
    public static final int STAND2 = 12;

    /**
     * Reads a Quake III player torso animation configuration from a stream.
     * 
     * @param in the stream to read from
     * @throws IOException
     */
    public AniCfgQ3Upper(InputStream in) throws IOException {
        super(in, 13);
    }

}
