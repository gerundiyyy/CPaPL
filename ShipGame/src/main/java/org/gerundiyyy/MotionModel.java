package org.gerundiyyy;

import java.awt.geom.Rectangle2D;

public abstract class MotionModel {
    protected int CoordX, CoordY, SpeedX, SpeedY;
    protected int modelW, modelH;
    protected volatile boolean running;
    protected Rectangle2D RectCollision;

    public MotionModel(int CoordX, int CoordY, int SpeedX, int SpeedY, boolean running) {
        this.CoordX = CoordX;
        this.CoordY = CoordY;
        this.SpeedX = SpeedX;
        this.SpeedY = SpeedY;
        this.running = running;
    }

    public void stop() {
        running = false;
    }

    protected abstract void updatePosition();
    protected abstract void calculateBounds();
    public abstract void checkWBorder();
    public abstract void checkHBorder();

    // Синхронизированные геттеры/сеттеры для потокобезопасности
    public synchronized int getCoordX() {
        return CoordX;
    }

    public synchronized void setCoordX(int coordX) {
        this.CoordX = coordX;
    }

    public synchronized int getCoordY() {
        return CoordY;
    }

    public synchronized void setCoordY(int coordY) {
        this.CoordY = coordY;
    }

    public synchronized int getSpeedX() {
        return SpeedX;
    }

    public synchronized void setSpeedX(int speedX) {
        this.SpeedX = speedX;
    }

    public synchronized int getSpeedY() {
        return SpeedY;
    }

    public synchronized void setSpeedY(int speedY) {
        this.SpeedY = speedY;
    }

    public synchronized void setPosition(int x, int y) {
        this.CoordX = x;
        this.CoordY = y;
    }

    public synchronized void setSpeed(int vx, int vy) {
        this.SpeedX = vx;
        this.SpeedY = vy;
    }

    // Координаты столкновения — центр модели
    protected synchronized void calculateRectCollision() {
        RectCollision = new Rectangle2D.Double(CoordX / 2,CoordY / 2, modelW / 2, modelH / 2);
    }

    public synchronized Rectangle2D getRectCollision() {
        return RectCollision;
    }

    public synchronized void setRectCollision(Rectangle2D RectCollision) {
        this.RectCollision = RectCollision;
    }

    public synchronized int getModelW() {
        return modelW;
    }

    public synchronized void setModelW(int modelW) {
        this.modelW = modelW;
    }

    public synchronized int getModelH() {
        return modelH;
    }

    public synchronized void setModelH(int modelH) {
        this.modelH = modelH;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }
}
