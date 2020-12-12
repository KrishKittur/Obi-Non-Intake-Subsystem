package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    private final DoubleSolenoid smallSolenoid = new DoubleSolenoid(2, 7, 9);
    private final DoubleSolenoid largeSolenoid = new DoubleSolenoid(6, 7, 8);
    private final CANSparkMax intakeMotor = new CANSparkMax(13, CANSparkMaxLowLevel.MotorType.kBrushless);

    public IntakeSubsystem() {
        intakeMotor.setSmartCurrentLimit(20);
        intakeMotor.setSecondaryCurrentLimit(35);
    }

    public void startIntake(double speed) {
        intakeMotor.set(speed);
    }

    public void endIntake() {
        intakeMotor.set(0);
    }

    public void extendSmallSolenoid() {
        smallSolenoid.set(Value.kForward);
    }

    public void retractSmallSolenoid() {
        smallSolenoid.set(Value.kReverse);
    }

    public void extendLargeSolenoid() {
        largeSolenoid.set(Value.kForward);
    }

    public void retractLargeSolenoid() {
        largeSolenoid.set(Value.kReverse);
    }
}
