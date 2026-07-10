package org.firstinspires.ftc.teamcode.subsystems;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;

public class Tilt implements Mechanism {

    private Tilt(){
        leftTilt.setPosition(LEFT_TILT_DEACTIVE_POS);
        rightTilt.setPosition(RIGHT_TILT_DEACTIVE_POS);
    }
    private NextServo leftTilt = new NextServo(RobotController.expansionHub(), 2);
    private NextServo rightTilt = new NextServo(RobotController.controlHub(), 2);

    private final double LEFT_TILT_ACTIVE_POS = 0.7;
    private final double LEFT_TILT_DEACTIVE_POS = 0.0;
    private final double RIGHT_TILT_ACTIVE_POS = 0.7;
    private final double RIGHT_TILT_DEACTIVE_POS = 0.0;
    public Command activateTilt() {
         return instant(()-> leftTilt.setPosition(LEFT_TILT_ACTIVE_POS)).with(instant((()->rightTilt.setPosition(RIGHT_TILT_ACTIVE_POS))));
    }

    public Command deactivateTilt() {
        return instant(()-> leftTilt.setPosition(LEFT_TILT_DEACTIVE_POS)).with(instant((()->rightTilt.setPosition(RIGHT_TILT_DEACTIVE_POS))));
    }
}
