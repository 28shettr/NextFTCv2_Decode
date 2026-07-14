package org.firstinspires.ftc.teamcode.subsystems;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.util.MyMath;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;

public class LauncherHood implements Mechanism {

    public LauncherHood(){
    }

    private final NextServo launcherHoodServo = new NextServo(RobotController.expansionHub(), 3);

    public static final double MAX_SERVO_POSITION = 0.23;
    public static final double MIN_SERVO_POSITION = 0.0;


    public void init(){
        launcherHoodServo.setPosition(0);
    }
    private void setPos(double x){
        launcherHoodServo.setPosition(MyMath.clamp(x,MIN_SERVO_POSITION, MAX_SERVO_POSITION ));
    }

    public Command setHoodPos(double pos){
        return instant(() ->setPos(pos));
    }

    public double getPosition() {
        return launcherHoodServo.getPosition();
    }
}
