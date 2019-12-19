package org.usfirst.frc.team4653.robot.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDOutput;

public class SRXOutput implements PIDOutput {

    private TalonSRX talon;

    public SRXOutput(TalonSRX talon) {
        this.talon = talon;
    } 

    public void pidWrite(double output) {
        talon.set(ControlMode.PercentOutput, output);
    }
} 