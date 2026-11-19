package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class IntakeSub extends SubsystemBase {
    DcMotor intakeMotor;

    public IntakeSub(DcMotor intakeMotor){
        this.intakeMotor = intakeMotor;

        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void setIntakeMotorSpeed(double speed){
        intakeMotor.setPower(speed);
    }
}
