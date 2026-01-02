package org.gerundiyyy.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class AnimationFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Smooth Animation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new AnimationPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

class AnimationPanel extends JPanel {
    private final BufferedImage sprite;
    private int x = 0, y = 50;
    private int dx = 4, dy = 2;
    private final Timer timer;

    public AnimationPanel() {
        setPreferredSize(new Dimension(800, 480));
        setDoubleBuffered(true); // Swing уже делает это, но явно — не помешает

        // Загрузка ассета (положи sprite.png в resources или рядом с классом)
        sprite = loadImage("/sprite.png"); // пример: ресурс в classpath
        // Если нет ассета, можно создать временный образ
        // BufferedImage sprite = createPlaceholder();

        // Таймер обновляет модель и вызывает repaint на EDT
        timer = new Timer(16, e -> { // ~60 FPS
            updatePosition();
            repaint();
        });
        timer.start();
    }

    private BufferedImage loadImage(String resourcePath) {
        try {
            URL res = getClass().getResource(resourcePath);
            if (res != null) return ImageIO.read(res);
        } catch (IOException ignored) {}
        // fallback: простая заглушка
        BufferedImage img = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.MAGENTA);
        g.fillOval(0, 0, 80, 80);
        g.dispose();
        return img;
    }

    private void updatePosition() {
        x += dx;
        y += dy;
        if (x < 0 || x + sprite.getWidth() > getWidth()) dx = -dx;
        if (y < 0 || y + sprite.getHeight() > getHeight()) dy = -dy;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // очищает фон
        Graphics2D g2 = (Graphics2D) g.create();
        // сглаживание и качество
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // рисуем фон
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // рисуем ассет
        g2.drawImage(sprite, x, y, this);

        g2.dispose();
    }
}
