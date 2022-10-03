package ua.goit.hw5.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ua.goit.hw5.exeptions.OrderNotFoundException;
import ua.goit.hw5.model.ApiResponse;
import ua.goit.hw5.model.Order;
import ua.goit.hw5.model.OrderStatus;
import ua.goit.hw5.utils.HttpUtils;
import ua.goit.hw5.view.View;

import java.net.http.HttpResponse;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class StoreService {
    private final Gson gson = new Gson();
    private final HttpUtils httpUtils;

    public StoreService(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    public Order getOrder(View view) {
        Order order = new Order();
        boolean isMistakeInOrder;
        order.setId(0L);
        do {
            try {
                isMistakeInOrder = false;
                view.write("Write petId");
                order.setPetId(Long.valueOf(view.read()));
                view.write("Write quantity");
                order.setQuantity(Integer.valueOf(view.read()));
                view.write("Write date of shipping in format YYYY-MM-DD");
                LocalDateTime localDateTime = LocalDateTime.of(LocalDate.parse(view.read(),
                        DateTimeFormatter.ISO_LOCAL_DATE), LocalTime.of(0,0,0));
                order.setShipDate(localDateTime.toString());
                view.write("Write status of order -> placed, approved, delivered");
                order.setStatus(OrderStatus.valueOf(view.read()).toString());
                view.write("Order is complete? yes/not ");
                order.setComplete(view.read().equalsIgnoreCase("yes"));
            } catch (DateTimeException ex) {
                view.write("The date is in an illegal format");
                isMistakeInOrder = true;
            } catch (NumberFormatException ex) {
                view.write("PetId or quantity is in an illegal format");
                isMistakeInOrder = true;
            } catch (IllegalArgumentException ex) {
                view.write("Status of the order is in an illegal format");
                isMistakeInOrder = true;
            }
        } while (isMistakeInOrder);


        return order;
    }
    public Order placeOrderForPet(Order order){
        String url = "/store/order";
        String body = gson.toJson(order);
        HttpResponse<String> response = httpUtils.post(url, body);
        return gson.fromJson(response.body(), Order.class);
    }

    public Order findOrderById(Long id){
        String url = "/store/order/" + id;
        HttpResponse<String> response = httpUtils.get(url);
        if (response.statusCode()>299){
            throw new OrderNotFoundException(response.body());
        }
        return gson.fromJson(response.body(), Order.class);
    }
    public ApiResponse deleteOrderById(Long id){
        String url = "/store/order/" + id;
        HttpResponse<String> response = httpUtils.delete(url);
        return gson.fromJson(response.body(), ApiResponse.class);
    }

    public Map<String, Integer> getInventory(){
        String url = "/store/inventory";
        HttpResponse<String> response = httpUtils.get(url);
        return gson.fromJson(response.body(), new TypeToken<HashMap<String, Integer>>(){}.getType());
    }
}
