package org.gerundiyyy;

public class BallInstance {
    private BallPainter ball;
    private BallMotion motion;

    public BallPainter getBall() {
        return ball;
    }

    public void setBall(BallPainter ball) {
        this.ball = ball;
    }

    public BallMotion getMotion() {
        return motion;
    }

    public void setMotion(BallMotion motion) {
        this.motion = motion;
    }

    public BallInstance(BallPainter ball) {
        this.ball = ball;
    }
}
