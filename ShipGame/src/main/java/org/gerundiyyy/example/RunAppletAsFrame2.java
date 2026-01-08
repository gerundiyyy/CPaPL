package org.gerundiyyy.example;

import javax.swing.*;

public class RunAppletAsFrame2 {
    public static void main(String[] args) {
        // Создаём экземпляр апплета и вызываем init (как контейнер)
        AppletThreadSample applet = new AppletThreadSample();
        applet.init();

        // Показываем в EDT
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Applet in Frame");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(applet);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Сообщаем апплету, что он "запущен"
            applet.start();

            // При закрытии окна корректно остановим апплет и дочерние потоки
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    try {
                        applet.stop();
                    } catch (Throwable ignored) {}
                    try {
                        applet.destroy();
                    } catch (Throwable ignored) {}
                }
            });
        });
    }
}
