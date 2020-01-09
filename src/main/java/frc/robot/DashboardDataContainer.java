package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.enums.LEDColor;
import frc.robot.utils.DashboardController;

/**
 * DashboardDataContainer contains all the data to be viewed or put in the dashboard.  
 */
public class DashboardDataContainer {
    private DashboardController dashboardController;
    
    public DashboardDataContainer() {
        dashboardController = new DashboardController();
        SmartDashboard.putData("LED blink", new InstantCommand(() -> Robot.led.blinkColor(LEDColor.Black, 3)));
        
        // dashboardController.addBoolean(name, booleanSupplier);
        // dashboardController.addNumber(name, numberSupplier);
        // dashboardController.addString(name, stringSupplier);
        // SmartDashboard.putData(key, data);
        // etc.
    }

    public void update() {
        dashboardController.update();
    }
}
