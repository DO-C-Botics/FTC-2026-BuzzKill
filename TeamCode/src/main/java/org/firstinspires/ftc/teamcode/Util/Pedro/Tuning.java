package org.firstinspires.ftc.teamcode.Util.Pedro;

import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.Util.Pedro.procedures.ForesightTuner;
import org.firstinspires.ftc.teamcode.Util.Pedro.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.Util.Pedro.procedures.Tests;

public class Tuning {
    // Tuners go here

    @Tuner
    public static Procedure mecanumTuner() {
        return new MecanumTuner();
    }

    @Tuner
    public static Procedure foresightTuner() {
        return new ForesightTuner(
                hardwareMap -> new DriveIMULocalizer(hardwareMap),
                hardwareMap -> new Mecanum(hardwareMap, Constants.drivetrainConfig)
        );
    }

    @Tuner
    public static Procedure tests() {
        return new Tests(
                hardwareMap -> new Mecanum(hardwareMap, Constants.drivetrainConfig),
                hardwareMap -> new DriveIMULocalizer(hardwareMap),
                null
        );
    }
}
