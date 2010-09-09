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
package q3m.jpct.util;

import java.io.IOException;
import java.io.InputStream;

import q3m.Q3M;
import q3m.util.TGAImage;

import com.threed.jpct.Object3D;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;

/**
 * Utility class for loading Quake III textures.
 * 
 * @author nlotz
 */
public class TextureUtil {

    public static Texture fetchTexture(String texName, Object3D mesh)
            throws IOException {

        TextureManager txm = TextureManager.getInstance();
        if (txm.containsTexture(texName))
            return txm.getTexture(texName);

        Texture tx = loadTexture(texName, mesh);
        if (tx != null) {
            txm.addTexture(texName, tx);
            return tx;
        }

        throw new IOException();
    }

    private static Texture loadTexture(String texName, Object3D mesh) {

        String fn = "/res/q3/" + texName;

        Texture tx = loadTextureImage(fn, mesh);
        if (tx != null)
            return tx;

        if (fn.toLowerCase().endsWith(".tga")
                || fn.toLowerCase().endsWith(".jpg"))
            fn = fn.substring(0, fn.length() - 4);

        tx = loadTextureImage(fn + ".tga", mesh);
        if (tx != null)
            return tx;

        tx = loadTextureImage(fn + ".jpg", mesh);
        if (tx != null)
            return tx;

        tx = loadTextureImage(fn + ".TGA", mesh);
        if (tx != null)
            return tx;

        tx = loadTextureImage(fn + ".JPG", mesh);
        if (tx != null)
            return tx;

        return null;
    }

    private static Texture loadTextureImage(String path, Object3D mesh) {

        InputStream in = Q3M.getResStream(path);
        if (in == null)
            return null;

        try {
            if (path.toLowerCase().endsWith(".tga")) {

                TGAImage tga = new TGAImage(in);
                //if (tga.hasAlpha())
                //    mesh.setTransparency(0);

                return new Texture(tga.getImage(), tga.hasAlpha());
            }
        } catch (IOException ignored) {
        }

        return new Texture(in);
    }

}
