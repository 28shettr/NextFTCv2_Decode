package org.firstinspires.ftc.teamcode.subsystems.intakeSpindex;

import static com.pedropathing.ivy.groups.Groups.sequential;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.util.ElapsedTime;

import dev.nextftc.robot.Mechanism;

public class IntakeSpindexerGroup implements Mechanism {

    private final Intake intake;
    private final Spindexer spindexer;
    private final SpindexerSensors sensors;

    private enum FullnessState { NORMAL, WAITING_TO_REVERSE, LOCKED_SETTLING }

    private FullnessState fullnessState = FullnessState.NORMAL;
    private final ElapsedTime reverseDelayTimer = new ElapsedTime();
    public IntakeSpindexerGroup(Intake intake, Spindexer spindexer, SpindexerSensors spindexerSensors) {
        this.intake = intake;
        this.spindexer = spindexer;
        this.sensors = spindexerSensors;
    }

    private boolean lastIntakeArtifactState = false;


    private void autoIndex() {
        if (!intake.isIntaking()) return;

        boolean current = sensors.beamBreakSensedArtifact();
        if (current && !lastIntakeArtifactState) {
            spindexer.nextChamber().schedule();
        }
        lastIntakeArtifactState = current;
    }

    private void updateFullnessState() {
//        if (spindexer.isLaunching) return;

        boolean full = sensors.isSpindexerFull();

        switch (fullnessState) {
            case NORMAL:
                if (full) {
                    reverseDelayTimer.reset();
                    fullnessState = FullnessState.WAITING_TO_REVERSE;
                }
                break;

            case WAITING_TO_REVERSE:
                if (reverseDelayTimer.seconds() > 0.5) {
                    intake.setSpeed(Intake.IntakeState.REVERSE);
                    fullnessState = FullnessState.LOCKED_SETTLING;
                }
                break;

            case LOCKED_SETTLING:
                if (!full) {
                    if (!intake.isIntaking()) {
                        intake.setSpeed(Intake.IntakeState.FORWARD);
                    }
                    fullnessState = FullnessState.NORMAL;
                }
                break;
        }
    }

    public Command launch(){
        return sequential(
            spindexer.launchSequence(),
            intake.setSpeed(Intake.IntakeState.FORWARD)
        );
    }

    @Override
    public void periodic(){
       autoIndex();
       updateFullnessState();
    }

}


