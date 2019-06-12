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
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.driveTrain);
        this.isFieldOriented = isFieldOriented;
        this.canRotate = canRotate;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double forwardSpeed = -Robot.oi.getStickY();
		double strafeSpeed = Robot.oi.getStickX();
		double rotateSpeed = .85 * Robot.oi.getStickZ();
    	Robot.driveTrain.swerveDrive(forwardSpeed, strafeSpeed, rotateSpeed, isFieldOriented, canRotate);
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
