package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Constants.OIConstants.thresHoldController;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Commands.MecanumDriveCMD;
import org.firstinspires.ftc.teamcode.Subsystems.MecanumDriveSub;

@TeleOp(name = "TeleOp")
public class RobotContainer extends CommandOpMode {
    private MecanumDriveSub driveSub;
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
    }

    private void runCommands() {
        // Add other commands here if needed


    }
}
