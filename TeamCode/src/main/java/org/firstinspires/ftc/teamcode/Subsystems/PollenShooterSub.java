package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class PollenShooterSub extends SubsystemBase {
    DcMotorEx flyWheelMotor;

    public PollenShooterSub(DcMotorEx flyWheelMotor){
        this.flyWheelMotor = flyWheelMotor;

        flyWheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        flyWheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public DcMotorEx getFlyWheelMotor(){
        return flyWheelMotor;
    }

    public void setVelocity(double velocity){
        flyWheelMotor.setVelocity(velocity, AngleUnit.RADIANS);
    }
}
