package org.firstinspires.ftc.teamcode.subsystems.intakeSpindex;

import static com.pedropathing.ivy.groups.Groups.parallel;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.commands.Commands;

import static dev.nextftc.units.Units.Degrees;
import static dev.nextftc.units.Units.Rotations;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.units.measuretypes.Angle;

public class Spindexer implements Mechanism {

    public Spindexer() {

    }

    private static final Angle CHAMBER_DELTA = Rotations.of(1.0 / 3.0);
    private static final Angle CALIBRATION_INCREMENT = Rotations.of(5.0 / 360.0);


    private static final double LEFT_TRIGGER_LAUNCH_POSITION = 0.53;
    private static final double LEFT_TRIGGER_RESET_POSITION = 0.02;
    private static final double RIGHT_TRIGGER_LAUNCH_POSITION = 0.46;
    private static final double RIGHT_TRIGGER_RESET_POSITION = 0.94;
    private static final double LAUNCHER_GATE_OPEN_POSITION = 1.0;
    private static final double LAUNCHER_GATE_CLOSE_POSITION = 0.49; //0.16

    private static final double PID_SCALE = 8192.0 / 10.9;

    private static final double kP = 0.004 * PID_SCALE;
    private static final double kI = 0;
    private static final double kD = 0.000085 * PID_SCALE;
    private static final double kV = 0;
    private static final double kA = 0;
    private static final double kS = 0.0001;

    public NextMotor spindexerMotor = new NextMotor("spindexerMotor", Degrees.of(-360.0/8192));
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

        double currentPos = spindexerMotor.getEncoderPosition().into(Rotations);
        double chamberDelta = CHAMBER_DELTA.into(Rotations);

        double snappedPos = Math.round(currentPos / chamberDelta + 1) * chamberDelta;

        spindexerMotor.setPositionSetpoint(Rotations.of(snappedPos));
    }

    private void setPos(double goal) {
        lastGoalPosition = goal;
        spindexerMotor.setPositionSetpoint(Rotations.of(goal));
    }

    double currentPos;
    public Command spindexerUp() {
        return Commands.instant(() -> {
            double currentPos = spindexerMotor.getEncoderPosition().into(Rotations);
            setPos(currentPos + CALIBRATION_INCREMENT.into(Rotations));
        }).requiring(spindexerMotor);
    }

    public Command spindexerDown() {
        return Commands.instant(() -> {
            double currentPos = spindexerMotor.getEncoderPosition().into(Rotations);
            setPos(currentPos - CALIBRATION_INCREMENT.into(Rotations));
        }).requiring(spindexerMotor);
    }
    public void calcNewPos() {
        currentPos = spindexerMotor.getEncoderPosition().into(Rotations);

        double snappedPos = (Math.round(currentPos / CHAMBER_DELTA.getMagnitude()) + 1) * CHAMBER_DELTA.getMagnitude();

        setPos(snappedPos);
    }

    public Command nextChamber() {
        return Commands.instant(this::calcNewPos).requiring(spindexerMotor);
    }

    public Command openLauncherGate(){
        return Commands.instant(()->launcherGateServo.setPosition(LAUNCHER_GATE_OPEN_POSITION)).requiring(launcherGateServo);
    }
    public Command closeLauncherGate(){
        return Commands.instant(()->launcherGateServo.setPosition(LAUNCHER_GATE_CLOSE_POSITION)).requiring(launcherGateServo);
    }

    public Command activateRightTrigger(){
        return Commands.instant(()->rightTriggerServo.setPosition(RIGHT_TRIGGER_LAUNCH_POSITION)).requiring(rightTriggerServo);
    }
    public Command resetRightTrigger(){
        return Commands.instant(()->rightTriggerServo.setPosition(RIGHT_TRIGGER_RESET_POSITION)).requiring(rightTriggerServo);
    }

    public Command activateLeftTrigger(){
        return Commands.instant(()->rightTriggerServo.setPosition(RIGHT_TRIGGER_LAUNCH_POSITION)).requiring(rightTriggerServo);
    }
    public Command resetLeftTrigger(){
        return Commands.instant(()->rightTriggerServo.setPosition(RIGHT_TRIGGER_RESET_POSITION)).requiring(rightTriggerServo);
    }

    public Command launch(){
        return parallel(
                activateRightTrigger(),
                activateLeftTrigger(),
                openLauncherGate()
        );
    }

    public Command reset(){
        return parallel(
                resetRightTrigger(),
                resetLeftTrigger(),
                closeLauncherGate()
        );
    }

    @Override
    public void periodic() {
    }
}