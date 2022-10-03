package ua.goit.hw5.command.store;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.model.Order;
import ua.goit.hw5.services.StoreService;
import ua.goit.hw5.view.View;

public class StorePlaceOrder implements Command {
    public static final String PLACE_ORDER = "store -placeOrder";
    private final StoreService storeService;
    private final View view;

    public StorePlaceOrder(StoreService storeService, View view) {
        this.storeService = storeService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(PLACE_ORDER);
    }

    @Override
    public void execute(String input) {
        Order order = storeService.getOrder(view);
        Order placedOrder = storeService.placeOrderForPet(order);
        view.write(placedOrder.toString());
    }
}
