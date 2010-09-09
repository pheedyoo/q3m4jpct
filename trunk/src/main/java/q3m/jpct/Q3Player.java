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

    public String name;

    public String skin;

    //public Q3Model gunModel;
    //public Q3Skin gunSkin;

    public Q3Model headModel;
    public Q3Skin headSkin;

    public Q3Model upperModel;
    public Q3Skin upperSkin;

    public Q3Model lowerModel;
    public Q3Skin lowerSkin;

    public Q3Player(String name) throws IOException {
        this(name, "default");
    }

    public Q3Player(String name, String skin) throws IOException {
        super(Object3D.createDummyObj());
        this.name = name;
        this.skin = skin;

        //gunModel = new Q3Model(new MD3Model(
        //        "/res/q3/models/weapons2/rocketl/rocketl.md3"));

        String basePath = "/res/q3/models/players/" + name;

        headSkin = new Q3Skin(basePath + "/head_" + skin + ".skin");
        headModel = new Q3Model(new MD3Model(basePath + "/head.md3"), headSkin);

        upperSkin = new Q3Skin(basePath + "/upper_" + skin + ".skin");
        upperModel = new Q3Model(new MD3Model(basePath + "/upper.md3"),
                new AniCfgQ3Upper(basePath + "/animation.cfg"), upperSkin);

        lowerSkin = new Q3Skin(basePath + "/lower_" + skin + ".skin");
        lowerModel = new Q3Model(new MD3Model(basePath + "/lower.md3"),
                new AniCfgQ3Lower(basePath + "/animation.cfg"), lowerSkin);

        //upperModel.attach("tag_weapon", gunModel);
        upperModel.attach("tag_head", headModel);
        lowerModel.attach("tag_torso", upperModel);

        addChild(lowerModel);
    }

    public void addTo(World world) {
        world.addObject(this);
        //gunModel.addTo(world);
        headModel.addTo(world);
        upperModel.addTo(world);
        lowerModel.addTo(world);
    }

    public void aniTick(long now) {
        //gunModel.aniTick(now);
        headModel.aniTick(now);
        upperModel.aniTick(now);
        lowerModel.aniTick(now);
    }

    public void setLowerSequence(int sequence) {
        lowerModel.setAniSequence(sequence);
    }

    public void setUpperSequence(int sequence) {
        upperModel.setAniSequence(sequence);
    }

}
