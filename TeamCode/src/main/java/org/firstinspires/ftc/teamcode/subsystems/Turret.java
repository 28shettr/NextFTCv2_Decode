/*
package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.util.MyMath;
import org.firstinspires.ftc.teamcode.util.SavedData;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;

public class Turret implements Mechanism {

    private Turret(){}

    public static double distance;

    private final NextServo leftTurretServo = new NextServo(RobotController.controlHub(), 1);
    private final NextServo rightTurretServo = new NextServo(RobotController.controlHub(), 2);


    public static double targetTurretAng;

    private static final double TURRET_MIN_DEG = -170;
    private static final double TURRET_MAX_DEG =  170;

    private final double GEAR_RATIO = 0.9923076923;
    private final double SERVO_RANGE = 326;

    public static boolean AUTO_AIM = true;

    private Pose lastKnownPose = new Pose(0, 0, 0);

    //Red pose values

    public Pose exactGoal = new Pose(144, 144);
    public Pose goal = new Pose(144,144);
    public static Pose resetPose = new Pose(8.3,7.8,Math.toRadians(180));
    private final double linearFactor = (1.0 +2.0/90.0);

    private double turretAngleToServo(double angle){
        return (-angle * linearFactor ) * (GEAR_RATIO / SERVO_RANGE) + 0.50 ; //
    }

    public void setPose(SavedData.Alliance alliance) {
        if (alliance == SavedData.Alliance.RED) {
            goal = new Pose(142.62, 141.88);
            exactGoal = new Pose(144, 144);
            resetPose = new Pose(8.3, 7.8, Math.toRadians(180));
        } else if (alliance == SavedData.Alliance.BLUE) {
            goal = new Pose(9.2, 141.88);
            exactGoal = new Pose(144,144).mirror();
            resetPose = new Pose(8.3, 7.8, Math.toRadians(180)).mirror();
        }
    }

    public void disableAutoAim(){
        AUTO_AIM = false;
        setTurretAngle(0);
    }
    public void enableAutoAim(){
        AUTO_AIM = true;
        setTurretAngle(targetTurretAng);
    }

    public void setTurretAngle(double x){
        targetTurretAng = x;
    }
    public void turretLoop() {
        Pose currentPose = Robot.follower.getPose();

        double dx = goal.getX() - currentPose.getX();
        double dy = goal.getY() - currentPose.getY();

        double goalFieldDeg = Math.toDegrees(Math.atan2(dy, dx));
        double headingDeg = Math.toDegrees(currentPose.getHeading());

        double desired = MyMath.normalizeAngle(goalFieldDeg - headingDeg);

        targetTurretAng = MyMath.clamp(desired, TURRET_MIN_DEG, TURRET_MAX_DEG);
    }

    private void distanceCalc(){
        Pose pose = Robot.follower.getPose();
        distance = Math.hypot(exactGoal.getX() - pose.getX(), exactGoal.getY() - pose.getY()) / 12;
    }

    private void update(){
        double x = MyMath.clamp(turretAngleToServo(targetTurretAng), turretAngleToServo(TURRET_MAX_DEG), turretAngleToServo(TURRET_MIN_DEG));
        rightTurretServo.setPosition(x);
        leftTurretServo.setPosition(x);
    }

    @Override
    public void periodic() {
        distanceCalc();

        if (AUTO_AIM) {
            turretLoop();
        }

        update();
    }


}
*/
