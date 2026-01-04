package org.gerundiyyy;

import java.awt.geom.Rectangle2D;

public abstract class MotionModel {
    protected int shipCoordX;
    protected int shipCoordY;
    protected int shipSpeedX;
    protected int shipSpeedY;

    public MotionModel(int shipCoordX, int shipCoordY, int shipSpeedX, int shipSpeedY) {
        this.shipCoordX = shipCoordX;
        this.shipCoordY = shipCoordY;
        this.shipSpeedX = shipSpeedX;
        this.shipSpeedY = shipSpeedY;
    }

    protected abstract void updatePosition();

    // Координаты
    public int getShipCoordX() {
        return shipCoordX;
    }

    public void setShipCoordX(int shipCoordX) {
        this.shipCoordX = shipCoordX;
    }

    public int getShipCoordY() {
        return shipCoordY;
    }

    public void setShipCoordY(int shipCoordY) {
        this.shipCoordY = shipCoordY;
    }

    // Скорости
    public int getShipSpeedX() {
        return shipSpeedX;
    }

    public void setShipSpeedX(int shipSpeedX) {
        this.shipSpeedX = shipSpeedX;
    }

    public int getShipSpeedY() {
        return shipSpeedY;
    }

    public void setShipSpeedY(int shipSpeedY) {
        this.shipSpeedY = shipSpeedY;
    }

    // Удобные методы для установки/получения пары значений
    public void setPosition(int x, int y) {
        this.shipCoordX = x;
        this.shipCoordY = y;
    }

    public void setSpeed(int vx, int vy) {
        this.shipSpeedX = vx;
        this.shipSpeedY = vy;
    }
}
