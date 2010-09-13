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
package q3m.jpct;

import q3m.md3.MD3Tag;

import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

/**
 * Quake III tag.
 * 
 * @author nlotz
 */
public class Q3Tag extends Object3D {

    private static final long serialVersionUID = 1L;

    /**
     * Name.
     */
    public String name;

    /**
     * NUM_FRAMES * transformation matrix.
     */
    public Matrix[] matrix;

    /**
     * Creates a tag from MD3 data.
     * 
     * @param tag the MD3 tag
     */
    public Q3Tag(MD3Tag tag) {
        super(Object3D.createDummyObj());
        name = tag.name;
        matrix = new Matrix[tag.axis.length];
        for (int f = 0; f < matrix.length; f++) {
            matrix[f] = new Matrix();
            matrix[f].translate(new SimpleVector(tag.origin[f]));
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    matrix[f].set(row, col, tag.axis[f][row * 3 + col]);
                }
            }
        }
    }

    /**
     * Transforms the tag.
     * 
     * @param index the frame index
     * @param f1 the first frame
     * @param f2 the second frame
     */
    public void transform(float index, int f1, int f2) {
        Matrix m;
        if ((f1 == f2) || (index < 0.001f)) {
            m = matrix[f1];
        } else if (index > 0.999f) {
            m = matrix[f2];
        } else {
            m = new Matrix();
            m.interpolate(matrix[f1], matrix[f2], index);
        }
        setRotationMatrix(m); // applies translation, too
    }
}
