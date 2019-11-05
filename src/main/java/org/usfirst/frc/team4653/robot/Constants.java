package org.usfirst.frc.team4653.robot;

public class Constants {
	
	public static final int FLdrive = 1,
			  FLturn = 5,
			  FRdrive = 2,
			  FRturn = 6,
			  BLdrive = 3,
			  BLturn = 7,
			  BRdrive = 4,
			  BRturn = 8;
	
	public static final int FLoffset = 1215,
			  FRoffset = 601,
			  BLoffset = -1152,
			  BRoffset = -786;

	public static final int leftStickPort = 0,
			rightStickPort = 1,
			xboxPort = 2,
			kTimeoutMs = 10,
			kPIDLoopIdx = 0;
	
	public static final double SWERVE_P_GAIN = .945,
			SWERVE_I_GAIN = 0,
			SWERVE_D_GAIN = 49.5;

	public static final double WHEELBASE_INCHES = 19.0,
			TRACKWIDTH_INCHES = 27.0,
			TURN_RADIUS_INCHES = Math.hypot(WHEELBASE_INCHES, TRACKWIDTH_INCHES);
			
	public static final boolean modulePIDControl = true;

	public enum Location {
        FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT;
	}
	
}