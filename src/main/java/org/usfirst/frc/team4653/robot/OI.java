package org.usfirst.frc.team4653.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;

public class OI {

	public OI() {
		ahrs = new AHRS(SPI.Port.kMXP);

		leftStick = new Joystick(Constants.LEFT_STICK_PORT);
		rightStick = new Joystick(Constants.RIGHT_STICK_PORT);
		xbox = new XboxController(Constants.XBOX_PORT);
	}

	private AHRS ahrs;

	public Joystick leftStick;
	public Joystick rightStick;

	public XboxController xbox;

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
		if(Math.abs(a) < .02) {
			return 0;
		}
		return a;
	}
	
}