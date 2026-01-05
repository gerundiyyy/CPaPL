package org.gerundiyyy;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class BallPainter implements Drawable  {
    private Path2D shape;

    BallPainter(){
        shape = new Path2D.Float();
        createShapeOfTheFigure();
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(237, 117, 64));
        g2.fill(shape); // контур
        g2.setColor(Color.BLACK);
        g2.draw(shape);
    }

    public void createShapeOfTheFigure() {
        shape = new Path2D.Float();
        // корпус
        shape.moveTo(-10, 0);
        shape.lineTo(10, 0);
        shape.lineTo(20, 10);
        shape.lineTo(10, 20);
        shape.lineTo(-10, 20);
        shape.lineTo(-20, 10);
        shape.lineTo(-10, 0);
        shape.closePath();
    }
    public Rectangle2D getBounds() {
        return shape.getBounds2D();
    }
}
