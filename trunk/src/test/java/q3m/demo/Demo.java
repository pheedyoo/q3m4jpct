package q3m.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import q3m.Q3M;
import q3m.jpct.Q3Player;
import q3m.q3.ani.AniCfgQ3Lower;
import q3m.q3.ani.AniCfgQ3Upper;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Object3D;
import com.threed.jpct.World;

public class Demo extends JFrame implements Runnable, WindowListener {

    static {
        Q3M.logLevel = Q3M.DEBUG;
    }

    private static final long serialVersionUID = 1L;

    private volatile Thread thread = null;

    public FrameBuffer buffer = null;

    public World world = null;

    public Object3D sceneRoot = null;

    public Q3Player player = null;

    private Dimension currentDimension = null;

    public Demo() {
        setSize(512, 512);

        world = new World();
        world.setAmbientLight(255, 255, 255);
        World.setDefaultThread(Thread.currentThread());
        sceneRoot = Object3D.createDummyObj();
        sceneRoot.rotateX((float) (-90f * Math.PI / 180f));

        try {

            //player = new Q3Player("Chaos-Marine", "Black-Legion");
            player = new Q3Player("Chaos-Marine", "Iron-Warrior");// = 'default'
            //player = new Q3Player("Chaos-Marine", "Nightlord");// = 'blue'
            //player = new Q3Player("Chaos-Marine", "Word-Bearer");// = 'red'

            player.setUpperSequence(AniCfgQ3Upper.STAND);
            player.setLowerSequence(AniCfgQ3Lower.IDLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (player != null) {
            player.addTo(world);
            sceneRoot.addChild(player);
            world.getCamera().setPosition(100, -25, 0);
            world.getCamera().lookAt(player.getTransformedCenter());
        }

        currentDimension = new Dimension(-1, -1);

        addWindowListener(this);
        setLocationRelativeTo(null);
        setVisible(true);

        startThread();
    }

    public void paint(Graphics g) {

        if (thread == null)
            return;

        Dimension dim = getSize();
        if (!dim.equals(currentDimension)) {
            buffer = new FrameBuffer(getWidth(), getHeight(),
                    FrameBuffer.SAMPLINGMODE_OGSS_FAST);
            currentDimension = dim;
        }

        if (player != null) {
            player.aniTick(System.currentTimeMillis());
          //  player.rotateZ(0.01f);
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

    public void windowActivated(WindowEvent event) {
    }

    public void windowClosed(WindowEvent event) {
        stopThread();
        System.exit(0);
    }

    public void windowClosing(WindowEvent event) {
        event.getWindow().dispose();
    }

    public void windowDeactivated(WindowEvent event) {
    }

    public void windowDeiconified(WindowEvent event) {
    }

    public void windowIconified(WindowEvent event) {
    }

    public void windowOpened(WindowEvent event) {
    }

    public static void main(String[] args) {
        new Demo();
    }

}
