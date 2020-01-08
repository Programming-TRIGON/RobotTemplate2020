package frc.robot.subsystems;

import java.util.Random;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.enums.Color;

public class LED extends SubsystemBase {
  private Spark ledController;
  private Color currentColor;
  private Notifier notifier;
  private Random rand;
  private final double BLINK_TIME = 0.7;
  
  /**
   * Creates a new LED subsystem for Rev robotics Led controller and color changing.
   */
  public LED() {
    ledController = new Spark(Robot.robotConstants.pwm.LED_CONTROLLER);
    notifier = new Notifier(() -> {});
    rand = new Random();
    if(DriverStation.getInstance().getAlliance().equals(Alliance.Blue))
      setColor(Color.Blue);
    else
      setColor(Color.Red);
  }

  public void setColor(Color color) {
    setControllerPower(color.getValue());
    currentColor = color;
  } 

  public void setControllerPower(double value) {
    ledController.set(value);
  }

  public void turnOffLED() {
    currentColor = null;
    setControllerPower(0);
  }

  public Color getCurrentColor() {
    return currentColor;
  }

  /**
   * Blinks the LED with a certain color for several times.
   * @param color the color to blink
   * @param quantity the number of times to blink 
   */
  public void blinkColor(Color color, int quantity) {
    turnOffLED();
    notifier.setHandler(() -> toggleColor(color, quantity * 2));
    notifier.startSingle(0);
  }

  /**
   * Toggle the LED off and the given color.
   * Also, delay the thread for {@link #BLINK_TIME} seconds.   
   * @param color the color to toggle
   * @param amount the number of times to blink
   */
  private synchronized void toggleColor(Color color, int quantity) {
    for (int i = 0; i < quantity; i++) {
      if(color == currentColor) 
        turnOffLED();
      else
        setColor(color);
      Timer.delay(BLINK_TIME);
    }
  }

  public void setRandomPattern() {
    setControllerPower(getRandomPattern());
  }

  /**
   * random number between 
   * @return random number between -0.05 to -0.99 in jumps of 0.02
   */
  private double getRandomPattern() { 
    double x = 0.1 * rand.nextInt(10); //number between 0.0 and 0.9
    if(x==0.0)
      return x + 0.01 * (rand.nextInt(10) + 5); 
    return x + 0.01 * (rand.nextInt(10) + 5);
  }
}

