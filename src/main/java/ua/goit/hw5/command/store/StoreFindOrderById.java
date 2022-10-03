package ua.goit.hw5.command.store;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.exeptions.OrderNotFoundException;
import ua.goit.hw5.model.Order;
import ua.goit.hw5.services.StoreService;
import ua.goit.hw5.view.View;

import java.util.Objects;

public class StoreFindOrderById implements Command {
    public static final String FIND_ORDER_BY_ID = "store -findOrderById";
    private final StoreService storeService;
    private final View view;

    public StoreFindOrderById(StoreService storeService, View view) {
        this.storeService = storeService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(FIND_ORDER_BY_ID);
    }

    @Override
    public void execute(String input) {
        view.write("Write order id");
        Long orderId = Long.valueOf(view.read());
        Order order = null;
        try {
            order = storeService.findOrderById(orderId);
        } catch (OrderNotFoundException ex){
            view.write(ex.getMessage());
        }
        if (Objects.nonNull(order)) {
            view.write(order.toString());
        }
    }
}
