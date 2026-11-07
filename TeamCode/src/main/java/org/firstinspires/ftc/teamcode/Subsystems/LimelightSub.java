package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class LimelightSub extends SubsystemBase {

    Limelight3A limelight;
    private final FtcDashboard dashboard = FtcDashboard.getInstance();


    public LimelightSub(HardwareMap hardwareMap){
        this.limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.start();
    }
    public boolean hasTarget(){
        LLResult r = limelight.getLatestResult();
        return r != null && r.isValid();
    }
    public double getTx(){ //horizontal offset in degrees
        LLResult r = limelight.getLatestResult();
        return (r != null && r.isValid()) ? r.getTx() : -1.0; //-1 means no target
    }

    public double getTy(){
        LLResult r = limelight.getLatestResult();
        return (r != null && r.isValid()) ? r.getTy() : -1.0; //-1 mean no target
    }

    public double getAprilTagDistanceInches() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return -1;

        List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
        if (fiducials == null || fiducials.isEmpty()) return -1;

        LLResultTypes.FiducialResult fid = fiducials.get(0);

        Pose3D targetPose = fid.getTargetPoseCameraSpace();
        if (targetPose == null) return -1;

        double x = targetPose.getPosition().x;
        double y = targetPose.getPosition().y;
        double z = targetPose.getPosition().z;

        return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public void periodic() {
        LLResult result = limelight.getLatestResult();

        TelemetryPacket llpacket = new TelemetryPacket();

        if (result != null && result.isValid()) {
            llpacket.put("Tx", result.getTx());
            llpacket.put("Ty", result.getTy());
            llpacket.put("Ta", result.getTa());
            llpacket.put("DistIn", getAprilTagDistanceInches());
        } else {
            llpacket.put("Limelight", "No valid target");
        }

        dashboard.sendTelemetryPacket(llpacket);
    }

}