/**
 * @Author: Georgia Petroff (Editied by Dylan Castle)
 * @Project: Box Bob Code
 * @Start: 11/XX/20
 * @Last: 11/XX/20
 */

package org.firstinspires.ftc.teamcode.Bob;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@SuppressWarnings({"unused"})
public class Driver_Controlled_2Drivers_With_Arm extends OpMode {

    private DcMotor driveRF;//drive wheel located RIGHT FRONT
    private DcMotor driveRB;//drive wheel located RIGHT BACK
    private DcMotor driveLF;//drive wheel located LEFT FRONT
    private DcMotor driveLB;//drive wheel located LEFT BACK
    private DcMotor Shooter;
    private DcMotor mainTreads;
    private DcMotor backTreads;
    private Servo bandHolder;
    private CRServo extendContinuous;
    private Servo rotateArm;
    private Servo clampArm;

    @Override
    public void init()
    {

        driveRF = hardwareMap.get(DcMotor.class, "driveRF");
        driveRB = hardwareMap.get(DcMotor.class, "driveRB");
        driveLF = hardwareMap.get(DcMotor.class, "driveLF");
        driveLB = hardwareMap.get(DcMotor.class, "driveLB");
        Shooter = hardwareMap.get(DcMotor.class, "Shooter");
        mainTreads = hardwareMap.get(DcMotor.class, "mainTreads");
        backTreads = hardwareMap.get(DcMotor.class, "backTreads");
        bandHolder = hardwareMap.get(Servo.class, "bandHolder");
        extendContinuous = hardwareMap.get(CRServo.class, "extendContinuous");
        rotateArm = hardwareMap.get(Servo.class, "rotateArm");
        clampArm = hardwareMap.get(Servo.class, "clampArm");

        //clockwise
        driveRF.setDirection(DcMotor.Direction.FORWARD);
        driveRB.setDirection(DcMotor.Direction.FORWARD);
        //counter-clockwise
        driveLF.setDirection(DcMotor.Direction.REVERSE);
        driveLB.setDirection(DcMotor.Direction.REVERSE);


        driveRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        driveRF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }


    @Override

    public void loop()
    {
        double xPow,yPow,rxPow,sSpeed, p2x1, p2y1, p2y2, p2x2;

        xPow = -gamepad1.left_stick_x;
        yPow = -gamepad1.left_stick_y;
        rxPow = gamepad1.right_stick_x;

        p2x1 = gamepad2.left_stick_x;
        p2y1 = gamepad2.left_stick_y;

        p2x2 = gamepad2.right_stick_x;
        p2y2 = gamepad2.right_stick_y;

        //-----------------Conveyor belt On/Off-----------------
        if (gamepad2.right_bumper){
            mainTreads.setPower(1);
            backTreads.setPower(-1);
            if (gamepad2.x && !gamepad2.left_bumper){
                mainTreads.setPower(-1);
                backTreads.setPower(1);
            }
        }
        if (!gamepad2.right_bumper){
            mainTreads.setPower(0);
            backTreads.setPower(0);
        }

        //-----------------Shooter-----------------
        if (!gamepad1.left_bumper){
            sSpeed = 0;
            Shooter.setPower(sSpeed);
        }
        if (gamepad1.left_bumper){
            sSpeed = 0;
            if (gamepad1.a){
                sSpeed = 0.4;
            }
            if (gamepad1.b){
                sSpeed = 0.45;
            }
            if (gamepad1.x){
                sSpeed = 0.3;
            }
            if (gamepad1.y){
                sSpeed = 0.19;
            }
            Shooter.setPower(sSpeed);
        }
        //-----------------Band Holder-----------------
        if (gamepad2.a && !gamepad2.left_bumper && !gamepad2.right_bumper){
            bandHolder.setPosition(0.4);
        }
        if (gamepad2.b && !gamepad2.left_bumper && !gamepad2.right_bumper){
            bandHolder.setPosition(-0.3);
        }
        //-----------------Wobble Goal Arm-----------------

        //Extend/Retract and rotating

        extendContinuous.setPower(p2y1);
        //rotateArm.setPower(0);
        //if(p2y2 > 0){
        //   rotateArm.setPosition(1);
        //}
        //else if(p2y2 <= 0){
        //    rotateArm.setPosition(0.25);
        //}
        //if(p2y2 - 0.25 < 1) {
        rotateArm.setPosition(-p2y2);
        //}
        //Clamp
        if (gamepad2.a){
            clampArm.setPosition(0.4);
        }
        if (gamepad2.b) {
            clampArm.setPosition(0);
        }


        //-----------------Wheels-----------------
        driveRF.setPower(yPow + xPow - rxPow);
        driveRB.setPower(yPow - xPow - rxPow);
        driveLF.setPower(yPow - xPow + rxPow);
        driveLB.setPower(yPow + xPow + rxPow);

        telemetry.addData("Status", "Running");
        telemetry.addData("ENCDR-RF", driveRF.getCurrentPosition());
        telemetry.addData("ENCDR-RB", driveRB.getCurrentPosition());
        telemetry.addData("ENCDR-LF", driveLF.getCurrentPosition());
        telemetry.addData("ENCDR-LB", driveLB.getCurrentPosition());
        telemetry.update();
    }
}