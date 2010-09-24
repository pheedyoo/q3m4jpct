package q3m.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import q3m.q3.ani.AniCfgQ3Both;

public class DemoControls extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel labelFps;
    private JComboBox upperAni;
    private JComboBox lowerAni;

    public DemoControls(final Demo demo) {

        setBorder(new EmptyBorder(2, 2, 2, 2));
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Demo.MENU_WIDTH, Demo.DEFAULT_HEIGHT));
        setMinimumSize(new Dimension(Demo.MENU_WIDTH, Demo.DEFAULT_HEIGHT));
        setPreferredSize(new Dimension(Demo.MENU_WIDTH, Demo.DEFAULT_HEIGHT));
        setSize(new Dimension(Demo.MENU_WIDTH, Demo.DEFAULT_HEIGHT));

        JPanel fields = new JPanel();
        fields.setLayout(new GridLayout(3, 1));

        JPanel panelStatus = new JPanel();
        panelStatus.setBorder(new TitledBorder(" Renderer "));
        panelStatus.setLayout(new GridLayout(1, 2));

        panelStatus.add(new JLabel("FPS: ", JLabel.RIGHT));
        panelStatus.add(labelFps = new JLabel("---"));;

        fields.add(panelStatus);

        JPanel panelUpper = new JPanel();
        panelUpper.setBorder(new TitledBorder(" Upper animation "));
        panelUpper.setLayout(new GridLayout(1, 1));
        upperAni = new JComboBox(new String[] { "DEATH_1", "DEAD_1", "DEATH_2",
                "DEAD_2", "DEATH_3", "DEAD_3", "GESTURE", "ATTACK", "ATTACK_2",
                "DROP", "RAISE", "STAND", "STAND_2" });
        upperAni.setSelectedItem("STAND");
        upperAni.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    if ((demo.playerView != null)
                            && (demo.playerView.player != null)) {
                        for (int i = 0; i < demo.playerView.player.upperModel.aniCfg
                                .getDefinedSequences(); i++) {
                            if (demo.playerView.player.upperModel.aniCfg
                                    .getSequenceName(i + 1).equals(
                                            upperAni.getSelectedItem())) {
                                demo.playerView.player.setUpperSequence(i + 1);
                                if ((i + 1) <= AniCfgQ3Both.DEAD_3)
                                    lowerAni.setSelectedItem(upperAni
                                            .getSelectedItem());
                                break;
                            }
                        }
                    }
                }
            }
        });
        panelUpper.add(upperAni);
        fields.add(panelUpper);

        JPanel panelLower = new JPanel();
        panelLower.setBorder(new TitledBorder(" Lower animation "));
        panelLower.setLayout(new GridLayout(1, 1));
        lowerAni = new JComboBox(new String[] { "DEATH_1", "DEAD_1", "DEATH_2",
                "DEAD_2", "DEATH_3", "DEAD_3", "WALK_CR", "WALK", "RUN",
                "BACK", "SWIM", "JUMP", "LAND", "JUMP_B", "LAND_B", "IDLE",
                "IDLE_CR", "TURN" });
        lowerAni.setSelectedItem("IDLE");
        lowerAni.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    if ((demo.playerView != null)
                            && (demo.playerView.player != null)) {
                        for (int i = 0; i < demo.playerView.player.lowerModel.aniCfg
                                .getDefinedSequences(); i++) {
                            if (demo.playerView.player.lowerModel.aniCfg
                                    .getSequenceName(i + 1).equals(
                                            lowerAni.getSelectedItem())) {
                                demo.playerView.player.setLowerSequence(i + 1);
                                if ((i + 1) <= AniCfgQ3Both.DEAD_3)
                                    upperAni.setSelectedItem(lowerAni
                                            .getSelectedItem());
                                break;
                            }
                        }
                    }
                }
            }
        });
        panelLower.add(lowerAni);
        fields.add(panelLower);

        fields.add(panelLower);

        add(fields, BorderLayout.NORTH);
        add(new JPanel(), BorderLayout.CENTER);
    }

    public void setLabelFps(String value) {
        if (labelFps != null)
            labelFps.setText(value);
    }
}
