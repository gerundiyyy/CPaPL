package org.gerundiyyy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.ThreadLocalRandom;

public class AnimPanel extends JPanel {
    private final ShipPainter ship = new ShipPainter();
    private final CannonPainter canon = new CannonPainter();
    private final BallPainter ball = new BallPainter();
    private ShipMotion shipMotion;
    private BallMotion ballMotion;

    public AnimPanel(int startX, int startY) {
        setPreferredSize(new Dimension(600, 400));
        addComponentListener(new AnimPanelListener(this, startX, startY));
    }

    public void startShipMotion(int startX, int startY) {
        shipMotion = new ShipMotion(startX, startY,
                ThreadLocalRandom.current().nextInt(2, 5),
                ThreadLocalRandom.current().nextInt(0, 3),
                ship, getWidth(), getHeight() * 2 / 3);
        new Thread(shipMotion).start();
        Timer timer = new Timer(16, new TimerListener(this));
        timer.start();
    }

    public void startBallMotion() {
        int startX = 400;
        int startY = 250;

        ballMotion = new BallMotion(startX, startY,
                ThreadLocalRandom.current().nextInt(2, 5),
                ThreadLocalRandom.current().nextInt(0, 3),
                ball, getWidth(), getHeight() * 2 / 3);
        new Thread(ballMotion).start();
        Timer timer = new Timer(16, new TimerListener(this));
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
            paintCanon(g2);
            paintBall(g2);
            paintShip(g2);
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

    private void paintBall(Graphics2D g2) {
        if (ballMotion != null) {
            g2.translate(-400, -250);
            ball.draw(g2);
            g2.translate(-400, -250);
        }
    }

    private void paintCanon(Graphics2D g2) {
        g2.translate(400, 250);
        canon.draw(g2);
        g2.translate(-400, -250);
    }

    private void paintShip(Graphics2D g2) {
        if (shipMotion != null) {
            g2.translate(shipMotion.getCoordX(), shipMotion.getCoordY());
            ship.draw(g2);
            g2.translate(-shipMotion.getCoordX(), -shipMotion.getCoordY());
        }
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
