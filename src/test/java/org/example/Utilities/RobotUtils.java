package org.example.Utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class RobotUtils {
    private Robot robot;

    public RobotUtils() throws AWTException {
        this.robot = new Robot();
    }

    public void pressKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }

    public void pressUpArrow() {
        pressKey(KeyEvent.VK_UP);
    }

    public void pressDownArrow() {
        pressKey(KeyEvent.VK_DOWN);
    }

    public void pressLeftArrow() {
        pressKey(KeyEvent.VK_LEFT);
    }

    public void pressRightArrow() {
        pressKey(KeyEvent.VK_RIGHT);
    }

    public void pressEnter() {
        pressKey(KeyEvent.VK_ENTER);
    }
}
