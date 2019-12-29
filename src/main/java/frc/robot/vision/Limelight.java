package frc.robot.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.Vector2d;
import static frc.robot.Robot.robotConstants;

public class Limelight {

  public static final String DEFAULT_TABLE_KEY = "limelight";
  private final NetworkTableEntry tv, tx, ty, ta, ts, ledMode, camMode, pipeline;

  /**
   * @param tableKey the key of the limelight - if it was changed.
   */
  public Limelight(String tableKey) {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable limelightTable = inst.getTable(tableKey);
    tv = limelightTable.getEntry("tv");
    tx = limelightTable.getEntry("tx");
    ty = limelightTable.getEntry("ty");
    ta = limelightTable.getEntry("ta");
    ts = limelightTable.getEntry("ts");
    ledMode = limelightTable.getEntry("ledMode");
    camMode = limelightTable.getEntry("camMode");
    pipeline = limelightTable.getEntry("pipeline");

  }

  public Limelight() {
    this(DEFAULT_TABLE_KEY);
  }

  /**
   * @return Whether the limelight has any valid targets (0 or 1)
   */
  public boolean getTv() {
    return tv.getDouble(0) == 1;
  }

  /**
   * @return Horizontal Offset From Crosshair To Target
   */
  public double getTx() {
    return tx.getDouble(0);
  }

  /**
   * @return Vertical Offset From Crosshair To Target
   */
  public double getTy() {
    return ty.getDouble(0);
  }

  /**
   * @return Target Area (0% of image to 100% of image)
   */
  public double getTa() {
    return ta.getDouble(0);
  }

  /**
   * @return Skew or rotation (-90 degrees to 0 degrees)
   */
  public double getTs() {
    return ts.getDouble(0);
  }

  /**
   * @return The distance between the the target and the limelight
   */
  //TODO: set real function
  public double getDistanceFromLimelight() {
    double x = getTy();
    return robotConstants.visionConstants.DISTANCE_CALCULATION_A_COEFFICIENT * x * x +
            robotConstants.visionConstants.DISTANCE_CALCULATION_B_COEFFICIENT * x;
  }

  /**
   * @return The distance between the the target and the the middle of the robot
   */
  public double getDistance() {
    return calculateVector().magnitude();
  }

  /**
   * @return the angle from the middle of the robot to the target
   */
  public double getAngle(){
    Vector2d vector = calculateVector();
    return Math.atan(vector.y/vector.x);
  }

  /**
   * @return the cam mode in the NetworkTable.
   */
  public CamMode getCamMode() {
    return camMode.getDouble(0) == 0 ? CamMode.vision : CamMode.driver;
  }

  /**
   * @param camMode the mode to be changed to.
   */
  public void setCamMode(int camMode) {
    this.camMode.setNumber(camMode);
  }

  /**
   * @param camMode the mode to be changed to.
   */
  public void setCamMode(CamMode camMode) {
    this.camMode.setNumber(camMode.getValue());
  }

  /**
   * This enum has two states - send images to driver and calculate the vision.
   */
  public enum CamMode {
    vision(0), driver(1);

    private final int value;

    CamMode(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

  }

  public enum LedMode {
    defaultPipeline(0), off(1), blink(2), on(3);

    private final int value;

    LedMode(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  public void toggleLedMode() {
    if (getLedMode().equals(LedMode.off))
      setLedMode(LedMode.on);
    else
      setLedMode(LedMode.off);

  }

  /**
   * @return the led mode in the NetworkTable.
   */
  public LedMode getLedMode() {
    int index = (int) ledMode.getDouble(0);
    return LedMode.values()[index];
  }

  /**
   * @param ledMode the mode to be changed to.
   */
  public void setLedMode(LedMode ledMode) {
    System.out.println(this.ledMode.setNumber(ledMode.getValue()));
  }

  /**
   * @param ledMode the mode to be changed to.
   */
  public void setLedMode(int ledMode) {
    this.ledMode.setNumber(ledMode);
  }


  /**
   * @return the current target in the NetworkTable.
   */
  public Target getTarget() {
    int index = (int) pipeline.getDouble(0);
    return Target.values()[index];
  }

  /**
   * @param pipeline pipeline index to be changed to.
   */
  public void setPipeline(int pipeline) {
    this.pipeline.setNumber(pipeline);
  }

  /**
   * @param target the target to be changed to.
   */
  public void setPipeline(Target target) {
    setPipeline(target.getIndex());
  }

  /**
   * @return the vector between the middle of the robot and and the target.
   */
  private Vector2d calculateVector() {
    //This is the offset vector.
    Vector2d MiddleToLimelight = new Vector2d(-robotConstants.visionConstants.LIMELIGHT_OFFSET_X, -robotConstants.visionConstants.LIMELIGHT_OFFSET_Y);
    //This is the the vector from the limelight to the target.
    Vector2d limelightToTarget = new Vector2d(0, getDistanceFromLimelight());
    limelightToTarget.rotate(getTx());
    // The offset is subtracted from the limelightToTarget vector in order to get the final vector.
    return new Vector2d(MiddleToLimelight.x + limelightToTarget.x, MiddleToLimelight.y + limelightToTarget.y);
  }
}