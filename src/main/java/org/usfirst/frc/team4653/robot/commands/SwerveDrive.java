package org.usfirst.frc.team4653.robot.commands;

import org.usfirst.frc.team4653.robot.subsystems.Drivetrain;

import org.usfirst.frc.team4653.robot.Constants;
import org.usfirst.frc.team4653.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

//The command running swerve drive using a single stick
public class SwerveDrive extends CommandBase {
	
	private final Drivetrain drivetrain;
	private final Joystick stick;
	private final boolean isFieldOriented;

    public SwerveDrive(boolean isFieldOriented) {
		drivetrain = Robot.drivetrain;
		stick = Robot.stick;
		this.isFieldOriented = isFieldOriented;
		addRequirements(drivetrain);
    }

    public void initialize() {
    }

    public void execute() {

		double forwardSpeed = -.5 * stick.getY();
		double strafeSpeed = .5 * stick.getX();
		double rotateSpeed = .4 * stick.getZ();

		drivetrain.swerveDrive(Constants.filter(forwardSpeed), Constants.filter(strafeSpeed), Constants.filter(rotateSpeed), isFieldOriented);

    }

    public boolean isFinished() {
        return false;
    }

    public void end() {
    }

    public void interrupted() {
	}
	
}
