package org.gerundiyyy.example;

import javax.swing.*;

public class RunAppletAsFrame {
    public static void main(String[] args) {
        // Создаём экземпляр апплета и вызываем его init (как это делает контейнер)
        DrawHouseThreadApplet applet = new DrawHouseThreadApplet();
        applet.init();

        // Запуск GUI в EDT
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Applet in Frame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(applet);
            frame.setSize(420, 440);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Сообщаем апплету, что он "запущен" (аналог start() в контейнере)
            applet.start();
        });
    }
}
