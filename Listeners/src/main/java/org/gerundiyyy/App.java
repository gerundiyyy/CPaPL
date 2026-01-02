package org.gerundiyyy;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        FrameManager frame = new FrameManager();
        frame.setTitle("Radiobutton to button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
