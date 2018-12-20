/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4653.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4653.robot.Robot;
import org.usfirst.frc.team4653.robot.RobotMap.DriveDirection;

public class ArcadeDrive extends Command {
	
	private DriveDirection driveDirection;
	
	public ArcadeDrive() {
		requires(Robot.driveTrain);
		driveDirection = DriveDirection.forward;
	}
	
	public ArcadeDrive(DriveDirection direction) {
		requires(Robot.driveTrain);
		driveDirection = direction;
	}

	
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		switch(driveDirection) {
		case forward:
			Robot.driveTrain.arcadeDrive(DriveDirection.forward, -Robot.oi.getStickY(), Robot.oi.getStickZ());
			break;
		case backward:
			Robot.driveTrain.arcadeDrive(DriveDirection.backward, -Robot.oi.getStickY(), Robot.oi.getStickZ());
			break;
		case left:
			Robot.driveTrain.arcadeDrive(DriveDirection.left, -Robot.oi.getStickY(), Robot.oi.getStickZ());
			break;
		case right:
			Robot.driveTrain.arcadeDrive(DriveDirection.right, -Robot.oi.getStickY(), Robot.oi.getStickZ());
			break;			
		}		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
