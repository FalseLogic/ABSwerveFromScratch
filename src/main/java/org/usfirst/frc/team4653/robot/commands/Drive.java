package org.usfirst.frc.team4653.robot.commands;

import org.usfirst.frc.team4653.robot.Constants.ModuleLocation;
import org.usfirst.frc.team4653.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {
	
	private double targetAngle;
	private double power;
	
    public Drive() {
    	requires(Robot.driveTrain);
    }

    protected void initialize() {
    }

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

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
