package org.usfirst.frc.team4653.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.usfirst.frc.team4653.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Robot extends TimedRobot {
	
	public static OI oi;
	public static DriveTrain driveTrain;
	/*
	CANSparkMax frontLeft = new CANSparkMax(Constants.FLdrive, MotorType.kBrushless);
	CANSparkMax frontRight = new CANSparkMax(Constants.FRdrive, MotorType.kBrushless);
	CANSparkMax backLeft = new CANSparkMax(Constants.BLdrive, MotorType.kBrushless);
	CANSparkMax backRight = new CANSparkMax(Constants.BRdrive, MotorType.kBrushless);
	TalonSRX frontLeftT = new TalonSRX(Constants.FLturn);
	TalonSRX frontRightT = new TalonSRX(Constants.FRturn);
	TalonSRX backLeftT = new TalonSRX(Constants.BLturn);
	TalonSRX backRightT = new TalonSRX(Constants.BRturn);*/

	//Preferences prefs;
	//double P, I, D, F;

	@Override
	public void robotInit() {
		oi = new OI();
		driveTrain = new DriveTrain();

		oi.resetGyro();
	}

	@Override
	public void teleopInit() {

		/*
		prefs = Preferences.getInstance();
		P = prefs.getDouble("P gain", Constants.kP);
		I = prefs.getDouble("I gain", Constants.kI);
		D = prefs.getDouble("D gain", Constants.kD);
		driveTrain.setAllTurnPID(P, I, D);
		*/

		//driveTrain.setAllTurnPID(Constants.SWERVE_P_GAIN, Constants.SWERVE_I_GAIN, Constants.SWERVE_D_GAIN);
		

	}

	@Override
	public void teleopPeriodic() {

		if(oi.leftStick.getRawButtonPressed(11)) {
			driveTrain.resetTurnEncoders();
		}

		driveTrain.printAdjTurnEnc();

		Scheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
	}

}