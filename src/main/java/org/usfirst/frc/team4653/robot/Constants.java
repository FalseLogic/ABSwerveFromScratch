package org.usfirst.frc.team4653.robot;

//Every constant the robot uses, in one package.
//You can nest classes here to organize the data better, if you want.
//Make sure you name the constants so that you know what they are by looking at them
public class Constants {

	//CAN IDs
	public static final int FL_DRIVE_PORT = 1,
							FL_TURN_PORT = 5,
							FR_DRIVE_PORT = 2,
			    			FR_TURN_PORT = 6,
			   				BL_DRIVE_PORT = 3,
			  				BL_TURN_PORT = 7,
			  				BR_DRIVE_PORT = 4,
			 				BR_TURN_PORT = 8;

	public static final int FL_OFFSET = 0,
			  				FR_OFFSET = 0,
							BL_OFFSET = 0,
			  				BR_OFFSET = 0;

	public static final int STICK_PORT = 1,
							SRX_TIMEOUT_MS = 10,
							SRX_PIDLOOPIDX = 0;
	
	public static final double SWERVE_P_GAIN = .945,
							   SWERVE_I_GAIN = 0,
							   SWERVE_D_GAIN = 49.5;

	//Some physical dimensions of the robot for swerve calculations
	public static final double WHEELBASE_INCHES = 19.5,
							   TRACKWIDTH_INCHES = 28.0,
							   TURN_RADIUS_INCHES = Math.hypot(WHEELBASE_INCHES, TRACKWIDTH_INCHES),
							   MODULE_FULL_ROTATION = 4096 / 1.2;

	//This basic function can be used to filter noise out of a joystick input
	public static double filter(double a) {
		if(Math.abs(a) < .02) {
			return 0;
		}
		return a;
	}
	
}