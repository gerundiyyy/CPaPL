package org.gerundiyyy;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShotListener extends MouseAdapter {
    private final AnimPanel ap;

    public ShotListener(AnimPanel ap) {
        this.ap = ap;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Проверяем, что нажата левая кнопка мыши
        if (e.getButton() == MouseEvent.BUTTON1) {
            BallInstance ballInstance = new BallInstance(new BallPainter());
            ap.spawnBall(ballInstance);
        }
    }
}
