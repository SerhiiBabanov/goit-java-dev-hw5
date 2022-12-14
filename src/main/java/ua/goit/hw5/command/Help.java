package ua.goit.hw5.command;

import ua.goit.hw5.command.pet.*;
import ua.goit.hw5.command.store.StoreDeleteOrder;
import ua.goit.hw5.command.store.StoreFindOrderById;
import ua.goit.hw5.command.store.StoreGetInventory;
import ua.goit.hw5.command.store.StorePlaceOrder;
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
        view.write(String.format("Enter %s to add new pet", PetAddNewPet.ADD_NEW_PET));
        view.write(String.format("Enter %s to delete pet", PetDelete.DELETE));
        view.write(String.format("Enter %s to find pet by id", PetFindById.FIND_BY_ID));
        view.write(String.format("Enter %s to find pet by status", PetFindByStatus.FIND_BY_STATUS));
        view.write(String.format("Enter %s to update existing pet", PetUpdateExisting.UPDATE_EXISTING));
        view.write(String.format("Enter %s to update pet with form data", PetUpdateWithFormData.UPDATE_BY_FORM_DATA));
        view.write(String.format("Enter %s to upload image for pet", PetUploadImage.UPLOAD_IMAGE_OF_PET));
        view.write(String.format("Enter %s to place order for a pet", StorePlaceOrder.PLACE_ORDER));
        view.write(String.format("Enter %s to find order by orderId", StoreFindOrderById.FIND_ORDER_BY_ID));
        view.write(String.format("Enter %s to delete order by orderId", StoreDeleteOrder.DELETE_ORDER_BY_ID));
        view.write(String.format("Enter %s to get inventory", StoreGetInventory.GET_INVENTORY));
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
