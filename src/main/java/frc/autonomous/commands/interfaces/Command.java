package frc.autonomous.commands.interfaces;

public interface Command
{
    void init();
    void execute();
    boolean isFinished();
    void end();
}