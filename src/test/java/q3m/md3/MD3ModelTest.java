package q3m.md3;

import junit.framework.TestCase;
import q3m.Q3M;

public class MD3ModelTest extends TestCase {

    static {
        Q3M.logLevel = Q3M.DEBUG;
    }

    public void testQ3TankGirlHead() throws Exception {

        MD3Model model = new MD3Model(
                "/res/q3/models/players/tankgirl/head.md3");

        assertNotNull(model);
        assertEquals("tankgirl", model.name);
        assertEquals(0, model.flags);
        assertNotNull(model.frames);
        assertEquals(1, model.frames.length);
        assertNotNull(model.meshes);
        assertEquals(2, model.meshes.length);
    }

    public void testQ3TankGirlLower() throws Exception {

        MD3Model model = new MD3Model(
                "/res/q3/models/players/tankgirl/lower.md3");

        assertNotNull(model);
        assertEquals("tankgirl", model.name);
        assertEquals(0, model.flags);
        assertNotNull(model.frames);
        assertEquals(220, model.frames.length);
        assertNotNull(model.meshes);
        assertEquals(1, model.meshes.length);
    }

    public void testQ3TankGirlUpper() throws Exception {

        MD3Model model = new MD3Model(
                "/res/q3/models/players/tankgirl/upper.md3");

        assertNotNull(model);
        assertEquals("tankgirl", model.name);
        assertEquals(0, model.flags);
        assertNotNull(model.frames);
        assertEquals(175, model.frames.length);
        assertNotNull(model.meshes);
        assertEquals(4, model.meshes.length);
    }

}
