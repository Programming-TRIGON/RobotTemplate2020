package frc.robot.constants;

/**
 * The RobotConstants maps constants to a variable name.
 */
public abstract class RobotConstants extends RobotMap {
    public DrivetrainConstants drivetrainConstants = new DrivetrainConstants(); 

    // Example: 
    public static class DrivetrainConstants {
        public double WHEEL_DIAMETER; 
        public double WHEEL_BASE_WIDTH;
        public double ROBOT_LENGTH;
        public double ROBOT_WIDTH;
        public double LEFT_ENCODER_METER_PER_TICKS;
        public double RIGHT_ENCODER_METER_PER_TICKS;
    }

    // More static class here! 

}
