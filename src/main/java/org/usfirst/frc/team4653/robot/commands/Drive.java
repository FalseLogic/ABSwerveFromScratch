package org.usfirst.frc.team4653.robot.commands;

import org.usfirst.frc.team4653.robot.Constants.ModuleLocation;
import org.usfirst.frc.team4653.robot.Robot;
import org.usfirst.frc.team4653.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {
	
	private double targetAngle;
	private double power;
	
    public Drive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	targetAngle = Robot.oi.getStickAngle();
    	if(Math.abs(Robot.oi.getStickX()) > Math.abs(Robot.oi.getStickY())) {
    		power = Math.abs(Robot.oi.getStickX());
    	}
    	else {
    		power = Math.abs(Robot.oi.getStickY());
    	}
    	
    	Robot.driveTrain.setModule(ModuleLocation.FRONT_LEFT, targetAngle, power);
    	Robot.driveTrain.setModule(ModuleLocation.FRONT_RIGHT, targetAngle, power);
    	Robot.driveTrain.setModule(ModuleLocation.BACK_LEFT, targetAngle, power);
    	Robot.driveTrain.setModule(ModuleLocation.BACK_RIGHT, targetAngle, power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
