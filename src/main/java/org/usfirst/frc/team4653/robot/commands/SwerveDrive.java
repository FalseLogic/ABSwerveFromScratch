package org.usfirst.frc.team4653.robot.commands;

import org.usfirst.frc.team4653.robot.Robot;
import org.usfirst.frc.team4653.robot.OI.Stick;

import edu.wpi.first.wpilibj.command.Command;

public class SwerveDrive extends Command {

    boolean isFieldOriented, canRotate;

    public SwerveDrive() {
        requires(Robot.driveTrain);
    }

    protected void initialize() {
    }

    protected void execute() {

		canRotate = true;

		isFieldOriented = false;


		double forwardSpeed = .45 * -Robot.oi.getStickY(Stick.LEFT);
		double strafeSpeed = .45 * Robot.oi.getStickX(Stick.LEFT);
		double rotateSpeed = .375 * Robot.oi.getStickZ(Stick.LEFT);
		if(!canRotate) rotateSpeed = 0;
		
		if(Robot.oi.leftStick.getRawButton(2)) {
			Robot.driveTrain.fullStop();
		}
		else if(Robot.oi.leftStick.getRawButton(1)) {
			Robot.driveTrain.swerveDrive(filter(forwardSpeed), filter(strafeSpeed), filter(rotateSpeed), false);
		}
		else {
			Robot.driveTrain.swerveDrive(filter(forwardSpeed), filter(strafeSpeed), filter(rotateSpeed), true);
		}

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
