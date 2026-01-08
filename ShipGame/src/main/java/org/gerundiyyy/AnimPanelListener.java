package org.gerundiyyy;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AnimPanelListener extends ComponentAdapter{
    final private AnimPanel ap;
    final private int startX;
    final private int startY;

    AnimPanelListener(AnimPanel ap, int startX, int startY){
        this.ap = ap;
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public void componentShown(ComponentEvent e) {
        ap.startShipMotion(startX, startY);
        ap.addMouseListener(new ShotListener(ap));
    }
    @Override
    public void componentResized(ComponentEvent e) {
        ap.startShipMotion(startX, startY);
        ap.addMouseListener(new ShotListener(ap));
    }
}
