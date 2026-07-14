package org.firstinspires.ftc.teamcode.subsystems.launcher;

import static dev.nextftc.units.Units.Degrees;
import static dev.nextftc.units.Units.DegreesPerSecond;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextRGBIndicator;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.units.measuretypes.AngularVelocity;

public class Launcher implements Mechanism {

    private static final double TICKS_TO_DEGREES = 360.0 / 28.0;

    public NextMotor leftLauncher = new NextMotor("leftLauncher", Degrees.of(TICKS_TO_DEGREES));
    NextMotor rightLauncher = new NextMotor("rightLauncher", Degrees.of(TICKS_TO_DEGREES));

    NextRGBIndicator rgb = new NextRGBIndicator("rgbLight1");

    private LauncherState launcherState;
    public enum LauncherState {
        IDLE,
        SPINUP,
        READY
    }

    public void setIdle(){
        launcherState = LauncherState.IDLE;
    }

    private static final double IDLE_TPS = 1000 * TICKS_TO_DEGREES;
    private static final double VELO_TOLERANCE_TPS = 40 * TICKS_TO_DEGREES;

    private AngularVelocity veloGoal;
    private double current;

    public double getCurrent() {
        return current;
    }

    public Launcher() {
        rightLauncher.getVelocityConstants().setKP(0.0009 / TICKS_TO_DEGREES);
        rightLauncher.getVelocityConstants().setKV(0.000423 / TICKS_TO_DEGREES);
        rightLauncher.getVelocityConstants().setKS(0.067);

        leftLauncher.follow(rightLauncher, NextMotor.Direction.REVERSE);

        launcherState = LauncherState.IDLE;
        veloGoal = DegreesPerSecond.of(IDLE_TPS * TICKS_TO_DEGREES);
        rightLauncher.setVelocitySetpoint(veloGoal);
    }

    public String getSpeed() {
        return "Right launcher velocity" + rightLauncher.getEncoderVelocity() + "Left launcher velocity" + leftLauncher.getEncoderVelocity();
    }

    public void setGoal(double goalTPS) {
        veloGoal = DegreesPerSecond.of(goalTPS * TICKS_TO_DEGREES);
        rightLauncher.setVelocitySetpoint(veloGoal);
        launcherState = LauncherState.SPINUP;
    }

    @Override
    public void periodic() {
        if (launcherState == LauncherState.IDLE) {
            rightLauncher.setVelocitySetpoint(DegreesPerSecond.of(IDLE_TPS));
        }

        current = rightLauncher.getEncoderVelocity().getMagnitude();
        double error = Math.abs(current - veloGoal.getMagnitude());
        double toleranceDegPerSec = VELO_TOLERANCE_TPS;

        if (launcherState != LauncherState.IDLE) {
            launcherState = (error <= toleranceDegPerSec)
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
}