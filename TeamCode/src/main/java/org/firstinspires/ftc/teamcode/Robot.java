package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

import java.util.Set;

import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;

public class Robot implements NextRobot {
//    public static Follower follower;
    public final Intake i = new Intake();


    @Override
    public Set<Mechanism> getMechanisms() {
        return Set.of(i);
    }

   /* public void setFollower(Follower follower){
        Robot.follower = follower;
    }*/
}