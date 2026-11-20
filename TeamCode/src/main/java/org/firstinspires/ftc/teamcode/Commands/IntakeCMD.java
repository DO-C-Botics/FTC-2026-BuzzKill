package org.firstinspires.ftc.teamcode.Commands;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSub;

public class IntakeCMD extends CommandBase {

    IntakeSub intakeSub;
    public final double intakeSpeed = Constants.IntakeConstants.intakeSpeed;
    public boolean isReversed;
    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    public IntakeCMD(IntakeSub intakeSub){
        this.intakeSub = intakeSub;
        addRequirements(intakeSub);
    }

    public IntakeCMD(IntakeSub intakeSub, boolean isReversed){
            this.intakeSub = intakeSub;
            this.isReversed = isReversed;
            addRequirements(intakeSub);

    }

    @Override
    public void initialize() {
        intakeSub.setIntakeMotorSpeed(0);
    }

    @Override
    public void execute() {

        if(isReversed) {
            intakeSub.setIntakeMotorSpeed(-intakeSpeed);
        } else {
            intakeSub.setIntakeMotorSpeed(intakeSpeed);
        }

        TelemetryPacket TransferPacket = new TelemetryPacket();
        TransferPacket.put("Intake Speed", intakeSpeed);
        dashboard.sendTelemetryPacket(TransferPacket);

    }

    @Override
    public void end(boolean interrupted) {
        intakeSub.setIntakeMotorSpeed(0);

    }
}
