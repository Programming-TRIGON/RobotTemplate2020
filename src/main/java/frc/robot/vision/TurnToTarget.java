package frc.robot.vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.utils.PIDSettings;
import frc.robot.vision.Limelight.CamMode;
import frc.robot.vision.Limelight.LedMode;

import java.util.function.BiConsumer;
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
   * @param target The target the robot will follow
   * @param output accepts rotation output.
   */
  public TurnToTarget(Target target, DoubleConsumer output) {
    addRequirements(Robot.drivetrain);
    this.target = target;
    this.output = output;
    PIDSettings rotationSettings = robotConstants.pidConstants.visionRotationSettings;
    rotationPIDController = new PIDController(rotationSettings.getKP(), rotationSettings.getKI(), rotationSettings.getKD());
    rotationPIDController.setTolerance(rotationSettings.getTolerance(), rotationSettings.getDeltaTolerance());
  }

  @Override
  public void initialize() {
    rotationPIDController.reset();
    lastTimeSeenTarget = Timer.getFPGATimestamp();
    // Configure the limelight to start computing vision.
    limelight.setPipeline(target);
    limelight.setCamMode(CamMode.vision);
    limelight.setLedMode(LedMode.on);
  }

  @Override
  public void execute() {
    if (limelight.getTv()) {
      double rotationOutput = rotationPIDController.calculate(limelight.getAngle());
      output.accept(rotationOutput);
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
    limelight.setLedMode(LedMode.off);
    limelight.setCamMode(CamMode.driver);
  }

  public void enableTuning() {
    SmartDashboard.putData("PID/visionRotation", rotationPIDController);
  }
}
