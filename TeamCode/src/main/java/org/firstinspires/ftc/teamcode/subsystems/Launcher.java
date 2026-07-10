package org.firstinspires.ftc.teamcode.subsystems;

import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedback.PIDController;
import dev.nextftc.control.feedforward.SimpleFeedforward;
import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextRGBIndicator;
import dev.nextftc.robot.Mechanism;

public class Launcher implements Mechanism {
    private Launcher(){
        leftLauncher.setThrottle(0);
        rightLauncher.follow(leftLauncher, NextMotor.Direction.REVERSE);

        launcherState = LauncherState.IDLE;
        this.veloGoal = 0;
    }

    NextMotor leftLauncher = new NextMotor(RobotController.controlHub(), 2);
    NextMotor rightLauncher = new NextMotor(RobotController.controlHub(), 1);

    NextRGBIndicator rgb = new NextRGBIndicator("rgb1",0.01);

    private LauncherState launcherState;
    public enum LauncherState {
        IDLE,
        SPINUP,
        READY
    }

    private double veloGoal;
    PIDCoefficients pid = new PIDCoefficients(0.0009, 0, 0);
    PIDController launcherPID = new PIDController(pid);
    SimpleFeedforward basicFF = new SimpleFeedforward(0.000423, 0, 0.067);

    public void setGoal(double goal){
        veloGoal = 0;
    }

    @Override
    public void periodic(){
        if (launcherState == LauncherState.IDLE){
            setGoal(1000);
        }

        double veloPID = launcherPID.calculateFromReference(veloGoal, leftLauncher.getEncoderVelocity().getMagnitude());
        double veloFF = basicFF.calculate(veloGoal);

        leftLauncher.setThrottle(veloPID + veloFF);

        if (launcherState != LauncherState.IDLE) {
            launcherState = (Math.abs(leftLauncher.getEncoderVelocity().getMagnitude() - veloGoal ) <= 40)
                    ? LauncherState.READY
                    : LauncherState.SPINUP;
        }

        if (launcherState == LauncherState.IDLE){
            rgb.setColor(NextRGBIndicator.Color.WHITE);
        } else if (launcherState == LauncherState.READY){
            rgb.setColor(NextRGBIndicator.Color.VIOLET);
        } else if (launcherState == LauncherState.SPINUP){
            rgb.setColor(NextRGBIndicator.Color.AZURE);
        }
    }
}
