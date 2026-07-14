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

    public static double launcherMath(double x) {
        double x2 = x * x;
        double x3 = x2 * x;
        double x4 = x3 * x;

        return 0.0658846 * x4
                - 2.30574 * x3
                + 28.67283 * x2
                - 81.70799 * x
                + 956.74449;
    }

    public static double launcherHoodMath(double x) {
        return 0.230219 / (1 + Math.exp(-(-4.7077 * x + 27.63986)));
    }
}
