package frc.sensors;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Wrapper class for the AHRS, not extending because then super() must be called 
 * first which doesn't allow for the try, catch to make sure that the navX can be detected. 
 * @author Maxwell Li
 */
public class NavX// extends AHRS
{
    private static final String className = new String("[NavX]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static AHRS ahrs;
    private static double startingAngle; //this will be based on which direction that the robot is facing
    private static double angleOfPowerPortWall = 0.00; //this will also be determined on which direction that the robot is facing
    private static NavX instance = new NavX();

    protected NavX()
    {
        System.out.println(className + " : Constructor Started");

        // Communicate w navX-MXP via the MXP SPI Bus
        // Alternates: I2C.Port.MXP, SerialPort.Port,.kMXP or SerialPort.Port.kUSB
        try{
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex )
        {
           DriverStation.reportError("Error instantiating the Navx: " + ex.getMessage(), true);
        }
        
        System.out.println(className + ": Constructor Finished");
    }

    public static NavX getInstance()
    {
        return instance;
    }

    public boolean isConnected()
    {
        return ahrs.isConnected();
    }

    public boolean isCalibrating()
    {
        return ahrs.isCalibrating();
    }

    public boolean isMooving()
    {
        return ahrs.isMoving();
    }

    public boolean isRotating()
    {
        return ahrs.isRotating();
    }

    public double getYaw()
    {
        return ahrs.getYaw();
    }

    public double getPitch()
    {
        return ahrs.getPitch();
    }

    public double getRoll()
    {
        return ahrs.getRoll();
    }

    public double getAngle()
    {
        return ahrs.getAngle();
    }

    public double getRate()
    {
        return ahrs.getRate();
    }

    public double getAngleOfPowerPortWall()
    {
        return angleOfPowerPortWall;
    }

    public Rotation2d getRotation2d()
    {
        return ahrs.getRotation2d();
    }

    public void reset()
    {
        ahrs.reset();
    }

}