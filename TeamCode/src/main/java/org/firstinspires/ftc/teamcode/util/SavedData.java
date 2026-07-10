package org.firstinspires.ftc.teamcode.util;

import com.pedropathing.geometry.Pose;

import java.lang.Math;


public class SavedData {

    public enum Alliance {
        BLUE,
        RED
    }
    public static Alliance alliance = Alliance.RED;
    public static int patternID = 67;
    public static Pose savedPose = new Pose(112, 80, Math.toRadians(90));


}