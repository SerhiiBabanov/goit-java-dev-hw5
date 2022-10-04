package ua.goit.hw5.command.pet;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.model.ApiResponse;
import ua.goit.hw5.services.PetService;
import ua.goit.hw5.view.View;

public class PetDelete implements Command {
    public static final String DELETE = "pet -delete";
    private final PetService petService;
    private final View view;

    public PetDelete(PetService petService, View view) {
        this.petService = petService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(DELETE);
    }

    @Override
    public void execute(String input) {
        view.write("Write pet id for delete");
        Long petId = Long.valueOf(view.read());
        ApiResponse response = petService.deletePetInStoreById(petId);
        view.write(response.toString());
    }
}
