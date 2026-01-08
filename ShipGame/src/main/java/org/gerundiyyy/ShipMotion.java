package org.gerundiyyy;

import java.awt.geom.Rectangle2D;

public class ShipMotion extends MotionModel implements Runnable {
    private final Object lock = new Object();
    private final ShipPainter painter;
    private final int seaW, seaH;

    public ShipMotion(int CoordX, int CoordY, int SpeedX, int SpeedY, boolean running,
                      ShipPainter painter, int seaW, int seaH) {
        super(CoordX, CoordY, SpeedX, SpeedY, running);
        this.painter = painter;
        this.seaW = seaW;
        this.seaH = seaH;

        calculateBounds();
        calculateRectCollision();
    }

    @Override
    protected void updatePosition() {
        synchronized (lock) {
            calculateBounds(); // обновляет modelW/modelH
            CoordX += SpeedX;
            CoordY += SpeedY;
            // корректные проверки границ с учётом ширины/высоты модели
            checkWBorder();
            checkHBorder();
            calculateRectCollision();
        }
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

    @Override
    public void calculateBounds(){
        if (painter != null){
            Rectangle2D bounds = painter.getBounds();
            modelW = (int) Math.ceil(bounds.getWidth());
            modelH = (int) Math.ceil(bounds.getHeight());
        }
    }

    @Override
    public synchronized void checkWBorder(){
        if (CoordX < 0) {
            CoordX = 0;
            SpeedX = -SpeedX;
        } else if (CoordX + modelW > seaW) {
            CoordX = seaW - modelW;
            SpeedX = -SpeedX;
        }
    }

    @Override
    public synchronized void checkHBorder(){
        if (CoordY < 0) {
            CoordY = 0;
            SpeedY = -SpeedY;
        } else if (CoordY + modelH > seaH) {
            CoordY = seaH - modelH;
            SpeedY = -SpeedY;
        }
    }

    public ShipPainter getPainter(){
        return painter;
    }

    public int getSeaW(){
        return seaW;
    }

    public int getSeaH(){
        return seaH;
    }
}
