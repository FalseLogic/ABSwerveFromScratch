/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4653.robot;

public class Constants {
	
	public static int FLdrive = 2,
			  FLturn = 1,
			  FRdrive = 4,
			  FRturn = 3,
			  BLdrive = 6,
			  BLturn = 5,
			  BRdrive = 8,
			  BRturn = 7;
	
	public static int stickPort = 0,
			kTimeoutMs = 10,
			kPIDLoopIdx = 0;
	
	public static double DrivekF = 1.6,
			DrivekP = 1.75;
	
	public enum ModuleLocation {
        FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT;
    }
}
