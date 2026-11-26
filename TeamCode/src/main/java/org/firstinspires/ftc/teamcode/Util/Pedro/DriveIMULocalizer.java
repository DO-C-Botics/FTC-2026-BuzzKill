package org.firstinspires.ftc.teamcode.Util.Pedro;

import com.pedropathing.localization.Localizer;
import com.pedropathing.localization.MotionState;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Velocity;
import com.pedropathing.revhub.localizers.RevHubIMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveIMULocalizer implements Localizer {
    private final DcMotorEx fl, fr, bl, br;
    private final RevHubIMU imu;

    private static final double TICKS_PER_REV = 449.6;
    private static final double WHEEL_DIAMETER = 3.2;
    private static final double LATERAL_MULTIPLIER = 1.0;

    private static final double INCHES_PER_TICK = (Math.PI * WHEEL_DIAMETER) / TICKS_PER_REV;

    private int lastFL, lastFR, lastBL, lastBR;
    private double headingOffset = 0.0;
    private double lastHeading = 0.0;
    private Pose currentPose = Pose.zero();
    private Velocity currentVelocity = Velocity.zero();
    private long lastTimeNanos;

    public DriveIMULocalizer(HardwareMap h) {
        fl = h.get(DcMotorEx.class, "front_left");
        fr = h.get(DcMotorEx.class, "front_right");
        bl = h.get(DcMotorEx.class, "back_left");
        br = h.get(DcMotorEx.class, "back_right");

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        imu = new RevHubIMU(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        imu.initialize(h, "imu");

        reset();
    }

    @Override
    public void update() {
        long now = System.nanoTime();
        double dt = (now - lastTimeNanos) / 1e9;
        lastTimeNanos = now;

        int curFL = fl.getCurrentPosition();
        int curFR = fr.getCurrentPosition();
        int curBL = bl.getCurrentPosition();
        int curBR = br.getCurrentPosition();

        double dFL = (curFL - lastFL) * INCHES_PER_TICK;
        double dFR = (curFR - lastFR) * INCHES_PER_TICK;
        double dBL = (curBL - lastBL) * INCHES_PER_TICK;
        double dBR = (curBR - lastBR) * INCHES_PER_TICK;

        lastFL = curFL; lastFR = curFR; lastBL = curBL; lastBR = curBR;

        double dxRobot = (dFL + dFR + dBL + dBR) / 4.0;
        double dyRobot = (-dFL + dFR + dBL - dBR) / 4.0 * LATERAL_MULTIPLIER;

        double heading = angleWrap(imu.getHeading() + headingOffset);
        double dHeading = angleWrap(heading - lastHeading);
        double midHeading = lastHeading + dHeading / 2.0;
        lastHeading = heading;

        double cos = Math.cos(midHeading);
        double sin = Math.sin(midHeading);
        double dxField = dxRobot * cos - dyRobot * sin;
        double dyField = dxRobot * sin + dyRobot * cos;

        currentPose = new Pose(
                currentPose.x() + dxField,
                currentPose.y() + dyField,
                heading
        );

        if (dt > 0) {
            currentVelocity = new Velocity(
                    dxField / dt,
                    dyField / dt,
                    dHeading / dt
            );
        }
    }

    @Override
    public void setPose(Pose pose) {
        headingOffset = angleWrap(pose.heading() - imu.getHeading());
        lastHeading = pose.heading();
        currentPose = pose;
    }

    @Override
    public MotionState state() {
        return MotionState.ofVelocity(currentPose, currentVelocity);
    }

    @Override
    public void reset() {
        lastFL = fl.getCurrentPosition();
        lastFR = fr.getCurrentPosition();
        lastBL = bl.getCurrentPosition();
        lastBR = br.getCurrentPosition();
        imu.resetYaw();
        headingOffset = 0.0;
        lastHeading = 0.0;
        currentPose = Pose.zero();
        currentVelocity = Velocity.zero();
        lastTimeNanos = System.nanoTime();
    }

    private double angleWrap(double angle) {
        while (angle > Math.PI) angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }
}
