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
	static WPI_TalonSRX frontLeft = new WPI_TalonSRX(RobotMap.FLdrive);
	static WPI_TalonSRX frontRight = new WPI_TalonSRX(RobotMap.FRdrive);
	static WPI_TalonSRX backLeft = new WPI_TalonSRX(RobotMap.BLdrive);
	static WPI_TalonSRX backRight = new WPI_TalonSRX(RobotMap.BRdrive);
	static TalonSRX turn1 = new TalonSRX(RobotMap.FLturn);
	static TalonSRX turn2 = new TalonSRX(RobotMap.FRturn);
	static TalonSRX turn3 = new TalonSRX(RobotMap.BLturn);
	static TalonSRX turn4 = new TalonSRX(RobotMap.BRturn);
	
	static {
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
	
	//RobotDrive driveF = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
	//RobotDrive driveR = new RobotDrive(frontRight, frontLeft, backRight, backLeft);
	//RobotDrive driveL = new RobotDrive(backLeft, backRight, frontLeft, frontRight);
	//RobotDrive driveB = new RobotDrive(backRight, frontRight, backLeft, frontLeft);
	
	SpeedControllerGroup leftF = new SpeedControllerGroup(frontLeft, backLeft);
	SpeedControllerGroup rightF = new SpeedControllerGroup(frontRight, backRight);
	DifferentialDrive driveF = new DifferentialDrive(leftF, rightF);
	
	SpeedControllerGroup leftR = new SpeedControllerGroup(frontRight, frontLeft);
	SpeedControllerGroup rightR = new SpeedControllerGroup(backRight, backLeft);
	DifferentialDrive driveR = new DifferentialDrive(leftR, rightR);
	
	SpeedControllerGroup leftL = new SpeedControllerGroup(backLeft, backRight);
	SpeedControllerGroup rightL = new SpeedControllerGroup(frontLeft, frontRight);
	DifferentialDrive driveL = new DifferentialDrive(leftL, rightL);
	
	SpeedControllerGroup leftB = new SpeedControllerGroup(backRight, frontRight);
	SpeedControllerGroup rightB = new SpeedControllerGroup(backLeft, frontLeft);
	DifferentialDrive driveB = new DifferentialDrive(leftB, rightB);
	
	private void turnMotorControl(TalonSRX talon, double targetPos) {
		
		double currentPos, amtOff, turnMotorPower;
		
		if(talon == turn1) {
			currentPos =  talon.getSensorCollection().getPulseWidthPosition() - 870;
		}
		else if(talon == turn2) {
			currentPos =  talon.getSensorCollection().getPulseWidthPosition() - 1300;
		}
		else if(talon == turn3) {
			currentPos =  talon.getSensorCollection().getPulseWidthPosition() - 470;
		}
		else if(talon == turn4) {
			currentPos =  talon.getSensorCollection().getPulseWidthPosition() - 670;
		}
		else {
			currentPos =  talon.getSensorCollection().getPulseWidthPosition();
		}
		
		if(talon == turn1) {
			if(targetPos == 0) {
				targetPos += 0;
			}
			if(targetPos == 1024) {
				targetPos += -200;
			}
		}
		if(talon == turn2 || talon == turn3) {
			if(targetPos == 0) {
				targetPos += 0;
			}
			if(targetPos == 1024) {
				targetPos += 2048 + 100;
			}
		}
		if(talon == turn3) {
			if(targetPos == 0) {
				targetPos += -100;
			}
			if(targetPos == 1024) {
				targetPos += 2048;
			}
		}
		if(talon == turn4) {
			if(targetPos == 0) {
				targetPos += 0;
			}
			if(targetPos == 1024) {
				targetPos += 0 - 100;
			}
		}
		
		amtOff = currentPos - targetPos;
		
		turnMotorPower = (-amtOff/4096) + Math.floor((amtOff + 2048)/4096);
		
		if(turnMotorPower >= 0) {
			turnMotorPower += .05;
		}
		else {
			turnMotorPower -= .05;
		}
		
		if(Math.abs(turnMotorPower) > .055) {
			talon.set(ControlMode.PercentOutput, turnMotorPower);
		}
		else {
			talon.set(ControlMode.PercentOutput, 0);
		}
		
		if(talon == turn1) {
		}
		else if(talon == turn2) {
		}
		else if(talon == turn3) {
		}
		else if(talon == turn4) {
		}
		else {
		}
		
	}
	
	public void resetSwervePos() {
		turn1.getSensorCollection().setPulseWidthPosition(0, 0);
		turn2.getSensorCollection().setPulseWidthPosition(0, 0);
		turn3.getSensorCollection().setPulseWidthPosition(0, 0);
		turn4.getSensorCollection().setPulseWidthPosition(0, 0);
	}
	
	public void setSwervePos(double targetPos) {
		turnMotorControl(turn1, targetPos);
		turnMotorControl(turn2, targetPos);
		turnMotorControl(turn3, targetPos);
		turnMotorControl(turn4, targetPos);
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
		
		/*
		driveF.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
		driveF.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
		driveL.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		driveL.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		driveR.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		driveR.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		driveB.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		driveB.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		*/
		
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
			setSwervePos(1024);
			break;
		case right:
			driveR.arcadeDrive(speed, rotate);
			setSwervePos(1024);
			break;
		}
	}
	
	
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}