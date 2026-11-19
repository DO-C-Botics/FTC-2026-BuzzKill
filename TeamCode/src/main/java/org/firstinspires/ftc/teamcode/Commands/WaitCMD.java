package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

public class WaitCMD extends CommandBase {
    private final double m_timeWaiting;
    private ElapsedTime runtime = new ElapsedTime();
    public WaitCMD(double timeWaiting){
        m_timeWaiting = timeWaiting;
    }
    @Override
    public void initialize(){
        runtime.reset();
    }
    @Override
    public void execute(){

    }
    @Override
    public boolean isFinished() {
        if(runtime.seconds() >= m_timeWaiting){
            return true;
        }
        return  false;

    }
}
