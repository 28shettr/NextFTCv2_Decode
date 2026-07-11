package org.firstinspires.ftc.teamcode.subsystems.intakeSpindex;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;


import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;

public class Intake implements Mechanism {

    public Intake() {
        intakeState = IntakeState.OFF;
        power = OFF_SPEED;
    }
    NextMotor intakeMotor = new NextMotor("intakeMotor");
    NextServo intakeServo = new NextServo("intakeLiftServo");
    public IntakeState intakeState;
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

    private void setState(IntakeState intakeState) {
        this.intakeState = intakeState;
    }

    public Command setSpeed(IntakeState s) {
        return instant(() -> this.setState(s));
    }

    public void cycle() {
        if (intakeState == IntakeState.REVERSE) power = FORWARD_SPEED;
        else if (intakeState == IntakeState.FORWARD) power = REVERSE_SPEED;
        else power = FORWARD_SPEED;
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