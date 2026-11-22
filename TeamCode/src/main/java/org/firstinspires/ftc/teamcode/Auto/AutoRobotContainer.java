package org.firstinspires.ftc.teamcode.Auto;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Commands.CommandGroups.ShooterRoutine;
import org.firstinspires.ftc.teamcode.Commands.PollenShooterCMD;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSub;
import org.firstinspires.ftc.teamcode.Subsystems.PollenIndexerSub;
import org.firstinspires.ftc.teamcode.Subsystems.PollenShooterSub;

public class AutoRobotContainer extends CommandOpMode {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public MecanumDriveSub driveSub;


    public DcMotorEx shooterMotor;
    public PollenShooterSub flywheelSub;
    public DcMotor indexerMotor;
    public PollenIndexerSub indexerSub;

    @Override
    public void initialize() {
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");


        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        shooterMotor = hardwareMap.get(DcMotorEx.class,"Pollen_Shooter");
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        indexerMotor = hardwareMap.get(DcMotor.class, "Pollen_Indexer");
        indexerMotor.setDirection(DcMotorSimple.Direction.REVERSE);




        initSubsystems();
        path();


    }


    private void initSubsystems(){
        driveSub = new MecanumDriveSub(frontLeft, frontRight, backLeft, backRight, hardwareMap);

        flywheelSub = new PollenShooterSub(shooterMotor);

        indexerSub = new PollenIndexerSub(indexerMotor);

    }



    public void runShooter(){
        schedule(
                new SequentialCommandGroup(
                        new ShooterRoutine(flywheelSub, indexerSub)
                )
        );
    }



    public void stopShooter(){
        schedule(
                new SequentialCommandGroup(
                        new PollenShooterCMD(flywheelSub, 0)
                )
        );
    }


    public void path(){

    }
}