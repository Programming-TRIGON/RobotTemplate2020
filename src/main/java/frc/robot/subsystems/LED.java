package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.enums.Color;

public class LED extends SubsystemBase {
  private Spark ledController;
  
  /**
   * Creates a new LED subsystem for Rev robotics Led controller and color changing.
   */
  public LED() {
    ledController = new Spark(Robot.robotConstants.pwm.LED_CONTROLLER);
  }

  public void setColor(Color color) {
    setControllerPower(color.getValue());
  } 

  public void setControllerPower(double value) {
    ledController.set(value);
  }

  public void turnOffLED() {
    setControllerPower(0);
  }

  @Override
  public void periodic() {
    // Do some blink method here...?
  }
}

