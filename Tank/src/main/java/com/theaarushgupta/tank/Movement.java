package com.theaarushgupta.tank;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Movement {
    private Hardware hardware;

    private static final double ticksPerInch = (((hardware.countsPerRotation * hardware.gearRatio) / (hardware.wheelDiameter * Math.PI)) / 12) * (10 / 3);

    public Movement(Hardware hardware) {
        this.hardware = hardware;
    }

    public void drive(double inches) {
        double seconds;
        if (inches <= 33) {
            seconds = 1.0;
        } else {
            seconds = inches / 33;
        }
        double ticks = inches * this.ticksPerInch;
        for (DcMotor motor : this.hardware.left) {
            motor.setVelocity((int)(ticks * -1));
        }
        for (DcMotor motor : this.hardware.right) {
            motor.setVelocity((int)(ticks * -1));
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < seconds)) {}
        for (DcMotor motor : this.hardware.left) {
            motor.setVelocity(0);
        }
        for (DcMotor motor : this.hardware.right) {
            motor.setVelocity(0);
        }
        sleep(this.wait);
    }

    public void turn(double degrees) {
        double ticks = ((this.hardware.botWidth * Math.PI) * (degrees / 360)) * this.ticksPerInch;
        if (degrees > 0) {
            robot.leftDrive.setVelocity((int)Math.abs(ticks));
            robot.rightDrive.setVelocity(-1 * (int)Math.abs(ticks));
        } else {
            robot.leftDrive.setVelocity(-1 * (int)Math.abs(ticks));
            robot.rightDrive.setVelocity((int)Math.abs(ticks));
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {}
        for (DcMotor motor : this.hardware.left) {
            motor.setVelocity(0);
        }
        for (DcMotor motor : this.hardware.right) {
            motor.setVelocity(0);
        }
        sleep(this.wait);
    }
}
