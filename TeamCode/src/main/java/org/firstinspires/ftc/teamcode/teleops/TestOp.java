package org.firstinspires.ftc.teamcode.teleops;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.intakeSpindex.Intake;

import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.opmode.NextTeleop;
import dev.nextftc.robot.triggers.CommandGamepad;
import dev.nextftc.robot.triggers.Trigger;

@NextTeleop(name = "test", group = "1")
public class TestOp extends NextOpMode {
    private final Robot robot;

    public TestOp(Robot robot){
        super(robot);
        this.robot = robot;
    }

    @Override
    public void onInit() {
        robot.spindexer.init();
        CommandGamepad gp1 = new CommandGamepad(Trigger.Companion.getDefaultEventLoop(), gamepad1);
        gp1.rightBumper().onTrue(robot.intake.setSpeed(Intake.IntakeState.FORWARD));
        gp1.leftBumper().onTrue(robot.intake.setSpeed(Intake.IntakeState.OFF));

        gp1.cross().onTrue(robot.spindexer.nextChamber());
    }

    @Override
    public void periodic() {
        telemetry.addData("Spindexer Position", robot.spindexer.spindexerMotor.getEncoderPosition());
        telemetry.update();
    }
}
