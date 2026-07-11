package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Spindexer;

import java.util.Set;

import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;

public class Robot implements NextRobot {
    private Follower follower;
//    private SpindexerSensors ss = new SpindexerSensors();
    public final Intake i = new Intake();
    public final Spindexer s = new Spindexer();

    public void setFollower(Follower follower) {
        this.follower = follower;
    }

    @Override
    public Set<Mechanism> getMechanisms() {
        return Set.of(i,s);
    }

   /* public void setFollower(Follower follower){
        Robot.follower = follower;
    }*/
}