package org.gerundiyyy;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public interface Drawable {
    void draw(Graphics2D g2);
    void createShipShape();
    Rectangle2D getBounds();
}
