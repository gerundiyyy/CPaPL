package org.gerundiyyy;

public abstract class MotionModel {
    protected int CoordX;
    protected int CoordY;
    protected int SpeedX;
    protected int SpeedY;

    public MotionModel(int CoordX, int CoordY, int SpeedX, int SpeedY) {
        this.CoordX = CoordX;
        this.CoordY = CoordY;
        this.SpeedX = SpeedX;
        this.SpeedY = SpeedY;
    }

    protected abstract void updatePosition();

    // Координаты
    public int getCoordX() {
        return CoordX;
    }

    public void setCoordX(int coordX) {
        this.CoordX = coordX;
    }

    public int getCoordY() {
        return CoordY;
    }

    public void setCoordY(int coordY) {
        this.CoordY = coordY;
    }

    // Скорости
    public int getSpeedX() {
        return SpeedX;
    }

    public void setSpeedX(int speedX) {
        this.SpeedX = speedX;
    }

    public int getSpeedY() {
        return SpeedY;
    }

    public void setSpeedY(int speedY) {
        this.SpeedY = speedY;
    }

    // Удобные методы для установки/получения пары значений
    public void setPosition(int x, int y) {
        this.CoordX = x;
        this.CoordY = y;
    }

    public void setSpeed(int vx, int vy) {
        this.SpeedX = vx;
        this.SpeedY = vy;
    }
}
