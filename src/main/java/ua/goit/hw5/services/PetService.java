package ua.goit.hw5.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ua.goit.hw5.exeptions.PetNotFoundException;
import ua.goit.hw5.model.*;
import ua.goit.hw5.utils.HttpUtils;
import ua.goit.hw5.view.View;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetService {
    private final Gson gson = new Gson();
    private final HttpUtils httpUtils;

    public PetService(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    public Pet getPet(View view) {
        Pet pet = new Pet();
        boolean isMistakeInOrder;
        do {
            try {
                isMistakeInOrder = false;
                view.write("Write pet id");
                pet.setId(Long.valueOf(view.read()));
                Category category = new Category();
                view.write("Write category id");
                category.setId(Long.valueOf(view.read()));
                view.write("Write category name");
                category.setName(view.read());
                pet.setCategory(category);
                view.write("Write pet name");
                pet.setName(view.read());
                view.write("How many photos do you want to upload?");
                int count = Integer.parseInt(view.read());
                String[] photoUrlArray = new String[count];
                view.write("Write " + count + " url, press Enter after each of them");
                for (int i = 0; i < count; i++) {
                    photoUrlArray[i] = view.read();
                }
                pet.setPhotoUrls(photoUrlArray);
                view.write("How many tags do you want to add to pet?");
                count = Integer.parseInt(view.read());
                List<Tag> tags = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Tag tag = new Tag();
                    view.write("Enter id for tag #" + i);
                    tag.setId(Long.valueOf(view.read()));
                    view.write("Enter name for tag #" + i);
                    tag.setName(view.read());
                    tags.add(tag);
                }
                pet.setTags(tags);
                view.write("Enter status for the pet -> available, pending, sold");
                pet.setStatus(PetStatus.valueOf(view.read()).toString());
            } catch (NumberFormatException ex) {
                isMistakeInOrder = true;
                view.write("Illegal number. Please try again");
            } catch (IllegalArgumentException ex) {
                isMistakeInOrder = true;
                view.write("Illegal pet status. Please, try again -> available, pending, sold");
            }

        } while (isMistakeInOrder);

        return pet;
    }
    public Pet addPetInStore(Pet pet){
        String url = "/pet";
        String body = gson.toJson(pet);
        HttpResponse<String> response = httpUtils.post(url, body);
        return gson.fromJson(response.body(), Pet.class);
    }

    public Pet updatePetInStore(Pet pet){
        String url = "/pet";
        String body = gson.toJson(pet);
        HttpResponse<String> response = httpUtils.put(url, body);
        if (response.statusCode()>299){
            throw new PetNotFoundException(response.body());
        }
        return gson.fromJson(response.body(), Pet.class);
    }

    public List<Pet> findPetInStoreByStatus(PetStatus status){
        String url = "/pet/findByStatus?status=" + status.toString();
        HttpResponse<String> response = httpUtils.get(url);
        if (response.statusCode()>299){
            throw new PetNotFoundException(response.body());
        }
        return gson.fromJson(response.body(), new TypeToken<List<Pet>>(){}.getType());
    }
    public Pet findPetInStoreById(Long petId){
        String url = "/pet/" + petId;
        HttpResponse<String> response = httpUtils.get(url);
        if (response.statusCode()>299){
            throw new PetNotFoundException(response.body());
        }
        return gson.fromJson(response.body(), Pet.class);
    }

    public ApiResponse updatePetInStoreByFormData(Long petId, String petName, PetStatus petStatus){
        String url = "/pet/" + petId;
        Map<String, String> formData = new HashMap<>();
        formData.put("name", petName);
        formData.put("status", petStatus.toString());
        String body = getFormDataAsString(formData);
        HttpResponse<String> response = httpUtils.postForm(url, body );
        return gson.fromJson(response.body(), ApiResponse.class);
    }
    public ApiResponse deletePetInStoreById(Long petId){
        String url = "/pet/" + petId;
        HttpResponse<String> response = httpUtils.delete(url);
        if (response.statusCode()>299){
            throw new PetNotFoundException(response.body());
        }
        return gson.fromJson(response.body(), ApiResponse.class);
    }
    public int uploadPetImage(Long petId, Path path){
        String url = "/pet/" + petId + "/uploadImage";
        try {
            return httpUtils.postFileApacheHTTPClient(url, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (formBodyBuilder.length() > 0) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }
}
