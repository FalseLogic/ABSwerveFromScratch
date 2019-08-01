package org.usfirst.frc.team4653.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

	private AHRS ahrs = new AHRS(SPI.Port.kMXP);

	public Joystick stick = new Joystick(Constants.stickPort);
	public XboxController xbox = new XboxController(Constants.xboxPort);

	public Button button1 = new JoystickButton(stick, 1),
		   button2 = new JoystickButton(stick, 2),
		   button3 = new JoystickButton(stick, 3),
		   button4 = new JoystickButton(stick, 4),
		   button5 = new JoystickButton(stick, 5),
		   button6 = new JoystickButton(stick, 6),
		   button7 = new JoystickButton(stick, 7),
		   button8 = new JoystickButton(stick, 8),
		   button9 = new JoystickButton(stick, 9),
		   button10 = new JoystickButton(stick, 10),
		   button11 = new JoystickButton(stick, 11),
		   button12 = new JoystickButton(stick, 12);

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
	
	private double lastStickAngle, rads, degrees;
	public double getStickAngle() {
		rads = Math.atan2(stick.getX(), -stick.getY());
		degrees = Math.toDegrees(rads);
		
		if(this.getStickMagnitude() < .1) {
			return lastStickAngle;
		}
		else {
			lastStickAngle = degrees;
			return degrees;	
		}
		

	}

	public double getStickMagnitude() {
		return Math.hypot(stick.getX(), stick.getY());
	}
	
}