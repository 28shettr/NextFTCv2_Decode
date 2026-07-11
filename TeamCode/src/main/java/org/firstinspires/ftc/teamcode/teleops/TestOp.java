package org.firstinspires.ftc.teamcode.teleops;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.opmode.NextTeleop;
import dev.nextftc.robot.triggers.CommandGamepad;
import dev.nextftc.robot.triggers.Trigger;

@NextTeleop(name = "test", group = "1")
public class TestOp extends NextOpMode {
    private final Robot r;

    public TestOp(Robot r){
        super(r);
        this.r = r;
    }

    @Override
    public void onInit() {
        r.s.init();
        CommandGamepad gp1 = new CommandGamepad(Trigger.Companion.getDefaultEventLoop(), gamepad1);
        gp1.rightBumper().onTrue(r.i.setSpeed(Intake.IntakeState.FORWARD));
        gp1.leftBumper().onTrue(r.i.setSpeed(Intake.IntakeState.OFF));

        gp1.cross().onTrue(r.s.nextChamber());
    }

    @Override
    public void periodic() {
        telemetry.addData("Spindexer Position", r.s.spindexerMotor.getEncoderPosition());
        telemetry.update();
    }
}
