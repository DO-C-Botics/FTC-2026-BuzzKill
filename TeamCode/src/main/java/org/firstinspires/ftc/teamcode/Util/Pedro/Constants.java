package org.firstinspires.ftc.teamcode.Util.Pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("front_left");
        c.frontRightName.set("front_right");
        c.backLeftName.set("back_left");
        c.backRightName.set("back_right");
        // Must match the directions that drive ALL 4 wheels forward!
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.74);
                Controller secondaryTranslationalForward = Controller.proportional(0.27);
                Controller primaryTranslationalLateral = Controller.proportional(0.85);
                Controller secondaryTranslationalLateral = Controller.proportional(0.35);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.0188));
                c.brake.set(Controller.proportionalFeedforward(0.0160));

                c.headingFeedback.set(Controller.proportional(2.5));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.049, 0.011));

                c.linearBrakeCoefficients.set(Matrix.diag(0.0162, 0.00245));
                c.quadraticBrakeCoefficients.set(Matrix.diag(3.64E-5, 3.73E-4));

                c.maxAchievableForwardVelocity.set(46.67);
                c.maxAchievableStrafeVelocity.set(44.46);
                c.naturalForwardDeceleration.set(102.37);
                c.naturalStrafeDeceleration.set(273.15);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new DriveIMULocalizer(h),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}
