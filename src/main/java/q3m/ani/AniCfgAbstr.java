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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;

import q3m.Q3M;

/**
 * Abstract baseclass for animation configuration.
 * 
 * @author nlotz
 */
public abstract class AniCfgAbstr implements AniCfg {
    
    /**
     * Name of sequence #0.
     */
    public static final String ZERO_SEQUENCE_NAME = "jPCT sequence #0";

    /**
     * Sequence names.
     */
    protected String[] sequences;

    /**
     * Configuration values.
     */
    protected int[][] values;

    /**
     * Values for animation sequence '0'.
     */
    protected int[] zeroSequence;

    /**
     * Creates an animation configuration from configuration values.
     * 
     * @param values the configuration values
     * @param sequences the sequence names
     */
    public AniCfgAbstr(int[][] values, String[] sequences) {
        this.values = values;
        this.sequences = sequences;
        zeroSequence = null;
    }

    /**
     * Reads an animation configuration from resource path.
     * 
     * @param path the resource path
     * @param rows the number of animation sequences to read
     * @param sequences the sequence names
     * @throws IOException
     */
    public AniCfgAbstr(String path, int rows, String[] sequences)
            throws IOException {
        this(Q3M.getResStream(path), rows, sequences);
    }

    /**
     * Reads an animation configuration from a stream.
     * 
     * @param in the stream to read from
     * @param rows the number of animation sequences to read
     * @param sequences the sequence names
     * @throws IOException
     */
    public AniCfgAbstr(InputStream in, int rows, String[] sequences)
            throws IOException {
        Q3M.debug("reading " + rows + " animation sequences");
        this.sequences = sequences;
        values = new int[rows][4];
        zeroSequence = null;
        int row = 0;
        try {
            String line = null;
            Reader r = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(r);
            while ((null != (line = br.readLine())) && (row < rows)) {

                line = line.trim();
                if ((line.length() < 1) || (line.startsWith("//"))) {
                    continue;
                }

                StringTokenizer st = new StringTokenizer(line);
                if (st.countTokens() < 4) {
                    continue;
                }

                try {
                    int[] v = new int[4];
                    for (int col = 0; col < 4; col++) {
                        v[col] = Integer.parseInt(st.nextToken());
                    }
                    values[row++] = v;
                } catch (NumberFormatException nan) {
                }
            }
        } finally {
            Q3M.close(in);
        }

        if (row < rows)
            throw new IOException("row underflow (" + row + " < " + rows + ")");
    }

    private int get(int sequence, int valueIndex) {
        return (sequence == 0) ? getZeroSequence()[valueIndex]
                : values[sequence - 1][valueIndex];
    }

    /* (non-Javadoc)
     * @see q3m.ani.AniCfg#getAniFrame(float, int)
     */
    public AniFrame getAniFrame(float index, int sequence) {
        return new AniFrame(this, index, sequence);
    }

    /* (non-Javadoc)
     * @see q3m.anim.AnimCfg#getDefinedKeyFrames()
     */
    public int getDefinedKeyFrames() {
        if (values.length == 0) {
            return zeroSequence[getLengthIndex()];
        }
        int total = 0;
        for (int s = 0; s < values.length; s++) {
            total += values[s][getLengthIndex()];
        }
        return total;
    }

    /* (non-Javadoc)
     * @see q3m.anim.AnimCfg#getDefinedSequences()
     */
    public int getDefinedSequences() {
        return values.length;
    }

    /* (non-Javadoc)
     * @see q3m.anim.AnimCfg#getFirst(int)
     */
    public int getFirst(int sequence) {
        return get(sequence, getFirstIndex());
    }

    /* (non-Javadoc)
     * @see q3m.anim.AnimCfg#getFps(int)
     */
    public int getFps(int sequence) {
        return get(sequence, getFpsIndex());
    }

    /* (non-Javadoc)
     * @see q3m.anim.AnimCfg#getLength(int)
     */
    public int getLength(int sequence) {
        return get(sequence, getLengthIndex());
    }

    /* (non-Javadoc)
     * @see q3m.anim.AnimCfg#getLooping(int)
     */
    public int getLooping(int sequence) {
        return get(sequence, getLoopingIndex());
    }

    /* (non-Javadoc)
     * @see q3m.ani.AniCfg#getSequenceName(int)
     */
    public String getSequenceName(int sequence) {
        return sequences[sequence];
    }

    /* (non-Javadoc)
     * @see q3m.ani.AniCfg#getZeroSequence()
     */
    public int[] getZeroSequence() {
        if (zeroSequence == null) {
            int averageFps = 0;
            for (int s = 0; s < values.length; s++) {
                averageFps += values[s][getFpsIndex()];
            }
            averageFps /= values.length;
            zeroSequence = new int[4];
            zeroSequence[getFirstIndex()] = 0;
            zeroSequence[getFpsIndex()] = averageFps;
            zeroSequence[getLengthIndex()] = getDefinedKeyFrames();
            zeroSequence[getLoopingIndex()] = getDefinedKeyFrames();
        }
        return zeroSequence;
    }

}
