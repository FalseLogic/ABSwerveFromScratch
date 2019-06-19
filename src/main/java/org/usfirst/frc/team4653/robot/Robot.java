package org.usfirst.frc.team4653.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4653.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
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

	Preferences prefs;
	double P, I, D, F;

	@Override
	public void robotInit() {
		oi = new OI();
		driveTrain = new DriveTrain();

		driveTrain.resetDriveEncoders();
		driveTrain.resetTurnEncoders();

		oi.resetGyro();
	}

	@Override
	public void teleopInit() {
		

		prefs = Preferences.getInstance();
		P = prefs.getDouble("P gain", 0.000146484375);
		I = prefs.getDouble("I gain", 0);
		D = prefs.getDouble("D gain", 0);
		F = prefs.getDouble("F gain", 0);

		driveTrain.setAllPIDF(P, I, D, F);
	}

	@Override
	public void teleopPeriodic() {

		Scheduler.getInstance().run();

		//if(oi.stick.getRawButton(1)) {
		//	driveTrain.swerveDrive(true, true);
		//}
		//else {
		//	driveTrain.swerveDrive(true, false);
		//}

		if(oi.stick.getRawButtonPressed(11)) {
			driveTrain.resetDriveEncoders();
			driveTrain.resetTurnEncoders();
			oi.resetGyro();
		}
		
		
		if(oi.stick.getRawButton(5)) {
			frontLeft.set(ControlMode.PercentOutput, oi.stick.getY());
		}
		else {
			frontLeft.set(ControlMode.PercentOutput, 0);
		}
		if(oi.stick.getRawButton(6)) {
			frontRight.set(ControlMode.PercentOutput, oi.stick.getY());
		}
		else {
			frontRight.set(ControlMode.PercentOutput, 0);
		}
		if(oi.stick.getRawButton(3)) {
			backLeft.set(ControlMode.PercentOutput, oi.stick.getY());
		}
		else {
			backLeft.set(ControlMode.PercentOutput, 0);
		}
		if(oi.stick.getRawButton(4)) {
			backRight.set(ControlMode.PercentOutput, oi.stick.getY());
		}
		else {
			backRight.set(ControlMode.PercentOutput, 0);
		}
		frontLeftT.set(ControlMode.PercentOutput, 0);
		frontRightT.set(ControlMode.PercentOutput, 0);
		backLeftT.set(ControlMode.PercentOutput, 0);
		backRightT.set(ControlMode.PercentOutput, 0);
		
		if(oi.stick.getRawButton(1)) {
			driveTrain.printRawTurnEnc();
		}
		else {
			driveTrain.printDriveRawPosition();
		}
		
		SmartDashboard.putNumber("target angle?", oi.getStickAngle());
		SmartDashboard.putNumber("stick magnitude", oi.getStickMagnitude());

		SmartDashboard.putNumber("stick X", oi.getStickX());
		SmartDashboard.putNumber("stick Y", oi.getStickY());
		SmartDashboard.putNumber("stick Z", oi.getStickZ());
		SmartDashboard.putNumber("gyro angle", oi.getGyroDegrees());
	}
	
	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
	}

}