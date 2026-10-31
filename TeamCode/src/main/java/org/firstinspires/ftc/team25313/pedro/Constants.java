package org.firstinspires.ftc.team25313.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Cấu hình hệ thống Pedro Pathing v3 cho Robot khung bánh Mecanum.
 */
public class Constants {



    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {

        c.frontLeftName.set(org.firstinspires.ftc.team25313.Constants.FRONT_LEFT_MOTOR);
        c.frontRightName.set(org.firstinspires.ftc.team25313.Constants.FRONT_RIGHT_MOTOR);
        c.backLeftName.set(org.firstinspires.ftc.team25313.Constants.BACK_LEFT_MOTOR);
        c.backRightName.set(org.firstinspires.ftc.team25313.Constants.BACK_RIGHT_MOTOR);


        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });


    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set(org.firstinspires.ftc.team25313.Constants.PINPOINT_NAME); // Tên thiết bị Pinpoint trong HardwareMap
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD); // goBILDA_4_BAR_POD hoặc goBILDA_SWINGARM_POD
        c.xPodOffset.set(-0.7410829649196835); // Offset con lăn dọc (Forward) so với tâm quay robot (inch)
        c.yPodOffset.set(3.1902036892147514); // Offset con lăn ngang (Strafe) so với tâm quay robot (inch)
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });


    public static ForesightConfig foresightConfig = new ForesightConfig(c -> {
        Controller primaryTranslationalForward = Controller.proportional(0.05);
        Controller secondaryTranslationalForward = Controller.proportional(0.01);
        Controller primaryTranslationalLateral = Controller.proportional(0.05);
        Controller secondaryTranslationalLateral = Controller.proportional(0.01);

        c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
        c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

        c.coast.set(Controller.proportionalFeedforward(0.02));
        c.brake.set(Controller.proportionalFeedforward(0.02));

        c.headingFeedback.set(Controller.proportional(0.4)); // Giảm hệ số P xoay góc từ 1.5 xuống 0.4 để tránh bị lắc (overshoot)
        c.headingBrakeCoefficients.set(Vector2D.cartesian(0.0, 0.0));

        c.linearBrakeCoefficients.set(Matrix.diag(0.0, 0.0));
        c.quadraticBrakeCoefficients.set(Matrix.diag(0.0, 0.0));

        c.maxAchievableForwardVelocity.set(60.0);
        c.maxAchievableStrafeVelocity.set(50.0);
        c.naturalForwardDeceleration.set(30.0);
        c.naturalStrafeDeceleration.set(30.0);
    });

    /**
     * Hàm tạo đối tượng Follower chính để điều khiển Robot di chuyển.
     * @param hardwareMap HardwareMap từ OpMode
     * @return Khởi tạo Follower hoàn chỉnh
     */
    public static Follower create(HardwareMap hardwareMap) {
        Mecanum drivetrain = new Mecanum(hardwareMap, drivetrainConfig);
        PinpointLocalizer localizer = new PinpointLocalizer(hardwareMap, localizerConfig);
        Foresight foresight = new Foresight(foresightConfig);

        return new Follower(localizer, drivetrain, foresight);
    }
}
