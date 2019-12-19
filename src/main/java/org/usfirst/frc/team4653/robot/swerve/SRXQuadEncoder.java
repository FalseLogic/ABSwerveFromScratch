package org.usfirst.frc.team4653.robot.swerve;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class SRXQuadEncoder implements PIDSource {

	private PIDSourceType kPIDSourceType;
	private TalonSRX talon;

	public SRXQuadEncoder(TalonSRX talon) {
		kPIDSourceType = PIDSourceType.kDisplacement;
		this.talon = talon;
	}

	public double pidGet() {
		double fullRot = 4096 / 1.2;
		return talon.getSensorCollection().getQuadraturePosition() / fullRot * 360;
	}

	public void setPIDSourceType(PIDSourceType pidSource) {
		kPIDSourceType = pidSource;
	}

	public PIDSourceType getPIDSourceType() {
		return kPIDSourceType;	
	}

}