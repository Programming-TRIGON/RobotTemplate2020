package frc.robot.utils;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * this class allowes the user to control a double solenoid useing boolean
 * values
 */
public class TrigonDoubleSolenoid extends DoubleSolenoid {
    public TrigonDoubleSolenoid(final int forwardChannel, final int reverseChannel) {
        super(forwardChannel, reverseChannel);
    }

    /**
     * @param state pushes the solenoid forward if true else it pulls it
     */
    public void push(boolean state) {
        set(state ? Value.kForward : Value.kReverse);
    }

    public boolean isOn() {
        return get() != Value.kOff;
    }

    public boolean isPushed() {
        return get() == Value.kForward;
    }

    public void off() {
        set(Value.kOff);
    }
}
