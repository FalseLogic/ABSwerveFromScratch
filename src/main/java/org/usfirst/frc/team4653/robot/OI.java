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
		return filter(stick.getX());
	}
	public double getStickY() {
		return filter(stick.getY());
	}
	public double getStickZ() {
		return filter(stick.getZ());
	}
	
	public double getStickAngle() {
		double rads = Math.atan2(stick.getX(), -stick.getY());
		
		if(getStickMagnitude() == 0) {
			return 0;
		}
		return Math.toDegrees(rads);
	}

	public double getStickMagnitude() {
		double mag = Math.hypot(stick.getX(), stick.getY());

		return filter(mag);
	}

	private double filter(double a) {
		if(a < .1) {
			return 0;
		}
		return a;
	}
	
}