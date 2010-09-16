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

import q3m.md3.MD3Model;
import q3m.q3.ani.AniCfgQ3Lower;
import q3m.q3.ani.AniCfgQ3Upper;

import com.threed.jpct.Object3D;
import com.threed.jpct.World;

/**
 * Quake III player model.
 * 
 * @author nlotz
 */
public class Q3Player extends Object3D {

    private static final long serialVersionUID = 1L;

    /**
     * The player name.
     */
    public String name;

    /**
     * The skin name.
     */
    public String skin;

    /**
     * The weapon model.
     */
    public Q3Weapon gunModel;

    /**
     * The head model.
     */
    public Q3Model headModel;

    /**
     * The upper model.
     */
    public Q3Model upperModel;

    /**
     * The lower model.
     */
    public Q3Model lowerModel;

    /**
     * Creates a player model by name.
     * 
     * @param name the model name
     * @throws IOException
     */
    public Q3Player(String name) throws IOException {
        this(name, "default");
    }

    /**
     * Creates a player model by name.
     * 
     * @param name the model name
     * @param skin the skin name
     * @throws IOException
     */
    public Q3Player(String name, String skin) throws IOException {
        super(Object3D.createDummyObj());
        this.name = name;
        this.skin = skin;

        gunModel = new Q3Weapon("machinegun");

        String basePath = "/res/q3/models/players/" + name;

        headModel = new Q3Model(new MD3Model(basePath + "/head.md3"),
                new Q3Skin(basePath + "/head_" + skin + ".skin"));

        upperModel = new Q3Model(new MD3Model(basePath + "/upper.md3"),
                new AniCfgQ3Upper(basePath + "/animation.cfg"), new Q3Skin(
                        basePath + "/upper_" + skin + ".skin"));

        lowerModel = new Q3Model(new MD3Model(basePath + "/lower.md3"),
                new AniCfgQ3Lower(basePath + "/animation.cfg"), new Q3Skin(
                        basePath + "/lower_" + skin + ".skin"));

        upperModel.attach("tag_weapon", gunModel);
        upperModel.attach("tag_head", headModel);
        lowerModel.attach("tag_torso", upperModel);

        addChild(lowerModel);
    }

    /**
     * Adds the player to a jPCT world.
     * 
     * @param world the world to add the player to
     */
    public void addTo(World world) {
        world.addObject(this);
        gunModel.addTo(world);
        headModel.addTo(world);
        upperModel.addTo(world);
        lowerModel.addTo(world);
    }

    /**
     * Animates the player model.
     * 
     * @param now the current system time
     */
    public void aniTick(long now) {
        gunModel.aniTick(now);
        headModel.aniTick(now);
        upperModel.aniTick(now);
        lowerModel.aniTick(now);
    }

    /**
     * Sets the animation sequence for the lower model.
     * 
     * @param sequence the sequence to set
     */
    public void setLowerSequence(int sequence) {
        lowerModel.setAniSequence(sequence);
    }

    /**
     * Sets the animation sequence for the upper model.
     * 
     * @param sequence the sequence to set
     */
    public void setUpperSequence(int sequence) {
        upperModel.setAniSequence(sequence);
    }

}
