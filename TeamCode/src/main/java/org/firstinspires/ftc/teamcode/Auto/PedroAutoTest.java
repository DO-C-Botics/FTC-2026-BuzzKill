package org.firstinspires.ftc.teamcode.Auto;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Util.Pedro.Constants;
import org.firstinspires.ftc.teamcode.Auto.AutoPaths;

@Autonomous(name = "Pedro Auto Test")
public class PedroAutoTest extends OpMode {
    private Follower follower;
    private AutoPaths paths;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        paths = new AutoPaths();

        // Tells Pedro Pathing where the robot starts on the field
        follower.setPose(paths.start);
    }

    @Override
    public void start() {
        // Drives path1 (holds position at the end automatically)
        follower.follow(paths.path1());
    }

    @Override
    public void loop() {
        follower.update();

        telemetry.addData("Pose", follower.pose());
        telemetry.addData("Busy", follower.isBusy());
        telemetry.update();
    }
}
