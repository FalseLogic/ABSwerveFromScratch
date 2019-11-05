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

		isFieldOriented = true;

		double forwardSpeed = .8 * -Robot.oi.getStickY(Stick.LEFT);
		double strafeSpeed = .8 * Robot.oi.getStickX(Stick.LEFT);
		double rotateSpeed = .4 * Robot.oi.getStickZ(Stick.LEFT);
		if(!canRotate) rotateSpeed = 0;
		
		if(Robot.oi.leftStick.getRawButton(1))
			Robot.driveTrain.swerveDrive(forwardSpeed, strafeSpeed, rotateSpeed, isFieldOriented);
		else
			Robot.driveTrain.fullStop();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
	}
	
}
