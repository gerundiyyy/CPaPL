package org.gerundiyyy.example;
import java.awt.*;
import java.awt.event.*;

public class MyButton {
    public static void main(String[] args) {
        MyButtonFrame frm = new MyButtonFrame();
        frm.show();
    }
}

/* A frame with a panel of buttons */
class MyButtonFrame extends Frame {
    public MyButtonFrame() {
        setTitle("To Test Button Event");
        setSize(300, 200);

        MyButtonPanel panel = new MyButtonPanel();
        add(panel);
    }
}

/* A panel of three buttons. */
class MyButtonPanel extends Panel {
    public MyButtonPanel() {
        // create buttons
        Button bButton = new Button("Blue");
        Button rButton = new Button("Green");
        Button eButton = new Button("Exit");

        // Add buttons to panel
        add(bButton);
        add(rButton);
        add(eButton);

        // Create button actions
        MyListenerAction bAction = new MyListenerAction(Color.blue);
        MyListenerAction rAction = new MyListenerAction(Color.green);
        MyListenerAction eAction = new MyListenerAction(Color.red);

        // Add Listener object to Buttons
        bButton.addActionListener(bAction);
        rButton.addActionListener(rAction);
        eButton.addActionListener(rAction);
    }

    // Action listener Class which is used to set background color
    private class MyListenerAction implements ActionListener {
        private Color bgColor;

        public MyListenerAction(Color c) {
            bgColor = c;
        }

        public void actionPerformed(ActionEvent event) {
            setBackground(bgColor);
            repaint();

            if (event.getActionCommand() == "Exit") {
                System.exit(0);
            }
        }
    }
}

