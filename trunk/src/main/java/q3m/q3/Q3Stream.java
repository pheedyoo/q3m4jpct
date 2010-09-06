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
package q3m.q3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Quake III Resource stream.
 * 
 * @author nlotz
 */
public class Q3Stream implements Q3 {

    private DataInputStream stream;
    private int offset;
    private byte[] b4;

    public Q3Stream(InputStream in) throws IOException {
        stream = new DataInputStream(in);
        offset = 0;
        b4 = new byte[4];
    }

    public int getOffset() {
        return offset;
    }

    public int read() throws IOException {
        int value = stream.read();
        offset++;
        return value;
    }

    public byte[] read(int length) throws IOException {
        byte[] b = new byte[length];
        stream.readFully(b);
        offset += length;
        return b;
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public float[] readFloats(int dim) throws IOException {

        int off = 0;
        byte[] b = read(dim * 4);
        float[] v = new float[dim];
        for (int i = 0; i < dim; i++) {
            v[i] = Float.intBitsToFloat(((b[off + 0] & 0xff) << 0)
                    | ((b[off + 1] & 0xff) << 8) | ((b[off + 2] & 0xff) << 16)
                    | ((b[off + 3] & 0xff) << 24));
            off += 4;
        }

        return v;
    }

    public float[][] readFloats(int dim1, int dim2) throws IOException {

        int off = 0;
        byte[] b = read(dim1 * dim2 * 4);
        float[][] v = new float[dim1][dim2];
        for (int i1 = 0; i1 < dim1; i1++) {
            for (int i2 = 0; i2 < dim2; i2++) {
                v[i1][i2] = Float.intBitsToFloat(
                         (b[off + 0] & 0xff) |
                        ((b[off + 1] & 0xff) << 8) |
                        ((b[off + 2] & 0xff) << 16) |
                        ((b[off + 3] & 0xff) << 24));
                off += 4;
            }
        }

        return v;
    }

    public int readInt() throws IOException {
        stream.readFully(b4);
        offset += 4;
        return ( (b4[0] & 0xff) | 
                ((b4[1] & 0xff) << 8) | 
                ((b4[2] & 0xff) << 16) | 
                ((b4[3] & 0xff) << 24));
    }

    public int[] readInts(int dim) throws IOException {

        int off = 0;
        byte[] b = read(dim * 4);
        int[] v = new int[dim];
        for (int i = 0; i < dim; i++) {
            v[i] =   (b[off + 0] & 0xff) | 
                    ((b[off + 1] & 0xff) << 8) |
                    ((b[off + 2] & 0xff) << 16) |
                    ((b[off + 3] & 0xff) << 24);
            off += 4;
        }

        return v;
    }

    public int[][] readInts(int dim1, int dim2) throws IOException {

        int off = 0;
        byte[] b = read(dim1 * dim2 * 4);
        int[][] v = new int[dim1][dim2];
        for (int i1 = 0; i1 < dim1; i1++) {
            for (int i2 = 0; i2 < dim2; i2++) {
                v[i1][i2] =  (b[off + 0] & 0xff) |
                            ((b[off + 1] & 0xff) << 8) |
                            ((b[off + 2] & 0xff) << 16) |
                            ((b[off + 3] & 0xff) << 24);
                off += 4;
            }
        }

        return v;
    }

    public int readUnsignedShort() throws IOException {
        stream.readFully(b4, 0, 2);
        offset += 2;
        return ((b4[0] & 0xff) | ((b4[1] & 0xff) << 8));
    }

    public String readString() throws IOException {
        return readString(MAX_QPATH);
    }

    public String readString(int length) throws IOException {

        int i = 0;
        byte[] b = read(length);
        while ((i < b.length) && (b[i] != 0))
            i++;

        return new String(b, 0, i);
    }

}
