package q3m.md3;

import junit.framework.TestCase;
import q3m.Q3M;
import q3m.q3.ani.AniCfgQ3Lower;
import q3m.q3.ani.AniCfgQ3Upper;

public class MD3ModelTest extends TestCase {

    public static final String BASE = "/res/q3/models/players/Chaos-Marine";

    static {
        Q3M.logLevel = Q3M.DEBUG;
    }

    public void testQ3ChaosMarineAniCfgLower() throws Exception {
        AniCfgQ3Lower aniCfg = new AniCfgQ3Lower(BASE + "/animation.cfg");
        assertNotNull(aniCfg);
        assertEquals(164, aniCfg.getDefinedKeyFrames());
        assertEquals(18, aniCfg.getDefinedSequences());
    }

    public void testQ3ChaosMarineAniCfgUpper() throws Exception {
        AniCfgQ3Upper aniCfg = new AniCfgQ3Upper(BASE + "/animation.cfg");
        assertNotNull(aniCfg);
        assertEquals(184, aniCfg.getDefinedKeyFrames());
        assertEquals(13, aniCfg.getDefinedSequences());
    }

    public void testQ3ChaosMarineHead() throws Exception {
        MD3Model model = new MD3Model(BASE + "/head.md3");
        assertNotNull(model);
        assertEquals("head.md3", model.name);
        assertEquals(0, model.flags);
        assertNotNull(model.frames);
        assertEquals(1, model.frames.length);
        assertNotNull(model.meshes);
        assertEquals(1, model.meshes.length);
    }

    public void testQ3ChaosMarineLower() throws Exception {
        MD3Model model = new MD3Model(BASE + "/lower.md3");
        assertNotNull(model);
        assertEquals("lower.md3", model.name);
        assertEquals(0, model.flags);
        assertNotNull(model.frames);
        assertEquals(161, model.frames.length);
        assertNotNull(model.meshes);
        assertEquals(1, model.meshes.length);
    }

    public void testQ3ChaosMarineUpper() throws Exception {
        MD3Model model = new MD3Model(BASE + "/upper.md3");
        assertNotNull(model);
        assertEquals("upper.md3", model.name);
        assertEquals(0, model.flags);
        assertNotNull(model.frames);
        assertEquals(181, model.frames.length);
        assertNotNull(model.meshes);
        assertEquals(2, model.meshes.length);
    }

}
