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
		double strafeSpeed = Robot.oi.getStickX();
		double rotateSpeed = .85 * Robot.oi.getStickZ();
    	Robot.driveTrain.swerveDrive(forwardSpeed, strafeSpeed, rotateSpeed, isFieldOriented, canRotate);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        //Robot.driveTrain.getSwerveModule(ModuleLocation.FRONT_LEFT).PIDloop.stop();
        //Robot.driveTrain.getSwerveModule(ModuleLocation.FRONT_RIGHT).PIDloop.stop();
        //Robot.driveTrain.getSwerveModule(ModuleLocation.BACK_LEFT).PIDloop.stop();
        //Robot.driveTrain.getSwerveModule(ModuleLocation.BACK_RIGHT).PIDloop.stop();
	}
	
}
