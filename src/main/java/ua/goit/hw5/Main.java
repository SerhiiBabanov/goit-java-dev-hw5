package ua.goit.hw5;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.command.Exit;
import ua.goit.hw5.command.Help;
import ua.goit.hw5.command.user.*;
import ua.goit.hw5.controller.ProjectManagementSystem;
import ua.goit.hw5.services.UserService;
import ua.goit.hw5.utils.HttpUtils;
import ua.goit.hw5.view.Console;
import ua.goit.hw5.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        View view = new Console(scanner);
        HttpUtils httpUtils = new HttpUtils("https://petstore.swagger.io/v2");
        UserService userService = new UserService(httpUtils);

        List<Command> commands = new ArrayList<>();
        commands.add(new Help(view));
        commands.add(new Exit(view));

        commands.add(new UserCreate(userService, view));
        commands.add(new UserCreateWithArray(userService, view));
        commands.add(new UserCreateWithList(userService, view));
        commands.add(new UserDeleteByUsername(userService, view));
        commands.add(new UserGetByUsername(userService, view));
        commands.add(new UserLogin(userService, view));
        commands.add(new UserLogout(userService, view));
        commands.add(new UserUpdateByUsername(userService, view));


        ProjectManagementSystem projectManagementSystem = new ProjectManagementSystem(view, commands);
        projectManagementSystem.run();
    }


}
