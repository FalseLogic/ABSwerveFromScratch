package org.usfirst.frc.team4653.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team4653.robot.Constants.Location;
import org.usfirst.frc.team4653.robot.OI.Stick;
import org.usfirst.frc.team4653.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {
	
	public static DriveTrain driveTrain;
	public static OI oi;

	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		oi = new OI();

		oi.resetGyro();
	}

	@Override
	public void autonomousInit() {
		//driveTrain.newOffsets();
		oi.resetGyro();
	}

	@Override
	public void autonomousPeriodic() {
		driveTrain.swerveDrive(.1, -.075, .35);
	}
	

	@Override
	public void teleopInit() {
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		//driveTrain.printRawTurnEnc();
		driveTrain.printAdjTurnEnc();

		if(oi.leftStick.getRawButtonPressed(7)) {
			oi.resetGyro();
		}

		
	}

	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
	}

}