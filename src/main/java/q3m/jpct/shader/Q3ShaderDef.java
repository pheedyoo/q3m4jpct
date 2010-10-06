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
package q3m.jpct.shader;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import q3m.jpct.util.TextureUtil;

import com.threed.jpct.Object3D;
import com.threed.jpct.TextureInfo;
import com.threed.jpct.TextureManager;

/**
 * Quake III shader definition.
 * 
 * @author nlotz
 */
public class Q3ShaderDef {

    private String id;
    private Hashtable cfg;
    private Vector stages;
    private TextureInfo texInfo;

    /**
     * Creates a shader definition by id.
     * 
     * @param id the shader id
     */
    public Q3ShaderDef(String id) {
        this.id = id;
        cfg = new Hashtable();
        stages = new Vector();
        texInfo = null;
    }

    protected void addCfgDef(Vector defTokens) {

        if (defTokens.size() < 1)
            return;

        put(cfg, defTokens);
    }

    protected void addStageDef(int stage, Vector defTokens) {

        if (defTokens.size() < 1)
            return;

        while (stage >= stages.size()) {
            stages.add(new Hashtable());
        }

        put((Hashtable) stages.get(stage), defTokens);
    }

    /**
     * Returns a configuration value.
     * 
     * @param key the name of the configuration key
     * @return the configuration value(s)
     */
    public String[] getCfgDef(String key) {
        return (String[]) cfg.get(key.toLowerCase());
    }

    /**
     * Returns the shader id.
     * 
     * @return the shader id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the number of shader stages.
     * 
     * @return the number of shader stages
     */
    public int getNumStages() {
        return stages.size();
    }

    /**
     * Returns a shader stage value.
     * 
     * @param stage the shader stage
     * @param key the name of the configuration key
     * @return the configuration value(s)
     */
    public String[] getStageDef(int stage, String key) {

        if (stage >= stages.size())
            return null;

        return (String[]) ((Hashtable) stages.get(stage))
                .get(key.toLowerCase());
    }

    /**
     * Returns the shader (multi-)texture.
     * 
     * @param mesh the mesh the texture will be applied to
     * @return the shader (multi-)texture
     * @throws IOException
     */
    public TextureInfo getTexInfo(Object3D mesh) throws IOException {

        if (texInfo != null)
            return texInfo;

        if (stages.size() < 1)
            throw new IOException("shader has no stages");

        TextureManager tm = TextureManager.getInstance();

        for (int i = 0; i < stages.size(); i++) {

            String[] tcGen = getStageDef(i, "tcGen");
            if (tcGen != null)
                continue; //TODO

            String[] map = getStageDef(i, "map");
            if ((map != null) && (map.length > 0)) {

                String mapName = map[0].substring(0, map[0].lastIndexOf('.'));
                TextureUtil.fetchTexture(mapName, mesh);
                int texId = tm.getTextureID(mapName);

                int blendMode = TextureInfo.MODE_BLEND;
                String[] blendFunc = getStageDef(i, "blendFunc");
                if ((blendFunc != null) && (blendFunc.length > 0)) {
                    if (blendFunc[0].equalsIgnoreCase("add"))
                        blendMode = TextureInfo.MODE_ADD;
                    else if (blendFunc[0].equalsIgnoreCase("blend"))
                        blendMode = TextureInfo.MODE_BLEND;
                }

                if (texInfo == null)
                    texInfo = new TextureInfo(texId);
                else
                    texInfo.add(texId, blendMode);
            }
        }

        return texInfo;
    }

    private void put(Hashtable h, Vector t) {
        String key = (String) t.firstElement();
        t.removeElementAt(0);
        String[] s = new String[t.size()];
        for (int i = 0; i < s.length; i++)
            s[i] = (String) t.elementAt(i);
        h.put(key.toLowerCase(), s);
    }

}
