package org.gerundiyyy;


import java.awt.geom.Rectangle2D;

public class BallMotion extends MotionModel implements Runnable {
    private final BallPainter painter;
    private final int seaW, seaH;
    private volatile boolean running = true;

    public BallMotion(int CoordX, int CoordY, int SpeedX, int SpeedY,
                      BallPainter painter, int seaW, int seaH) {
        super(CoordX, CoordY, SpeedX, SpeedY);
        this.painter = painter;
        this.seaW = seaW;
        this.seaH = seaH;
    }

    protected void updatePosition() {
        CoordX += SpeedX;
        CoordY += SpeedY;
        Rectangle2D bounds = painter.getBounds();
        int ballW = (int) Math.ceil(bounds.getWidth());
        int ballH = (int) Math.ceil(bounds.getHeight());

        if (CoordX < 0 || CoordX + ballW / 2 > seaW) SpeedX = -SpeedX;
        if (CoordY < 0 || CoordY + ballH / 2 > seaH) SpeedY = -SpeedY;
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
    public int getCoordX() { return CoordX; }
    public int getCoordY() { return CoordY; }
}

