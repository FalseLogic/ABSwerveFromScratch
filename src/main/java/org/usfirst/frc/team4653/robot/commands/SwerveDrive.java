package org.usfirst.frc.team4653.robot.commands;

import org.usfirst.frc.team4653.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SwerveDrive extends Command {

    boolean isFieldOriented, canRotate;

    public SwerveDrive() {
        requires(Robot.driveTrain);
        this.isFieldOriented = false;
        this.canRotate = true;
    }

    public SwerveDrive(boolean isFieldOriented, boolean canRotate) {
        requires(Robot.driveTrain);
        this.isFieldOriented = isFieldOriented;
        this.canRotate = canRotate;
    }

    protected void initialize() {
    }

    protected void execute() {
		double forwardSpeed = -Robot.oi.getStickY();
		if(Math.abs(forwardSpeed) < .1) {forwardSpeed = 0;}
		double strafeSpeed = Robot.oi.getStickX();
		if(Math.abs(strafeSpeed) < .1) {strafeSpeed = 0;}
		double rotateSpeed = .85 * Robot.oi.getStickZ();
		if(Math.abs(rotateSpeed) < .1) {rotateSpeed = 0;}
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
