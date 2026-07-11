package org.firstinspires.ftc.teamcode.subsystems;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;

import static dev.nextftc.units.Units.Rotations;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.units.measuretypes.Angle;

public class Spindexer implements Mechanism {

    public Spindexer() {

    }

    private static final Angle CHAMBER_DELTA = Rotations.of(1.0 / 3.0);

    private static final double LEFT_TRIGGER_LAUNCH_POSITION = 0.53;
    private static final double LEFT_TRIGGER_RESET_POSITION = 0.02;
    private static final double RIGHT_TRIGGER_LAUNCH_POSITION = 0.46;
    private static final double RIGHT_TRIGGER_RESET_POSITION = 0.94;
    private static final double LAUNCHER_GATE_OPEN_POSITION = 1.0;
    private static final double LAUNCHER_GATE_CLOSE_POSITION = 0.49; //0.16

    private static final double kP = 0;
    private static final double kI = 0;
    private static final double kD = 0;
    private static final double kV = 0;
    private static final double kA = 0;
    private static final double kS = 0;

    public NextMotor spindexerMotor = new NextMotor("spindexerMotor", Rotations.of(-1.0 / 8192.0));
    public NextServo leftTriggerServo = new NextServo("leftTriggerServo");
    public NextServo rightTriggerServo = new NextServo("rightTriggerServo");
    public NextServo launcherGateServo = new NextServo("launcherGateServo");

    private double lastGoalPosition = 0.0;

    public void init() {
        rightTriggerServo.setPosition(RIGHT_TRIGGER_RESET_POSITION);
        leftTriggerServo.setPosition(LEFT_TRIGGER_RESET_POSITION);
        launcherGateServo.setPosition(LAUNCHER_GATE_CLOSE_POSITION);

        spindexerMotor.getPositionConstants().setKP(kP);
        spindexerMotor.getPositionConstants().setKI(kI);
        spindexerMotor.getPositionConstants().setKD(kD);
        spindexerMotor.getPositionConstants().setKV(kV);
        spindexerMotor.getPositionConstants().setKA(kA);
        spindexerMotor.getPositionConstants().setKS(kS);
    }

    private void setPos(double goal) {
        lastGoalPosition = goal;
        spindexerMotor.setPositionSetpoint(Rotations.of(goal));
    }

    public void calcNewPos() {
        double currentPos = spindexerMotor.getEncoderPosition().into(Rotations);
        double chamberDeltaMag = CHAMBER_DELTA.getMagnitude();

        double snappedPos = (Math.round(currentPos / chamberDeltaMag) + 1) * chamberDeltaMag;

        setPos(snappedPos);
    }

    public Command nextChamber() {
        return instant(this::calcNewPos);
    }

    @Override
    public void periodic() {
        spindexerMotor.update();
    }
}