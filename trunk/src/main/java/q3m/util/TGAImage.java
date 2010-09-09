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
package q3m.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;
import java.io.IOException;
import java.io.InputStream;

import q3m.Q3M;
import q3m.q3.Q3Stream;

/**
 * Targa image resource.
 * 
 * @author nlotz
 */
public class TGAImage {

    /**
     * Image width.
     */
    public int width = 0;

    /**
     * Image height.
     */
    public int height = 0;

    /**
     * Bits per pixel.
     */
    public int bpp = 0;

    /**
     * Raw pixel data.
     */
    public int[] pixel = null;

    /**
     * Reads a TGA image from a stream.
     * 
     * @param in the stream to read from
     * @throws IOException
     */
    public TGAImage(InputStream in) throws IOException {
        try {
            Q3Stream stream = new Q3Stream(in);

            String comment = null;
            int commentLength = stream.read();

            int cmId = stream.read();
            if (cmId != 0)
                throw new IOException("TGA colormaps not supported");

            int tgaId = stream.read();

            stream.read(9); // skip colormap and origin info

            width = stream.readShortUnsigned();
            height = stream.readShortUnsigned();

            bpp = stream.read();
            int flags = stream.read();
            if (flags != 0) {
                Q3M.debug("TGA descriptor: " + Integer.toHexString(flags));
            }

            if (commentLength > 0) {
                comment = stream.readString(commentLength);
                Q3M.debug("TGA comment: " + comment);
            }

            // uncompressed
            if ((tgaId == 2) || (tgaId == 3)) {

                byte r, g, b, a;
                pixel = new int[width * height];

                int di = 0;
                byte[] data = stream.read(width * height * (bpp / 8));

                for (int row = height - 1; row >= 0; row--) {
                    int offset = row * width;
                    for (int col = 0; col < width; col++) {
                        switch (bpp) {
                        case 8:
                            b = g = r = data[di++];
                            a = (byte) 255;
                            break;
                        case 24:
                            b = data[di++];
                            g = data[di++];
                            r = data[di++];
                            a = (byte) 255;
                            break;
                        case 32:
                            b = data[di++];
                            g = data[di++];
                            r = data[di++];
                            a = data[di++];
                            break;
                        default:
                            throw new IOException(
                                    "TGA bits per pixel not supported: " + bpp);
                        }
                        pixel[offset++] = ((a & 0xff) << 24)
                                | ((r & 0xff) << 16) | ((g & 0xff) << 8)
                                | (b & 0xff);
                    }
                }
                return;
            }

            // runlength encoded
            if (tgaId == 10) {

                byte r = 0, g = 0, b = 0, a = (byte) 255;
                pixel = new int[width * height];

                for (int row = height - 1; row >= 0; row--) {
                    int offset = row * width;
                    for (int col = 0; col < width;) {
                        byte pHeader = stream.readByte();
                        int pSize = 1 + (pHeader & 0x7f);
                        if ((pHeader & 0x80) != 0) {
                            switch (bpp) {
                            case 24:
                                b = stream.readByte();
                                g = stream.readByte();
                                r = stream.readByte();
                                a = (byte) 255;
                                break;
                            case 32:
                                b = stream.readByte();
                                g = stream.readByte();
                                r = stream.readByte();
                                a = stream.readByte();
                                break;
                            default:
                                throw new IOException(
                                        "TGA bits per pixel not supported: "
                                                + bpp);
                            }
                            for (int n = 0; n < pSize; n++) {
                                pixel[offset++] = ((a & 0xff) << 24)
                                        | ((r & 0xff) << 16)
                                        | ((g & 0xff) << 8) | (b & 0xff);
                                col++;
                                if (col == width) {
                                    col = 0;
                                    if (row > 0) {
                                        row--;
                                    } else {
                                        return;
                                    }
                                    offset = row * width;
                                }
                            }
                        } else {
                            for (int n = 0; n < pSize; n++) {
                                switch (bpp) {
                                case 24:
                                    b = stream.readByte();
                                    g = stream.readByte();
                                    r = stream.readByte();
                                    a = (byte) 255;
                                    break;
                                case 32:
                                    b = stream.readByte();
                                    g = stream.readByte();
                                    r = stream.readByte();
                                    a = stream.readByte();
                                    break;
                                default:
                                    throw new IOException(
                                            "TGA bits per pixel not supported: "
                                                    + bpp);
                                }
                                pixel[offset++] = ((a & 0xff) << 24)
                                        | ((r & 0xff) << 16)
                                        | ((g & 0xff) << 8) | (b & 0xff);
                                col++;
                                if (col == width) {
                                    col = 0;
                                    if (row > 0) {
                                        row--;
                                    } else {
                                        return;
                                    }
                                    offset = row * width;
                                }
                            }
                        }
                    }
                }
            }

            throw new IOException("TGA image type not supported: " + tgaId);

        } finally {
            Q3M.close(in);
        }
    }

    /**
     * Creates an AWT image from the raw pixel data.
     * 
     * @return the AWT image created from the raw pixel data
     */
    public Image getImage() {

        if (pixel == null) {
            return null;
        }

        return Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(width, height, new DirectColorModel(32,
                        0xff0000, 0xff00, 0xff, 0xff000000), pixel, 0, width));
    }

    /**
     * Returns <code>true</code> if the alpha bits are valid.
     * 
     * @return <code>true</code> if the alpha bits are valid
     */
    public boolean hasAlpha() {
        return (bpp == 32);
    }

}
