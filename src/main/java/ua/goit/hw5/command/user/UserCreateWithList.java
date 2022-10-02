package ua.goit.hw5.command.user;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.model.ApiResponse;
import ua.goit.hw5.model.User;
import ua.goit.hw5.services.UserService;
import ua.goit.hw5.view.View;

import java.util.ArrayList;
import java.util.List;

public class UserCreateWithList implements Command {
    public static final String CREATE_USERS_COMMAND = "user -createWithList";
    private final UserService userService;
    private final View view;

    public UserCreateWithList(UserService userService, View view) {
        this.userService = userService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(CREATE_USERS_COMMAND);
    }

    @Override
    public void execute(String input) {
        view.write("How many user will be create?");
        int count = Integer.parseInt(view.read());
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(userService.getUser(view));
        }
        ApiResponse response = userService.createWithList(users);
        view.write(response.toString());
    }
}
