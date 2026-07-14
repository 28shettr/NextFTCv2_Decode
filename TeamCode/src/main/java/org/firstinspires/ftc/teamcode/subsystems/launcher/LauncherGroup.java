package org.firstinspires.ftc.teamcode.subsystems.launcher;

import org.firstinspires.ftc.teamcode.subsystems.LauncherHood;
import org.firstinspires.ftc.teamcode.util.MyMath;

import dev.nextftc.robot.Mechanism;

public class LauncherGroup implements Mechanism {
    private final Launcher launcher;
    private final LauncherHood hood;
    private final Turret turret;

    public LauncherGroup(Launcher launcher, LauncherHood hood, Turret turret){
        this.launcher = launcher;
        this.hood = hood;
        this.turret = turret;
    }

    private static final double MAX_VELOCITY = 2400;
    private static final double MIN_VELOCITY = 700;

    public void autoLauncher(){

        double d = Turret.distance;

        double speed = MyMath.clamp(MyMath.launcherMath(d), MIN_VELOCITY, MAX_VELOCITY);
        double pos = MyMath.clamp(MyMath.launcherHoodMath(d), LauncherHood.MIN_SERVO_POSITION, LauncherHood.MAX_SERVO_POSITION);

        launcher.setGoal(speed);
        hood.setHoodPos(pos);
    }


}
