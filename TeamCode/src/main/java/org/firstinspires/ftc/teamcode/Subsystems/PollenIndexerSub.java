package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class PollenIndexerSub {
    DcMotor transferMotor;

    public PollenIndexerSub(DcMotor transferMotor){
        this.transferMotor = transferMotor;

        transferMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public DcMotor getTransferMotor(){
        return transferMotor;
    }

    public void setTransferMotorSpeed(double speed){
        transferMotor.setPower(speed);
    }
}
