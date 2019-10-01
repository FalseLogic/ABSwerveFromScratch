package org.usfirst.frc.team4653.robot.commands;

import org.usfirst.frc.team4653.robot.Robot;
import org.usfirst.frc.team4653.robot.OI.Stick;

import edu.wpi.first.wpilibj.command.Command;

public class CrabDrive extends Command {
	
    public CrabDrive() {
    	requires(Robot.driveTrain);
    }

    protected void initialize() {
    }

    protected void execute() {
        double angle = Robot.oi.getStickAngle(Stick.LEFT);
		double speed = Robot.oi.getStickMagnitude(Stick.LEFT) * .85;
		System.out.println(angle + " " + speed);
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
