package ua.goit.hw5.command.user;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.model.ApiResponse;
import ua.goit.hw5.services.UserService;
import ua.goit.hw5.view.View;

public class UserLogin implements Command {
    public static final String LOGIN_USER = "user -login";
    private final UserService userService;
    private final View view;

    public UserLogin(UserService userService, View view) {
        this.userService = userService;
        this.view = view;
    }


    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(LOGIN_USER);
    }

    @Override
    public void execute(String input) {
        view.write("Write username for login");
        String username = view.read();
        view.write("Write password");
        String password = view.read();
        ApiResponse response = userService.login(username, password);
        view.write(response.toString());
    }
}
