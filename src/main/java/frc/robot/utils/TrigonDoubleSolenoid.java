package frc.robot.utils;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * this class allowes the user to control a double solenoid uses boolean values
 */
public class TrigonDoubleSolenoid extends DoubleSolenoid {

    public TrigonDoubleSolenoid(int forwardChannel, int reverseChannel) {
        super(forwardChannel, reverseChannel);
    }

    /**
     * @param state true kForward false kReverse
     */
    public void feedForward(boolean state) {
        set(state ? Value.kForward : Value.kReverse);
    }

    public void stop() {
        set(Value.kOff);
    }
}
