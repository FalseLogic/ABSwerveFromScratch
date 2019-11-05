package org.usfirst.frc.team4653.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team4653.robot.Constants;
import org.usfirst.frc.team4653.robot.Robot;
import org.usfirst.frc.team4653.robot.Constants.Location;
import org.usfirst.frc.team4653.robot.commands.ArcadeDrive;
import org.usfirst.frc.team4653.robot.commands.CrabDrive;
import org.usfirst.frc.team4653.robot.commands.SwerveDrive;
import org.usfirst.frc.team4653.robot.swerveutil.SwerveModule;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	private SwerveModule modFrontLeft;
	private SwerveModule modFrontRight;
	private SwerveModule modBackLeft;
	private SwerveModule modBackRight;
	
	public DriveTrain() {
		modFrontLeft = new SwerveModule(Constants.FLturn, Constants.FLdrive, Constants.FLoffset, true);
		modFrontRight = new SwerveModule(Constants.FRturn, Constants.FRdrive, Constants.FRoffset, false);
		modBackLeft = new SwerveModule(Constants.BLturn, Constants.BLdrive, Constants.BLoffset, true);
		modBackRight = new SwerveModule(Constants.BRturn, Constants.BRdrive, Constants.BRoffset, false);
	}

	public void setModule(Location location, double targetAngle, double power) {
		switch(location) {
			default:
			case FRONT_LEFT:
				modFrontLeft.setModule(targetAngle, power);
			case FRONT_RIGHT:
				modFrontRight.setModule(targetAngle, power);
			case BACK_LEFT:
				modBackLeft.setModule(targetAngle, power);
			case BACK_RIGHT:
				modBackRight.setModule(targetAngle, power);
		}
	}
	
	public SwerveModule getSwerveModule(Location location) {
		switch(location) {
			default:
				return null;
			case FRONT_LEFT:
				return modFrontLeft;
			case FRONT_RIGHT:
				return modFrontRight;
			case BACK_LEFT:
				return modBackLeft;
			case BACK_RIGHT:
				return modBackRight;

		}
	}

	public void setAllAngle(double targetAngle) {
		modFrontLeft.turnMotorControl(targetAngle);
		modFrontRight.turnMotorControl(targetAngle);
		modBackLeft.turnMotorControl(targetAngle);
		modBackRight.turnMotorControl(targetAngle);
	}
	
	public void setAllSpeed(double power) {
		modFrontLeft.spinWheel(power);
		modFrontRight.spinWheel(power);
		modBackLeft.spinWheel(power);
		modBackRight.spinWheel(power);
	}
	
	public void crabDrive(double angle, double speed) {
		modFrontLeft.setModule(angle, speed);
		modFrontRight.setModule(angle, speed);
		modBackLeft.setModule(angle, speed);
		modBackRight.setModule(angle, speed);
	}

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

	public void swerveDrive(double forwardSpeed, double strafeSpeed, double rotateSpeed) {
		swerveDrive(forwardSpeed, strafeSpeed, rotateSpeed, false);
	}

	public void swerveDrive(double forwardSpeed, double strafeSpeed, double rotateSpeed, boolean isFieldOriented) {

		double gyroAngle = Robot.oi.getGyroDegrees();

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

		modFrontLeft.setModule(FLangle, FLspeed * .93);
		modFrontRight.setModule(FRangle, FRspeed);
		modBackLeft.setModule(BLangle, BLspeed * .93);
		modBackRight.setModule(BRangle, BRspeed);
	}

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

	public void resetDriveEncoders() {
		modFrontLeft.resetDriveEncoder();
		modFrontRight.resetDriveEncoder();
		modBackLeft.resetDriveEncoder();
		modBackRight.resetDriveEncoder();
	}

	public void resetTurnEncoders() {
		modFrontLeft.resetTurnEncoder();
		modFrontRight.resetTurnEncoder();
		modBackLeft.resetTurnEncoder();
		modBackRight.resetTurnEncoder();
	}
	
	public void printDriveVelocity() {
		System.out.print(modFrontLeft.getDriveVelocity() + " ");
		System.out.print(modFrontRight.getDriveVelocity() + " ");
		System.out.print(modBackLeft.getDriveVelocity() + " ");
		System.out.println(modBackRight.getDriveVelocity());
	}

	public void printDriveRawPosition() {
		System.out.print(modFrontLeft.getDriveRawPosition() + " ");
		System.out.print(modFrontRight.getDriveRawPosition() + " ");
		System.out.print(modBackLeft.getDriveRawPosition() + " ");
		System.out.println(modBackRight.getDriveRawPosition());
	}
	
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

	public void printRawTurnEnc() {
		System.out.print(modFrontLeft.getTurnRawPosition() + " ");
		System.out.print(modFrontRight.getTurnRawPosition() + " ");
		System.out.print(modBackLeft.getTurnRawPosition() + " ");
		System.out.println(modBackRight.getTurnRawPosition());
	}

	public void printAdjTurnEnc() {
		System.out.print(modFrontLeft.getTurnAdjPosition() + " ");
		System.out.print(modFrontRight.getTurnAdjPosition() + " ");
		System.out.print(modBackLeft.getTurnAdjPosition() + " ");
		System.out.println(modBackRight.getTurnAdjPosition());
	}

	public void printTurnVelocity() {
		System.out.print(modFrontLeft.getTurnVelocity() + " ");
		System.out.print(modFrontRight.getTurnVelocity() + " ");
		System.out.print(modBackLeft.getTurnVelocity() + " ");
		System.out.println(modBackRight.getTurnVelocity());
	}
	
	public void setAllTurnPID(double kP, double kI, double kD) {
		modFrontLeft.setTurnPID(kP, kI, kD);
		modFrontRight.setTurnPID(kP, kI, kD);
		modBackLeft.setTurnPID(kP, kI, kD);
		modBackRight.setTurnPID(kP, kI, kD);
	}

    public void initDefaultCommand() {
		setDefaultCommand(new SwerveDrive());
	}
	
}