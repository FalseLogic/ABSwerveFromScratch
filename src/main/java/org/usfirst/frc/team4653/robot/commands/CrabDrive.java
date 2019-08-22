package org.usfirst.frc.team4653.robot.commands;

import org.usfirst.frc.team4653.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CrabDrive extends Command {
	
    public CrabDrive() {
    	requires(Robot.driveTrain);
    }

    protected void initialize() {
    }

    protected void execute() {
        double angle = Robot.oi.getStickAngle();
        double speed = Robot.oi.getStickMagnitude() * .85;
    	Robot.driveTrain.crabDrive(angle, speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
	}
	
}
