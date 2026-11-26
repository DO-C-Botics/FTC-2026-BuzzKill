package org.firstinspires.ftc.teamcode.Auto;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class AutoPaths {
    private final PoseFactory poseFactory = PoseFactory.degrees();

    public final Pose start = poseFactory.of(56.2823, 7.5726, 90);
    private final Pose path1 = poseFactory.of(56.2823, 34.9274, 0);

    public Path path1() {
        return line(start, path1).linear(start, path1);
    }
}
