package q3m.demo;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JApplet;

import q3m.Q3M;
import q3m.jpct.Q3Player;
import q3m.q3.ani.AniCfgQ3Lower;
import q3m.q3.ani.AniCfgQ3Upper;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Object3D;
import com.threed.jpct.World;

public class Demo extends JApplet implements Runnable {

    static {
        Q3M.logLevel = Q3M.DEBUG;
    }

    private static final long serialVersionUID = 1L;

    private volatile Thread thread = null;

    public FrameBuffer buffer = null;

    public World world = null;

    public Object3D sceneRoot = null;

    public Q3Player player = null;

    public void init() {

        setSize(800, 600);

        buffer = new FrameBuffer(getWidth(), getHeight(),
                FrameBuffer.SAMPLINGMODE_NORMAL);

        world = new World();
        world.setAmbientLight(255, 255, 255);     
        World.setDefaultThread(Thread.currentThread());
        sceneRoot = Object3D.createDummyObj();
        sceneRoot.rotateX((float) (-90f * Math.PI / 180f));

        try {
            
            player = new Q3Player("tankgirl");
            //player = new Q3Player("tankgirl", "tekkgirl");
            //player = new Q3Player("tankgirl", "tintin");
            
            player.setUpperSequence(AniCfgQ3Upper.GESTURE);           
            player.setLowerSequence(AniCfgQ3Lower.IDLE);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (player != null) {
            player.addTo(world);
            sceneRoot.addChild(player);
            world.getCamera().setPosition(75, 0, 0);
            world.getCamera().lookAt(player.getTransformedCenter());
        }

        startThread();
    }

    public void destroy() {
        stopThread();
    }

    public void paint(Graphics g) {

        if (thread == null)
            return;

        if (player != null) {
            player.aniTick(System.currentTimeMillis());
            player.rotateZ(0.01f);
        }

        buffer.clear(Color.GRAY);

        world.renderScene(buffer);
        world.draw(buffer);
        buffer.update();

        buffer.display(g, 0, 0);
    }

    public void run() {
        while (thread == Thread.currentThread()) {
            repaint();
            try {
                Thread.sleep(20);
            } catch (Exception ignored) {
            }
        }
    }

    public void startThread() {
        if (thread == null) {
            thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }

    }

    public void stopThread() {
        if (thread != null) {
            thread = null;
        }
    }

}
