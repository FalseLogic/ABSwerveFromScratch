package org.usfirst.frc.team4653.robot.swerveutil;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team4653.robot.Constants;


public class SwerveModule {

	private TalonSRX mTurn;
	private CANSparkMax mDrive;

	private boolean isReversed;	
	private double offset, targetAngle;

	private boolean PIDcontrol = Constants.modulePIDControl;

	/**
     * @param turnID   		the ID of the turn motor
     * @param driveID       the ID of the drive motor
     * @param isReversed    if the module is physically reversed on the robot
     * @param offset	encoder value when wheel is pointing stright
     */
	public SwerveModule(int turnID, int driveID, double offset, boolean isReversed) {
		mTurn = new TalonSRX(turnID);
		mDrive = new CANSparkMax(driveID, MotorType.kBrushless);
		this.offset = offset;
		this.isReversed = isReversed;

		mTurn.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}
	
	public void turnMotorControl(double targetAngle) {

		this.targetAngle = targetAngle;

		double amtOff, fullRot, targetPos, turnAdjEncoder, turnMotorPower;
		fullRot = 4096 / 1.2;
		targetPos = (targetAngle * fullRot / 360);
		
		
		if(PIDcontrol) {

		}
		else {
			turnAdjEncoder =  getTurnRawPosition() - offset;
			amtOff = turnAdjEncoder - targetPos;
			
			turnMotorPower = (-amtOff/fullRot) + Math.floor((amtOff + (fullRot / 2)) / fullRot);
			
			turnMotorPower += Math.copySign(.05, turnMotorPower);
			
			if(Math.abs(turnMotorPower) > .06) {
				mTurn.set(ControlMode.PercentOutput, turnMotorPower);
			}
			else {
				mTurn.set(ControlMode.PercentOutput, 0);
			}
		}
	}
	
	public void spinWheel(double speed) {
		if(isReversed) {
			mDrive.set(-speed);
		}
		else {
			mDrive.set(speed);
		}		
	}
	public void setModule(double targetAngle, double speed) {
		turnMotorControl(targetAngle);
		spinWheel(speed);
	}
	
	public double getTurnRawPosition() {
		return mTurn.getSensorCollection().getQuadraturePosition();
	}

	public double getTurnAdjPosition() {
		return getTurnRawPosition() - offset;
	}

	public double getTurnErrorDegrees() {
		return getTurnDegrees() - targetAngle;
	}

	public double getTurnVelocity() {
		return mTurn.getSensorCollection().getQuadratureVelocity();
	}

	public double getTurnDegrees() {
		double fullRot = 4096 / 1.2;
		return getTurnAdjPosition() / fullRot * 360;
	}

	double driveEncOffset;
	public double getDriveRawPosition() {
		return mDrive.getEncoder().getPosition() - driveEncOffset;
	}
	public double getDriveVelocity() {
		return mDrive.getEncoder().getVelocity();
	}
	public void resetTurnEncoder() {
		mTurn.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}
	public void resetDriveEncoder() {
		driveEncOffset = mDrive.getEncoder().getPosition();
	}
	
	public void setPIDF(double kP, double kI, double kD, double kF) {
	}

	public boolean isInverted() {
		return isReversed;
	}
	
}