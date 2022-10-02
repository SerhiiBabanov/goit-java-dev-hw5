package ua.goit.hw5;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.command.Exit;
import ua.goit.hw5.command.Help;
import ua.goit.hw5.controller.ProjectManagementSystem;
import ua.goit.hw5.view.Console;
import ua.goit.hw5.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        View view = new Console(scanner);

        List<Command> commands = new ArrayList<>();
        commands.add(new Help(view));
        commands.add(new Exit(view));

        ProjectManagementSystem projectManagementSystem = new ProjectManagementSystem(view, commands);
        projectManagementSystem.run();
    }
}
