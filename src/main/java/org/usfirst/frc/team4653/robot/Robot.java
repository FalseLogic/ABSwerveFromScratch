package org.usfirst.frc.team4653.robot;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc.team4653.robot.commands.SwerveDrive;
import org.usfirst.frc.team4653.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;

//This project uses commands/subsystems, but not the entire WPILib "Command Based" structure
//You may want to follow the documentation and create a RobotContainer, following their directions
//On the other hand, command based may not be useful for any given robot
public class Robot extends TimedRobot {

	public static Drivetrain drivetrain;
	public static Joystick stick;
	public static AHRS ahrs;

	@Override
	public void robotInit() {
		drivetrain = new Drivetrain();
		stick = new Joystick(Constants.STICK_PORT);
		ahrs = new AHRS(SPI.Port.kMXP);

		//Sets swerve drive to run in the background
		drivetrain.setDefaultCommand(new SwerveDrive(true));
	}

}