package org.usfirst.frc.team4653.robot.swerveutil;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team4653.robot.Constants;

import edu.wpi.first.wpilibj.PIDController;


public class SwerveModule {

	public TalonSRX mTurn;
	public CANSparkMax mDrive;

	private boolean isReversed;
	private double offset;

	private PIDController pidController;
	private SRXQuadEncoder kInput;
	private SRXOutput kOutput;


	private boolean PIDcontrol = Constants.modulePIDControl;

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

		kInput = new SRXQuadEncoder(mTurn);
		kOutput = new SRXOutput(mTurn);

		pidController = new PIDController(Constants.kP, Constants.kI, Constants.kD, Constants.kF, kInput, kOutput, .05);
		pidController.setInputRange(-180, 180);
		pidController.setContinuous();
		pidController.setOutputRange(-1.0, 1.0);
		pidController.enable();

		mTurn.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}

	public void turnMotorControl(double targetAngle) {

		if(PIDcontrol) {
			

			pidController.enable();
			pidController.setSetpoint(targetAngle);

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
		if(isReversed) {
			mDrive.set(-speed);
		}
		else {
			mDrive.set(speed);
		}
	}

	public void setModule(double targetAngle, double targetSpeed) {

		double target, current, speed;
			
		target = targetAngle;
		current = getTurnRawDegrees();
		speed = targetSpeed;

		while(current > 180) current -= 360;
		while(current < -180) current += 360;

		while(target > 180) target -= 360;
		while(target < -180) target += 360;

		double error = target - current;

		if(Math.abs(error) > 180.0) {
			error -= 360.0 * Math.signum(error);
		}

		if(Math.abs(error) > 90.0) {
			error -= 180.0 * Math.signum(error);
			speed *= -1;
		}

		mTurn.set(ControlMode.Position, getTurnRawPosition() + (error * fullRot / 360));
		mDrive.set(speed);

		//turnMotorControl(targetAngle);
		//spinWheel(speed);
	}
	
	public double getTurnRawPosition() {
		return mTurn.getSelectedSensorPosition(Constants.kPIDLoopIdx);
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
	
	public void setPIDF(double kP, double kI, double kD) {
		pidController.setPID(kP, kI, kD);
	}

	public boolean isInverted() {
		return isReversed;
	}
	
}