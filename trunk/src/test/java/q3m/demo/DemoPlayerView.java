package q3m.demo;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import q3m.Q3M;
import q3m.jpct.Q3Player;
import q3m.jpct.shader.Q3ShaderUtil;
import q3m.q3.ani.AniCfgQ3Lower;
import q3m.q3.ani.AniCfgQ3Upper;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.IRenderer;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

public class DemoPlayerView extends JPanel implements Runnable {

    private static final long serialVersionUID = 1L;

    private Point dragStart = null;

    class MouseHandler extends MouseAdapter {

        public void mousePressed(MouseEvent event) {
            dragStart = event.getPoint();
        }

        public void mouseReleased(MouseEvent e) {
            dragStart = null;
        }

    }

    class MouseMotionHandler extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent event) {
            if (player != null) {
                player.rotateZ(((int) dragStart.getX() - event.getX()) / 200f);
                player.rotateY((event.getY() - (int) dragStart.getY()) / 200f);
            }
            dragStart = event.getPoint();
        }
    }

    class MouseWheelHandler implements MouseWheelListener {
        public void mouseWheelMoved(MouseWheelEvent e) {
            if ((world != null) && (player != null)) {

                int x = e.getWheelRotation();

                SimpleVector camPos = world.getCamera().getPosition();
                camPos.x -= x;
                if (camPos.x < 0)
                    camPos.x = 0;

                camPos.y = camPos.x / 15;
                world.getCamera().setPosition(camPos);

                world.getCamera().lookAt(player.getTransformedCenter());

                camPos.y = 0;
                world.getCamera().setPosition(camPos);
            }
        }
    }

    private Demo demo = null;
    private Canvas glCanvas = null;
    private volatile Thread thread = null;
    private Exception error = null;
    private int fps = 0;
    private int fpsCount = 0;
    private long fpsLast = System.currentTimeMillis();
    private FrameBuffer frameBuffer;
    private World world = null;
    private Object3D sceneRoot = null;
    protected Q3Player player = null;

    public DemoPlayerView(Demo demo) {
        this.demo = demo;
        setLayout(new BorderLayout(0, 0));
        setMaximumSize(new Dimension(Demo.DEFAULT_WIDTH, Demo.DEFAULT_HEIGHT));
        setMinimumSize(new Dimension(Demo.DEFAULT_WIDTH, Demo.DEFAULT_HEIGHT));
        setPreferredSize(new Dimension(Demo.DEFAULT_WIDTH, Demo.DEFAULT_HEIGHT));
        setSize(new Dimension(Demo.DEFAULT_WIDTH, Demo.DEFAULT_HEIGHT));

        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    private void onRun() throws Exception {
        paint(getGraphics());
    }

    private void onSetup() throws Exception {

        frameBuffer = new FrameBuffer(getWidth(), getHeight(),
                FrameBuffer.SAMPLINGMODE_HARDWARE_ONLY);

        frameBuffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        frameBuffer.enableRenderer(IRenderer.RENDERER_OPENGL);
        glCanvas = frameBuffer.enableGLCanvasRenderer();
        add(glCanvas, BorderLayout.CENTER);

        glCanvas.addMouseListener(new MouseHandler());
        glCanvas.addMouseMotionListener(new MouseMotionHandler());
        glCanvas.addMouseWheelListener(new MouseWheelHandler());

        world = new World();
        world.setAmbientLight(255, 255, 255);
        World.setDefaultThread(Thread.currentThread());

        paint(getGraphics());

        Q3ShaderUtil.getInstance().addShaderDefs(
                Q3M.getResStream("/res/q3/scripts/IW-csm.shader"));

        player = new Q3Player("Chaos-Marine", "Iron-Warrior");// = 'default'

        player.setUpperSequence(AniCfgQ3Upper.STAND);
        player.setLowerSequence(AniCfgQ3Lower.IDLE);

        player.addTo(world);

        sceneRoot = Object3D.createDummyObj();
        sceneRoot.rotateX((float) (-90f * Math.PI / 180f));
        sceneRoot.addChild(player);

        world.getCamera().setPosition(100, 100 / 15f, 0);
        world.getCamera().lookAt(player.getTransformedCenter());
        world.getCamera().setPosition(100, 0, 0);

        demo.controls.enable();
    }

    private void onShutdown() {
        if (frameBuffer != null)
            frameBuffer.dispose();
    }

    public synchronized void paint(Graphics g) {

        if ((frameBuffer == null) || (world == null))
            return;

        long now = System.currentTimeMillis();

        fpsCount++;
        long elapsed = now - fpsLast;
        if (elapsed > 1000) {
            fps = (int) ((fpsCount * elapsed) / 1000L);
            fpsCount = 0;
            fpsLast = now;
            demo.controls.setLabelFps("" + fps);
        }

        if (player != null)
            player.aniTick(now);

        frameBuffer.clear(Color.GRAY);
        world.renderScene(frameBuffer);
        world.draw(frameBuffer);
        frameBuffer.update();
        frameBuffer.displayGLOnly();
        glCanvas.repaint();
    }

    public void run() {
        try {
            onSetup();
            while ((thread == Thread.currentThread()) && (error == null)) {
                Thread.yield();
                onRun();
            }
        } catch (Exception e) {
            e.printStackTrace();
            error = e;
        } finally {
            onShutdown();
        }
    }

    public void stop() {
        thread = null;
    }

}
