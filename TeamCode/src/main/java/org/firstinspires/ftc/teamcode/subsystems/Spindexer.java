/*
package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedback.PIDController;
import dev.nextftc.control.feedforward.SimpleFeedforward;
import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;

public class Spindexer implements Mechanism {

    private final SpindexerSensors spindexerSensors;
    private Spindexer(SpindexerSensors s){
        this.spindexerSensors = s;
        rightTriggerServo.setPosition(RIGHT_TRIGGER_RESET_POSITION);
        leftTriggerServo.setPosition(LEFT_TRIGGER_RESET_POSITION);
        launcherGateServo.setPosition(LAUNCHER_GATE_CLOSE_POSITION);
    }

    private static final double LEFT_TRIGGER_LAUNCH_POSITION = 0.53;
    private static final double LEFT_TRIGGER_RESET_POSITION = 0.02;
    private static final double RIGHT_TRIGGER_LAUNCH_POSITION = 0.46;
    private static final double RIGHT_TRIGGER_RESET_POSITION = 0.94;
    private static final double LAUNCHER_GATE_OPEN_POSITION = 1.0;
    private static final double LAUNCHER_GATE_CLOSE_POSITION = 0.49; //0.16

    public NextMotor spindexerMotor = new NextMotor(RobotController.controlHub(), 1);
    public NextServo leftTriggerServo = new NextServo(RobotController.expansionHub(), 0);
    public NextServo rightTriggerServo = new NextServo(RobotController.controlHub(), 5);
    public NextServo launcherGateServo = new NextServo(RobotController.expansionHub(), 1);

    private PIDController pid = new PIDController(new PIDCoefficients(0.004, 0,0.000085));

    public void calcNewPos(){
        ()
    }
}
*/
