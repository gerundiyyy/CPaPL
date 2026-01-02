package org.Listeners;

import javax.swing.*;
import java.awt.*;

public class FrameManager extends JFrame {
    private final JLabel MainLabel = new JLabel("Наведитесь на эту надпись");

    private final JButton b1 = new JButton("button1");
    private final JButton b2 = new JButton("button2");
    private final JButton b3 = new JButton("button3");
    private final JButton b4 = new JButton("button4");

    private final JCheckBox ch1 = new JCheckBox("checkbox1");
    private final JCheckBox ch2 = new JCheckBox("checkbox2");
    private final JCheckBox ch3 = new JCheckBox("checkbox3");
    private final JCheckBox ch4 = new JCheckBox("checkbox4");

    FrameManager(){
        initComponents();
        initActions();
    }

    public JPanel getButtons(){
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));

        btnPanel.add(b1);
        btnPanel.add(b2);
        btnPanel.add(b3);
        btnPanel.add(b4);

        return btnPanel;
    }

    public JPanel getCheckBoxes(){
        JPanel chbPanel = new JPanel();
        chbPanel.setLayout(new BoxLayout(chbPanel, BoxLayout.Y_AXIS));

        chbPanel.add(ch1);
        chbPanel.add(ch2);
        chbPanel.add(ch3);
        chbPanel.add(ch4);

        return chbPanel;
    }

    private void initComponents(){
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

        add(MainLabel);
        add(getCheckBoxes());
    }

    private void initActions(){
        MainLabel.addMouseListener(new MListener());
    }
}
