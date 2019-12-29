package frc.robot.vision;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utils.PIDSettings;

import java.util.function.DoubleConsumer;

import static frc.robot.Robot.robotConstants;

public class FollowTarget extends CommandBase {
  private PIDController rotationPIDController;
  private PIDController distancePIDController;

  public FollowTarget(Target target, DoubleConsumer output) {
    PIDSettings distanceSettings = robotConstants.pidConstants.visionDistanceSettings;
    PIDSettings rotationSettings = robotConstants.pidConstants.visionRotationSettings;
    distancePIDController = new PIDController(distanceSettings.getKP(), distanceSettings.getKI(), distanceSettings.getKD());
    rotationPIDController = new PIDController(rotationSettings.getKP(), rotationSettings.getKI(), rotationSettings.getKD());
  }

}
