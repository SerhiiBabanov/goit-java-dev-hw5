package ua.goit.hw5.command.store;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.model.ApiResponse;
import ua.goit.hw5.services.StoreService;
import ua.goit.hw5.view.View;

public class StoreDeleteOrder implements Command {
    public static final String DELETE_ORDER_BY_ID = "store -deleteOrderById";
    private final StoreService storeService;
    private final View view;

    public StoreDeleteOrder(StoreService storeService, View view) {
        this.storeService = storeService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(DELETE_ORDER_BY_ID);
    }

    @Override
    public void execute(String input) {
        view.write("Write order id");
        Long orderId = Long.valueOf(view.read());
        ApiResponse response = storeService.deleteOrderById(orderId);
        view.write(response.toString());
    }
}
