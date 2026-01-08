package org.gerundiyyy;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class ExplosionPainter implements Drawable {
    private Path2D shape;

    public ExplosionPainter() {
        shape = new Path2D.Float();
        createShapeOfTheFigure();
    }

    @Override
    public void draw(Graphics2D g2) {
        Graphics2D g = (Graphics2D) g2.create();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(new Color(255, 140, 0)); // оранжево-жёлтый
            g.fill(shape);
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(1.5f));
            g.draw(shape);
        } finally {
            g.dispose();
        }
    }

    public void createShapeOfTheFigure() {
        shape = new Path2D.Float();

        double cx = 40;    // центр X
        double cy = 20;    // центр Y
        double outerR = 40; // внешний радиус
        double innerR = 16; // внутренний радиус
        int points = 8;     // количество лучей

        double angleStep = Math.PI / points;
        double angle = -Math.PI / 2;

        shape.moveTo(cx + Math.cos(angle) * outerR, cy + Math.sin(angle) * outerR);

        for (int i = 1; i < points * 2; i++) {
            angle += angleStep;
            double r = (i % 2 == 0) ? outerR : innerR;
            shape.lineTo(cx + Math.cos(angle) * r, cy + Math.sin(angle) * r);
        }
        shape.closePath();
    }

    public Rectangle2D getBounds() {
        return shape.getBounds2D();
    }
}
