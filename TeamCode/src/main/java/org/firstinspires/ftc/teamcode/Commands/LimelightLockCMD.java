package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.Constants.MecanumConstants.ShooterLockedZone;
import static org.firstinspires.ftc.teamcode.Constants.MecanumConstants.clippedRotLockOnPower;
import static org.firstinspires.ftc.teamcode.Constants.MecanumConstants.llLockOnKp;
import static org.firstinspires.ftc.teamcode.Constants.MecanumConstants.tyOffSet;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.LimelightSub;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSub;

public class LimelightLockCMD extends CommandBase {
    LimelightSub llSub;
    MecanumDriveSub driveSub;

    public LimelightLockCMD(LimelightSub llSub,
                            MecanumDriveSub driveSub){
        this.llSub = llSub;
        this.driveSub = driveSub;
        addRequirements(driveSub);

    }

    @Override
    public void initialize() {
        driveSub.drive(0,0,0);
    }

    @Override
    public void execute() {

        double error = llSub.getTx();

        double rotPower = error * llLockOnKp;

        rotPower = Math.max(Math.min(rotPower, clippedRotLockOnPower), -clippedRotLockOnPower);

        driveSub.drive(0,0, rotPower);

    }

    @Override
    public boolean isFinished() {
        return Math.abs(llSub.getTy()) <= ShooterLockedZone;
    }

    @Override
    public void end(boolean interrupted) {
        driveSub.drive(0,0,0);
    }
}