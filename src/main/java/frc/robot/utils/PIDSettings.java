package frc.robot.utils;

/**
 * This class is used to store settings for different PIDs
 */
public class PIDSettings {

  private double KS;
  private double KP;
  private double KI;
  private double KD;
  private double KV;
  private double KA;
  private double tolerance;
  private double deltaTolerance;

  /**
   * @param KP        The the Proportional coefficient of the PID loop in this
   *                  command.
   * @param KI        The Integral coefficient of the PID loop in this command.
   * @param KD        The Differential coefficient of the PID loop in this
   *                  command.
   * @param tolerance The error tolerance of this command.
   * @param deltaTolerance  The time this PID loop will wait while within tolerance of
   *                  the setpoint before ending.
   */
  public PIDSettings(double KP, double KI, double KD, double tolerance, double deltaTolerance) {
    this.KP = KP;
    this.KI = KI;
    this.KD = KD;
    this.tolerance = tolerance;
    this.deltaTolerance = deltaTolerance;

  }

  public PIDSettings(double KP, double KV, double KA, double KS) {
    this.KP = KP;
    this.KV = KV;
    this.KA = KA;
    this.KS = KS;
  }

  public double getTolerance() {
    return tolerance;
  }

  public void setTolerance(double tolerance) {
    this.tolerance = tolerance;
  }

  public double getKP() {
    return KP;
  }

  public void setKP(double KP) {
    this.KP = KP;
  }

  public double getKI() {
    return KI;
  }

  public void setKI(double KI) {
    this.KI = KI;
  }

  public double getKD() {
    return KD;
  }

  public void setKD(double KD) {
    this.KD = KD;
  }

  public double getKV() {
    return KV;
  }

  public void setKV(double KV) {
    this.KV = KV;
  }

  public double getKA() {
    return KA;
  }

  public void setKA(double KA) {
    this.KA = KA;
  }

  public double getDeltaTolerance() {
    return deltaTolerance;
  }

  public void setDeltaTolerance(double deltaTolerance) {
    this.deltaTolerance = deltaTolerance;
  }

  public double getKS() {
    return KS;
  }

  public void setKS(double KS) {
    this.KS = KS;
  }
}