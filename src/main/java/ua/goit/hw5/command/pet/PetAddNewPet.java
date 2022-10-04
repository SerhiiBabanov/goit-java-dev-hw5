package ua.goit.hw5.command.pet;

import ua.goit.hw5.command.Command;
import ua.goit.hw5.model.Pet;
import ua.goit.hw5.services.PetService;
import ua.goit.hw5.view.View;

public class PetAddNewPet implements Command {
    public static final String ADD_NEW_PET = "pet -addNewPet";
    private final PetService petService;
    private final View view;

    public PetAddNewPet(PetService petService, View view) {
        this.petService = petService;
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equalsIgnoreCase(ADD_NEW_PET);
    }

    @Override
    public void execute(String input) {
        Pet pet = petService.getPet(view);
        Pet addedPet = petService.addPetInStore(pet);
        view.write(addedPet.toString());
    }
}
