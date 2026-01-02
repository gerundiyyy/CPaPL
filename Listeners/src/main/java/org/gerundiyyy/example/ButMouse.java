package org.gerundiyyy.example;

import javax.swing.*;
import java.awt.event.*;

public class ButMouse {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Главное окно");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        MyMouseListener listener = new MyMouseListener();

        // мост между стандартным MouseListener и твоим кастомным
        frame.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                listener.mousePressed(e, frame);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                listener.mouseReleased(e, frame);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                listener.mouseClicked(e, frame);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                listener.mouseEntered(e, frame);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                listener.mouseExited(e, frame);
            }
        });
    }
}

interface MouseListener {
    void mousePressed(MouseEvent evt, JFrame frame);
    void mouseReleased(MouseEvent evt, JFrame frame);
    void mouseClicked(MouseEvent evt, JFrame frame);
    void mouseEntered(MouseEvent evt, JFrame frame);
    void mouseExited(MouseEvent evt, JFrame frame);
}

class MyMouseListener implements MouseListener {

    // общий метод для показа сообщений
    private void showMessage(JFrame frame, String text) {
        JOptionPane.showMessageDialog(
                frame,
                text,
                "Сообщение",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void mousePressed(MouseEvent e, JFrame frame) {
        showMessage(frame, "Mouse-button pressed!");
    }

    @Override
    public void mouseReleased(MouseEvent e, JFrame frame) {
        showMessage(frame, "Mouse-button released!");
    }

    @Override
    public void mouseClicked(MouseEvent e, JFrame frame) {
        showMessage(frame, "Mouse-button clicked (pressed and released)!");
    }

    @Override
    public void mouseEntered(MouseEvent e, JFrame frame) {
        showMessage(frame, "Mouse-pointer entered the source component!");
    }

    @Override
    public void mouseExited(MouseEvent e, JFrame frame) {
        showMessage(frame, "Mouse exited-pointer the source component!");
    }
}
