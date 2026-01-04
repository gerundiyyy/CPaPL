package org.gerundiyyy;

import java.awt.geom.Rectangle2D;

// ShipMotion.java
public class ShipMotion extends MotionModel implements Runnable {
    private final ShipPainter painter;
    private final int seaW, seaY;
    private volatile boolean running = true;

    public ShipMotion(int x, int y, int sx, int sy, ShipPainter painter, int seaW, int seaY) {
        super(x, y, sx, sy);
        this.painter = painter;
        this.seaW = seaW;
        this.seaY = seaY;
    }

    protected void updatePosition() {
        shipCoordX += shipSpeedX;
        shipCoordY += shipSpeedY;
        Rectangle2D bounds = painter.getBounds();
        int shipW = (int) Math.ceil(bounds.getWidth());
        int shipH = (int) Math.ceil(bounds.getHeight());

        if (shipCoordX < 0 || shipCoordX + shipW / 2 > seaW) shipSpeedX = -shipSpeedX;
        if (shipCoordY < 0 || shipCoordY + shipH / 2 > seaY) shipSpeedY = -shipSpeedY;
    }

    @Override
    public void run() {
        while (running) {
            updatePosition();
            try {
                Thread.sleep(16); // ~60 обновлений в секунду
            } catch (InterruptedException e) {
                running = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() { running = false; }

    // безопасные геттеры
    public int getShipCoordX() { return shipCoordX; }
    public int getShipCoordY() { return shipCoordY; }
}
