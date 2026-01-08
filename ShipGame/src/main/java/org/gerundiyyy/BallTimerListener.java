package org.gerundiyyy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BallTimerListener implements ActionListener {
    final private AnimPanel ap;
    final private BallInstance ballInst;
    private final int ticksToDeleete;
    private int tickCounter = 0;

    BallTimerListener(AnimPanel ap, BallInstance ballInst, int ticksToDeleete){
        this.ap = ap;
        this.ballInst = ballInst;
        this.ticksToDeleete = ticksToDeleete;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ap.repaint();
        tickCounter++;
        if(tickCounter == ticksToDeleete) {
            ap.removeBall(ballInst);
            ap.repaint();
        }
    }
}
