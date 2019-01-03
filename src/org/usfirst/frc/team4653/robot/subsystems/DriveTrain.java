package org.usfirst.frc.team4653.robot.subsystems;

import org.usfirst.frc.team4653.robot.Constants;
import org.usfirst.frc.team4653.robot.Constants.ModuleLocation;
import org.usfirst.frc.team4653.robot.SwerveModule;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	private SwerveModule modFrontLeft = new SwerveModule(Constants.FLturn, Constants.FLdrive, 0, false);
	private SwerveModule modFrontRight = new SwerveModule(Constants.FRturn, Constants.FRdrive, 0, false);
	private SwerveModule modBackLeft = new SwerveModule(Constants.BLturn, Constants.BLdrive, 0, false);
	private SwerveModule modBackRight = new SwerveModule(Constants.BRturn, Constants.BRdrive, 0, false);

	// Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public void setModule(ModuleLocation location, double targetAngle, double power) {
		switch(location) {
		case FRONT_LEFT:
			modFrontLeft.setModule(targetAngle, power);
		case FRONT_RIGHT:
			modFrontRight.setModule(targetAngle, power);
		case BACK_LEFT:
			modBackLeft.setModule(targetAngle, power);
		case BACK_RIGHT:
			modBackRight.setModule(targetAngle, power);
		}
	}
	
	public void setAngle(double targetAngle) {
		modFrontLeft.turnMotorControl(targetAngle);
		modFrontRight.turnMotorControl(targetAngle);
		modBackLeft.turnMotorControl(targetAngle);
		modFrontRight.turnMotorControl(targetAngle);
	}
	
	public void setSpeed(double power) {
		modFrontLeft.spinWheel(power);
		modFrontRight.spinWheel(power);
		modBackLeft.spinWheel(power);
		modBackRight.spinWheel(power);
	}
	
	public void resetDriveEncoders() {
		modFrontLeft.resetDriveEncoder();
		modFrontRight.resetDriveEncoder();
		modBackLeft.resetDriveEncoder();
		modBackRight.resetDriveEncoder();
	}
	
	public void printDriveEnc() {
		System.out.print(modFrontLeft.getDriveVelocity() + " ");
		System.out.print(modFrontRight.getDriveVelocity() + " ");
		System.out.print(modBackLeft.getDriveVelocity() + " ");
		System.out.println(modBackRight.getDriveVelocity());
	}
	
	public void printRawTurnEnc() {
		System.out.print(modFrontLeft.getTurnRawPosition() + " ");
		System.out.print(modFrontRight.getTurnRawPosition() + " ");
		System.out.print(modBackLeft.getTurnRawPosition() + " ");
		System.out.println(modBackRight.getTurnRawPosition());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

