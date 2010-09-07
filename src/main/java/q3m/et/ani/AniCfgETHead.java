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
package q3m.et.ani;

import java.io.IOException;
import java.io.InputStream;

import q3m.Q3M;
import q3m.ani.AniCfgAbstr;

/**
 * Enemy Territory head animation configuration.
 * 
 * @author nlotz
 */
public class AniCfgETHead extends AniCfgAbstr {

    /**
     * Sequence index (small eye movements, played all the time).
     */
    public static final int IDLE1 = 0;

    /**
     * Sequence index (big blink).
     */
    public static final int IDLE2 = 1;

    /**
     * Sequence index (look left).
     */
    public static final int IDLE3 = 2;

    /**
     * Sequence index (large head movements).
     */
    public static final int IDLE4 = 3;

    /**
     * Sequence index (look up).
     */
    public static final int IDLE5 = 4;

    /**
     * Sequence index (look left and right with little head movements).
     */
    public static final int IDLE6 = 5;

    /**
     * Sequence index (look right).
     */
    public static final int IDLE7 = 6;

    /**
     * Sequence index (blink).
     */
    public static final int IDLE8 = 7;

    /**
     * Sequence index (head idle in pain, played all the time).
     */
    public static final int DAMAGED_IDLE1 = 8;

    /**
     * Sequence index (not quite dead).
     */
    public static final int DAMAGED_IDLE2 = 9;

    /**
     * Sequence index (no hope left).
     */
    public static final int DAMAGED_IDLE3 = 10;

    /**
     * Sequence index (left).
     */
    public static final int LEFT = 11;

    /**
     * Sequence index (right).
     */
    public static final int RIGHT = 12;

    /**
     * Sequence index (attack).
     */
    public static final int ATTACK = 13;

    /**
     * Sequence index (attack end).
     */
    public static final int ATTACK_END = 14;

    /**
     * Sequence index (pain 1).
     */
    public static final int PAIN_1 = 15;

    /**
     * Sequence index (pain 2).
     */
    public static final int PAIN_2 = 16;

    /**
     * Sequence index (smile).
     */
    public static final int SMILE = 17;

    /**
     * Reads the default ET head animation configuration.
     * 
     * The configuration will be read from the resource path
     * <code>/res/et/animations/human/heads/base.anim</code>.
     * 
     * @throws IOException
     */
    public AniCfgETHead() throws IOException {
        this(Q3M.getResStream("/res/et/animations/human/heads/base.anim"));
    }

    /**
     * Reads an ET head animation configuration from a stream.
     * 
     * @param in the stream to read from
     * @throws IOException
     */
    public AniCfgETHead(InputStream in) throws IOException {
        super(in, 18);
    }

    /* (non-Javadoc)
     * @see q3m.anim.AnimCfg#getFirstIndex()
     */
    public int getFirstIndex() {
        return 0;
    }

    /* (non-Javadoc)
     * @see q3m.anim.AnimCfg#getFpsIndex()
     */
    public int getFpsIndex() {
        return 2;
    }

    /* (non-Javadoc)
     * @see q3m.anim.AnimCfg#getLengthIndex()
     */
    public int getLengthIndex() {
        return 1;
    }

    /* (non-Javadoc)
     * @see q3m.anim.AnimCfg#getLoopingIndex()
     */
    public int getLoopingIndex() {
        return 3;
    }

}
