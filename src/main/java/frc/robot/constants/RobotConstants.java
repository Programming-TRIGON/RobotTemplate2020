package frc.robot.constants;

import frc.robot.utils.PIDSettings;

/**
 * The RobotConstants maps constants to a variable name.
 */
public abstract class RobotConstants extends RobotMap {
    public DrivetrainConstants drivetrainConstants = new DrivetrainConstants(); 
    public PIDConstants pidConstants = new PIDConstants();

    // Example: 
    public static class DrivetrainConstants {
        public double WHEEL_DIAMETER; 
        public double WHEEL_BASE_WIDTH;
        public double ROBOT_LENGTH;
        public double ROBOT_WIDTH;
        public double LEFT_ENCODER_TICKS_PER_METER;
        public double RIGHT_ENCODER_TICKS_PER_METER;
    }
    public static class PIDConstants {
        public PIDSettings exampleSettings;
    }

    // More static class here! 

}
