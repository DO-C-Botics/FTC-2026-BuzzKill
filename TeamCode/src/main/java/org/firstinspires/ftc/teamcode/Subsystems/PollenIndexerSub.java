package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class PollenIndexerSub extends SubsystemBase {
    DcMotor transferMotor;

    public PollenIndexerSub(DcMotor transferMotor){
        this.transferMotor = transferMotor;

        transferMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void setTransferMotorSpeed(double speed){
        transferMotor.setPower(speed);
    }
}
