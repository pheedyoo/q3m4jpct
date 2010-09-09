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

public class AniFrame {
    
    public float frameIndex;
    
    public int frame1;
    
    public int frame2;
    
    public AniFrame() {
        this(0,0,0);
    }
    
    public AniFrame(AniCfg aniCfg, float index, int sequence) {
        this(0,0,0);
        calculate(aniCfg, index, sequence);
    }
    
    public AniFrame(float frameIndex, int frame1, int frame2) {
        this.frameIndex = frameIndex;
        this.frame1 = frame1;
        this.frame2 = frame2;
    }
    
    public void calculate(AniCfg aniCfg, float index, int sequence) {
        
        int first = aniCfg.getFirst(sequence);
        int length = aniCfg.getLength(sequence);
        
        if(length < 2) {
            frameIndex = 0.5f;
            frame1 = first;
            frame2 = first;
            return;
        }
        
        float frame = first + (index * length);
        
        frame1 = (int)frame;      
        frameIndex = frame - frame1;       
        frame2 = frame1 + 1;
        
        int lastFrame = first + length - 1;
        while(frame2 > lastFrame) {
            frame2 -= length;
        }
    }

}
