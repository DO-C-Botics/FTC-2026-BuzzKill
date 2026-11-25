package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.teamcode.Constants.PollenShooterConstants.shooterTolerance;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Subsystems.LimelightSub;
import org.firstinspires.ftc.teamcode.Subsystems.PollenShooterSub;
import org.firstinspires.ftc.teamcode.Util.ShooterLookup;

public class PollenShooterCMD extends CommandBase {
    PollenShooterSub pollenShooterSub;
    LimelightSub limelightSub;
    double flyWheelPower;
    private final boolean distanceBased;

    FtcDashboard dashboard = FtcDashboard.getInstance();

    public PollenShooterCMD(PollenShooterSub pollenShooterSub, LimelightSub limelightSub, double flywheelPower){
        this.pollenShooterSub = pollenShooterSub;
        this.limelightSub = limelightSub;
        this.flyWheelPower = flywheelPower;
        this.distanceBased = false;
        addRequirements(pollenShooterSub);

    }

    public PollenShooterCMD(PollenShooterSub pollenShooterSub, LimelightSub limelightSub){
        this.pollenShooterSub = pollenShooterSub;
        this.limelightSub = limelightSub;
        this.distanceBased = true;
        addRequirements(pollenShooterSub);

    }

    @Override
    public void execute() {

        if(distanceBased){
            double distIn = limelightSub.getAprilTagDistanceInches();
            flyWheelPower = ShooterLookup.getInterpolatedVelocity(distIn);
        }
        pollenShooterSub.setVelocity(flyWheelPower);

        TelemetryPacket ShootPacket = new TelemetryPacket();
        ShootPacket.put("RealVelocity", pollenShooterSub.getFlyWheelMotor().getVelocity(AngleUnit.RADIANS));
        ShootPacket.put("SetVelocity", flyWheelPower);
        dashboard.sendTelemetryPacket(ShootPacket);

    }

    @Override
    public boolean isFinished() {
        boolean isAtSetVelocity = (pollenShooterSub.getFlyWheelMotor().getVelocity(AngleUnit.RADIANS) > (flyWheelPower - shooterTolerance))
                && (pollenShooterSub.getFlyWheelMotor().getVelocity(AngleUnit.RADIANS) < (flyWheelPower + shooterTolerance));
        return isAtSetVelocity;
    }


}
