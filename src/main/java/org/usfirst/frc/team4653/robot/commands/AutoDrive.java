/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4653.robot.commands;

import org.usfirst.frc.team4653.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoDrive extends Command {

	private double angle, startPos, targetPos;
	private double speed = .5;

	/**
	 * 
	 * @param angle angle in degrees to point wheels towards. forward is 0.
	 * @param distance units are encoder ticks. please don't try negative distance
	 */
	public AutoDrive(double angle, double distance) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		this.angle = angle;
		this.startPos = Robot.driveTrain.averageDrivePosition();
		this.targetPos = startPos + distance;
		
	}
	

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.driveTrain.crabDrive(angle, speed);

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		double currentPos = Robot.driveTrain.averageDrivePosition();
		if(currentPos > targetPos) {
			return true;
		}
		else {
			return false;
		}
		
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
