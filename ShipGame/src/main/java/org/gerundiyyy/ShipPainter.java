package org.gerundiyyy;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class ShipPainter implements Drawable {
    private Path2D shape;

    ShipPainter(){
        shape = new Path2D.Float();
        createShapeOfTheFigure();
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(185, 41, 242));
        g2.fill(shape); // контур
        g2.setColor(Color.BLACK);
        g2.draw(shape);
    }

    public void createShapeOfTheFigure() {
        shape = new Path2D.Float();
        // корпус
        shape.moveTo(0, 20);
        shape.lineTo(40, 0);
        shape.lineTo(80, 20);
        shape.closePath();
        // парус
        shape.moveTo(40, 0);
        shape.lineTo(40, -40);
        shape.lineTo(60, 0);
        shape.closePath();
    }
    public Rectangle2D getBounds() {
        return shape.getBounds2D();
    }
}
