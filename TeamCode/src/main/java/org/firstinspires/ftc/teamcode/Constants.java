package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.config.Config;

@Config
public class Constants {

    public static final class MecanumConstants {
        public static final double thresHold = 0.05;
        public static double strafeCorrection = 1.1;
        public static boolean isFieldCentric = true;
        public static final class encoderAutoConstants{
            public static final double DRIVE_SPEED = 0.25;
            public static final double TURN_SPEED = 0.5;

            public static final double COUNTS_PER_MOTOR_REV = 28 ;    // REV ultraplanetary motor
            public static final double DRIVE_GEAR_REDUCTION = 20 ;     // External Gearing.
            public static final double WHEEL_DIAMETER_INCHES = 3 ;     // For figuring circumference
            public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                    (WHEEL_DIAMETER_INCHES * Math.PI);

        }
    }

    public static final class OIConstants{
        public static final double thresHoldController = 0.05;
    }


    @Config
    public static class PollenShooterConstants{
        public static  double shooterTolerance = 0.2;
        public static  double shooterVelocityRAD = 10.75;

        public static  double shooterKp = 11.78;
        public static  double shooterKi = 0.1;
        public static  double shooterKd = 0.0;
        public static  double shooterKf = 14.0;

        public static final class PollenShooterIndexerConstants{
            public static final double indexerSpeed = 1.0;
            public static double timeOutShooting = 0.7;
            public static double timeOutBetweenShots = 0.6;
        }
    }

    public static final class IntakeConstants{
        public static final double intakeSpeed = 0.75;
    }

    public static final class ConversionConstants{
        public static final double inchesToFeet = 12; //divide
    }
}
