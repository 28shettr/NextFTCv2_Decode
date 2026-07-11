package org.firstinspires.ftc.teamcode.subsystems.intakeSpindex;

import dev.nextftc.hardware.actuators.NextRGBIndicator;
import dev.nextftc.hardware.sensors.NextColorDistanceSensor;
import dev.nextftc.hardware.sensors.NextDigitalSensor;
import dev.nextftc.hardware.sensors.colors.ColorProfile;
import dev.nextftc.hardware.sensors.colors.ColorSpace;
import dev.nextftc.hardware.sensors.colors.NextColor;
import dev.nextftc.robot.Mechanism;

public class SpindexerSensors implements Mechanism {
    public SpindexerSensors() {
        intakeChamber.setGain(4.0f);
        storageChamber.setGain(4.0f);
        launcherChamber.setGain(4.0f);
    }

    private static final double DISTANCE_THRESHOLD_CM = 3.6;
    private static final double STORAGE_DISTANCE_THRESHOLD_CM = 3.0;

    public enum DetectedColor {
        GREEN, PURPLE, UNKNOWN,
    }

    DetectedColor intakeColor = DetectedColor.UNKNOWN;
    DetectedColor storageColor = DetectedColor.UNKNOWN;
    DetectedColor launcherColor = DetectedColor.UNKNOWN;

    public enum SensedArtifact {
        ARTIFACT, NOARTIFACT
    }

    public SensedArtifact beamBreakSensedArtifact = SensedArtifact.NOARTIFACT;
    public SensedArtifact intakeSensedArtifact = SensedArtifact.NOARTIFACT;
    public SensedArtifact storageSensedArtifact = SensedArtifact.NOARTIFACT;
    public SensedArtifact launcherSensedArtifact = SensedArtifact.NOARTIFACT;

    private static final ColorProfile GREEN_PROFILE = new ColorProfile(ColorSpace.HSV, NextColor.Companion.hsv(167.5f, 0.6f, 0.5f), NextColor.Companion.hsv(17.5f, 0.4f, 1f));

    private static final ColorProfile PURPLE_PROFILE = new ColorProfile(ColorSpace.HSV, NextColor.Companion.hsv(225f, 0.6f, 0.5f), NextColor.Companion.hsv(25f, 0.4f, 1f));

    public enum SpindexerFullnessState {
        FULL, FILLING, NOTHING
    }

    public SpindexerFullnessState spindexerFull = SpindexerFullnessState.NOTHING;

    NextColorDistanceSensor intakeChamber = new NextColorDistanceSensor("intakeChamberSensor", true);
    NextColorDistanceSensor storageChamber = new NextColorDistanceSensor("storageChamberSensor", true);
    NextColorDistanceSensor launcherChamber = new NextColorDistanceSensor("launcherChamberSensor", true);
    NextDigitalSensor beamBreak = new NextDigitalSensor("beamBreak");
    NextRGBIndicator rgb2 = new NextRGBIndicator("rgb2", 0.01);

    public boolean beamBreakSensedArtifact() {
        return beamBreakSensedArtifact == SensedArtifact.ARTIFACT;
    }

    public boolean intakeChamberHasArtifact() {
        return intakeSensedArtifact == SensedArtifact.ARTIFACT;
    }

    public boolean storageHasArtifact() {
        return storageSensedArtifact == SensedArtifact.ARTIFACT;
    }

    public boolean launcherHasArtifact() {
        return launcherSensedArtifact == SensedArtifact.ARTIFACT;
    }

    private boolean isFull() {
        return intakeChamberHasArtifact() && storageHasArtifact() && launcherHasArtifact();
    }

    public boolean isSpindexerFull() {
        return spindexerFull == SpindexerFullnessState.FULL;
    }

    public boolean isEmpty() {
        return !intakeChamberHasArtifact() && !storageHasArtifact() && !launcherHasArtifact();
    }

    public int artifactCount() {
        int count = 0;
        count += intakeChamberHasArtifact() ? 1 : 0;
        count += storageHasArtifact() ? 1 : 0;
        count += launcherHasArtifact() ? 1 : 0;
        return count;
    }


    private DetectedColor readGreenPurple(NextColorDistanceSensor sensor) {
        if (sensor.isColor(GREEN_PROFILE)) return DetectedColor.GREEN;
        if (sensor.isColor(PURPLE_PROFILE)) return DetectedColor.PURPLE;
        return DetectedColor.UNKNOWN;
    }

    @Override
    public void periodic() {
        intakeChamber.update();
        storageChamber.update();
        launcherChamber.update();

        intakeSensedArtifact = intakeChamber.isWithinDistance(DISTANCE_THRESHOLD_CM) ? SensedArtifact.ARTIFACT : SensedArtifact.NOARTIFACT;
        storageSensedArtifact = storageChamber.isWithinDistance(STORAGE_DISTANCE_THRESHOLD_CM) ? SensedArtifact.ARTIFACT : SensedArtifact.NOARTIFACT;
        launcherSensedArtifact = launcherChamber.isWithinDistance(DISTANCE_THRESHOLD_CM) ? SensedArtifact.ARTIFACT : SensedArtifact.NOARTIFACT;

        intakeColor = intakeSensedArtifact == SensedArtifact.ARTIFACT ? readGreenPurple(intakeChamber) : DetectedColor.UNKNOWN;
        storageColor = storageSensedArtifact == SensedArtifact.ARTIFACT ? readGreenPurple(storageChamber) : DetectedColor.UNKNOWN;
        launcherColor = launcherSensedArtifact == SensedArtifact.ARTIFACT ? readGreenPurple(launcherChamber) : DetectedColor.UNKNOWN;

        beamBreakSensedArtifact = beamBreak.isTriggered() ? SensedArtifact.ARTIFACT : SensedArtifact.NOARTIFACT;

        if (isFull()) {
            spindexerFull = SpindexerFullnessState.FULL;
        } else if (isEmpty()) {
            spindexerFull = SpindexerFullnessState.NOTHING;
        } else {
            spindexerFull = SpindexerFullnessState.FILLING;
        }

        switch (artifactCount()) {
            case 0: rgb2.setColor(NextRGBIndicator.Color.RED); break;
            case 1: rgb2.setColor(NextRGBIndicator.Color.YELLOW); break;
            case 2: rgb2.setColor(NextRGBIndicator.Color.BLUE); break;
            case 3: rgb2.setColor(NextRGBIndicator.Color.GREEN); break;
        }
    }
}
