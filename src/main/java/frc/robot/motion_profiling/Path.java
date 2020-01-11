package frc.robot.motion_profiling;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import frc.robot.Robot;

import static frc.robot.Robot.robotConstants;

/**
 * Class for creating a new path for motion profiling path following.  
 */
public class Path {
    private Trajectory trajectory;
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
        List<Pose2d> poses = Arrays.asList(waypoints);
        String fileName = Filesystem.getOperatingDirectory() +
                "/cachedPaths/" + generateHash(poses.toString());
        File pathFile = new File(fileName);
        if(pathFile.exists() && !pathFile.isDirectory()){
            try {
                trajectory = loadTrajectoryFromFile(fileName);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        TrajectoryConfig config = new TrajectoryConfig(robotConstants.motionProfilingConstants.MAX_VELOCITY, robotConstants.motionProfilingConstants.MAX_ACCELERATION)
                .addConstraint(new CentripetalAccelerationConstraint(robotConstants.motionProfilingConstants.MAX_CENTRIPETAL_ACCELERATION))
                .setKinematics(Robot.drivetrain.getKinematics())
                .setReversed(reversed)
                .setStartVelocity(startVelocity)
                .setEndVelocity(endVelocity);

        trajectory = TrajectoryGenerator.generateTrajectory(poses, config);
        try {
            TrajectoryUtil.toPathweaverJson(trajectory, Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param pathName the name of the path to load from the filesystem.
     */
    public Path(String pathName) {
            String pathToTrajectory = Filesystem.getDeployDirectory() +
                    "/paths/" + pathName;
        try {
            trajectory = loadTrajectoryFromFile(pathToTrajectory);
        } catch (IOException e) {
            System.err.println("could not load path from: " + pathToTrajectory
                    + " initializing with empty path instead of loading");
            trajectory =  new Trajectory(Arrays.asList(new Trajectory.State()));
        }
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

    private Trajectory loadTrajectoryFromFile(String pathToTrajectory) throws IOException {
        var path = Paths.get(pathToTrajectory);
        return TrajectoryUtil.fromPathweaverJson(path);
    }
    /**
     * Takes a string and returns its hash.
     * This function was stolen from bumbleB 3339
     * @param toHash a string to be hashed using
     * @return hashed string by protocol SHA-256
     */
    private static String generateHash(String toHash){
            String hash = "";
            try {
                byte[] encodedHash = MessageDigest.getInstance("SHA-256").digest(toHash.getBytes());
                hash = bytesToHexString(encodedHash);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return hash;
    }

    //taken from bumbleB
    private static String bytesToHexString(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
