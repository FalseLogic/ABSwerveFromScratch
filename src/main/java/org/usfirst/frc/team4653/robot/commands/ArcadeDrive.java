package org.usfirst.frc.team4653.robot.commands;

import org.usfirst.frc.team4653.robot.Robot;
import org.usfirst.frc.team4653.robot.OI.Stick;

import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {
	
    public ArcadeDrive() {
    	requires(Robot.driveTrain);
    }

    protected void initialize() {
    }

    protected void execute() {
        Robot.driveTrain.arcadeDrive(filter(-.8 * Robot.oi.getStickY(Stick.LEFT)), filter(.8 * Robot.oi.getStickZ(Stick.LEFT)));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
	}
	
	private double filter(double a) {
		if(Math.abs(a) < .1) {
			return 0;
		}
		return a;
	}
}