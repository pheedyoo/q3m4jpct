package q3m.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class Demo extends JFrame implements Runnable, WindowListener,
        KeyListener {

    static {
        Q3M.logLevel = Q3M.DEBUG;
    }

    private static final long serialVersionUID = 1L;

    private static int SAMPLING_MODE = FrameBuffer.SAMPLINGMODE_NORMAL;

    private volatile Thread thread = null;

    public FrameBuffer buffer = null;

    public World world = null;

    public Object3D sceneRoot = null;

    public Q3Player player = null;

    private Dimension currentDimension = null;

    int fps = 0;

    private int fpsCount = 0;

    private long fpsLast = System.currentTimeMillis();

    public Demo() {
        setSize(600, 600);

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

        addKeyListener(this);
        addWindowListener(this);
        setLocationRelativeTo(null);
        setVisible(true);

        startThread();
    }

    public void keyPressed(KeyEvent e) {

        int s;
        switch (e.getKeyChar()) {
        case 's':
            switch (SAMPLING_MODE) {
            case FrameBuffer.SAMPLINGMODE_OGUS:
                setSamplingMode(FrameBuffer.SAMPLINGMODE_NORMAL);
                break;
            case FrameBuffer.SAMPLINGMODE_NORMAL:
                setSamplingMode(FrameBuffer.SAMPLINGMODE_OGSS_FAST);
                break;
            case FrameBuffer.SAMPLINGMODE_OGSS_FAST:
                setSamplingMode(FrameBuffer.SAMPLINGMODE_OGSS);
                break;
            case FrameBuffer.SAMPLINGMODE_OGSS:
                setSamplingMode(FrameBuffer.SAMPLINGMODE_OGUS);
                break;
            }
            break;
        case 'u':
            s = player.upperModel.aniSequence + 1;
            if (s > AniCfgQ3Upper.STAND2) {
                s = AniCfgQ3Upper.GESTURE;
            }
            player.upperModel.setAniSequence(s);
            break;
        case 'l':
            s = player.lowerModel.aniSequence + 1;
            if (s > AniCfgQ3Lower.TURN) {
                s = AniCfgQ3Lower.WALKCR;
            }
            player.lowerModel.setAniSequence(s);
            break;
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void paint(Graphics g) {

        if (thread == null)
            return;

        long now = System.currentTimeMillis();

        Dimension dim = getSize();
        if (!dim.equals(currentDimension)) {
            buffer = new FrameBuffer(dim.width, dim.height, SAMPLING_MODE);
            currentDimension = dim;
        }

        if (player != null) {
            player.aniTick(now);
            player.rotateZ(0.01f);
        }

        buffer.clear(Color.GRAY);

        world.renderScene(buffer);
        world.draw(buffer);
        buffer.update();

        buffer.display(g, 0, 0);

        fpsCount++;
        long elapsed = now - fpsLast;
        if (elapsed > 1000) {
            fps = (int) ((fpsCount * elapsed) / 1000L);
            fpsCount = 0;
            fpsLast = now;
        }

        paintHud(g);
    }

    public void paintHud(Graphics g) {

        String info = fps + " fps";

        g.setColor(Color.YELLOW);
        g.drawString(info, 10, 40);

        info = "Sampling Mode: ";

        switch (SAMPLING_MODE) {
        case FrameBuffer.SAMPLINGMODE_OGSS:
            info += "2x oversampling";
            break;
        case FrameBuffer.SAMPLINGMODE_OGSS_FAST:
            info += "1.5x oversampling";
            break;
        case FrameBuffer.SAMPLINGMODE_OGUS:
            info += "0.5x undersampling";
            break;
        default:
            info += "no oversampling";
            break;
        }

        info += " (press 's' to change)";

        g.setColor(Color.YELLOW);
        g.drawString(info, 10, getHeight() - 60);

        info = "Upper Anim: ";
        switch (player.upperModel.aniSequence) {
        case AniCfgQ3Upper.ATTACK:
            info += "ATTACK";
            break;
        case AniCfgQ3Upper.ATTACK2:
            info += "ATTACK_2";
            break;
        case AniCfgQ3Upper.DROP:
            info += "DROP";
            break;
        case AniCfgQ3Upper.GESTURE:
            info += "GESTURE";
            break;
        case AniCfgQ3Upper.RAISE:
            info += "RAISE";
            break;
        case AniCfgQ3Upper.STAND:
            info += "STAND";
            break;
        case AniCfgQ3Upper.STAND2:
            info += "STAND_2";
            break;
        default:
            info += "???";
            break;
        }

        info += " (press 'u' to change) => "
                + (int) (player.upperModel.aniIndex * 100) + "%";

        g.setColor(Color.YELLOW);
        g.drawString(info, 10, getHeight() - 40);

        info = "Lower Anim: ";
        switch (player.lowerModel.aniSequence) {
        case AniCfgQ3Lower.BACK:
            info += "BACK";
            break;
        case AniCfgQ3Lower.IDLE:
            info += "IDLE";
            break;
        case AniCfgQ3Lower.IDLECR:
            info += "IDLE_CR";
            break;
        case AniCfgQ3Lower.JUMP:
            info += "JUMP";
            break;
        case AniCfgQ3Lower.JUMPB:
            info += "JUMP_B";
            break;
        case AniCfgQ3Lower.LAND:
            info += "LAND";
            break;
        case AniCfgQ3Lower.LANDB:
            info += "LAND_B";
            break;
        case AniCfgQ3Lower.RUN:
            info += "RUN";
            break;
        case AniCfgQ3Lower.SWIM:
            info += "SWIM";
            break;
        case AniCfgQ3Lower.TURN:
            info += "TURN";
            break;
        case AniCfgQ3Lower.WALK:
            info += "WALK";
            break;
        case AniCfgQ3Lower.WALKCR:
            info += "WALK_CR";
            break;
        default:
            info += "???";
            break;
        }

        info += " (press 'l' to change) => "
                + (int) (player.lowerModel.aniIndex * 100) + "%";

        g.setColor(Color.YELLOW);
        g.drawString(info, 10, getHeight() - 20);
    }

    public void run() {
        while (thread == Thread.currentThread()) {
            repaint();
            try {
                Thread.yield();
            } catch (Exception ignored) {
            }
        }
    }

    public void setSamplingMode(int samplingMode) {
        SAMPLING_MODE = samplingMode;
        currentDimension = new Dimension(-1, -1);
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
