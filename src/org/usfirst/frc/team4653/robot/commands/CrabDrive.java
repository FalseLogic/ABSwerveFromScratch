package org.usfirst.frc.team4653.robot.commands;

import org.usfirst.frc.team4653.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CrabDrive extends Command {
	
    public CrabDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double angle = Robot.oi.getStickAngle();
        double speed = Robot.oi.getStickMagnitude() * .85;
    	Robot.driveTrain.crabDrive(angle, speed);
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
        //Robot.driveTrain.getSwerveModule(ModuleLocation.FRONT_LEFT).PIDloop.stop();
        //Robot.driveTrain.getSwerveModule(ModuleLocation.FRONT_RIGHT).PIDloop.stop();
        //Robot.driveTrain.getSwerveModule(ModuleLocation.BACK_LEFT).PIDloop.stop();
        //Robot.driveTrain.getSwerveModule(ModuleLocation.BACK_RIGHT).PIDloop.stop();
    }
}
