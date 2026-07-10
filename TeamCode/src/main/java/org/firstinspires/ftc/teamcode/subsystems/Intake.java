package org.firstinspires.ftc.teamcode.subsystems;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.robot.Mechanism;

public class Intake implements Mechanism {

    public Intake() {
        intakeState = IntakeState.OFF;
        power = off;
    }
    NextMotor i = new NextMotor(RobotController.controlHub(), 2);

    private IntakeState intakeState;
    public enum IntakeState {
        FORWARD,
        REVERSE,
        OFF
    }

    private double power;

    private final double forward = 1.0;
    private final double reverse = -1.0;
    private final double off = 0.0;

    private void setState(IntakeState intakeState) {
        this.intakeState = intakeState;
    }

    public Command setSpeed(IntakeState s) {
        return instant(() -> this.setState(s));
    }

    public void cycle() {
        if (intakeState == IntakeState.REVERSE) power = forward;
        else if (intakeState == IntakeState.FORWARD) power = reverse;
        else power = forward;
    }

    @Override
    public void periodic() {
        switch (intakeState) {
            case FORWARD:
                i.setThrottle(forward);
                break;
            case REVERSE:
                i.setThrottle(reverse);
                break;
            case OFF:
                i.setThrottle(off);
                break;
        };
    }

}