package frc.robot;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import frc.shuffleboard.SkillsCompetitionTab.AutoNavPath;

import frc.components.drivetrain.Drivetrain;

public class TrajectoryLoader
{
    private static final String fullClassName = MethodHandles.lookup().lookupClass().getCanonicalName();
    
    //public enum PathOption {BarrelRacing, Bounce, Slalom, Manual};
    
    private static final Drivetrain drivetrain = Drivetrain.getInstance();
    private static final ArrayList<Trajectory> trajectory = new ArrayList<Trajectory>();
    private static final DifferentialDriveVoltageConstraint voltageConstraint = 
            new DifferentialDriveVoltageConstraint(drivetrain.getMotorFeedforward(), drivetrain.getKinematics(), 6.0);
    private static final TrajectoryConfig trajectoryConfig =
            new TrajectoryConfig(1.0, 1.0).setKinematics(drivetrain.getKinematics()).addConstraint(voltageConstraint);

    private static TrajectoryLoader instance = new TrajectoryLoader();

    // *** STATIC INITIALIZATION BLOCK ****************************************
    // This block of code is run first when the class is loaded
    static
    {
        System.out.println("Class Loading: " + fullClassName);
    }


    private TrajectoryLoader()
    {
        System.out.println("Constructor Starting: " + fullClassName);
        // Add your constructor code here

        System.out.println("Constructor Finishing: " + fullClassName);
    }

    /**
     * The method to retrieve the instance of TrajectoryLoader.
     * @return instance 
     */
    public static TrajectoryLoader getInstance()
    {
        return instance;
    }

    // Accessor for trajectory
    public ArrayList<Trajectory> getTrajectory()
    {
        return trajectory;
    }

    public void createTrajectoryFromPath(AutoNavPath pathOption)
    {
        switch(pathOption)
        {
            case kNone:
                createTrajectoryFromPoints();
                break;
            case kBarrelRacing:
            case kSlalom:
            case kBounce:
                System.out.println("Path = " + pathOption);
                // createTrajectoryFromFile(pathOption);
                break;
            default:
                break;
        }
    }

    private void createTrajectoryFromPoints()
    {
        trajectory.clear();
        trajectory.add(
            TrajectoryGenerator.generateTrajectory(
                new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
                List.of(new Translation2d(1, 0), new Translation2d(2, 0)),
                new Pose2d(3, 0, Rotation2d.fromDegrees(0)),
                trajectoryConfig
            )
        );
    }

    private void createTrajectoryFromFile(AutoNavPath pathOption)
    {
        int pathNumber = 1;
        boolean done = false;
        String trajectoryJSON;
        Path trajectoryPath;

        trajectory.clear();

        while(!done)
        {
            trajectoryJSON = "output/" + pathOption.name() + pathNumber + ".wpilib.json";
            try
            {
                trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
                // trajectory[i] = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
                if(trajectoryPath.toFile().exists())
                {
                    trajectory.add(TrajectoryUtil.fromPathweaverJson(trajectoryPath));
                    pathNumber++; 
                }
                else
                {
                    done = true;
                }
            }
            catch (IOException ex)
            {
                DriverStation.reportError("Unable to open trajectory : " + trajectoryJSON, ex.getStackTrace());
            }
        }
    }
}
