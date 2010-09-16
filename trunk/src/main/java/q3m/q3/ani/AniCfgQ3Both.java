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

import q3m.Q3M;
import q3m.ani.AniCfgAbstr;

/**
 * Quake III abstract player animation configuration.
 * 
 * @author nlotz
 */
public abstract class AniCfgQ3Both extends AniCfgAbstr {

    /**
     * Sequence index (death 1).
     */
    public static final int DEATH_1 = 1;

    /**
     * Sequence index (dead 1).
     */
    public static final int DEAD_1 = 2;

    /**
     * Sequence index (death 2).
     */
    public static final int DEATH_2 = 3;

    /**
     * Sequence index (dead 2).
     */
    public static final int DEAD_2 = 4;

    /**
     * Sequence index (death 3).
     */
    public static final int DEATH_3 = 5;

    /**
     * Sequence index (dead 3).
     */
    public static final int DEAD_3 = 6;
    
    /**
     * Reads a Quake III player animation configuration from a resource path.
     * 
     * @param path the resource path
     * @param rows the number of sequences to read
     * @param sequences the sequence names
     * @throws IOException
     */
    protected AniCfgQ3Both(String path, int rows, String[] sequences) throws IOException {
        super(Q3M.getResStream(path), rows, sequences);
    }

    /**
     * Reads a Quake III player animation configuration from a stream.
     * 
     * @param in the stream to read from
     * @param rows the number of sequences to read
     * @param sequences the sequence names
     * @throws IOException
     */
    protected AniCfgQ3Both(InputStream in, int rows, String[] sequences) throws IOException {
        super(in, rows, sequences);
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
        return 3;
    }

    /* (non-Javadoc)
     * @see q3m.ani.AniCfg#getLengthIndex()
     */
    public int getLengthIndex() {
        return 1;
    }

    /* (non-Javadoc)
     * @see q3m.ani.AniCfg#getLoopingIndex()
     */
    public int getLoopingIndex() {
        return 2;
    }

}
