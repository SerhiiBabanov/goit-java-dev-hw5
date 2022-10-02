package ua.goit.hw5.command;

import ua.goit.hw5.view.View;

public class Help implements Command {
    private static final String HELP = "help";
    private final View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equals(HELP);
    }

    @Override
    public void execute(String input) {
        view.write(String.format("Enter %s to see all command", Help.HELP));
        view.write(String.format("Enter %s to exit program", Exit.EXIT));
    }
}
