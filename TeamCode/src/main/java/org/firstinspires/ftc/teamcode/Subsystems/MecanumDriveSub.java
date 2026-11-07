package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Constants;

public class MecanumDriveSub extends SubsystemBase {

    public final DcMotor m_Fl, m_Fr, m_Rl, m_Rr;

    // Store last joystick values for telemetry
    private double fwdPower, strPower, rotPower;

    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    public final IMU imu;
    public boolean isFieldCentric = Constants.mecanumConstants.isFieldCentric; //true = field-centric, false = robot-centric
    private double strafeCorrection = Constants.mecanumConstants.strafeCorrection;

    public MecanumDriveSub(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight, HardwareMap hardwareMap) {
        m_Fl = frontLeft;
        m_Fr = frontRight;
        m_Rl = backLeft;
        m_Rr = backRight;

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );

        imu.initialize(parameters);

        // Set motor directions (typical mecanum setup)
        m_Fl.setDirection(DcMotorSimple.Direction.FORWARD);
        m_Fr.setDirection(DcMotorSimple.Direction.REVERSE);
        m_Rl.setDirection(DcMotorSimple.Direction.FORWARD);
        m_Rr.setDirection(DcMotorSimple.Direction.REVERSE);

        resetIMU();
    }

    public void setFieldCentric(boolean fieldCentric){
        isFieldCentric = fieldCentric;
    }

    public void resetIMU(){
        imu.resetYaw();
    }

    /**
     * Drive method for teleop.
     * @param forward forward/backward input (-1 to 1)
     * @param strafe left/right input (-1 to 1)
     * @param rotation rotation input (-1 to 1)
     */
    public void drive(double forward, double strafe, double rotation) {
        // Apply deadzone
        forward = applyDeadzone(forward, Constants.mecanumConstants.thresHold);
        strafe  = applyDeadzone(strafe, Constants.mecanumConstants.thresHold);
        rotation = applyDeadzone(rotation, Constants.mecanumConstants.thresHold);

        fwdPower = forward;
        strPower = strafe;
        rotPower = rotation;

        //Field centric math
        if (isFieldCentric) {
            YawPitchRollAngles robotOrientation = imu.getRobotYawPitchRollAngles();
            double headingRad = -robotOrientation.getYaw(AngleUnit.RADIANS);  // -heading

            double tempForward = forward * Math.cos(headingRad) - strafe * Math.sin(headingRad);
            double tempStrafe  = forward * Math.sin(headingRad) + strafe * Math.cos(headingRad);

            forward = tempForward;
            strafe  = tempStrafe;
        }

        //also apply strafe correction (optional, but helps with the field-centric feel when driving)
        strafe *= strafeCorrection;



        // Mecanum kinematics
        double fl = forward + strafe + rotation;
        double fr = forward - strafe + rotation;
        double bl = forward - strafe - rotation;
        double br = forward + strafe - rotation;

        // Normalize motor powers
        double max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(fr), Math.max(Math.abs(bl), Math.abs(br)))));
        fl /= max;
        fr /= max;
        bl /= max;
        br /= max;

        // Set motor powers
        m_Fl.setPower(fl);
        m_Fr.setPower(fr);
        m_Rl.setPower(bl);
        m_Rr.setPower(br);
    }

    // Deadzone helper
    private double applyDeadzone(double value, double threshold) {
        return Math.abs(value) > threshold ? value : 0;
    }

    public DcMotor getFl(){
        return m_Fl;
    }
    public DcMotor getFr(){
        return m_Fr;
    }
    public DcMotor getRl(){
        return m_Rl;
    }
    public DcMotor getRr(){
        return m_Rr;
    }

    public double getFwdPower(){
        return fwdPower;
    }

    public double getStrPower(){
        return strPower;
    }

    public double getRotPower(){
        return rotPower;
    }

    @Override
    public void periodic() {
        TelemetryPacket drivePacket = new TelemetryPacket();
        drivePacket.put("Forward", getFwdPower());
        drivePacket.put("Strafe", getStrPower());
        drivePacket.put("Rotation", getRotPower());
        drivePacket.put("FL Power", getFl().getPower());
        drivePacket.put("FR Power", getFr().getPower());
        drivePacket.put("BL Power", getRl().getPower());
        drivePacket.put("BR Power", getRr().getPower());
        dashboard.sendTelemetryPacket(drivePacket);
    }
}