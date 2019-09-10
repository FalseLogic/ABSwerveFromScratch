package org.usfirst.frc.team4653.robot.swerveutil;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team4653.robot.Constants;


public class SwerveModule {

	public TalonSRX mTurn;
	public CANSparkMax mDrive;

	private boolean isReversed, tempinv;
	private double offset;

	private boolean PIDcontrol = true;

	double fullRot = 4096 / 1.2;

	/**
     * @param turnID   		the ID of the turn motor
     * @param driveID       the ID of the drive motor
     * @param isReversed    if the module is physically reversed on the robot
     * @param offset		encoder value when wheel is pointing stright
     */
	public SwerveModule(int turnID, int driveID, double offset, boolean isReversed) {
		mTurn = new TalonSRX(turnID);
		mDrive = new CANSparkMax(driveID, MotorType.kBrushless);
		this.offset = offset;
		this.isReversed = isReversed;

		mTurn.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}
	

	private double normalizeAngle(double angle) {

		double rads = Math.toRadians(angle);

		return Math.toDegrees(Math.atan2(Math.sin(rads), Math.cos(rads)));
	}

	public void turnMotorControl(double targetAngle) {

		if(PIDcontrol) {
		
			double target, current;
			current = getTurnRawDegrees();
			target = targetAngle;

			if(Math.abs(target - current) > 90 && Math.abs(target - current) < 270) {
				target = (target + 180) % 360;
				tempinv = true;
			}
			else {
				tempinv = false;
			}


			target *= fullRot / 360;
			mTurn.set(ControlMode.Position, target);

		}
		else {
			double turnMotorPower, amtOff, targetPos;

			targetPos = (targetAngle * fullRot / 360);
			amtOff = getTurnAdjPosition() - targetPos;
			
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
		if((isReversed && !tempinv) || (!isReversed && tempinv)) {
			mDrive.set(-speed);
			//mDrive.set(ControlMode.Velocity, speed * -600);
		}
		else {
			mDrive.set(speed);
			//mDrive.set(ControlMode.Velocity, speed * 600);
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
		return  mTurn.getSelectedSensorPosition(Constants.kTimeoutMs) - offset;
	}

	//public double getTurnErrorDegrees() {
	//	return getTurnRawDegrees() - targetAngle;
	//}

	public double getTurnVelocity() {
		return mTurn.getSensorCollection().getQuadratureVelocity();
	}

	public double getTurnRawDegrees() {
		return getTurnAdjPosition() / fullRot * 360;
	}

	public double getDriveRawPosition() {
		return mDrive.getEncoder().getPosition();
	}
	public double getDriveVelocity() {
		return mDrive.getEncoder().getVelocity();
	}
	public void resetTurnEncoder() {
		mTurn.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}
	public void resetDriveEncoder() {
		mDrive.getEncoder().setPosition(0);
	}
	
	public void setPIDF(double kP, double kI, double kD, double kF) {
		mTurn.config_kP(Constants.kPIDLoopIdx, kP, Constants.kTimeoutMs);
		mTurn.config_kI(Constants.kPIDLoopIdx, kI, Constants.kTimeoutMs);
		mTurn.config_kD(Constants.kPIDLoopIdx, kD, Constants.kTimeoutMs);
		mTurn.config_kF(Constants.kPIDLoopIdx, kF, Constants.kTimeoutMs);
	}

	public boolean isInverted() {
		return isReversed;
	}
	
}