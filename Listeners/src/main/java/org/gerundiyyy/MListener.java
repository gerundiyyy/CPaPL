package org.gerundiyyy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MListener extends MouseAdapter {

    @Override
    public void mouseEntered(MouseEvent evt) {
        JComponent comp = (JComponent) evt.getComponent();
        FrameManager frame = (FrameManager) SwingUtilities.getWindowAncestor(comp);
        Container cp = frame.getContentPane();

        cp.remove(frame.getJRadioButtons());
        cp.add(frame.getButtons());
        cp.revalidate();
        cp.repaint();
    }

    public void mouseExited(MouseEvent evt) {
        JComponent comp = (JComponent) evt.getComponent();
        FrameManager frame = (FrameManager) SwingUtilities.getWindowAncestor(comp);
        Container cp = frame.getContentPane();

        cp.remove(frame.getButtons());
        cp.add(frame.getJRadioButtons());
        cp.revalidate();
        cp.repaint();
    }
}
