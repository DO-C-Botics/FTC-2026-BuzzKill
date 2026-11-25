package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Constants.OIConstants.thresHoldController;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Commands.CommandGroups.ShooterRoutine;
import org.firstinspires.ftc.teamcode.Commands.IntakeCMD;
import org.firstinspires.ftc.teamcode.Commands.MecanumDriveCMD;
import org.firstinspires.ftc.teamcode.Commands.PollenIndexerCMD;
import org.firstinspires.ftc.teamcode.Commands.PollenShooterCMD;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSub;
import org.firstinspires.ftc.teamcode.Subsystems.LimelightSub;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSub;
import org.firstinspires.ftc.teamcode.Subsystems.PollenIndexerSub;
import org.firstinspires.ftc.teamcode.Subsystems.PollenShooterSub;

@TeleOp(name = "TeleOp")
public class RobotContainer extends CommandOpMode {
    private MecanumDriveSub driveSub;
    private PollenShooterSub pollenShooterSub;
    private PollenIndexerSub pollenIndexerSub;
    private LimelightSub limelightSub;
    private IntakeSub intakeSub;
    private GamepadEx driverJoystick;

    @Override
    public void initialize() {
        driveSub = new MecanumDriveSub(
                hardwareMap.get(DcMotor.class, "front_left"),
                hardwareMap.get(DcMotor.class, "front_right"),
                hardwareMap.get(DcMotor.class, "back_left"),
                hardwareMap.get(DcMotor.class, "back_right"),
                hardwareMap
        );

        pollenShooterSub = new PollenShooterSub(
                hardwareMap.get(DcMotorEx.class, "Pollen_Shooter")
        );

        pollenIndexerSub = new PollenIndexerSub(
                hardwareMap.get(DcMotorEx.class, "Pollen_Indexer")
        );

        intakeSub = new IntakeSub(
                hardwareMap.get(DcMotor.class, "Intake")
        );

        limelightSub = new LimelightSub(hardwareMap);


        driverJoystick = new GamepadEx(gamepad1);

        setDefaultCommands();
        runCommands();

    }
    /**
     * Apply a joystick deadband so tiny inputs don’t move the motors.
     *
     * @param value     joystick value
     * @param threshold minimum absolute value to count as input
     * @return filtered value
     */
    private double applyDeadband(double value, double threshold) {
        return (Math.abs(value) > threshold) ? value : 0.0;
    }

    public void setDefaultCommands() {

        driveSub.setDefaultCommand(
                new MecanumDriveCMD(
                        driveSub,
                        () -> applyDeadband(driverJoystick.getLeftY(), thresHoldController),  // Forward/back
                        () -> applyDeadband(driverJoystick.getLeftX(), thresHoldController),  // Strafe
                        () -> applyDeadband(driverJoystick.getRightX(), thresHoldController), // Rotate
                        () -> driverJoystick.getButton(GamepadKeys.Button.START)        // Reset
                )
        );

        Trigger shootTrigger = new Trigger(() -> {
            return driverJoystick.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.6;
        });
        shootTrigger.whileActiveContinuous(new ShooterRoutine(pollenShooterSub, pollenIndexerSub, limelightSub));

        //Kill Shooter
        driverJoystick.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whileActiveContinuous(
                        new PollenShooterCMD(pollenShooterSub, limelightSub, 0)
                );

        Trigger intakeTrigger = new Trigger(() -> {
            return driverJoystick.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.6;
        });
        intakeTrigger.whileActiveContinuous(new IntakeCMD(intakeSub));

        driverJoystick.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileActiveContinuous(
                        new IntakeCMD(intakeSub, true)
                );
    }

    private void runCommands() {
        // Add other commands here if needed


    }
}
