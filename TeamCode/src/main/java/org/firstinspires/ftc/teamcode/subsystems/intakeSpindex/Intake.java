package org.firstinspires.ftc.teamcode.subsystems.intakeSpindex;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;


import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;

public class Intake implements Mechanism {

    public Intake() {

    }
    NextMotor intakeMotor = new NextMotor(RobotController.controlHub(),2);
    NextServo intakeServo = new NextServo(RobotController.controlHub(),4);
    public IntakeState intakeState = IntakeState.OFF;
    public enum IntakeState {
        FORWARD,
        REVERSE,
        OFF
    }

    private double power;

    private final double FORWARD_SPEED = 1.0;
    private final double REVERSE_SPEED = -1.0;
    private final double OFF_SPEED = 0.0;

    private final double LIFT_POS = 0.19;
    private final double INTAKE_POS = 0.38;

    public boolean isIntaking(){
        return intakeState == IntakeState.FORWARD;
    }
    private void setState(IntakeState intakeState) {
        this.intakeState = intakeState;
    }

    public Command setSpeed(IntakeState s) {
        return instant(() -> this.setState(s));
    }

    public void cycle() {
        if (intakeState == IntakeState.REVERSE) setState(IntakeState.FORWARD);
        else if (intakeState == IntakeState.FORWARD) setState(IntakeState.REVERSE);
        else setState(IntakeState.FORWARD); ;
    }

    @Override
    public void periodic() {
        switch (intakeState) {
            case FORWARD:
                intakeMotor.setThrottle(FORWARD_SPEED);
                intakeServo.setPosition(INTAKE_POS);
                break;
            case REVERSE:
                intakeMotor.setThrottle(REVERSE_SPEED);
                intakeServo.setPosition(LIFT_POS);
                break;
            case OFF:
                intakeMotor.setThrottle(OFF_SPEED);
                intakeServo.setPosition(LIFT_POS);
                break;
        };
    }

}