package org.usfirst.frc.team4653.robot;

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

	public static final int FL_OFFSET = -777,
			  				FR_OFFSET = 234,
							BL_OFFSET = 314,
			  				BR_OFFSET = -69;

	public static final int LEFT_STICK_PORT = 1,
							RIGHT_STICK_PORT = 2,
							XBOX_PORT = 0,
							VJOY_PORT = 4,
							SRX_TIMEOUT_MS = 10,
							SRX_PIDLOOPIDX = 0;
	
	public static final double SWERVE_P_GAIN = .945,
							   SWERVE_I_GAIN = 0,
							   SWERVE_D_GAIN = 49.5;

	public static final double WHEELBASE_INCHES = 19.5,
							   TRACKWIDTH_INCHES = 28.0,
							   TURN_RADIUS_INCHES = Math.hypot(WHEELBASE_INCHES, TRACKWIDTH_INCHES);
			
	public enum Location {
        FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT;
	}
	
}