package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.LauncherHood;
import org.firstinspires.ftc.teamcode.subsystems.Tilt;
import org.firstinspires.ftc.teamcode.subsystems.intakeSpindex.Intake;
import org.firstinspires.ftc.teamcode.subsystems.intakeSpindex.IntakeSpindexerGroup;
import org.firstinspires.ftc.teamcode.subsystems.intakeSpindex.Spindexer;
import org.firstinspires.ftc.teamcode.subsystems.intakeSpindex.SpindexerSensors;
import org.firstinspires.ftc.teamcode.subsystems.launcher.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.launcher.LauncherGroup;
import org.firstinspires.ftc.teamcode.subsystems.launcher.Turret;

import java.util.Set;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;
import dev.nextftc.robot.Telemetry;
import gay.zharel.fateweaver.flight.FlightRecorder;

public class HazmatRobot implements NextRobot {
    public Follower follower;
    public Turret turret;
    public final Intake intake = new Intake();
    public final Spindexer spindexer = new Spindexer();
    public final SpindexerSensors sensors = new SpindexerSensors();
    public final IntakeSpindexerGroup intakeGroup = new IntakeSpindexerGroup(intake,spindexer,sensors);
    public final Launcher launcher = new Launcher();
    public final LauncherHood hood = new LauncherHood();
    public final LauncherGroup launchGroup = new LauncherGroup(launcher, hood, turret);
    public final Tilt tilt = new Tilt();

    private boolean autoStart = true;
    private boolean launcherAutomation = false;

    public HazmatRobot(){
        Telemetry.addBackend(FlightRecorder.INSTANCE);
    }

    public Follower getFollower() {
        if (follower == null) {
            follower = Constants.createFollower(RobotController.hardwareMap());
            turret = new Turret(follower);
        }

        return follower;
    }

    public void updateFollower(){
        follower.update();
    }

    public void init(){
        spindexer.init();
        hood.init();
        sensors.setGains();
        tilt.init();
    }

    public void startWhen2() {
        if (!launcherAutomation) return;

        if (sensors.artifactCount() >= 2) {
            autoStart = true;
            launchGroup.autoLauncher();
        } else if (autoStart) {
                launcher.setIdle();
                autoStart = false;
        }
    }

    private void disableAutoStart() {
        launcherAutomation = false;
        if (autoStart) {
            launcher.setIdle();
        }
    }
    private void enableAutoStart()  {
        launcherAutomation = true;
    }

    public void toggleAutoStart(){
        if (launcherAutomation){
            disableAutoStart();
        } else
            enableAutoStart();
    }
    @Override
    public Set<Mechanism> getMechanisms() {
        return Set.of(intake, spindexer, sensors, intakeGroup, launcher, turret);
    }

    @Override
    public void periodic(){
        startWhen2();
    }
}

