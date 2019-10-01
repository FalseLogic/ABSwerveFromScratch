package org.usfirst.frc.team4653.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;

public class OI {

	private AHRS ahrs = new AHRS(SPI.Port.kMXP);

	public Joystick leftStick = new Joystick(Constants.leftStickPort);
	public Joystick rightStick = new Joystick(Constants.rightStickPort);

	public XboxController xbox = new XboxController(Constants.xboxPort);

	public enum Stick {
		LEFT, RIGHT
	}

	public double getGyroDegrees() {
		return ahrs.getAngle();
	}

	public void resetGyro() {
		ahrs.reset();
	}

	public double getStickX(Stick stick) {
		if(stick == Stick.LEFT) return filter(leftStick.getX());
		else if(stick == Stick.RIGHT) return filter(rightStick.getX());
		return 0.0;
	}
	public double getStickY(Stick stick) {
		if(stick == Stick.LEFT) return filter(leftStick.getY());
		else if(stick == Stick.RIGHT) return filter(rightStick.getY());
		return 0.0;
	}
	public double getStickZ(Stick stick) {
		if(stick == Stick.LEFT) return filter(leftStick.getZ());
		else if(stick == Stick.RIGHT) return filter(rightStick.getZ());
		return 0.0;
	}
	
	public double getStickAngle(Stick stick) {
		double rads = Math.atan2(getStickX(stick), -getStickY(stick));
		return Math.toDegrees(rads);
	}

	public double getStickMagnitude(Stick stick) {
		double mag = Math.hypot(getStickX(stick), getStickY(stick));

		return filter(mag);
	}

	private double filter(double a) {
		if(Math.abs(a) < .1) {
			return 0;
		}
		return a;
	}
	
}