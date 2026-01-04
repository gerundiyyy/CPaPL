package org.gerundiyyy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerListener implements ActionListener {
    final private AnimPanel ap;

    TimerListener(AnimPanel ap){
        this.ap = ap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ap.repaint();
    }
}
