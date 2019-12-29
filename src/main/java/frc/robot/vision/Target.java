package frc.robot.vision;

/**
 * This class represent a potential targets that the robot
 * can follow using vision.
 * Each of the targets has an index representing
 * what pipeline limelight should use for finding it.
 */
public enum Target {
  //These are examples for targets.
  RocketMiddle(0), RocketSide(1), Feeder(2);

  private final int index;

  Target(int index) {
    this.index = index;

  }

  public int getIndex() {
    return index;
  }

}
