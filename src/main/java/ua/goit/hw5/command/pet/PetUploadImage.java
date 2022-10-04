package ua.goit.hw5.command.pet;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.services.PetService;
import ua.goit.hw5.view.View;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PetUploadImage implements Command {
    public static final String UPLOAD_IMAGE_OF_PET = "pet -uploadImage";
    private final PetService petService;
    private final View view;

    public PetUploadImage(PetService petService, View view) {
        this.petService = petService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(UPLOAD_IMAGE_OF_PET);
    }

    @Override
    public void execute(String input) {
        view.write("Write petId");
        Long petId = Long.valueOf(view.read());
        view.write("Write filename of image for upload");
        Path fileName;
        try {
            fileName = Paths.get(view.read());
            if (!new File(fileName.toUri()).isFile()){
                view.write("filepath not the file");
                throw new InvalidPathException(fileName.toString(), "filepath not the file");
            }
        } catch (InvalidPathException ex) {
            view.write("Invalid path to file. Command terminated");
            return;
        }
        int response = petService.uploadPetImage(petId, fileName);
        view.write("Response from server - status code " + response);
    }
}
