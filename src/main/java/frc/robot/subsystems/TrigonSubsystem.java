package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * This is interface for all of our moving subsystems.
 * For example every moving SS will have to move, stop etc.
 * It is also used for dependency injection with commands that works the same way,
 * for multiple subsystems.           
 */
public interface TrigonSubsystem extends Subsystem {
    public void move(double power);
    public void moveWithVision(double power);

    public void moveWithXbox(double y);
    public void moveWithXbox(double x, double y);

    public void stop();
}
