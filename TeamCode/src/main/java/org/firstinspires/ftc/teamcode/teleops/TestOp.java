package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.opmode.NextTeleop;
import dev.nextftc.robot.triggers.CommandGamepad;
import dev.nextftc.robot.triggers.Trigger;

@NextTeleop(name = "test", group = "1")
public class TestOp extends NextOpMode {
    public TestOp(Robot r){
        super(r);


        CommandGamepad gp1 = new CommandGamepad(Trigger.Companion.getDefaultEventLoop(), gamepad1);
        gp1.cross().onTrue(r.i.setSpeed(Intake.IntakeState.FORWARD));
        gp1.circle().onTrue(r.i.setSpeed(Intake.IntakeState.FORWARD));
    }


}
