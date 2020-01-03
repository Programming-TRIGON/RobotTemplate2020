package frc.robot.motion_profiling;

import java.util.Arrays;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import frc.robot.Robot;

import static frc.robot.Robot.robotConstants;

/**
 * Class for creating a new path for motion profiling path following.  
 */
public class Path {
    private Trajectory trajectory;
    private final TrajectoryConfig config;
    private boolean reversed;
    private double startVelocity;
    private double endVelocity;
    private static final double DEFAULT_START_PATH_VELOCITY = 0.0;
    private static final double DEFAULT_END_PATH_VELOCITY = 0.0;

    /**
     * Creates new path for motion profiling with default configuration. 
     * @param waypoints the path waypoints
     */
    public Path(Waypoint... waypoints) {
        this(false, DEFAULT_START_PATH_VELOCITY, DEFAULT_END_PATH_VELOCITY, waypoints);
    }

    /**
     * Creates new path for motion profiling with velocity default configuration.
     * @param reversed whether the path is reversed
     * @param waypoints the path waypoints 
     */
    public Path(boolean reversed, Waypoint... waypoints) {
        this(reversed, DEFAULT_START_PATH_VELOCITY, DEFAULT_END_PATH_VELOCITY, waypoints);
    }

    /**
     * Creates new path for motion profiling with start velocity default value of zero.
     * @param reversed whether the path is reversed
     * @param endVelocity the velocity at the end of the path
     * @param waypoints the path waypoints 
     */
    public Path(boolean reversed, double endVelocity, Waypoint... waypoints) {
        this(reversed, DEFAULT_START_PATH_VELOCITY, endVelocity, waypoints);
    }

    /**
     * Creates new path with reversed, start velocity & end velocity params. 
     * @param reversed whether the path is reversed
     * @param startVelocity the velocity at the start of the path 
     * @param endVelocity the velocity at the end of the path 
     * @param waypoints the path waypoints 
     */
    public Path(boolean reversed, double startVelocity, double endVelocity, Waypoint... waypoints) {
        this.reversed = reversed;
        this.startVelocity = startVelocity;
        this.endVelocity = endVelocity;
        
        config = new TrajectoryConfig(robotConstants.motionProfilingConstants.MAX_VELOCITY, robotConstants.motionProfilingConstants.MAX_ACCELERATION)
            .addConstraint(new CentripetalAccelerationConstraint(robotConstants.motionProfilingConstants.MAX_CENTRIPETAL_ACCELERATION))
            .setKinematics(Robot.drivetrain.getKinematics())
            .setReversed(reversed)
            .setStartVelocity(startVelocity)
            .setEndVelocity(endVelocity);

        trajectory = TrajectoryGenerator.generateTrajectory(Arrays.asList(waypoints), config); 
    }

    public Trajectory getTrajectory() {
        return trajectory;
    }

    public double getStartVelocity() {
        return startVelocity;
    }

    public double getEndVelocity() {
        return endVelocity;
    }

    public boolean isReversed() {
        return reversed;
    }

    /**
     * Return the time of the robot to drive the path.
     * @return path time in seconds 
     */
    public double getPathTime() {
        return trajectory.getTotalTimeSeconds();
    }
}
