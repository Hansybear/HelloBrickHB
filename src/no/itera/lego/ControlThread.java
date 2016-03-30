package no.itera.lego;

import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.RobotState;

public class ControlThread implements Runnable {

    private RobotState robotState;
    private EV3Helper ev3Helper;
    private boolean black;
    private int encounteredRed;

    public ControlThread(RobotState robotState) {
    	black = false;
        this.robotState = robotState;
        this.ev3Helper = robotState.ev3Helper;
        encounteredRed = 0;
    }

    @Override
    public void run() {
        while (robotState.shouldRun) {

            if (robotState.lastStatus == null || !robotState.lastStatus.isActive) {
                continue;
            }
            if(encounteredRed>5) {
        		ev3Helper.stop();
        	}
            switch (robotState.lastColor) {
                case BLACK:
                	ev3Helper.backward(10);
                	ev3Helper.turnLeft(120);
                	black = true;
                case BLUE:
                	encounteredRed -=1;
                	ev3Helper.turnLeft(2);
                	ev3Helper.forward();
                case RED:
                	ev3Helper.stop();
                	encounteredRed += 1;
                case GREY:
                	ev3Helper.forward();
                case YELLOW:
                	ev3Helper.forward();
                	//ev3Helper.tur;
                    break;
                case GREEN:
                    ev3Helper.forward();
                    break;
                default:
                    ev3Helper.forward();
            }
        }
        robotState.latch.countDown();
    }
}
