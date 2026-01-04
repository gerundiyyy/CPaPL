package org.gerundiyyy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.ThreadLocalRandom;

public class AnimPanel extends JPanel {
    private ShipPainter ship = new ShipPainter();
    private ShipMotion shipMotion;
    private Timer timer;

    public AnimPanel(int startX, int startY) {
        // не запускаем motion в конструкторе, ждём размера панели
        setPreferredSize(new Dimension(600, 400));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                startMotion(startX, startY);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                startMotion(startX, startY);
            }
        });
    }

    public void startMotion(int startX, int startY) {
        shipMotion = new ShipMotion(startX, startY,
                ThreadLocalRandom.current().nextInt(2, 5),
                ThreadLocalRandom.current().nextInt(0, 3),
                ship, getWidth(), getHeight() * 2 / 3);
        new Thread(shipMotion).start();
        timer = new Timer(16, new TimerListener(this));
        timer.start();
    }

    public void setShipMotion(ShipMotion shipMotion) {
        this.shipMotion = shipMotion;
    }

    public ShipMotion getShipMotion() {
        return shipMotion;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            paintBackgroud(g2);
            if (shipMotion != null) { g2.translate(shipMotion.getShipCoordX(), shipMotion.getShipCoordY()); }
            ship.draw(g2);
        } finally {
            g2.dispose();
        }
    }

    private void paintBackgroud(Graphics2D g2) {
        int w = getWidth();
        int h = getHeight();
        int twoThirds = getHeight() * 2 / 3;

        g2.setColor(new Color(41, 235, 242));
        g2.fillRect(0, 0, w, twoThirds);

        g2.setColor(new Color(242, 193, 41));
        g2.fillRect(0, twoThirds, w, h - twoThirds);
    }

    private static class TransformSaver {
        private final Graphics2D g;
        private final java.awt.geom.AffineTransform old;

        TransformSaver(Graphics2D g) {
            this.g = g;
            this.old = g.getTransform();
        }

        void restore() {
            g.setTransform(old);
        }
    }
}
