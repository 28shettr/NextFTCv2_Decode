/*
package org.firstinspires.ftc.teamcode.subsystems;

import static dev.nextftc.units.Units.DegreesPerSecond;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextRGBIndicator;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.units.measuretypes.AngularVelocity;

public class Launcher implements Mechanism {

    NextMotor leftLauncher = new NextMotor(RobotController.controlHub(), 2);
    NextMotor rightLauncher = new NextMotor(RobotController.controlHub(), 1);

    NextRGBIndicator rgb = new NextRGBIndicator("rgb1", 0.01);

    private LauncherState launcherState;
    public enum LauncherState {
        IDLE,
        SPINUP,
        READY
    }

    private static final double IDLE_VELOCITY = 1000;
    private static final double VELO_TOLERANCE = 40;

    private AngularVelocity veloGoal;

    public Launcher() {
        leftLauncher.getVelocityConstants().setKP(0.0009);
        leftLauncher.getVelocityConstants().setKI(0.0);
        leftLauncher.getVelocityConstants().setKD(0.0);
        leftLauncher.getVelocityConstants().setKS(0.000423);
        leftLauncher.getVelocityConstants().setKV(0.0);
        leftLauncher.getVelocityConstants().setKA(0.067);

        rightLauncher.follow(leftLauncher, NextMotor.Direction.REVERSE);

        launcherState = LauncherState.IDLE;
        veloGoal = DegreesPerSecond.of(IDLE_VELOCITY);
        leftLauncher.setVelocitySetpoint(veloGoal);
    }

    public void setGoal(double goal) {
        veloGoal = DegreesPerSecond.of(goal);
        leftLauncher.setVelocitySetpoint(veloGoal);
        launcherState = LauncherState.SPINUP;
    }

    @Override
    public void periodic() {
        if (launcherState == LauncherState.IDLE) {
            leftLauncher.setVelocitySetpoint(DegreesPerSecond.of(IDLE_VELOCITY));
        }

        double error = Math.abs(
                leftLauncher.getEncoderVelocity().getMagnitude() - veloGoal.getMagnitude()
        );

        if (launcherState != LauncherState.IDLE) {
            launcherState = (error <= VELO_TOLERANCE)
                    ? LauncherState.READY
                    : LauncherState.SPINUP;
        }

        if (launcherState == LauncherState.IDLE) {
            rgb.setColor(NextRGBIndicator.Color.WHITE);
        } else if (launcherState == LauncherState.READY) {
            rgb.setColor(NextRGBIndicator.Color.VIOLET);
        } else {
            rgb.setColor(NextRGBIndicator.Color.AZURE);
        }
    }
}*/
