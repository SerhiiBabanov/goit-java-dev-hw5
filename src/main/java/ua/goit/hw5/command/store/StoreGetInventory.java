package ua.goit.hw5.command.store;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.services.StoreService;
import ua.goit.hw5.view.View;

import java.util.Map;

public class StoreGetInventory implements Command {
    public static final String GET_INVENTORY = "store -getInventory";
    private final StoreService storeService;
    private final View view;

    public StoreGetInventory(StoreService storeService, View view) {
        this.storeService = storeService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(GET_INVENTORY);
    }

    @Override
    public void execute(String input) {
        Map<String, Integer> inventory = storeService.getInventory();
        inventory.forEach((key, value) -> view.write(key + " " + value));
    }
}
