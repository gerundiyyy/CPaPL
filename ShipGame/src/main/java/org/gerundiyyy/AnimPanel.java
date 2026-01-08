package org.gerundiyyy;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class AnimPanel extends JPanel {
    private ShipPainter ship = new ShipPainter();
    private final CannonPainter canon = new CannonPainter();
    private final Vector<BallInstance> balls;
    private ShipMotion shipMotion;
    private ExplosionPainter exp = new ExplosionPainter();

    public AnimPanel(int startShipX, int startShipY) {
        setPreferredSize(new Dimension(600, 400));
        addComponentListener(new AnimPanelListener(this, startShipX, startShipY));
        balls = new Vector<>();
    }

    public void startShipMotion(int startShipX, int startShipY) {
        shipMotion = new ShipMotion(startShipX, startShipY,
                ThreadLocalRandom.current().nextInt(2, 5),
                ThreadLocalRandom.current().nextInt(0, 3), true,
                ship, getWidth(), getHeight() * 2 / 3);
        new Thread(shipMotion).start();
        Timer timer = new Timer(16, new ShipTimerListener(this));
        timer.start();
    }

    public void startBallMotion(BallInstance ballInst) {
        int startBallX = getWidth()/2;
        int startBallY = getHeight() * 2 / 3 - 20;

        ballInst.setMotion(new BallMotion(startBallX, startBallY,
                ThreadLocalRandom.current().nextInt(-5, 5),
                ThreadLocalRandom.current().nextInt(-3, 3), true,
                ballInst.getBall(), getWidth(), getHeight() * 2 / 3));
        new Thread(ballInst.getMotion()).start();
        Timer timer = new Timer(16, new BallTimerListener(this, ballInst, 100));
        timer.start();
    }

    public void spawnBall(BallInstance ballInst){
        balls.add(ballInst);
        startBallMotion(ballInst);
        repaint();
    }

    public void removeBall(BallInstance ballInst){
        balls.remove(ballInst);
    }

    public void removeShip() {
        // остановите motion если есть
        if (shipMotion != null) {
            shipMotion.setRunning(false); // или другой способ остановки
        }
        ship = null;
        SwingUtilities.invokeLater(this::repaint);
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
            paintExplosion(g2);
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
        int startCanonX = getWidth()/2;
        int startCanonY = getHeight() * 2 / 3 - 20;

        if(canon != null){
            g2.translate(startCanonX, startCanonY);
            canon.draw(g2);
            g2.translate(-startCanonX, -startCanonY);
        }
    }

    private void paintShip(Graphics2D g2) {
        if (ship != null && shipMotion != null) {
            g2.translate(shipMotion.getCoordX(), shipMotion.getCoordY());
            ship.draw(g2);
            g2.translate(-shipMotion.getCoordX(), -shipMotion.getCoordY());
        }
    }

    public void paintExplosion(Graphics2D g2) {
        if (exp != null && ship == null){
            int Xstep = 0;
            int Ystep = 0;
            for(int i = 0 ; i < 3; i++){
                Xstep = ThreadLocalRandom.current().nextInt(-30, 30);
                Ystep = ThreadLocalRandom.current().nextInt(-30, 30);
                g2.translate(shipMotion.getCoordX() + Xstep, shipMotion.getCoordY() + Ystep);
                exp.draw(g2);
                g2.translate(-shipMotion.getCoordX() - Xstep, -shipMotion.getCoordY() - Ystep);
            }
        }
    }

    public void setShipMotion(ShipMotion shipMotion) {
        this.shipMotion = shipMotion;
    }

    public ShipMotion getShipMotion() {
        return shipMotion;
    }

    public ShipPainter getShip() {
        return ship;
    }

    public CannonPainter getCanon() {
        return canon;
    }

    public Vector<BallInstance> getBalls() {
        return balls;
    }
}
