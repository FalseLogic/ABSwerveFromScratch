package org.usfirst.frc.team4653.robot.swerveutil;


public class SwerveMath {

	/**
	 * 
	 * @param angle	any angle in degrees
	 * @return	given angle normalized between -180 and 180
	 */
	public double normalizeAngle(double angle) {
		return Math.atan2(Math.sin(angle), Math.cos(angle));
	}

}