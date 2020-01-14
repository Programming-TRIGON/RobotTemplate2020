package frc.robot.vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.vision.Target;
import frc.robot.subsystems.MoveableSubsystem;
import frc.robot.utils.PIDSettings;
import java.util.function.DoubleConsumer;

import static frc.robot.Robot.limelight;
import static frc.robot.Robot.robotConstants;

/**
 * this is just template for a subsystem turn to target command.
 * It will be probably changed according the game and the robot.
 */
public class TurnToTarget extends CommandBase {
  private Target target;
  private DoubleConsumer output;
  private PIDController rotationPIDController;
  private double lastTimeSeenTarget;

  /**
   * @param target    The target the robot will follow
   * @param output    accepts rotation output.
   * @param subsystem the subsystem to require
   */
  public TurnToTarget(Target target, MoveableSubsystem subsystem) {
    addRequirements(subsystem);
    this.target = target;
    output = subsystem::move;
    PIDSettings rotationSettings = robotConstants.controlConstants.visionRotationSettings;
    rotationPIDController = new PIDController(rotationSettings.getKP(), rotationSettings.getKI(), rotationSettings.getKD());
    rotationPIDController.setTolerance(rotationSettings.getTolerance(), rotationSettings.getDeltaTolerance());
  }

  @Override
  public void initialize() {
    rotationPIDController.reset();
    rotationPIDController.setSetpoint(0);
    lastTimeSeenTarget = Timer.getFPGATimestamp();
    // Configure the limelight to start computing vision.
    limelight.startVision(target);
  }

  @Override
  public void execute() {
    if (limelight.getTv()) {
      output.accept(rotationPIDController.calculate(limelight.getAngle()));
      lastTimeSeenTarget = Timer.getFPGATimestamp();
    } else
      // The target wasn't found
      output.accept(0.0);
  }

  @Override
  public boolean isFinished() {
    return ((Timer.getFPGATimestamp() - lastTimeSeenTarget) > robotConstants.visionConstants.TARGET_NOT_FOUND_WAIT_TIME)
            || rotationPIDController.atSetpoint();
  }

  @Override
  public void end(boolean interrupted) {
    output.accept(0.0);
    limelight.stopVision();
  }

  public void enableTuning() {
    SmartDashboard.putData("PID/visionRotation", rotationPIDController);
  }
}
