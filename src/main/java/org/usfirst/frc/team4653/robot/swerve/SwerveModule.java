package org.usfirst.frc.team4653.robot.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team4653.robot.Constants;


public class SwerveModule {

	public WPI_TalonSRX turn;
	public CANSparkMax drive;

	private boolean isReversed;
	private double offset;

	double fullRot = 4096 / 1.2;

	/**
     * @param turnID   		the ID of the turn motor
     * @param driveID       the ID of the drive motor
     * @param isReversed    if the module is physically reversed on the robot
     * @param offset		encoder value when wheel is pointing stright
     */
	public SwerveModule(int turnID, int driveID, double offset, boolean isReversed) {
		turn = new WPI_TalonSRX(turnID);
		drive = new CANSparkMax(driveID, MotorType.kBrushless);
		this.offset = offset;
		this.isReversed = isReversed;

		turn.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, Constants.SRX_PIDLOOPIDX, Constants.SRX_TIMEOUT_MS);
		turn.config_kP(Constants.SRX_PIDLOOPIDX, Constants.SWERVE_P_GAIN);
		turn.config_kI(Constants.SRX_PIDLOOPIDX, Constants.SWERVE_I_GAIN);
		turn.config_kD(Constants.SRX_PIDLOOPIDX, Constants.SWERVE_D_GAIN);
		turn.setNeutralMode(NeutralMode.Brake);

	}

	public WPI_TalonSRX getTurnController() {
		return turn;
	}

	public CANSparkMax getDriveController() {
		return drive;
	}

	public void oldTurnMotorControl(double targetAngle) {

		double turnMotorPower, amtOff, targetPos;

		targetPos = (targetAngle * fullRot / 360);
		amtOff = getTurnAdjPosition() - targetPos;
		
		turnMotorPower = (-amtOff/fullRot) + Math.floor((amtOff + (fullRot / 2)) / fullRot);
		
		turnMotorPower += Math.copySign(.05, turnMotorPower);
		
		if(Math.abs(turnMotorPower) > .06) {
			turn.set(ControlMode.PercentOutput, turnMotorPower);
		}
		else {
			turn.set(ControlMode.PercentOutput, 0);
		}
		
	}
	
	public void spinWheel(double speed) {
		if(isReversed) {
			drive.set(-speed);
		}
		else {
			drive.set(speed);
		}
	}

	public void setModule(double targetAngle, double targetSpeed) {

		double target, current, speed;
			
		target = targetAngle;
		current = getTurnDegrees();
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

		turn.set(ControlMode.Position, getTurnRawPosition() + (error * fullRot / 360));
		//drive.set(speed);
		//turnMotorControl(targetAngle);
		spinWheel(speed);
	}
	
	public double getTurnRawPosition() {
		return turn.getSelectedSensorPosition(Constants.SRX_PIDLOOPIDX);
	}

	public double getTurnAdjPosition() {
		return turn.getSelectedSensorPosition(Constants.SRX_PIDLOOPIDX) - offset;
	}

	//public double getTurnErrorDegrees() {
	//	return getTurnRawDegrees() - targetAngle;
	//}

	public double getTurnVelocity() {
		return turn.getSensorCollection().getQuadratureVelocity();
	}

	public double getTurnDegrees() {
		return getTurnAdjPosition() / fullRot * 360;
	}

	public double getDriveRawPosition() {
		return drive.getEncoder().getPosition();
	}
	public double getDriveVelocity() {
		return drive.getEncoder().getVelocity();
	}
	public void resetTurnEncoder() {
		//turn.getSensorCollection().setQuadraturePosition(0, Constants.SRX_TIMEOUT_MS);
		turn.setSelectedSensorPosition(0);
	}
	public void resetDriveEncoder() {
		drive.getEncoder().setPosition(0);
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public void newOffset() {
		this.offset = getTurnRawPosition();
	}
	
	public void setTurnPID(double kP, double kI, double kD) {
		turn.config_kP(Constants.SRX_PIDLOOPIDX, kP);
		turn.config_kI(Constants.SRX_PIDLOOPIDX, kI);
		turn.config_kD(Constants.SRX_PIDLOOPIDX, kD);
	}

	public boolean isInverted() {
		return isReversed;
	}
	
}