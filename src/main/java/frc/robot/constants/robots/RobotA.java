package frc.robot.constants.robots;

import frc.robot.constants.RobotConstants;
import frc.robot.utils.PIDSettings;

/**
 * Constants and robot map for robot A.
 */
public class RobotA extends RobotConstants {
    // TODO: Set constants

    public RobotA() {
        /* Robot Constants */
        /* Drivetrain Constants */
        drivetrainConstants.WHEEL_DIAMETER = 0;
        drivetrainConstants.WHEEL_BASE_WIDTH = 0;
        drivetrainConstants.ROBOT_LENGTH = 0;
        drivetrainConstants.ROBOT_WIDTH = 0;
        drivetrainConstants.LEFT_ENCODER_TICKS_PER_METER = 1;
        drivetrainConstants.RIGHT_ENCODER_TICKS_PER_METER = 1;

        /* Vision Constants */
        visionConstants.DISTANCE_CALCULATION_A_COEFFICIENT = 0;
        visionConstants.DISTANCE_CALCULATION_B_COEFFICIENT = 0;
        visionConstants.LIMELIGHT_OFFSET_X = 0;
        visionConstants.LIMELIGHT_OFFSET_Y = 0;

        pidConstants.exampleSettings = new PIDSettings(0,0,0,0);
        /* Robot Map */

    }
}
