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

		if(Robot.oi.leftStick.getRawButton(1) || Robot.oi.rightStick.getRawButton(1)) {
			isFieldOriented = false;
		}
		else {
			isFieldOriented = true;
		}

		double forwardSpeed = -Robot.oi.getStickY(Stick.LEFT);
		double strafeSpeed = Robot.oi.getStickX(Stick.LEFT);
		double rotateSpeed = .85 * Robot.oi.getStickZ(Stick.LEFT);
		if(!canRotate) rotateSpeed = 0;

		if(Robot.oi.leftStick.getRawButton(1))
			Robot.driveTrain.swerveDrive(forwardSpeed, strafeSpeed, rotateSpeed, isFieldOriented);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
	}
	
}
