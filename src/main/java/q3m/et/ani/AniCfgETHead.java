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
    public static final int IDLE_1 = 1;

    /**
     * Sequence index (big blink).
     */
    public static final int IDLE_2 = 2;

    /**
     * Sequence index (look left).
     */
    public static final int IDLE_3 = 3;

    /**
     * Sequence index (large head movements).
     */
    public static final int IDLE_4 = 4;

    /**
     * Sequence index (look up).
     */
    public static final int IDLE_5 = 5;

    /**
     * Sequence index (look left and right with little head movements).
     */
    public static final int IDLE_6 = 6;

    /**
     * Sequence index (look right).
     */
    public static final int IDLE_7 = 7;

    /**
     * Sequence index (blink).
     */
    public static final int IDLE_8 = 8;

    /**
     * Sequence index (head idle in pain, played all the time).
     */
    public static final int DAMAGED_IDLE_1 = 9;

    /**
     * Sequence index (not quite dead).
     */
    public static final int DAMAGED_IDLE_2 = 10;

    /**
     * Sequence index (no hope left).
     */
    public static final int DAMAGED_IDLE_3 = 11;

    /**
     * Sequence index (left).
     */
    public static final int LEFT = 12;

    /**
     * Sequence index (right).
     */
    public static final int RIGHT = 13;

    /**
     * Sequence index (attack).
     */
    public static final int ATTACK = 14;

    /**
     * Sequence index (attack end).
     */
    public static final int ATTACK_END = 15;

    /**
     * Sequence index (pain 1).
     */
    public static final int PAIN_1 = 16;

    /**
     * Sequence index (pain 2).
     */
    public static final int PAIN_2 = 17;

    /**
     * Sequence index (smile).
     */
    public static final int SMILE = 18;

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
        super(in, 18, new String[] { ZERO_SEQUENCE_NAME, "IDLE_1", "IDLE_2",
                "IDLE_3", "IDLE_4", "IDLE_5", "IDLE_6", "IDLE_7", "IDLE_8",
                "DAMAGED_IDLE_1", "DAMAGED_IDLE_2", "DAMAGED_IDLE_3", "LEFT",
                "RIGHT", "ATTACK", "ATTACK_END", "PAIN_1", "PAIN_2", "SMILE" });
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
