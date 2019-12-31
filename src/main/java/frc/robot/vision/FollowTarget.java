package frc.robot.vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.enums.Target;
import frc.robot.utils.PIDSettings;
import java.util.function.BiConsumer;

import static frc.robot.Robot.limelight;
import static frc.robot.Robot.robotConstants;

/**
 * this is just template for a target follow command.
 * It will be probably changed according the game and the robot.
 */
public class FollowTarget extends CommandBase {
  private Target target;
  private BiConsumer<Double, Double> output;
  private PIDController rotationPIDController;
  private PIDController distancePIDController;
  private double lastTimeSeenTarget;

  /**
   * @param target The target the robot will follow
   * @param output accepts rotation output and distance output.
   */
  public FollowTarget(Target target, BiConsumer<Double, Double> output) {
    addRequirements(Robot.drivetrain);
    this.target = target;
    this.output = output;
    PIDSettings distanceSettings = robotConstants.pidConstants.visionDistanceSettings;
    PIDSettings rotationSettings = robotConstants.pidConstants.visionRotationSettings;
    distancePIDController = new PIDController(distanceSettings.getKP(), distanceSettings.getKI(), distanceSettings.getKD());
    distancePIDController.setTolerance(distanceSettings.getTolerance(), distanceSettings.getDeltaTolerance());
    rotationPIDController = new PIDController(rotationSettings.getKP(), rotationSettings.getKI(), rotationSettings.getKD());
    rotationPIDController.setTolerance(rotationSettings.getTolerance(), rotationSettings.getDeltaTolerance());
  }

  @Override
  public void initialize() {
    distancePIDController.reset();
    rotationPIDController.reset();

    lastTimeSeenTarget = Timer.getFPGATimestamp();
    // Configure the limelight to start computing.
    limelight.startVision(target);
  }

  @Override
  public void execute() {
    if (limelight.getTv()) {
      double distanceOutput = distancePIDController.calculate(limelight.getDistance());
      double rotationOutput = rotationPIDController.calculate(limelight.getAngle());
      output.accept(rotationOutput, distanceOutput);
      lastTimeSeenTarget = Timer.getFPGATimestamp();
    } else
      // The target wasn't found
      output.accept(0.0, 0.0);
  }

  @Override
  public boolean isFinished() {
    return ((Timer.getFPGATimestamp() - lastTimeSeenTarget) > robotConstants.visionConstants.TARGET_NOT_FOUND_WAIT_TIME)
            || (rotationPIDController.atSetpoint() && distancePIDController.atSetpoint());
  }

  @Override
  public void end(boolean interrupted) {
    output.accept(0.0, 0.0);
    limelight.stopVision();
  }

  public void enableTuning() {
    SmartDashboard.putData("PID/visionRotation", rotationPIDController);
    SmartDashboard.putData("PID/visionDistance", distancePIDController);
  }
}
