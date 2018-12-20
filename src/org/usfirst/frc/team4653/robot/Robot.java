package org.usfirst.frc.team4653.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4653.robot.commands.ArcadeDrive;
import org.usfirst.frc.team4653.robot.commands.BackwardDrive;
import org.usfirst.frc.team4653.robot.commands.ForwardDrive;
import org.usfirst.frc.team4653.robot.commands.LeftDrive;
import org.usfirst.frc.team4653.robot.commands.RightDrive;
import org.usfirst.frc.team4653.robot.commands.Turn;
import org.usfirst.frc.team4653.robot.subsystems.DriveTrain;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {
	
	public static DriveTrain driveTrain;
	public static OI oi;
	

	TalonSRX frontLeft = new TalonSRX(RobotMap.FLdrive);
	TalonSRX frontRight = new TalonSRX(RobotMap.FRdrive);
	TalonSRX backLeft = new TalonSRX(RobotMap.BLdrive);
	TalonSRX backRight = new TalonSRX(RobotMap.BRdrive);
	TalonSRX frontLeftT = new TalonSRX(RobotMap.FLturn);
	TalonSRX frontRightT = new TalonSRX(RobotMap.FRturn);
	TalonSRX backLeftT = new TalonSRX(RobotMap.BLturn);
	TalonSRX backRightT = new TalonSRX(RobotMap.BRturn);

	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		oi = new OI();

		frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		backLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		backRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		frontLeft.config_kF(0, 1.6, 10);
		frontLeft.config_kP(0, 1.75, 10);
		frontLeft.config_kI(0, 0, 10);
		frontLeft.config_kD(0, 15, 10);
		frontRight.config_kF(0, 1.6, 10);
		frontRight.config_kP(0, 1.75, 10);
		frontRight.config_kI(0, 0, 10);
		frontRight.config_kD(0, 15, 10);
		backLeft.config_kF(0, 1.6, 10);
		backLeft.config_kP(0, 1.75, 10);
		backLeft.config_kI(0, 0, 10);
		backLeft.config_kD(0, 15, 10);
		backRight.config_kF(0, 1.6, 10);
		backRight.config_kP(0, 1.75, 10);
		backRight.config_kI(0, 0, 10);
		backRight.config_kD(0, 15, 10);
	}

	@Override
	public void teleopInit() {
		oi.ahrs.reset();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		//oi.button7.whenActive(new ForwardDrive());
		//oi.button9.whenActive(new BackwardDrive());
		//oi.button11.whenActive(new LeftDrive());
		//oi.button12.whenActive(new RightDrive());
		if(oi.stick.getRawButton(1)) {
			if(oi.stick.getRawButton(5)) {
				frontLeftT.set(ControlMode.PercentOutput, oi.stick.getY());
			}
			if(oi.stick.getRawButton(6)) {
				frontRightT.set(ControlMode.PercentOutput, oi.stick.getY());
			}
			if(oi.stick.getRawButton(3)) {
				backLeftT.set(ControlMode.PercentOutput, oi.stick.getY());
			}
			if(oi.stick.getRawButton(4)) {
				backRightT.set(ControlMode.PercentOutput, oi.stick.getY());
			}
		}
		else {
			if(oi.stick.getRawButton(5)) {
				frontLeft.set(ControlMode.PercentOutput, oi.stick.getY());
			}
			if(oi.stick.getRawButton(6)) {
				frontRight.set(ControlMode.PercentOutput, oi.stick.getY());
			}
			if(oi.stick.getRawButton(3)) {
				backLeft.set(ControlMode.PercentOutput, oi.stick.getY());
			}
			if(oi.stick.getRawButton(4)) {
				backRight.set(ControlMode.PercentOutput, oi.stick.getY());
			}
		}
		//frontLeft.set(ControlMode.Velocity, oi.stick.getY() * 500);
		//frontRight.set(ControlMode.PercentOutput, oi.stick.getY());
		//backLeft.set(ControlMode.Velocity, oi.stick.getY() * 500);
		//backRight.set(ControlMode.Velocity, oi.stick.getY() * 500);
		
		driveTrain.printDriveEnc();
		
		SmartDashboard.putNumber("stick Y", oi.stick.getY());
	}
	
	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
		
		frontLeft.set(ControlMode.PercentOutput, 0);
		frontRight.set(ControlMode.PercentOutput, 0);
		backLeft.set(ControlMode.PercentOutput, 0);
		backRight.set(ControlMode.PercentOutput, 0);
		frontLeft.set(ControlMode.PercentOutput, 0);
		frontRight.set(ControlMode.PercentOutput, 0);
		backLeft.set(ControlMode.PercentOutput, 0);
		backRight.set(ControlMode.PercentOutput, 0);
		
	}
}
