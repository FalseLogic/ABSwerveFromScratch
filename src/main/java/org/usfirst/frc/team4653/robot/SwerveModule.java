/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4653.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class SwerveModule {
	
	private TalonSRX mTurn;
	private WPI_TalonSRX mDrive;
	private double offset;
	private boolean isReversed;	
	/**
     * @param turnID   		the ID of the turn motor
     * @param driveID       the ID of the drive motor
     * @param isReversed    if the module is physically reversed on the robot
     * @param offset		number of degrees to orient steering encoder towards front
     */
	public SwerveModule(int turnID, int driveID, double offset, boolean isReversed) {
		mTurn = new TalonSRX(turnID);
		mDrive = new WPI_TalonSRX(driveID);
		this.offset = offset;
		this.isReversed = isReversed;
		
		mTurn.configSelectedFeedbackSensor(FeedbackDevice.PulseWidthEncodedPosition, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		mDrive.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		
		mDrive.config_kF(Constants.kPIDLoopIdx, 1.6, Constants.kTimeoutMs);
		mDrive.config_kP(Constants.kPIDLoopIdx, 1.75, Constants.kTimeoutMs);
	}
	

	public void turnMotorControl(double targetAngle) {
		double currentPos, amtOff, turnMotorPower, fullRot, targetPos;
		
		currentPos =  mTurn.getSelectedSensorPosition(Constants.kTimeoutMs);
		fullRot = 4096 / 1.2;
		targetPos = (targetAngle + offset) * fullRot / 360;
		amtOff = currentPos - targetPos;
		
		turnMotorPower = (-amtOff/fullRot) + Math.floor((amtOff + (fullRot / 2)) / fullRot);
		
		turnMotorPower += Math.copySign(.05, turnMotorPower);
		
		if(Math.abs(turnMotorPower) > .055) {
			mTurn.set(ControlMode.PercentOutput, turnMotorPower);
		}
		else {
			mTurn.set(ControlMode.PercentOutput, 0);
		}
	}
	
	public void spinWheel(double speed) {
		if(isReversed) {
			mDrive.set(ControlMode.PercentOutput, -speed);
		}
		else {
			mDrive.set(ControlMode.PercentOutput, speed);
		}		
	}
	public void setModule(double targetAngle, double power) {
		turnMotorControl(targetAngle);
		spinWheel(power);
	}
	
	public double getTurnRawPosition() {
		return mTurn.getSensorCollection().getPulseWidthPosition();
	}
	public double getTurnDegrees() {
		double fullRot = 4096 / 1.2;
		return getTurnRawPosition() / fullRot * 360;
	}
	
	public double getDriveRawPosition() {
		return mDrive.getSensorCollection().getQuadraturePosition();
	}
	public double getDriveVelocity() {
		return mDrive.getSensorCollection().getQuadratureVelocity();
	}
	public void resetTurnEncoder() {
		mTurn.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}
	public void resetDriveEncoder() {
		mDrive.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}
	
}
