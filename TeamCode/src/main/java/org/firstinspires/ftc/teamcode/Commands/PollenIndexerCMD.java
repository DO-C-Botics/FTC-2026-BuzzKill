package org.firstinspires.ftc.teamcode.Commands;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.PollenIndexerSub;

public class PollenIndexerCMD extends CommandBase {

    PollenIndexerSub pollenIndexerSub;
    double transferSpeed;

    private final double m_timeIndexing;
    private ElapsedTime runtime = new ElapsedTime();

    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    public PollenIndexerCMD(PollenIndexerSub pollenIndexerSub,
                       double transferSpeed,
                       double timeIndexing){

        this.pollenIndexerSub = pollenIndexerSub;
        this.transferSpeed = transferSpeed;
        this.m_timeIndexing = timeIndexing;

        addRequirements();

    }

    @Override
    public void initialize() {
        pollenIndexerSub.setTransferMotorSpeed(0);
        runtime.reset();

    }

    @Override
    public void execute() {

        pollenIndexerSub.setTransferMotorSpeed(transferSpeed);

        TelemetryPacket TransferPacket = new TelemetryPacket();
        TransferPacket.put("TransferSpeed", transferSpeed);
        dashboard.sendTelemetryPacket(TransferPacket);

    }

    @Override
    public boolean isFinished() {
        return runtime.seconds() >= m_timeIndexing;
    }

    @Override
    public void end(boolean interrupted) {
        pollenIndexerSub.setTransferMotorSpeed(0);

    }
}
