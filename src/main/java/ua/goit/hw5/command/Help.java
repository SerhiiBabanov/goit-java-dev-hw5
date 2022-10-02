package ua.goit.hw5.command;

import ua.goit.hw5.command.user.*;
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
        view.write(String.format("Enter %s to create user", UserCreate.CREATE_USER_COMMAND));
        view.write(String.format("Enter %s to create users with array", UserCreateWithArray.CREATE_USERS_COMMAND));
        view.write(String.format("Enter %s to create users with list", UserCreateWithList.CREATE_USERS_COMMAND));
        view.write(String.format("Enter %s to delete user by username", UserDeleteByUsername.DELETE_USERS_COMMAND));
        view.write(String.format("Enter %s to get user by username", UserGetByUsername.GET_USER_BY_USERNAME));
        view.write(String.format("Enter %s to login user", UserLogin.LOGIN_USER));
        view.write(String.format("Enter %s to logout user", UserLogout.LOGOUT_USER));
        view.write(String.format("Enter %s to update user by username", UserUpdateByUsername.UPDATE_USER_BY_USERNAME));
        view.write(String.format("Enter %s to exit program", Exit.EXIT));
    }
}
