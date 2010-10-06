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

import java.io.IOException;

import q3m.Q3M;
import q3m.jpct.shader.Q3ShaderDef;
import q3m.jpct.shader.Q3ShaderUtil;
import q3m.jpct.util.TextureUtil;
import q3m.md3.MD3Mesh;

import com.threed.jpct.Animation;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

/**
 * Quake III surface.
 * 
 * @author nlotz
 */
public class Q3Mesh extends Object3D {

    private static final long serialVersionUID = 1L;

    /**
     * The mesh name.
     */
    public String name;

    /**
     * Creates a jPCT mesh from an MD3 mesh.
     * 
     * @param parent the parent jPCT model
     * @param md3 the MD3 mesh
     * @param skin the skin
     */
    public Q3Mesh(Q3Model parent, MD3Mesh md3, Q3Skin skin) {
        super(md3.triangles.length);
        this.name = md3.name;

        float scaleUV = 1f;

        Q3M.debug("building animated mesh: " + name);

        int numFrames = md3.vertices.length;
        int numTriangles = md3.triangles.length;

        int f = 0, v1, v2, v3;
        if ((numFrames > 1) && (parent.aniCfg != null)) {
            f = parent.aniCfg.getFirst(0);
        }

        disableVertexSharing();

        for (int t = 0; t < numTriangles; t++) {
            v1 = md3.triangles[t][2]; // jPCT uses
            v2 = md3.triangles[t][1]; // counter-clockwise
            v3 = md3.triangles[t][0]; // ordering
            addTriangle(new SimpleVector(md3.vertices[f][v1]), scaleUV
                    * md3.texCoords[v1][0], scaleUV * md3.texCoords[v1][1],
                    new SimpleVector(md3.vertices[f][v2]), scaleUV
                            * md3.texCoords[v2][0], scaleUV
                            * md3.texCoords[v2][1], new SimpleVector(
                            md3.vertices[f][v3]), scaleUV
                            * md3.texCoords[v3][0], scaleUV
                            * md3.texCoords[v3][1]);
        }

        build();

        if ((numFrames > 1) && (parent.aniCfg != null)) {
            Animation anim = new Animation(parent.aniCfg.getDefinedKeyFrames());
            if (parent.aniCfg.getDefinedSequences() == 0) {
                anim.createSubSequence("sequence #1");
                int first = parent.aniCfg.getFirst(0);
                int length = parent.aniCfg.getLength(0);
                for (f = (first + 1); f < (first + length); f++) {
                    Object3D m = new Object3D(numTriangles);
                    m.disableVertexSharing();
                    for (int t = 0; t < numTriangles; t++) {
                        v1 = md3.triangles[t][2]; // jPCT uses
                        v2 = md3.triangles[t][1]; // counter-clockwise
                        v3 = md3.triangles[t][0]; // ordering
                        m.addTriangle(new SimpleVector(md3.vertices[f][v1]),
                                new SimpleVector(md3.vertices[f][v2]),
                                new SimpleVector(md3.vertices[f][v3]));
                    }
                    m.build();
                    anim.addKeyFrame(m.getMesh());
                }
            } else {
                for (int i = 1; i <= parent.aniCfg.getDefinedSequences(); i++) {
                    anim.createSubSequence(parent.aniCfg.getSequenceName(i));
                    int first = parent.aniCfg.getFirst(i);
                    int length = parent.aniCfg.getLength(i);
                    for (f = first; (f < (first + length))
                            && (f < md3.vertices.length); f++) {
                        if ((i == 1) && (f == first)) {
                            continue;
                        }
                        Object3D m = new Object3D(numTriangles);
                        m.disableVertexSharing();
                        for (int t = 0; t < numTriangles; t++) {
                            v1 = md3.triangles[t][2]; // jPCT uses
                            v2 = md3.triangles[t][1]; // counter-clockwise
                            v3 = md3.triangles[t][0]; // ordering
                            m.addTriangle(
                                    new SimpleVector(md3.vertices[f][v1]),
                                    new SimpleVector(md3.vertices[f][v2]),
                                    new SimpleVector(md3.vertices[f][v3]));
                        }
                        m.build();
                        anim.addKeyFrame(m.getMesh());
                    }
                }
            }
            anim.strip();
            anim.setInterpolationMethod(Animation.LINEAR);
            anim.setClampingMode(Animation.USE_WRAPPING);
            setAnimationSequence(anim);
        }

        String texName = null;

        if (skin != null) {
            texName = (String) skin.shaders.get(name);
        } else if (md3.shaders.length > 0) {
            texName = md3.shaders[0].name;
        }

        if (texName != null) {
            try {
                Q3ShaderUtil su = Q3ShaderUtil.getInstance();
                String shaderName = texName.substring(0, texName.length() - 4);
                Q3ShaderDef shaderDef = su.getShaderDef(shaderName);
                if (shaderDef != null) {
                    setTexture(shaderDef.getTexInfo(this));
                } else {
                    TextureUtil.fetchTexture(texName, this);
                    setTexture(texName);
                }
            } catch (IOException e) {
                Q3M.warn("can't fetch shader: " + e.getMessage());
            }
        }
    }
}
