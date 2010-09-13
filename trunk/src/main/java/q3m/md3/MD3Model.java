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
import java.io.InputStream;

import q3m.Q3M;
import q3m.q3.Q3Stream;

/**
 * MD3 Model.
 * 
 * @author nlotz
 */
public class MD3Model implements MD3 {

    /**
     * Name.
     */
    public String name = null;

    /**
     * Flags.
     */
    public int flags = 0;

    /**
     * Animation frames.
     */
    public MD3Frame[] frames;

    /**
     * Animation tags.
     */
    public MD3Tag[] tags;

    /**
     * Surfaces.
     */
    public MD3Mesh[] meshes;

    /**
     * Reads an MD3 model from resource path.
     * 
     * @param path the resource path
     * @throws IOException
     */
    public MD3Model(String path) throws IOException {
        this(Q3M.getResStream(path));
    }

    /**
     * Reads an MD3 model from a stream.
     * 
     * @param md3 the stream to read from
     * @throws IOException
     */
    public MD3Model(InputStream md3) throws IOException {
        try {

            Q3Stream stream = new Q3Stream(md3);            

            int ident = stream.readInt();
            if (ident != IDENT)
                throw new IOException("MD3_IDENT expected (" + ident + " <> "
                        + IDENT + ")");

            int version = stream.readInt();
            if (version != VERSION)
                throw new IOException("MD3_VERSION expected (" + version
                        + " <> " + VERSION + ")");

            name = stream.readString();
            flags = stream.readInt();

            int numFrames = stream.readInt();
            int numTags = stream.readInt();
            int numMeshes = stream.readInt();
            int numSkins = stream.readInt();

            int offsetFrames = stream.readInt();
            int offsetTags = stream.readInt();
            int offsetMeshes = stream.readInt();
            int offsetEnd = stream.readInt();

            Q3M.debug("<MD3ModelHeader>");
            Q3M.debug("  Name: " + name);
            Q3M.debug(" Flags: " + flags);
            Q3M.debug(" Skins: " + numSkins);
            Q3M.debug("Frames: " + numFrames + " at offset " + offsetFrames);
            Q3M.debug("  Tags: " + numTags + " at offset " + offsetTags);
            Q3M.debug("Meshes: " + numMeshes + " at offset " + offsetMeshes);
            Q3M.debug("   End: " + offsetEnd);
            Q3M.debug("</MD3ModelHeader>");

            if (numMeshes < 1)
                meshes = new MD3Mesh[0];

            int skipped = 0;
            int offset = stream.getOffset();
            while (offset < offsetEnd) {
                if (offset == offsetFrames) {
                    Q3M.debug("reading frame(s) at offset " + offset);
                    frames = new MD3Frame[numFrames];
                    for (int f = 0; f < numFrames; f++) {
                        frames[f] = new MD3Frame(stream);
                    }
                } else if (offset == offsetTags) {
                    Q3M.debug("reading tag(s) at offset " + offset);
                    tags = new MD3Tag[numTags];
                    for (int f = 0; f < numFrames; f++) {
                        for (int t = 0; t < numTags; t++) {
                            if (f == 0) {
                                tags[t] = new MD3Tag(numFrames);
                            }
                            tags[t].name = stream.readString();
                            tags[t].origin[f] = stream.readFloats(3);
                            tags[t].axis[f] = stream.readFloats(9);
                            if (f == 0) {
                                Q3M.debug("Tag #" + t + ": " + tags[t].name);
                            }
                        }
                    }
                } else if (offset == offsetMeshes) {
                    Q3M.debug("reading mesh(es) at offset " + offset);
                    meshes = new MD3Mesh[numMeshes];
                    for (int m = 0; m < numMeshes; m++) {
                        meshes[m] = new MD3Mesh(this, stream);
                    }
                } else {
                    // no matching chunk!?
                    // skip one byte and try again
                    stream.read();
                    skipped++;
                }
                offset = stream.getOffset();
            }

            if (frames == null)
                throw new IOException("MD3Model: frame(s) not found");

            if (tags == null)
                throw new IOException("MD3Model: tag(s) not found");

            if (meshes == null)
                throw new IOException("MD3Model: mesh(es) not found");

            if (skipped > 0) {
                Q3M.warn("MD3Model: skipped " + skipped + " bytes of data");
            }

        } finally {
            Q3M.close(md3);
        }
    }
}
