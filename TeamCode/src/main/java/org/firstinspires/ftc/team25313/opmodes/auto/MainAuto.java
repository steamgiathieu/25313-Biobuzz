package org.firstinspires.ftc.team25313.opmodes.auto;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.team25313.pedro.Constants;

import static com.pedropathing.api.Paths.line;

@Autonomous(name = "Main Auto", group = "Autonomous")
public class MainAuto extends LinearOpMode {
    private Follower follower;
    private int pathState = 0;

    private static final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose startPose = poseFactory.of(56, 7.5299, 90);
    private final Pose targetPose = poseFactory.of(57.7531, 106.1751, -91.0181);

    private Path path1;

    @Override
    public void runOpMode() throws InterruptedException {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);

        path1 = line(startPose, targetPose).reverseTangent();

        telemetry.addData("Status", "Pedro Pathing MainAuto Initialized");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        pathState = 0;

        while (opModeIsActive() && !isStopRequested()) {
            follower.update();


            switch (pathState) {
                case 0:
                    follower.follow(path1);
                    pathState = 1;
                    break;
                case 1:
                    if (!follower.isBusy()) {
                        pathState = -1;
                    }
                    break;
            }

            telemetry.addData("Path State", pathState);
            telemetry.addData("Is Busy", follower.isBusy());
            telemetry.addData("X", "%.2f", follower.pose().x());
            telemetry.addData("Y", "%.2f", follower.pose().y());
            telemetry.addData("Heading (Deg)", "%.2f°", Math.toDegrees(follower.pose().heading()));
            telemetry.update();
        }
    }
}
