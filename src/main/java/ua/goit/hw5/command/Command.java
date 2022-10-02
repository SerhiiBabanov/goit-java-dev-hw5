package ua.goit.hw5.command;

public interface Command {
    boolean canExecute(String input);

    void execute(String input);
}
