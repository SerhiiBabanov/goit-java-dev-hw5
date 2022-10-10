package ua.goit.hw5.services;

import com.google.gson.Gson;
import ua.goit.hw5.model.ApiResponse;
import ua.goit.hw5.model.User;
import ua.goit.hw5.utils.HttpUtils;
import ua.goit.hw5.view.View;

import java.net.http.HttpResponse;
import java.util.List;



public class UserService {
    private final Gson gson = new Gson();
    private final HttpUtils httpUtils;

    public UserService(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }
    public User getUser(View view){
        User user = new User();
        user.setId(0L);
        view.write("Write username");
        user.setUsername(view.read());
        view.write("Write first name");
        user.setFirstName(view.read());
        view.write("Write last name");
        user.setLastName(view.read());
        view.write("Write email");
        user.setEmail(view.read());
        view.write("Write password");
        user.setPassword(view.read());
        view.write("Write phone");
        user.setPhone(view.read());
        view.write("Write user status");
        user.setUserStatus(Integer.valueOf(view.read()));
        return user;
    }
    public ApiResponse createWithArray(User[] users){
        String url = "/user/createWithArray";
        String body = gson.toJson(users);
        HttpResponse<String> response = httpUtils.post(url, body);
        return gson.fromJson(response.body(), ApiResponse.class);
    }

    public ApiResponse createWithList(List<User> users) {
        String url = "/user/createWithList";
        String body = gson.toJson(users);
        HttpResponse<String> response = httpUtils.post(url, body);
        return gson.fromJson(response.body(), ApiResponse.class);
    }
    public User getUserByUsername(String username){
        String url = "/user/" + username;
        HttpResponse<String> response = httpUtils.get(url);
        return gson.fromJson(response.body(), User.class);
    }

    public ApiResponse update(String username, User user){
        String url = "/user/" + username;
        String body = gson.toJson(user);
        HttpResponse<String> response = httpUtils.put(url, body);
        return gson.fromJson(response.body(), ApiResponse.class);
    }
    public ApiResponse delete(String username){
        String url = "/user/" + username;
        HttpResponse<String> response = httpUtils.delete(url);
        return gson.fromJson(response.body(), ApiResponse.class);
    }
    public ApiResponse login(String username, String password){
        String url = String.format("/user/login?username=%s&password=%s",username, password);
        HttpResponse<String> response = httpUtils.get(url);
        return gson.fromJson(response.body(), ApiResponse.class);
    }
    public ApiResponse logout(){
        String url = "/user/logout";
        HttpResponse<String> response = httpUtils.get(url);
        return gson.fromJson(response.body(), ApiResponse.class);
    }
    public ApiResponse create(User user){
        String url = "/user";
        String body = gson.toJson(user);
        HttpResponse<String> response = httpUtils.post(url, body);
        return gson.fromJson(response.body(), ApiResponse.class);
    }
}
