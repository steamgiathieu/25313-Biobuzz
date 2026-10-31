package org.firstinspires.ftc.team25313.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;
import com.pedropathing.tuning.autotune.TunerRegistrar;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import org.firstinspires.ftc.team25313.pedro.procedures.*;

public class Tuning {


    @OpModeRegistrar
    public static void registerProcedures(OpModeManager manager) {
        TunerRegistrar.deregisterAll();
        TunerRegistrar.register("Mecanum Tuner", mecanumTuner());
        TunerRegistrar.register("Pinpoint Tuner", pinpointTuner());
        TunerRegistrar.register("OTOS Tuner", otosTuner());
        TunerRegistrar.register("OctoQuad Tuner", octoQuadTuner());
        TunerRegistrar.register("Three Wheel Tuner", threeWheelTuner());
        TunerRegistrar.register("Three Wheel IMU Tuner", threeWheelImuTuner());
        TunerRegistrar.register("Two Wheel Tuner", twoWheelTuner());
        TunerRegistrar.register("Foresight Tuner", foresightTuner());
        TunerRegistrar.register("Tests", tests());
    }


    @Tuner
    public static Procedure mecanumTuner() {
        return new MecanumTuner();
    }


    @Tuner
    public static Procedure pinpointTuner() {
        return new PinpointTuner();
    }


    @Tuner
    public static Procedure otosTuner() {
        return new OTOSTuner();
    }


    @Tuner
    public static Procedure octoQuadTuner() {
        return new OctoQuadTuner();
    }


    @Tuner
    public static Procedure threeWheelTuner() {
        return new ThreeWheelTuner();
    }


    @Tuner
    public static Procedure threeWheelImuTuner() {
        return new ThreeWheelIMUTuner();
    }


    @Tuner
    public static Procedure twoWheelTuner() {
        return new TwoWheelTuner();
    }


    @Tuner
    public static Procedure foresightTuner() {
        return new ForesightTuner(
                hardwareMap -> new PinpointLocalizer(hardwareMap, Constants.localizerConfig),
                hardwareMap -> new Mecanum(hardwareMap, Constants.drivetrainConfig)
        );
    }


    @Tuner
    public static Procedure tests() {
        return new Tests(
                hardwareMap -> new Mecanum(hardwareMap, Constants.drivetrainConfig),
                hardwareMap -> new PinpointLocalizer(hardwareMap, Constants.localizerConfig),
                () -> new Foresight(Constants.foresightConfig)
        );
    }
}
