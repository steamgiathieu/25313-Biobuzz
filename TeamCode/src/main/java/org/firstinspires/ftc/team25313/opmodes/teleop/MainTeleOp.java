package org.firstinspires.ftc.team25313.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.team25313.Constants;

/**
 * Main TeleOp điều khiển Robot-Centric trực tiếp cho 4 bánh Mecanum.
 */
@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {

    private DcMotorEx leftFront;
    private DcMotorEx rightFront;
    private DcMotorEx leftBack;
    private DcMotorEx rightBack;

    @Override
    public void runOpMode() throws InterruptedException {

        leftFront = hardwareMap.get(DcMotorEx.class, Constants.FRONT_LEFT_MOTOR);
        rightFront = hardwareMap.get(DcMotorEx.class, Constants.FRONT_RIGHT_MOTOR);
        leftBack = hardwareMap.get(DcMotorEx.class, Constants.BACK_LEFT_MOTOR);
        rightBack = hardwareMap.get(DcMotorEx.class, Constants.BACK_RIGHT_MOTOR);


        leftFront.setDirection(DcMotorEx.Direction.REVERSE);
        leftBack.setDirection(DcMotorEx.Direction.REVERSE);
        rightFront.setDirection(DcMotorEx.Direction.FORWARD);
        rightBack.setDirection(DcMotorEx.Direction.FORWARD);

        // Đặt phanh hãm (BRAKE) khi nhả cần điều khiển
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Main TeleOp (Robot-Centric) Initialized");
        telemetry.update();

        waitForStart();
//hold nút R1 để chạy chậm
        while (opModeIsActive() && !isStopRequested()) {
            double speedMultiplier = gamepad1.right_bumper ? Constants.SLOW_MODE_MULTIPLIER : Constants.DEFAULT_SPEED_MULTIPLIER;


            double y = -gamepad1.left_stick_y * speedMultiplier; // Tiến / Lùi
            double x = gamepad1.left_stick_x * speedMultiplier;   // Strafe sang phải / trái
            double rx = -gamepad1.right_stick_x * speedMultiplier; // Quay trái / phải


            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;


            leftFront.setPower(frontLeftPower);
            leftBack.setPower(backLeftPower);
            rightFront.setPower(frontRightPower);
            rightBack.setPower(backRightPower);


            telemetry.addData("Drive Mode", "Robot-Centric");
            telemetry.addData("Speed Multiplier", "%.0f%%", speedMultiplier * 100);
            telemetry.update();
        }
    }
}
