package org.team1540.chewbawka;

import ccre.channel.*;
import ccre.ctrl.ExtendedMotorFailureException;
import ccre.drivers.ctre.talon.TalonExtendedMotor;
import ccre.frc.FRC;

public class Intake {
	
	private static final TalonExtendedMotor floorIntakeTEM = FRC.talonCAN(12);
	
	private static final BooleanInput floorIntakeButton = Robot.controlBinding.addBoolean("Toggle Floor Intake");
	
	// Create a boolean cell that switches the intake
	private static final BooleanCell intake = new BooleanCell(false);
    
	public static void setup() throws ExtendedMotorFailureException {
		
		// Make a FloatOutput that controls the intake speed
		FloatOutput floorIntakeMotor = floorIntakeTEM.simpleControl().addRamping(.02f, FRC.constantPeriodic);
		
		// Set the speed to zero when enabling
		intake.setWhen(false, FRC.startDisabled.or(FRC.startTele).or(FRC.startAuto).or(FRC.startTest));
		
		// Setup intake logic
		FloatInput floorIntakeSpeed = Robot.mainTuning.getFloat("Floor Intake Speed", 1f);
		
		intake.toFloat(0f, floorIntakeSpeed).send(floorIntakeMotor);

		floorIntakeButton.onPress(intake.eventToggle());
		
	}
}
