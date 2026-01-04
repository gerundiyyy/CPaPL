package org.gerundiyyy;

import javax.swing.*;

public class FrameManager extends JFrame {
    final private AnimPanel ap = new AnimPanel(30,30);

    FrameManager(){
        add(ap);
    }
}
