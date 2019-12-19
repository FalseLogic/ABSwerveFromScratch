package org.usfirst.frc.team4653.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.usfirst.frc.team4653.robot.subsystems.DriveTrain;

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
	public void teleopInit() {
		driveTrain.newOffsets();
		oi.resetGyro();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		driveTrain.printRawTurnEnc();
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