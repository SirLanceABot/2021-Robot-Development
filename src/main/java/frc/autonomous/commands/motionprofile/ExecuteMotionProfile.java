package frc.autonomous.commands.motionprofile;

import frc.autonomous.commands.motionprofile.*;
import frc.components.drivetrain.Drivetrain;
import edu.wpi.first.wpilibj.Notifier;
//import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExecuteMotionProfile implements Runnable 
{
	private Drivetrain drivetrain = Drivetrain.getInstance();
	private double kP = 0.025;
    private final double kAngle = 0.0125;
    private final double vMax = 1.008; // 2.88;
	
	private DriveTrainProfile profile;
	private int currentPoint = 0;
	private long lastTime;
	private Notifier notifier;
	private boolean isNotifierRunning = false;
	private long minElapsedTime = 10000;
	private long maxElapsedTime = 0;
	
    public ExecuteMotionProfile(DriveTrainProfile profile) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.drive);
    	this.profile = profile;
		notifier = new Notifier(this);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.resetEncoders();
    	lastTime = System.currentTimeMillis(); 
    	isNotifierRunning = true;
    	notifier.startPeriodic(0.01);
    }

    // Called repeatedly when this Command is scheduled to run
	protected void execute() 
	{
    }

    // Make this return true when this Command no longer needs to run execute()
	protected void stopNotifier() 
	{
    	isNotifierRunning = false;
    }
    
	protected boolean isNotifierRunning() 
	{
    	return isNotifierRunning;
    }
    
    // Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() 
	{
        return !isNotifierRunning();
    }

    // Called once after isFinished returns true
	protected void end()
	 {
    	System.out.println("Time took to execute profile: " + minElapsedTime + " | " + maxElapsedTime);
    	drivetrain.stop();
    	notifier.stop();
    	//Robot.drive.setLeftOutput(ControlMode.PercentOutput, 0);
    	//Robot.drive.setRightOutput(ControlMode.PercentOutput, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
	protected void interrupted() 
	{
    	end();
    }
	
	@Override
	public void run() 
	{
		System.out.println("Running the Motion Profile");
		if(currentPoint == profile.getLeftCheckpoints().size()){return;}
    	String command = profile.getCommand(currentPoint ).trim();
    	Checkpoint leftPoint = (Checkpoint) profile.getLeftCheckpoints().get(currentPoint);
    	Checkpoint rightPoint = (Checkpoint) profile.getRightCheckpoints().get(currentPoint);
    	double leftPos = leftPoint.position;
    	double leftVel = leftPoint.velocity;
    	double rightPos = rightPoint.position;
    	double rightVel = rightPoint.velocity;
    	double heading = rightPoint.angle;
    	
    	double currentLeftPos = drivetrain.getLeftPosition();
    	double currentRightPos = drivetrain.getRightPosition();
    	

    	// 	double temp = currentLeftPos;
    	// 	currentLeftPos = -currentRightPos;
    	// 	currentRightPos = -temp;

    	
    	double leftError = leftPos-currentLeftPos;   	
    	double rightError = rightPos-currentRightPos;
    	//double angleError = normalizeAngle(heading - Robot.drive.getHeading(), 180);
    	//double correction = kAngle * angleError;
		//if (profile.getLeftCheckpoints().size() - currentPoint < 20)
		//{ correction = 0;} 
		if (profile.getLeftCheckpoints().size() - currentPoint == 20) 
		{
			kP /= 2.0;
		}
    	//System.out.println(leftError + " " + rightError + " " + angleError);
    	
    	currentPoint++;
    	
    	double leftPower = leftVel / vMax + (kP * (leftError)); //- correction;
    	double rightPower = rightVel / vMax + (kP * (rightError)); //+ correction;
    	
    	leftPower = leftPower > 1 ? 1 : leftPower;
    	rightPower = rightPower > 1 ? 1 : rightPower;
    	
    	leftPower = leftPower < -1 ? -1 : leftPower;
    	rightPower = rightPower < -1 ? -1 : rightPower;
    	
		drivetrain.setLeftPower(leftPower);
		drivetrain.setRightPower(rightPower);
    	
    	long currentTime = System.currentTimeMillis();
    	long elapsedTime = currentTime - lastTime;
    	if(elapsedTime < minElapsedTime) {minElapsedTime = elapsedTime;}
    	if(elapsedTime > maxElapsedTime) {maxElapsedTime = elapsedTime;}
    	lastTime = currentTime;
		if(currentPoint == profile.getLeftCheckpoints().size()) 
		{
			drivetrain.stop();
    		stopNotifier();
    	}
		System.out.println(toString(leftPos, rightPos, leftPower, rightPower, heading));

	}

	public String toString(double leftPosition, double rightPosition, double leftPower, double rightPower, double heading)
	{
		String toString = String.format("","L Pos: ", leftPosition,"R Pos: ", rightPosition,"L Speed: ", leftPower, "R Speed: ",rightPower, "Heading", heading);
		return toString;
	}

	public double normalizeAngle(double angle, double cutPoint) 
	{
		while(angle > cutPoint) angle -= 360;
		while(angle < (cutPoint - 360)) angle += 360;
		return angle;
	}


	
}


//TODO: Test reimplement the correction in relation to heading once navX is up and running
