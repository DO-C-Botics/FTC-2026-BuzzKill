package org.firstinspires.ftc.teamcode.Commands.CommandGroups;

import static org.firstinspires.ftc.teamcode.Constants.PollenShooterConstants.PollenShooterIndexerConstants.indexerSpeed;
import static org.firstinspires.ftc.teamcode.Constants.PollenShooterConstants.PollenShooterIndexerConstants.timeOutShooting;
import static org.firstinspires.ftc.teamcode.Constants.PollenShooterConstants.PollenShooterIndexerConstants.timeOutBetweenShots;
import static org.firstinspires.ftc.teamcode.Constants.PollenShooterConstants.shooterVelocityRAD;

import com.arcrobotics.ftclib.command.RepeatCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Commands.PollenIndexerCMD;
import org.firstinspires.ftc.teamcode.Commands.PollenShooterCMD;
import org.firstinspires.ftc.teamcode.Commands.WaitCMD;
import org.firstinspires.ftc.teamcode.Subsystems.PollenIndexerSub;
import org.firstinspires.ftc.teamcode.Subsystems.PollenShooterSub;

public class ShooterRoutine extends SequentialCommandGroup {

    public ShooterRoutine(PollenShooterSub pollenShooterSub, PollenIndexerSub pollenIndexerSub){

        addCommands(
                new PollenShooterCMD(pollenShooterSub, shooterVelocityRAD),
                new RepeatCommand(
                        new SequentialCommandGroup(
                                new WaitCMD(timeOutBetweenShots),
                                new PollenIndexerCMD(pollenIndexerSub, indexerSpeed, timeOutShooting),
                                new WaitCMD(timeOutBetweenShots)
                        )
                )

        );

    }
}
