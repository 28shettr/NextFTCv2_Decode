package org.firstinspires.ftc.teamcode.subsystems;

import static dev.nextftc.units.Units.Inches;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import dev.nextftc.control.geometry.Pose2d;
import dev.nextftc.hardware.webcams.NextLimelight;
import dev.nextftc.robot.Mechanism;

public class Limelight implements Mechanism {

    NextLimelight limelight = new NextLimelight("Limelight");

    private boolean instantReset;

    private Follower f;

    public void start(){
        limelight.startReading(7, 50);
    }

    public Limelight(){
        this.instantReset = false;

    }

    private Pose toPedroPose(Pose2d p) {
        return new Pose(p.position.x.into(Inches), p.position.y.into(Inches), p.heading.log());
    }
    public void relocalize(){
        if (instantReset){
            instantReset = false;

            Pose2d p = limelight.getPedroPose();

            if (p != null) {
                f.setPose(toPedroPose(p));
            }

        }
    }





}
