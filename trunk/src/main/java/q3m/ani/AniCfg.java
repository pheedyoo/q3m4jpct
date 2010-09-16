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
 * Animation configuration.
 * 
 * @author nlotz
 */
public interface AniCfg {
    
    /**
     * Return keyframe information for a given index into a sequence.
     * 
     * @param index the index into the sequence
     * @param sequence the animation sequence
     * @return keyframe information
     */
    public AniFrame getAniFrame(float index, int sequence);
    
    /**
     * Returns the total number of keyframes.
     * 
     * @return the total number of keyframes
     */
    public int getDefinedKeyFrames();

    /**
     * Returns the total number of sequences.
     * 
     * @return the total number of sequences
     */
    public int getDefinedSequences();
    
    /**
     * Returns the index of the 'first' value.
     * 
     * @return the index of the 'first' value
     */
    public int getFirstIndex();

    /**
     * Returns the 'first' value for a sequence.
     * 
     * @param sequence the sequence to get the value for
     * @return the value for the sequence
     */
    public int getFirst(int sequence);

    /**
     * Returns the index of the 'fps' value.
     * 
     * @return the index of the 'fps' value
     */
    public int getFpsIndex();

    /**
     * Returns the 'fps' value for a sequence.
     * 
     * @param sequence the sequence to get the value for
     * @return the value for the sequence
     */
    public int getFps(int sequence);

    /**
     * Returns the index of the 'length' value.
     * 
     * @return the index of the 'length' value
     */
    public int getLengthIndex();

    /**
     * Returns the 'length' value for a sequence.
     * 
     * @param sequence the sequence to get the value for
     * @return the value for the sequence
     */
    public int getLength(int sequence);

    /**
     * Returns the index of the 'looping' value.
     * 
     * @return the index of the 'looping' value
     */
    public int getLoopingIndex();

    /**
     * Returns the 'looping' value for a sequence.
     * 
     * @param sequence the sequence to get the value for
     * @return the value for the sequence
     */
    public int getLooping(int sequence);
    
    /**
     * Returns the name of an animation sequence.
     * 
     * @param sequence the sequence to get the value for 
     * @return the name of the sequence
     */
    public String getSequenceName(int sequence);
    
    /**
     * Returns the configuration values for the zero animation.
     * 
     * @return the configuration values for the zero animation
     */
    public int[] getZeroSequence();
    
}
