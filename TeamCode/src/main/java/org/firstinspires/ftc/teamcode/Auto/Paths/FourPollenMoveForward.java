package org.firstinspires.ftc.teamcode.Auto.Paths;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Auto.AutoRobotContainer;
import org.firstinspires.ftc.teamcode.Auto.MoveRobotEncoderXY_CMD;
import org.firstinspires.ftc.teamcode.Commands.CommandGroups.ShooterRoutine;
import org.firstinspires.ftc.teamcode.Commands.WaitCMD;

@Autonomous
public class FourPollenMoveForward extends AutoRobotContainer {

    @Override
    public void path() {
        schedule(
                new SequentialCommandGroup(
                        new WaitCMD(5.0),
                        new MoveRobotEncoderXY_CMD(-1.5,-1.5, 3, 0.4, driveSub),
                        new WaitCMD(0.8),
                        new ShooterRoutine(flywheelSub, indexerSub, limelightSub)

                )
        );
    }
}