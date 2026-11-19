package org.firstinspires.ftc.teamcode.Commands;

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
        boolean isAtSetVelocity = (pollenShooterSub.getFlyWheelMotor().getVelocity(AngleUnit.RADIANS) > (flyWheelPower - shooterTolerance))
                && (pollenShooterSub.getFlyWheelMotor().getVelocity(AngleUnit.RADIANS) < (flyWheelPower + shooterTolerance));
        return isAtSetVelocity;
    }
}
