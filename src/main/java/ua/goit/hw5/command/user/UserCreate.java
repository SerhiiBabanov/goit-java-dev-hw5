package ua.goit.hw5.command.user;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.model.ApiResponse;
import ua.goit.hw5.model.User;
import ua.goit.hw5.services.UserService;
import ua.goit.hw5.view.View;

public class UserCreate implements Command {
    public static final String CREATE_USER_COMMAND = "user -create";
    private final UserService userService;
    private final View view;

    public UserCreate(UserService userService, View view) {
        this.userService = userService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(CREATE_USER_COMMAND);
    }

    @Override
    public void execute(String input) {
        User user = userService.getUser(view);
        ApiResponse response = userService.create(user);
        view.write(response.toString());
    }
}
