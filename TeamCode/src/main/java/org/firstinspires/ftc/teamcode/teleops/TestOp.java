package org.firstinspires.ftc.teamcode.teleops;

import com.pedropathing.ivy.commands.Commands;

import org.firstinspires.ftc.teamcode.HazmatRobot;
import org.firstinspires.ftc.teamcode.subsystems.intakeSpindex.Intake;
import org.firstinspires.ftc.teamcode.util.SavedData;

import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.opmode.NextTeleop;
import dev.nextftc.robot.triggers.CommandGamepad;
import dev.nextftc.robot.triggers.Trigger;

@NextTeleop(name = "test", group = "1")
public class TestOp extends NextOpMode {
    private final HazmatRobot robot;

    public TestOp(HazmatRobot robot){
        super(robot);
        this.robot = robot;
        robot.getFollower();
        robot.follower.setPose(SavedData.savedPose);
        robot.turret.setAlliance(SavedData.alliance);
        robot.init();
        Trigger.Companion.getDefaultEventLoop().clear();
        CommandGamepad gp1 = new CommandGamepad(Trigger.Companion.getDefaultEventLoop(), gamepad1);
        robot.limelight.start();


        //Intake
        gp1.rightBumper().onTrue(Commands.instant(robot.intake::cycle));
        gp1.leftBumper().onTrue(robot.intake.setSpeed(Intake.IntakeState.OFF));

        //Spindexer
        gp1.cross().onTrue(robot.spindexer.nextChamber());
        gp1.dpadUp().onTrue(robot.spindexer.spindexerUp());
        gp1.dpadDown().onTrue(robot.spindexer.spindexerDown());

        //Launcher
        gp1.rightTrigger().isOver(0.2).onTrue(robot.intakeGroup.launch());
        gp1.triangle().onTrue(Commands.instant(robot::toggleAutoStart));

        //Tilt
        gp1.rightBumper().and(gp1.leftBumper()).onTrue(robot.tilt.activateTilt());

        //Relocalization
        gp1.circle().onTrue(Commands.instant(robot.limelight::relocalize));
        gp1.square().onTrue(Commands.instant(()->robot.follower.setPose(robot.turret.resetPose)));


    }

    @Override
    public void periodic() {
        telemetry.addLine(robot.launcher.getSpeed());
        telemetry.addData("intake state",robot.intake.intakeState);

        /*Telemetry.log("launcher/leftVelocity", robot.launcher.leftLauncher.getEncoderVelocity());
        FlightRecorder.write("launcher/leftVelocity", robot.launcher.leftLauncher.getEncoderVelocity().getMagnitude());
        FlightRecorder.timestamp();
        Telemetry.update();*/

        telemetry.update();
        robot.updateFollower();
    }
}
