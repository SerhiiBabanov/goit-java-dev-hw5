package ua.goit.hw5.command.user;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.model.ApiResponse;
import ua.goit.hw5.services.UserService;
import ua.goit.hw5.view.View;

public class UserLogout implements Command {
    public static final String LOGOUT_USER = "user -logout";
    private final UserService userService;
    private final View view;

    public UserLogout(UserService userService, View view) {
        this.userService = userService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(LOGOUT_USER);
    }

    @Override
    public void execute(String input) {
        ApiResponse response = userService.logout();
        view.write(response.toString());
    }
}
