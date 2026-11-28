package org.firstinspires.ftc.teamcode.Auto.Paths;

import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Auto.AutoRobotContainer;
import org.firstinspires.ftc.teamcode.Auto.MoveRobotEncoderXY_CMD;
import org.firstinspires.ftc.teamcode.Commands.CommandGroups.ShooterRoutine;
import org.firstinspires.ftc.teamcode.Commands.WaitCMD;

@Autonomous
public class FourPollenPark extends AutoRobotContainer {

    @Override
    public void path() {
        schedule(
                new SequentialCommandGroup(
                        new WaitCMD(2.0),
                        new MoveRobotEncoderXY_CMD(-2.0,-2.0, 3, 0.4, driveSub),
                        new ParallelRaceGroup(
                                new WaitCMD(10.0),
                                new ShooterRoutine(flywheelSub, indexerSub, limelightSub)
                        ),

                        new MoveRobotEncoderXY_CMD(-100,-100, 3, 0.6, driveSub),
                        new MoveRobotEncoderXY_CMD(12.5, -12.5, 2.0, 0.4, driveSub),
                        new MoveRobotEncoderXY_CMD(30.0, 30.0, 2.0, 0.4, driveSub)
                )
        );
    }
}