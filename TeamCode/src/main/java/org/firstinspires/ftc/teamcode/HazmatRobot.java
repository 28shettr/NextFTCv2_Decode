package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.subsystems.intakeSpindex.Intake;
import org.firstinspires.ftc.teamcode.subsystems.intakeSpindex.Spindexer;
import org.firstinspires.ftc.teamcode.subsystems.intakeSpindex.SpindexerSensors;

import java.util.Set;

import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;

public class HazmatRobot implements NextRobot {
    private Follower follower;
    public final SpindexerSensors ss = new SpindexerSensors();
    public final Intake intake = new Intake();
    public final Spindexer spindexer = new Spindexer();

    public void setFollower(Follower follower) {
        this.follower = follower;
    }

    @Override
    public Set<Mechanism> getMechanisms() {
        return Set.of(intake, spindexer);
    }
}

