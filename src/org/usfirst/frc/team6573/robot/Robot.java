package org.usfirst.frc.team6573.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.AnalogGyro;

import edu.wpi.first.wpilibj.CameraServer;

// NOTE: All lines beginning with two forward slashes are 
// comments any may be removed from the code without affecting
// how to robot operates. Feel free to include your own notes
// for anything that the next person should be aware of.

// CHECKING FOR ERRORS
// Look for underlined items and red areas along the right-hand
// scroll bar. These are errors.

// SENDING CODE.
// 1. Click the green play button in the tool bar above.
// 2. Select WPILib Java Deply
// 3. Wait while laptop sends code
// 4. "Build Successful" indicates code was sent.

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String startRight = "right";
	final String startCenter = "center";
	final String startLeft = "left";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	double k = 0.08;

	// Declaring

	// JOYSTICK
	Joystick myStick;
	Joystick xbox;

	// MOTOR
	Spark leftMotor;
	Spark rightMotor;
	Spark climbingMotor;
	Spark leftClaw;
	Spark rightClaw;
	Spark hookMotor;
	Spark leftArm;
	Spark rightArm;

	double magnitude;
	double curve;
	Timer autoTimer;

	// Other motor controllers for lifts, limit switches,
	// servos, and other stuff goes here. Each line
	// starts with the type of motor controller followed
	// by the name you wish to use to identify it. This
	// is referred to as DECLARING. Actually connecting
	// to a port is done below under DEFINING.

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {

		// Defining

		// The lines below allow communication between the
		// SmartDashboard and the roboRio. This allows you
		// to have a tappable button on your driver station
		// that can change robot behavior. This is not
		// required to make your robot function. We have
		// not used this feature in our robot yet.
		chooser.addDefault("Center Start", startCenter);
		chooser.addObject("Left Start", startLeft);
		chooser.addObject("Right Start", startRight);
		SmartDashboard.putData("Auton Starting Position", chooser);

		// If the robot is not responding to the joystick then
		// check or change the USB port in the driver station.
		myStick = new Joystick(0);
		xbox = new Joystick(1);

		// These numbers represent the PWM ports on the roboRio
		// that the motor controllers are plugged. For example,
		// the leftMotor is plugged into PWM port 0.
		myStick = new Joystick(0);
		xbox = new Joystick(1);

		// Motors
		leftArm = new Spark(0);
		leftMotor = new Spark(1);
		rightMotor = new Spark(2);
		rightArm = new Spark(3);
		hookMotor = new Spark(4);
		climbingMotor = new Spark(7);
		rightClaw = new Spark(8);
		leftClaw = new Spark(9);

		autoTimer = new Timer();

		// Camera
		CameraServer.getInstance().startAutomaticCapture();

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString line to get the
	 * auto na me from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the SendableChooser
	 * make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);

		// rightFront.setInverted( true );
		// rightRear.setInverted( true );

		autoTimer = new Timer();
		autoTimer.reset();
		autoTimer.start();
	}

	// AUTONOMOUS CODE goes here. Some sample code is shown below.
	// The robot will drive forward at half speed for 3 seconds and
	// then stop.

	/**
	 * This function is called periodically during autonomous
	 */

	@Override
	public void autonomousPeriodic() {
		String message = DriverStation.getInstance().getGameSpecificMessage();
		if (autoSelected.equals(startCenter)) {
			if (message.charAt(0) == 'L') {
				Center_Start_Left_Switch();
			} else {
				Center_Start_Right_Switch();
			}
		}
	}

	private void Center_Start_Right_Switch() {
		// Right Auto Code

		if (autoTimer.get() < 1) {
			leftMotor.set(-0.4);
			rightMotor.set(.35);
		} else if (autoTimer.get() < 2) {
			leftMotor.set(-.27);
			rightMotor.set(-.27);
		} else if (autoTimer.get() < 3.5) {
			leftMotor.set(-0.4);
			rightMotor.set(.35);
		} else if (autoTimer.get() < 4.4) {
			leftMotor.set(.32);
			rightMotor.set(.32);
		} else if (autoTimer.get() < 5.2) {
			rightMotor.set(0);
			leftMotor.set(0);
			leftArm.set(.5);
			rightArm.set(-.5);
		} else if (autoTimer.get() < 6) {
			leftClaw.set(1);
			rightClaw.set(-1);
		} else if (autoTimer.get() < 6.7) {
			leftArm.set(-.1);
			rightArm.set(.1);
			leftClaw.set(0);
			rightClaw.set(0);
		} else if (autoTimer.get() < 8) {
			rightMotor.set(0);
			leftMotor.set(0);
		} else if (autoTimer.get() < 8.5) {
			rightMotor.set(.3);
			leftMotor.set(-.3);
		} else if (autoTimer.get() < 9) {
			rightMotor.set(0);
			leftMotor.set(0);
		}
	}

	private void Center_Start_Left_Switch() {
		// Left Auto Code

		if (autoTimer.get() < 1) {
			leftMotor.set(-0.4);
			rightMotor.set(.35);
		} else if (autoTimer.get() < 2) {
			leftMotor.set(.27);
			rightMotor.set(.27);
		} else if (autoTimer.get() < 3.5) {
			leftMotor.set(-0.4);
			rightMotor.set(.35);
		} else if (autoTimer.get() < 4.4) {
			leftMotor.set(-.32);
			rightMotor.set(-.32);
		} else if (autoTimer.get() < 5.2) {
			rightMotor.set(0);
			leftMotor.set(0);
			leftArm.set(.5);
			rightArm.set(-.5);
		} else if (autoTimer.get() < 6) {
			leftClaw.set(1);
			rightClaw.set(-1);
		} else if (autoTimer.get() < 6.7) {
			leftArm.set(-.1);
			rightArm.set(.1);
			leftClaw.set(0);
			rightClaw.set(0);
		} else if (autoTimer.get() < 8) {
			rightMotor.set(0);
			leftMotor.set(0);
		} else if (autoTimer.get() < 8.5) {
			rightMotor.set(.3);
			leftMotor.set(-.3);
		} else if (autoTimer.get() < 9) {
			rightMotor.set(0);
			leftMotor.set(0);
		}
	}

	/**
	 * This function is called once when the robot enters operator control.
	 */

	/*
	 * /** This function is called periodically during autonomous
	 * 
	 * 
	 * 
	 * /* This function is called once when the robot enters operator control.
	 */
	@Override
	public void teleopInit() {

		// Uncomment the bottom line if the robot does not seem
		// to be responding.
		autoTimer.stop();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		// Read the axes on the joystick
		magnitude = xbox.getY() * .750;
		curve = xbox.getX() * -.750;

		// This is what actually makes the robot respond by
		// feeding the joystick status to the robot.
		leftMotor.set(1 * magnitude + curve);

		rightMotor.set(-1 * magnitude + curve);

		// testForClaw
		if (xbox.getRawButton(1)) {
			rightClaw.set(.75);
		} else {
			rightClaw.set(0);
		}
		if (xbox.getRawButton(2)) {
			rightClaw.set(-1);
		}

		// Left Claw
		if (xbox.getRawButton(1)) {
			leftClaw.set(-.75);
		} else {
			leftClaw.set(0);
		}
		if (xbox.getRawButton(2)) {
			leftClaw.set(1);
		}

		// Climbing Motor
		if (xbox.getRawButton(9)) {
			climbingMotor.set(1.00);
		} else {
			climbingMotor.set(0);
		}
		if (xbox.getRawButton(10)) {
			climbingMotor.set(-1.00);
		}

		// Hook Motor
		if (xbox.getRawButton(5)) {
			hookMotor.set(.15);
		} else {
			hookMotor.set(0);
		}

		if (xbox.getRawButton(6)) {
			hookMotor.set(-.4);
		}

		// Arm
		// Button 3 Lowers
		// Button 4 Raises
		// Right Arm
		if (xbox.getRawButton(3)) {
			rightArm.set(.15);
		} else {
			rightArm.set(0);
		}

		if (xbox.getRawButton(4)) {
			rightArm.set(-.50);
		}
		// Left Arm
		if (xbox.getRawButton(3)) {
			leftArm.set(-.15);
		} else {
			leftArm.set(0);
		}
		if (xbox.getRawButton(4)) {
			leftArm.set(.50);
		}
		// This delay is here since the motor controllers
		// update every 20ms. No point in trying to update
		// more often.
		Timer.delay(0.020);

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
