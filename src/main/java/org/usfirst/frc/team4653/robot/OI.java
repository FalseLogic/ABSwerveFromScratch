package org.usfirst.frc.team4653.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;

public class OI {

	private AHRS ahrs = new AHRS(SPI.Port.kMXP);

	public Joystick stick = new Joystick(Constants.stickPort);
	public XboxController xbox = new XboxController(Constants.xboxPort);

	public double getGyroDegrees() {
		return ahrs.getAngle();
	}

	public void resetGyro() {
		ahrs.reset();
	}

	public double getStickX() {
		return stick.getX();
	}
	public double getStickY() {
		return stick.getY();
	}
	public double getStickZ() {
		return stick.getZ();
	}
	
	public double getStickAngle() {
		double rads = Math.atan2(stick.getX(), -stick.getY());
		return Math.toDegrees(rads);
	}

	public double getStickMagnitude() {
		double mag = Math.hypot(stick.getX(), stick.getY());

		if(Math.abs(mag) > .05) {
			return mag;
		} 
		else {
			return 0;
		}
	}
	
}