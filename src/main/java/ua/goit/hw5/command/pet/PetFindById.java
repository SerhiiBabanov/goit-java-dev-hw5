package ua.goit.hw5.command.pet;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.exeptions.PetNotFoundException;
import ua.goit.hw5.model.Pet;
import ua.goit.hw5.services.PetService;
import ua.goit.hw5.view.View;

public class PetFindById implements Command {
    public static final String FIND_BY_ID = "pet -findById";
    private final PetService petService;
    private final View view;

    public PetFindById(PetService petService, View view) {
        this.petService = petService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(FIND_BY_ID);
    }

    @Override
    public void execute(String input) {
        view.write("Write pet id");
        Long petId = Long.valueOf(view.read());
        Pet pet;
        try {
            pet = petService.findPetInStoreById(petId);
        } catch (PetNotFoundException ex) {
            view.write(ex.getMessage());
            return;
        }
        view.write(pet.toString());
    }
}
