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
package q3m.md3;

import java.io.IOException;

import q3m.Q3M;
import q3m.q3.Q3Stream;

/**
 * MD3 Mesh.
 * 
 * @author nlotz
 */
public class MD3Mesh implements MD3 {

    /**
     * Name.
     */
    public String name = null;

    /**
     * Flags.
     */
    public int flags = 0;

    /**
     * Shaders.
     */
    public MD3Shader[] shaders = null;

    /**
     * Triangles.
     */
    public int[][] triangles = null;

    /**
     * Texture coordinates.
     */
    public float[][] texCoords = null;

    /**
     * NUM_FRAMES * Vertices.
     */
    public float[][][] vertices = null;

    /**
     * Reads an MD3 surface from a stream.
     * 
     * @param model the parent MD3 model
     * @param stream the stream to read from
     * @throws IOException
     */
    public MD3Mesh(MD3Model model, Q3Stream stream) throws IOException {

        int offsetStart = stream.getOffset();

        int ident = stream.readInt();
        if (ident != IDENT)
            throw new IOException("MD3_IDENT expected (" + ident + " <> "
                    + IDENT + ")");

        name = stream.readString();
        flags = stream.readInt();

        int numFrames = stream.readInt();
        int numShaders = stream.readInt();
        int numVertices = stream.readInt();
        int numTriangles = stream.readInt();

        int offTriangles = stream.readInt();
        int offShaders = stream.readInt();
        int offTexCoords = stream.readInt();
        int offVertices = stream.readInt();
        int offEnd = stream.readInt();

        if (MD3Config.ENFORCE_LIMITS) {
            MD3Config.limit("MAX_FRAMES", MAX_FRAMES, numFrames);
            MD3Config.limit("MAX_SHADERS", MAX_SHADERS, numShaders);
            MD3Config.limit("MAX_VERTICES", MAX_VERTS, numVertices);
            MD3Config.limit("MAX_TRIANGLES", MAX_TRIANGLES, numTriangles);
        }

        Q3M.debug("<MD3MeshHeader>");
        Q3M.debug("     Name: " + name);
        Q3M.debug("   Frames: " + numFrames);
        Q3M.debug("  Shaders: " + numShaders + " at offset " + offShaders);
        Q3M.debug("Triangles: " + numTriangles + " at offset " + offTriangles);
        Q3M.debug("TexCoords: " + numVertices + " at offset " + offTexCoords);
        Q3M.debug(" Vertices: " + numVertices + " at offset " + offVertices);
        Q3M.debug("      End: " + offEnd);
        Q3M.debug("</MD3MeshHeader>");

        if (numShaders < 1) {
            shaders = new MD3Shader[0];
        }

        int skipped = 0;
        long offset = stream.getOffset() - offsetStart;
        while (offset < offEnd) {
            if ((shaders == null) && (offset == offShaders)) {
                Q3M.debug("reading shaders at offset " + offset);
                shaders = new MD3Shader[numShaders];
                for (int s = 0; s < numShaders; s++) {
                    shaders[s] = new MD3Shader(stream);
                }
            } else if ((triangles == null) && (offset == offTriangles)) {
                Q3M.debug("reading triangles at offset " + offset);
                triangles = stream.readInts(numTriangles, 3);
            } else if ((texCoords == null) && (offset == offTexCoords)) {
                Q3M.debug("reading texture coords at offset " + offset);
                texCoords = stream.readFloats(numVertices, 2);
            } else if ((vertices == null) && (offset == offVertices)) {
                Q3M.debug("reading vertices & normals at offset " + offset);
                vertices = new float[numFrames][numVertices][3];
                for (int f = 0; f < numFrames; f++) {
                    byte[] b = stream.read(numVertices * 8);
                    for (int v = 0, x = 0; v < numVertices; v++, x += 8) {
                        vertices[f][v][0] = (((short) (b[x + 0] & 0xff) | (short) ((b[x + 1] & 0xff) << 8)))
                                * XYZ_SCALE;
                        vertices[f][v][1] = (((short) (b[x + 2] & 0xff) | (short) ((b[x + 3] & 0xff) << 8)))
                                * XYZ_SCALE;
                        vertices[f][v][2] = (((short) (b[x + 4] & 0xff) | (short) ((b[x + 5] & 0xff) << 8)))
                                * XYZ_SCALE;
                        //int compressedNormalZenith = b[x + 6] & 0xff;// TODO
                        //int compressedNormalAzimuth = b[x + 7] & 0xff;// TODO
                    }
                }
            } else {
                // no matching chunk!?
                // skip one byte and try again
                stream.read();
                skipped++;
            }
            offset = stream.getOffset() - offsetStart;
        }

        if (triangles == null)
            throw new IOException("MD3Mesh: triangle(s) not found");

        if (texCoords == null)
            throw new IOException("MD3Mesh: texture coords not found");

        if (vertices == null)
            throw new IOException("MD3Mesh: vertices not found");

        if (shaders == null) {
            Q3M.warn("MD3Mesh: shader(s) not found");
        }

        if (skipped > 0) {
            Q3M.warn("MD3Mesh: skipped " + skipped + " bytes of data");
        }
    }
}
