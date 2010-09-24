package q3m.demo;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Demo extends JFrame {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 480;
    public static final int MENU_WIDTH = 140;

    class CloseHandler extends WindowAdapter {

        public void windowClosed(WindowEvent event) {
            System.exit(0);
        }

        public void windowClosing(WindowEvent event) {
            event.getWindow().dispose();
        }
    }

    protected DemoControls controls;
    protected DemoPlayerView playerView;

    public Demo() {
        setTitle("q3m4jPCT Demo");
        getContentPane().setLayout(new BorderLayout(0, 0));
        controls = new DemoControls(this);
        getContentPane().add(controls, BorderLayout.EAST);
        playerView = new DemoPlayerView(this);
        getContentPane().add(playerView, BorderLayout.CENTER);
        addWindowListener(new CloseHandler());
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Demo();
    }

}
