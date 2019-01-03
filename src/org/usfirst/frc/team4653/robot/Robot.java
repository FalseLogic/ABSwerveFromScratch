package org.usfirst.frc.team4653.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4653.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {
	
	public static OI oi;
	public static DriveTrain driveTrain;
	
	TalonSRX frontLeft = new TalonSRX(Constants.FLdrive);
	TalonSRX frontRight = new TalonSRX(Constants.FRdrive);
	TalonSRX backLeft = new TalonSRX(Constants.BLdrive);
	TalonSRX backRight = new TalonSRX(Constants.BRdrive);
	TalonSRX frontLeftT = new TalonSRX(Constants.FLturn);
	TalonSRX frontRightT = new TalonSRX(Constants.FRturn);
	TalonSRX backLeftT = new TalonSRX(Constants.BLturn);
	TalonSRX backRightT = new TalonSRX(Constants.BRturn);

	@Override
	public void robotInit() {
		oi = new OI();
		driveTrain = new DriveTrain();
	}

	@Override
	public void teleopInit() {
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		
		if(oi.stick.getRawButton(5)) {
			frontLeftT.set(ControlMode.PercentOutput, oi.stick.getY());
		}
		else {
			frontLeftT.set(ControlMode.PercentOutput, 0);
		}
		if(oi.stick.getRawButton(6)) {
			frontRightT.set(ControlMode.PercentOutput, oi.stick.getY());
		}
		else {
			frontRightT.set(ControlMode.PercentOutput, 0);
		}
		if(oi.stick.getRawButton(3)) {
			backLeftT.set(ControlMode.PercentOutput, oi.stick.getY());
		}
		else {
			backLeftT.set(ControlMode.PercentOutput, 0);
		}
		if(oi.stick.getRawButton(4)) {
			backRightT.set(ControlMode.PercentOutput, oi.stick.getY());
		}
		else {
			backRightT.set(ControlMode.PercentOutput, 0);
		}
		
		driveTrain.printRawTurnEnc();
		
		SmartDashboard.putNumber("target angle?", oi.getStickAngle());
	}
	
	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
		
		frontLeft.set(ControlMode.Disabled, 0);
		frontRight.set(ControlMode.Disabled, 0);
		backLeft.set(ControlMode.Disabled, 0);
		backRight.set(ControlMode.Disabled, 0);
		frontLeft.set(ControlMode.Disabled, 0);
		frontRight.set(ControlMode.Disabled, 0);
		backLeft.set(ControlMode.Disabled, 0);
		backRight.set(ControlMode.Disabled, 0);
		
	}
}
