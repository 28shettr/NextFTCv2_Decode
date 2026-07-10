/*
package org.firstinspires.ftc.teamcode.subsystems;

import static dev.nextftc.units.Units.Inches;
import com.pedropathing.geometry.Pose;
import org.firstinspires.ftc.teamcode.Robot;
import dev.nextftc.control.geometry.Pose2d;
import dev.nextftc.hardware.webcams.NextLimelight;
import dev.nextftc.robot.Mechanism;

public class Limelight implements Mechanism {

    NextLimelight limelight = new NextLimelight("Limelight");

    private boolean instantReset;

    private Limelight(){
        this.instantReset = false;

    }

    private Pose toPedroPose(Pose2d p) {
        double x = p.position.x.into(Inches);
        double y = p.position.y.into(Inches);
        double headingRad = p.heading.log();

        return new Pose(x, y, headingRad);
    }
    public void relocalize(){
        if (instantReset){
            instantReset = false;

            Pose2d p = limelight.getPedroPose();
            if (p != null) {
                Robot.follower.setPose(toPedroPose(p));
            }

        }
    }



}
*/
