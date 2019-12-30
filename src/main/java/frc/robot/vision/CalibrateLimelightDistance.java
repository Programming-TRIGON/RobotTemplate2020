package frc.robot.vision;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.utils.Logger;

import java.util.function.Supplier;


public class CalibrateLimelightDistance extends CommandBase {
  private static final int DEFAULT_DELTA_DISTANCE = 30;
  private static final int AMOUNT_OF_LOGS = 8;
  private double currentDistance = 0;
  private boolean isPressed = false;
  private Supplier<Boolean> logButton;
  private double deltaDistance;
  private Logger logger;

  /**
   * @param logButton whenever the supplier toggles to true - log the values.
   */
  public CalibrateLimelightDistance(Supplier<Boolean> logButton) {
    this(logButton, DEFAULT_DELTA_DISTANCE);
  }

  /**
   * @param logButton whenever the supplier toggles to true - log the values.
   * @param deltaDistance the distance between each log.
   */
  public CalibrateLimelightDistance(Supplier<Boolean> logButton, double deltaDistance) {
    this.logButton = logButton;
    this.deltaDistance = deltaDistance;
  }

  @Override
  public void initialize() {
    logger = new Logger("distance calibration.csv", "height", "Distance");
  }


  @Override
  public void execute() {
    if (logButton.get()) {
      if (!isPressed) {
        isPressed = true;
        logger.log(Robot.limelight.getTy(), currentDistance);
        currentDistance += deltaDistance;
      }
    } else
      isPressed = false;
  }

  @Override
  public boolean isFinished() {
    return currentDistance > deltaDistance * AMOUNT_OF_LOGS;

  }

  @Override
  public void end(boolean interrupted) {
    logger.close();

  }
}

