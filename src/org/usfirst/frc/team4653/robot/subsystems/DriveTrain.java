/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4653.robot.subsystems;

import org.usfirst.frc.team4653.robot.RobotMap;
import org.usfirst.frc.team4653.robot.RobotMap.DriveDirection;
import org.usfirst.frc.team4653.robot.commands.ArcadeDrive;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

@SuppressWarnings("deprecation")
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	WPI_TalonSRX frontLeft = new WPI_TalonSRX(RobotMap.FLdrive);
	WPI_TalonSRX frontRight = new WPI_TalonSRX(RobotMap.FRdrive);
	WPI_TalonSRX backLeft = new WPI_TalonSRX(RobotMap.BLdrive);
	WPI_TalonSRX backRight = new WPI_TalonSRX(RobotMap.BRdrive);
	TalonSRX turn1 = new TalonSRX(RobotMap.FLturn);
	TalonSRX turn2 = new TalonSRX(RobotMap.FRturn);
	TalonSRX turn3 = new TalonSRX(RobotMap.BLturn);
	TalonSRX turn4 = new TalonSRX(RobotMap.BRturn);
	
	// prob should just make a swerve module class
	//then make 1 for each position and use them here
	//but i guess this works for now
	public DriveTrain() {
		frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		backLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		backRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		turn1.configSelectedFeedbackSensor(FeedbackDevice.PulseWidthEncodedPosition, 0, 10);
		turn2.configSelectedFeedbackSensor(FeedbackDevice.PulseWidthEncodedPosition, 0, 10);
		turn3.configSelectedFeedbackSensor(FeedbackDevice.PulseWidthEncodedPosition, 0, 10);
		turn4.configSelectedFeedbackSensor(FeedbackDevice.PulseWidthEncodedPosition, 0, 10);	
	}
	
	
	//F, R, L, B, stand for side treated as front
	
	SpeedControllerGroup leftF = new SpeedControllerGroup( frontLeft,  backLeft);
	SpeedControllerGroup rightF = new SpeedControllerGroup( frontRight,  backRight);
	DifferentialDrive driveF = new DifferentialDrive(leftF, rightF);
	
	SpeedControllerGroup leftR = new SpeedControllerGroup( frontRight,  frontLeft);
	SpeedControllerGroup rightR = new SpeedControllerGroup( backRight,  backLeft);
	DifferentialDrive driveR = new DifferentialDrive(leftR, rightR);
	
	SpeedControllerGroup leftL = new SpeedControllerGroup( backLeft,  backRight);
	SpeedControllerGroup rightL = new SpeedControllerGroup( frontLeft,  frontRight);
	DifferentialDrive driveL = new DifferentialDrive(leftL, rightL);
	
	SpeedControllerGroup leftB = new SpeedControllerGroup( backRight,  frontRight);
	SpeedControllerGroup rightB = new SpeedControllerGroup( backLeft,  frontLeft);
	DifferentialDrive driveB = new DifferentialDrive(leftB, rightB);
	
	private void turnMotorControl(TalonSRX talon, double targetAngle) {
		
		double currentPos, amtOff, turnMotorPower, fullRot, targetPos;
		
		currentPos =  talon.getSensorCollection().getPulseWidthPosition();
		fullRot = 4096 / 1.2;
		targetPos = targetAngle * fullRot / 360;
		amtOff = currentPos - targetPos;
		
		turnMotorPower = (-amtOff/fullRot) + Math.floor((amtOff + (fullRot / 2)) / fullRot);
		
		turnMotorPower += Math.copySign(.05, turnMotorPower);
		
		if(Math.abs(turnMotorPower) > .055) {
			talon.set(ControlMode.PercentOutput, turnMotorPower);
		}
		else {
			talon.set(ControlMode.PercentOutput, 0);
		}
		
	}
	
	public void resetSwerveEnc() {
		turn1.getSensorCollection().setPulseWidthPosition(0, 10);
		turn2.getSensorCollection().setPulseWidthPosition(0, 10);
		turn3.getSensorCollection().setPulseWidthPosition(0, 10);
		turn4.getSensorCollection().setPulseWidthPosition(0, 10);
	}
	
	public void resetDriveTrainEnc() {
		turn1.setSelectedSensorPosition(0, 0, 10);
		turn2.setSelectedSensorPosition(0, 0, 10);
		turn3.setSelectedSensorPosition(0, 0, 10);
		turn4.setSelectedSensorPosition(0, 0, 10);
		frontLeft.setSelectedSensorPosition(0, 0, 10);
		frontRight.setSelectedSensorPosition(0, 0, 10);
		backLeft.setSelectedSensorPosition(0, 0, 10);
		backRight.setSelectedSensorPosition(0, 0, 10);
	}
	
	public void setSwervePos(double targetPos) {
		turnMotorControl(turn1, targetPos);
		turnMotorControl(turn2, targetPos);
		turnMotorControl(turn3, targetPos);
		turnMotorControl(turn4, targetPos);
	}
	
	public void spinWheels(double speed) {
		frontLeft.set(ControlMode.PercentOutput, speed);
		frontRight.set(ControlMode.PercentOutput, -speed);
		backLeft.set(ControlMode.PercentOutput, speed);
		backRight.set(ControlMode.PercentOutput, -speed);
	}
	
	public void arcadeDrive(double speed, double rotate) {
		arcadeDrive(DriveDirection.forward, speed, rotate);
	}
	
	public void arcadeDrive(Joystick stick) {
		arcadeDrive(DriveDirection.forward, stick.getY(), stick.getZ());
	}
	
	public void arcadeDrive(DriveDirection direction, Joystick stick) {
		arcadeDrive(direction, stick.getY(), stick.getZ());
	}
	
	
	public void arcadeDrive(DriveDirection direction, double speed, double rotate) {
		
		switch(direction) {
		case forward:
			driveF.arcadeDrive(speed, rotate);
			setSwervePos(0);
			break;
		case backward:
			driveB.arcadeDrive(speed, rotate);
			setSwervePos(0);
			break;
		case left:
			driveL.arcadeDrive(speed, rotate);
			setSwervePos(90);
			break;
		case right:
			driveR.arcadeDrive(speed, rotate);
			setSwervePos(90);
			break;
		}
		
	}

	
	public void swerveStrafe(double speed, double angle) {
		setSwervePos(angle);
		spinWheels(speed);
	}
	
	public void printSwervePos() {
		System.out.print(turn1.getSelectedSensorPosition(0) + " ");
		System.out.print(turn2.getSelectedSensorPosition(0) + " ");
		System.out.print(turn3.getSelectedSensorPosition(0) + " ");
		System.out.println(turn4.getSelectedSensorPosition(0));	
	}
	
	public void printDriveEnc() {
		System.out.print(frontLeft.getSelectedSensorVelocity(0) + " ");
		System.out.print(frontRight.getSelectedSensorVelocity(0) + " ");
		System.out.print(backLeft.getSelectedSensorVelocity(0) + " ");
		System.out.println(backRight.getSelectedSensorVelocity(0));
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}