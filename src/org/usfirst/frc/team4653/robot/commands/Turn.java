package org.usfirst.frc.team4653.robot.commands;

import org.usfirst.frc.team4653.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class Turn extends Command {
	
	private double startAngle;
    private double targetAngle;
    private double currentAngle;
    private int direction;
    
    public Turn(double target) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	targetAngle = target;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	currentAngle = Robot.oi.getAngle();
    	startAngle = currentAngle;
    	if(targetAngle > startAngle) {
    		direction = 1;
    	}
    	else if(targetAngle < startAngle) {
    		direction = -1;
    	}
    	else {
    		direction = 0;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(direction == 1) {
    		Robot.driveTrain.arcadeDrive(0, .6);
    	}
    	else if(direction == -1) {
    		Robot.driveTrain.arcadeDrive(0, -.6);
    	}
    	currentAngle = Robot.oi.getAngle();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(direction == 1) {
	    	if(currentAngle > targetAngle) {
	    		return true;
	    	}
	    	else {
	    		return false;
	    	}
    	}
    	else if(direction == -1) {
    		if(currentAngle < targetAngle) {
	    		return true;
	    	}
    		else {
    			return false;
    		}
    	}
    	else {
    		return false;
    	}
    }
        
    

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
