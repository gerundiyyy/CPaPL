package org.gerundiyyy;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class AnimPanel extends JPanel {
    private final ShipPainter ship = new ShipPainter();
    private final CannonPainter canon = new CannonPainter();
    private final Vector<BallInstance> balls;
    private int startBallX;
    private int startBallY;
    private ShipMotion shipMotion;

    public AnimPanel(int startShipX, int startShipY, int startBallX, int startBallY) {
        this.startBallX = startBallX;
        this.startBallY = startBallY;
        setPreferredSize(new Dimension(600, 400));
        addComponentListener(new AnimPanelListener(this, startShipX, startShipY));
        balls = new Vector<>();
    }

    public void startShipMotion(int startShipX, int startShipY) {
        shipMotion = new ShipMotion(startShipX, startShipY,
                ThreadLocalRandom.current().nextInt(2, 5),
                ThreadLocalRandom.current().nextInt(0, 3),
                ship, getWidth(), getHeight() * 2 / 3);
        new Thread(shipMotion).start();
        Timer timer = new Timer(16, new TimerListener(this));
        timer.start();
    }

    public void startBallMotion(BallInstance ballInst) {
        ballInst.setMotion(new BallMotion(startBallX, startBallY,
                ThreadLocalRandom.current().nextInt(-5, 5),
                ThreadLocalRandom.current().nextInt(-3, 3),
                ballInst.getBall(), getWidth(), getHeight() * 2 / 3));
        new Thread(ballInst.getMotion()).start();
        Timer timer = new Timer(16, new TimerListener(this));
        timer.start();
    }

    public void spawnBall(BallInstance ballInst){
        balls.add(ballInst);
        startBallMotion(ballInst);
        repaint();
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
            paintBalls(g2);
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

    private void paintBalls(Graphics2D g2) {
        for(BallInstance ballInst : balls){
            if (ballInst.getMotion() != null) {
                g2.translate(ballInst.getMotion().getCoordX(), ballInst.getMotion().getCoordY());
                ballInst.getBall().draw(g2);
                g2.translate(-ballInst.getMotion().getCoordX(), -ballInst.getMotion().getCoordY());
            }
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
}
