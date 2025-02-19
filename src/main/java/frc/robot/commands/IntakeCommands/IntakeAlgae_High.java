// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.LEDConstants;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.EndEffectorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class IntakeAlgae_High extends Command {
  /** Creates a new IntakeAlgae_High_Elevator. */
  private final ElevatorSubsystem m_ElevatorSubsystem;
  private final EndEffectorSubsystem m_EndEffectorSubsystem;
  public IntakeAlgae_High(ElevatorSubsystem ElevatorSubsystem, EndEffectorSubsystem endEffectorSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_ElevatorSubsystem = ElevatorSubsystem;
    this.m_EndEffectorSubsystem = endEffectorSubsystem;

    addRequirements(m_ElevatorSubsystem, m_EndEffectorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ElevatorSubsystem.intakeAlgae_High();
    m_EndEffectorSubsystem.intakeAlgae_High_Arm();
    m_EndEffectorSubsystem.intakeAlgae_High_Wheel();

    LEDConstants.intakeGamePiece = true;
    LEDConstants.hasGamePiece = false;
    LEDConstants.LEDFlag = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_EndEffectorSubsystem.hasAlgae()) {
      LEDConstants.hasGamePiece = true;
      LEDConstants.LEDFlag = true;
    }else {
      LEDConstants.hasGamePiece = false;
      LEDConstants.LEDFlag = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ElevatorSubsystem.toPrimitive();
    m_EndEffectorSubsystem.primitiveArm();
    
    if (m_EndEffectorSubsystem.hasAlgae()) {
      m_EndEffectorSubsystem.holdAlgae();

      LEDConstants.hasGamePiece = true;
      LEDConstants.intakeGamePiece = false;
      LEDConstants.LEDFlag = true;
    }else{
      m_EndEffectorSubsystem.stopWheel();

      LEDConstants.hasGamePiece = false;
      LEDConstants.intakeGamePiece = false;
      LEDConstants.LEDFlag = true;
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return LEDConstants.hasGamePiece;
  }
}
