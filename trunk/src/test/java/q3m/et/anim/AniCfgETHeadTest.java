package q3m.et.anim;

import junit.framework.TestCase;
import q3m.Q3M;
import q3m.et.ani.AniCfgETHead;

public class AniCfgETHeadTest extends TestCase {

    static {
        Q3M.logLevel = Q3M.DEBUG;
    }

    public void testAniCfgETHeadDefault() throws Exception {
        AniCfgETHead ani = new AniCfgETHead();
        assertNotNull(ani);
        assertEquals(421, ani.getTotalKeyFrames());
        assertEquals(18, ani.getTotalSequences());
    }

}
