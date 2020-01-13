package org.usfirst.frc.team4653.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team4653.robot.Constants;
import org.usfirst.frc.team4653.robot.Robot;
import org.usfirst.frc.team4653.robot.swerve.SwerveModule;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

//The drive train subsystem, which handles driving calculations and gives modules commands
//Consists of four SwerveModules
public class Drivetrain extends SubsystemBase {
	
	private final SwerveModule modFrontLeft;
	private final SwerveModule modFrontRight;
	private final SwerveModule modBackLeft;
	private final SwerveModule modBackRight;
	
	//This set of inversions works when all of the wide parts of the modules begin facing inwards
	//Invert accordingly to have them all face out/to one side.
	public Drivetrain() {
		modFrontLeft = new SwerveModule(Constants.FL_TURN_PORT, Constants.FL_DRIVE_PORT, Constants.FL_OFFSET, true);
		modFrontRight = new SwerveModule(Constants.FR_TURN_PORT, Constants.FR_DRIVE_PORT, Constants.FR_OFFSET, false);
		modBackLeft = new SwerveModule(Constants.BL_TURN_PORT, Constants.BL_DRIVE_PORT, Constants.BL_OFFSET, true);
		modBackRight = new SwerveModule(Constants.BR_TURN_PORT, Constants.BR_DRIVE_PORT, Constants.BR_OFFSET, false);
	}
	
	//Crab drive - sets all motors to the same angle and speed
	//Basically swerve drive without rotation
	public void crabDrive(double angle, double speed) {
		modFrontLeft.setModule(angle, speed);
		modFrontRight.setModule(angle, speed);
		modBackLeft.setModule(angle, speed);
		modBackRight.setModule(angle, speed);
	}

	//A couple of methods that work like regular tank drives
	//Not particularly useful
	public void tankDrive(double left, double right) {
		modFrontLeft.setModule(0, left);
		modFrontRight.setModule(0, right);
		modBackLeft.setModule(0, left);
		modBackRight.setModule(0, right);
	}
	public void arcadeDrive(double power, double turn) {
		modFrontLeft.setModule(0, power + turn);
		modFrontRight.setModule(0, power - turn);
		modBackLeft.setModule(0, power + turn);
		modBackRight.setModule(0, power - turn);
	}

	//Default swerve drive is field oriented
	public void swerveDrive(double forwardSpeed, double strafeSpeed, double rotateSpeed) {
		swerveDrive(forwardSpeed, strafeSpeed, rotateSpeed, true);
	}
	//Swerve drive - takes forward, sideways, and rotational speeds, and does calculations to make the robot move
	//Allows for field/robot orientation
	public void swerveDrive(double forwardSpeed, double strafeSpeed, double rotateSpeed, boolean isFieldOriented) {

		double gyroAngle = Robot.ahrs.getAngle();

		double sin = Math.sin(Math.toRadians(gyroAngle));
		double cos = Math.cos(Math.toRadians(gyroAngle));

		if(isFieldOriented) {
			double T = (forwardSpeed * cos) + (strafeSpeed * sin);
			strafeSpeed = (-forwardSpeed * sin) + (strafeSpeed * cos);
			forwardSpeed = T;
		}

		double J = Constants.WHEELBASE_INCHES / Constants.TURN_RADIUS_INCHES;
		double K = Constants.TRACKWIDTH_INCHES / Constants.TURN_RADIUS_INCHES;

		double A = strafeSpeed - (rotateSpeed * J);
		double B = strafeSpeed + (rotateSpeed * J);
		double C = forwardSpeed - (rotateSpeed * K);
		double D = forwardSpeed + (rotateSpeed * K);

		double FLspeed = Math.hypot(B, D);
		double FRspeed = Math.hypot(B, C);
		double BLspeed = Math.hypot(A, D);
		double BRspeed = Math.hypot(A, C);

		double FLangle = Math.atan2(B, D) * 180 / Math.PI;
		double FRangle = Math.atan2(B, C) * 180 / Math.PI;
		double BLangle = Math.atan2(A, D) * 180 / Math.PI;
		double BRangle = Math.atan2(A, C) * 180 / Math.PI;

		double max = Math.max(Math.max(FLspeed, FRspeed), Math.max(BLspeed, BRspeed));
		if(max > 1) {FLspeed /= max; FRspeed /= max; BLspeed /= max; BRspeed /= max;}

		modFrontLeft.setModule(FLangle, FLspeed * .89);
		modFrontRight.setModule(FRangle, FRspeed);
		modBackLeft.setModule(BLangle, BLspeed * .89);
		modBackRight.setModule(BRangle, BRspeed);
	}

	//Stops ALL motors from moving
	public void fullStop() {
		modFrontLeft.getDriveController().set(0);
		modFrontRight.getDriveController().set(0);
		modBackLeft.getDriveController().set(0);
		modBackRight.getDriveController().set(0);

		modFrontLeft.getTurnController().set(ControlMode.PercentOutput, 0);
		modFrontRight.getTurnController().set(ControlMode.PercentOutput, 0);
		modBackLeft.getTurnController().set(ControlMode.PercentOutput, 0);
		modBackRight.getTurnController().set(ControlMode.PercentOutput, 0);
	}

	//Resets all turn encoders - see SwerveModule.newOffset()
	public void newOffsets() {
		modFrontLeft.newOffset();
		modFrontRight.newOffset();
		modBackLeft.newOffset();
		modBackRight.newOffset();
	}
	//Resets all drive encoders
	public void resetDriveEncoders() {
		modFrontLeft.resetDriveEncoder();
		modFrontRight.resetDriveEncoder();
		modBackLeft.resetDriveEncoder();
		modBackRight.resetDriveEncoder();
	}
	
	//Average position of all drive encoders
	//Use carefully - will not necessarily be distance travelled, as some modules might turn different ways than others
	public double averageDrivePosition() {

		double sum = 0;

		if(modFrontLeft.isInverted()) {sum -= modFrontLeft.getDriveRawPosition();}
		else {sum += modFrontLeft.getDriveRawPosition();}

		if(modFrontRight.isInverted()) {sum -= modFrontRight.getDriveRawPosition();}
		else {sum += modFrontRight.getDriveRawPosition();}

		if(modBackLeft.isInverted()) {sum -= modBackLeft.getDriveRawPosition();}
		else {sum += modBackLeft.getDriveRawPosition();}

		if(modBackRight.isInverted()) {sum -= modBackRight.getDriveRawPosition();}
		else {sum += modBackRight.getDriveRawPosition();}

		return sum / 4;
	}
	
	//Sets PID gains of all modules
	//Individual tuning is recommended
	public void setAllTurnPID(double kP, double kI, double kD) {
		modFrontLeft.setTurnPID(kP, kI, kD);
		modFrontRight.setTurnPID(kP, kI, kD);
		modBackLeft.setTurnPID(kP, kI, kD);
		modBackRight.setTurnPID(kP, kI, kD);
	}

    public void initDefaultCommand() {
	}
	
}