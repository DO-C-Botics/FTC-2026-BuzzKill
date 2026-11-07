package org.firstinspires.ftc.teamcode;

public class Constants {

    public static final class mecanumConstants{
        public static final double         thresHold = 0.05;
        public static double               clippedRotLockOnPower = 0.5;
        public static double               strafeCorrection = 1.1;
        public static boolean              isFieldCentric = true;
        public static final class encoderAutoConstants{
            public static final double     DRIVE_SPEED             = 0.25;
            public static final double     TURN_SPEED              = 0.5;

            public static final double     COUNTS_PER_MOTOR_REV    = 28 ;    // REV ultraplanetary motor
            public static final double     DRIVE_GEAR_REDUCTION    = 20 ;     // External Gearing.
            public static final double     WHEEL_DIAMETER_INCHES   = 3 ;     // For figuring circumference
            public static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                    (WHEEL_DIAMETER_INCHES * Math.PI);

        }
    }
}
