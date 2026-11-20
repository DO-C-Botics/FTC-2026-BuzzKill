package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.Constants.PollenShooterConstants.shooterKd;
import static org.firstinspires.ftc.teamcode.Constants.PollenShooterConstants.shooterKf;
import static org.firstinspires.ftc.teamcode.Constants.PollenShooterConstants.shooterKi;
import static org.firstinspires.ftc.teamcode.Constants.PollenShooterConstants.shooterKp;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class PollenShooterSub extends SubsystemBase {
    DcMotorEx flyWheelMotor;

    public PollenShooterSub(DcMotorEx flyWheelMotor){
        this.flyWheelMotor = flyWheelMotor;

        flyWheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        flyWheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        flyWheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,new PIDFCoefficients(shooterKp,shooterKi,shooterKd,shooterKf));

    }

    public DcMotorEx getFlyWheelMotor(){
        return flyWheelMotor;
    }

    public void setVelocity(double velocity){
        flyWheelMotor.setVelocity(velocity, AngleUnit.RADIANS);
    }
}
