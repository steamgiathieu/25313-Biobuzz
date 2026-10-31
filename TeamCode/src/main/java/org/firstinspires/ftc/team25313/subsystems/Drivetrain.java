package org.firstinspires.ftc.team25313.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.team25313.pedro.Constants;

/**
 * Subsystem Drivetrain điều khiển hệ khung bánh Mecanum thông qua Pedro Pathing Follower.
 */
public class Drivetrain {
    private final Follower follower;

    public Drivetrain(HardwareMap hardwareMap) {
        this.follower = Constants.create(hardwareMap);
    }

    /**
     * Khai báo Pose ban đầu của Robot.
     */
    public void setPose(Pose startPose) {
        follower.setPose(startPose);
    }

    /**
     * Lấy vị trí (Pose) hiện tại của Robot trên sân (X, Y, Heading).
     */
    public Pose getPose() {
        return follower.pose();
    }

    /**
     * Cập nhật vòng lặp của Pedro Pathing (Cần gọi liên tục trong khi OpMode active).
     */
    public void update() {
        follower.update();
    }

    /**
     * Bắt đầu chế độ TeleOp (Chuyển sang điều khiển thủ công bằng tay).
     */
    public void startTeleOp() {
        follower.manual(0, 0, 0);
    }

    /**
     * Điều khiển Robot bằng tín hiệu joystick (TeleOp Drive).
     * @param forward Tiến / Lùi (-1.0 -> 1.0)
     * @param strafe Sang trái / Sang phải (-1.0 -> 1.0)
     * @param turn Quay trái / Quay phải (-1.0 -> 1.0)
     * @param robotCentric true cho robot-centric, false cho field-centric
     */
    public void drive(double forward, double strafe, double turn, boolean robotCentric) {
        if (robotCentric) {
            follower.manual(forward, strafe, turn);
        } else {
            double heading = follower.pose().heading();
            double rotatedForward = forward * Math.cos(-heading) - strafe * Math.sin(-heading);
            double rotatedStrafe = forward * Math.sin(-heading) + strafe * Math.cos(-heading);
            follower.manual(rotatedForward, rotatedStrafe, turn);
        }
    }

    /**
     * Đặt lại góc Heading hiện tại về góc mong muốn (thường dùng khi reset góc Field-Centric).
     */
    public void resetHeading(double headingRadians) {
        Pose currentPose = follower.pose();
        follower.setPose(new Pose(currentPose.x(), currentPose.y(), headingRadians));
    }

    /**
     * Cho Robot di chuyển theo một đường (Path).
     */
    public void follow(Path path) {
        follower.follow(path);
    }

    /**
     * Giữ vị trí tại Pose chỉ định.
     */
    public void hold(Pose pose) {
        follower.hold(pose);
    }

    /**
     * Kiểm tra xem Robot có đang di chuyển theo Path hay không.
     */
    public boolean isBusy() {
        return follower.isBusy();
    }

    /**
     * Trả về đối tượng Follower trực tiếp nếu cần tùy chỉnh nâng cao.
     */
    public Follower getFollower() {
        return follower;
    }
}
