package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.Constants.PollenShooterConstants.shooterTolerance;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Subsystems.PollenShooterSub;

public class PollenShooterCMD extends CommandBase {
    PollenShooterSub pollenShooterSub;
    double flyWheelPower;

    public PollenShooterCMD(PollenShooterSub pollenShooterSub, double flywheelPower){
        this.pollenShooterSub = pollenShooterSub;
        this.flyWheelPower = flywheelPower;
        addRequirements(pollenShooterSub);

    }

    @Override
    public void execute() {

        pollenShooterSub.setVelocity(flyWheelPower);

    }

    @Override
    public boolean isFinished() {
        double currentVelocity = Math.abs(pollenShooterSub.getFlyWheelMotor().getVelocity(AngleUnit.RADIANS));
        double targetVelocity = Math.abs(flyWheelPower);

        boolean isAtSetVelocity = (currentVelocity > (targetVelocity - shooterTolerance))
                && (currentVelocity < (targetVelocity + shooterTolerance));

        return isAtSetVelocity;
    }

}
