package org.firstinspires.ftc.teamcode.util;

public class MyMath {
    public static double clamp(double value, double lo, double hi) {
        return java.lang.Math.max(lo, java.lang.Math.min(hi, value));
    }
    public static double normalizeAngle(double angDeg) {
        double ang = angDeg % 360.0;
        if (ang > 180.0)  ang -= 360.0;
        if (ang < -180.0) ang += 360.0;
        return ang;
    }
}
