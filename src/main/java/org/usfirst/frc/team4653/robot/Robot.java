package org.usfirst.frc.team4653.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.usfirst.frc.team4653.robot.subsystems.DriveTrain;

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

	//Preferences prefs;
	//double P, I, D, F;

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

		/*
		prefs = Preferences.getInstance();
		P = prefs.getDouble("P gain", Constants.kP);
		I = prefs.getDouble("I gain", Constants.kI);
		D = prefs.getDouble("D gain", Constants.kD);
		F = prefs.getDouble("F gain", Constants.kF);
		driveTrain.setAllPIDF(P, I, D, F);
		*/

	}

	@Override
	public void teleopPeriodic() {

		Scheduler.getInstance().run();

		driveTrain.printAdjTurnEnc();
	}

	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
	}

}