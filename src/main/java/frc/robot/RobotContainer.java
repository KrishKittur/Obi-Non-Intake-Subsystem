/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.lang.Math;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.intake.IntakeSubsystem;


public class RobotContainer {

    IntakeSubsystem intake_subsystem = new IntakeSubsystem();
    XboxController driverController = new XboxController(0);
    XboxController operatorController = new XboxController(1);
    
    public RobotContainer() {
      // Intake Subystem default command
      intake_subsystem.setDefaultCommand(
        new RunCommand(
          () -> {
            double speed = operatorController.getTriggerAxis(Hand.kRight) - 
            operatorController.getTriggerAxis(Hand.kLeft) + 
            driverController.getTriggerAxis(Hand.kLeft) - 
            driverController.getTriggerAxis(Hand.kRight);

            if (speed > Math.abs(0.1)) {
              intake_subsystem.startIntake(speed);
            }

          }, 
          intake_subsystem
        )
      );

      // Configure the button bindings
      configureButtonBindings();
    }

    private void configureButtonBindings() {

      // Extend intake when driver controller clicks the A button
      new JoystickButton(driverController, Button.kA.value).whenPressed(new SequentialCommandGroup(
        new InstantCommand(() -> intake_subsystem.extendSmallSolenoid(), intake_subsystem),
        new WaitCommand(0.2),
        new InstantCommand(() -> intake_subsystem.extendLargeSolenoid(), intake_subsystem)
      ));

      // Retract the small solenoid when driver controller clicks the X button
      new JoystickButton(driverController, Button.kX.value).whenPressed(
        new InstantCommand(() -> intake_subsystem.extendSmallSolenoid(), intake_subsystem)
      );

      // Extend the intake when the operator controller clicks the X button
      new JoystickButton(operatorController, Button.kX.value).whenPressed(new SequentialCommandGroup(
        new InstantCommand(() -> intake_subsystem.extendSmallSolenoid(), intake_subsystem),
        new WaitCommand(0.2),
        new InstantCommand(() -> intake_subsystem.extendLargeSolenoid(), intake_subsystem)
      ));

      // Retract the intake when the operator controller changes the Y button
      new JoystickButton(operatorController, Button.kY.value).whenPressed(new SequentialCommandGroup(
        new InstantCommand(() -> intake_subsystem.retractLargeSolenoid(), intake_subsystem),
        new WaitCommand(0.2),
        new InstantCommand(() -> intake_subsystem.retractSmallSolenoid(), intake_subsystem),
        new WaitCommand(0.2)
      ));



    }


    public void getAutonomousCommand() {
      
    }
}
