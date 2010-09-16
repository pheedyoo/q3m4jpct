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

import java.util.Hashtable;

import q3m.ani.AniCfg;
import q3m.ani.AniCfgSingle;
import q3m.ani.AniFrame;
import q3m.md3.MD3Frame;
import q3m.md3.MD3Model;

import com.threed.jpct.Object3D;
import com.threed.jpct.World;

/**
 * Quake III model.
 * 
 * @author nlotz
 */
public class Q3Model extends Object3D {

    private static final long serialVersionUID = 1L;

    /**
     * The animation configuration.
     */
    public AniCfg aniCfg;

    /**
     * The current animation index.
     */
    public float aniIndex;

    /**
     * The current animation sequence.
     */
    public int aniSequence;

    /**
     * The last animation update.
     */
    private long aniLast;

    /**
     * The tags.
     */
    public Q3Tag[] tags;

    /**
     * The tags indexed by name.
     */
    public Hashtable tagHash;

    /**
     * The meshes.
     */
    public Q3Mesh[] meshes;

    /**
     * The skin.
     */
    public Q3Skin skin;

    /**
     * The MD3 frames.
     */
    public MD3Frame[] frames;

    /**
     * Creates a jPCT model from an MD3 model.
     * 
     * @param md3 the MD3 model
     */
    public Q3Model(MD3Model md3) {
        this(md3, null);
    }

    /**
     * Creates a jPCT model from an MD3 model.
     * 
     * @param md3 the MD3 model
     * @param skin the skin
     */
    public Q3Model(MD3Model md3, Q3Skin skin) {
        this(md3, new AniCfgSingle(md3.frames.length), skin);
    }

    /**
     * Creates a jPCT model from an MD3 model.
     * 
     * @param md3 the MD3 model
     * @param aniCfg the animation configuration
     * @param skin the skin
     */
    public Q3Model(MD3Model md3, AniCfg aniCfg, Q3Skin skin) {
        super(Object3D.createDummyObj());
        this.aniCfg = aniCfg;
        this.skin = skin;
        this.frames = md3.frames;

        tags = new Q3Tag[md3.tags.length];
        tagHash = new Hashtable();
        for (int t = 0; t < tags.length; t++) {
            tags[t] = new Q3Tag(md3.tags[t]);
            tagHash.put(tags[t].name, tags[t]);
            addChild(tags[t]);
        }

        meshes = new Q3Mesh[md3.meshes.length];
        for (int m = 0; m < meshes.length; m++) {
            meshes[m] = new Q3Mesh(this, md3.meshes[m], skin);
            addChild(meshes[m]);
        }

        aniIndex = 0;
        aniSequence = 0;
        aniLast = System.currentTimeMillis();
    }

    /**
     * Adds the model to a jPCT world.
     * 
     * @param world the world to add the model to
     */
    public void addTo(World world) {
        world.addObject(this);
        world.addObjects(tags);
        world.addObjects(meshes);
    }

    /* (non-Javadoc)
     * @see com.threed.jpct.Object3D#animate(float, int)
     */
    public void animate(float index, int sequence) {

        AniFrame f = aniCfg.getAniFrame(index, sequence);

        for (int t = 0; t < tags.length; t++) {
            tags[t].transform(f.frameIndex, f.frame1, f.frame2);
        }

        for (int m = 0; m < meshes.length; m++) {
            meshes[m].animate(index, sequence);
        }
    }

    /**
     * Animates the model.
     * 
     * @param now the current system time.
     */
    public void aniTick(long now) {

        if (aniCfg == null)
            return;

        int fps = aniCfg.getFps(aniSequence);
        int length = aniCfg.getLength(aniSequence);
        if (length < 2) {
            aniIndex = 0;
            aniLast = now;
        } else {
            float elapsedSecs = (float) (now - aniLast) / 1000f;
            aniLast = now;
            float elapsedIndex = (elapsedSecs * fps) / length;
            aniIndex += elapsedIndex;
            while (aniIndex > 0.999f)
                aniIndex -= 1;
        }

        animate(aniIndex, aniSequence);
    }

    /**
     * Attaches a model to a tag.
     * 
     * @param tagName the tag name
     * @param child the model to attach
     * @return <code>true</code> if the tag was found
     */
    public boolean attach(String tagName, Object3D child) {
        Q3Tag tag = getTag(tagName);
        if (tag != null) {
            tag.addChild(child);
            return true;
        }
        return false;
    }

    /**
     * Finds a tag by name.
     * 
     * @param tagName the tag name
     * @return the tag or <code>null</code> if not found
     */
    public Q3Tag getTag(String tagName) {
        return (Q3Tag) tagHash.get(tagName);
    }

    /**
     * Sets the animation sequence.
     * 
     * @param aniSequence the sequence to set
     */
    public void setAniSequence(int aniSequence) {
        this.aniSequence = aniSequence;
        aniIndex = 0;
        aniLast = System.currentTimeMillis();
    }
}
