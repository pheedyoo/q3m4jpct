package q3m.md3;

import java.io.InputStream;

import junit.framework.TestCase;
import q3m.Q3M;

public class MD3ModelTest extends TestCase {

    static {
        Q3M.logLevel = Q3M.DEBUG;
    }

    public void testETHead() throws Exception {
        InputStream in = null;
        MD3Model model = null;
        try {

            in = Q3M.getResStream("/res/et/models/players/hud/head.md3");
            assertNotNull(in);

            model = new MD3Model(in);
            assertNotNull(model);

            assertEquals("", model.name);
            assertEquals(0, model.flags);

            assertNotNull(model.frames);
            assertEquals(401, model.frames.length);

            assertNotNull(model.meshes);
            assertEquals(4, model.meshes.length);
            assertEquals("teeth", model.meshes[0].name);
            assertEquals(401, model.meshes[0].vertices.length);
            assertEquals("head", model.meshes[1].name);
            assertEquals(401, model.meshes[1].vertices.length);
            assertEquals("eye1", model.meshes[2].name);
            assertEquals(401, model.meshes[2].vertices.length);
            assertEquals("eye2", model.meshes[3].name);
            assertEquals(401, model.meshes[3].vertices.length);

        } finally {
            Q3M.close(in);
        }
    }

    public void testETHeadLod1() throws Exception {
        InputStream in = null;
        MD3Model model = null;
        try {

            in = Q3M.getResStream("/res/et/models/players/hud/head_1.md3");
            assertNotNull(in);

            model = new MD3Model(in);
            assertNotNull(model);

            assertEquals("", model.name);
            assertEquals(0, model.flags);

            assertNotNull(model.frames);
            assertEquals(1, model.frames.length);

            assertNotNull(model.meshes);
            assertEquals(3, model.meshes.length);
            assertEquals("head", model.meshes[0].name);
            assertEquals(1, model.meshes[0].vertices.length);
            assertEquals("eye1", model.meshes[1].name);
            assertEquals(1, model.meshes[1].vertices.length);
            assertEquals("eye2", model.meshes[2].name);
            assertEquals(1, model.meshes[2].vertices.length);

        } finally {
            Q3M.close(in);
        }
    }

}
