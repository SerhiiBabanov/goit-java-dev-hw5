package ua.goit.hw5.command.user;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.model.ApiResponse;
import ua.goit.hw5.model.User;
import ua.goit.hw5.services.UserService;
import ua.goit.hw5.view.View;

public class UserUpdateByUsername implements Command {
    public static final String UPDATE_USER_BY_USERNAME = "user -updateByUsername";
    private final UserService userService;
    private final View view;

    public UserUpdateByUsername(UserService userService, View view) {
        this.userService = userService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(UPDATE_USER_BY_USERNAME);
    }

    @Override
    public void execute(String input) {
        view.write("Write username");
        String username = view.read();
        User user = userService.getUser(view);
        ApiResponse response = userService.update(username, user);
        view.write(response.toString());
    }
}
